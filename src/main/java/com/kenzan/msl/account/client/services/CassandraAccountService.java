/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.services;

import com.kenzan.msl.account.client.archaius.ArchaiusHelper;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.cassandra.query.AlbumsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.ArtistsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.SongsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.UserQuery;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.dto.UserDto;
import rx.Observable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Implementation of the AccountService interface that retrieves its data from a Cassandra cluster.
 */
public class CassandraAccountService implements AccountService {

  private QueryAccessor queryAccessor;
  private MappingManager mappingManager;

  private static final String DEFAULT_CONTACT_POINT = "127.0.0.1";
  private static final String DEFAULT_MSL_KEYSPACE = "msl";
  private static final String DEFAULT_MSL_REGION = "us-west-2";

  private static CassandraAccountService instance = null;

  private CassandraAccountService(HashMap<String, Optional<String>> archaiusProperties) {
    ArchaiusHelper.setupArchaius();

    DynamicPropertyFactory propertyFactory = DynamicPropertyFactory.getInstance();
    DynamicStringProperty contactPoint =
        propertyFactory.getStringProperty("contact_point", DEFAULT_CONTACT_POINT);

    DynamicStringProperty keyspace =
        propertyFactory.getStringProperty("keyspace", DEFAULT_MSL_KEYSPACE);

    DynamicStringProperty region =
            propertyFactory.getStringProperty("region", DEFAULT_MSL_REGION);

    String region2 = "" , domainName = "";

    for (Map.Entry<String, Optional<String>> entry : archaiusProperties.entrySet()) {
      if (entry.getValue().isPresent()) {
        switch(entry.getKey()) {
          case "region":
            region2 = entry.getValue().get();
            break;
          case "domainName":
            domainName = entry.getValue().get();
            break;
        }
      }
    }

    Cluster cluster = Cluster.builder().addContactPoint(domainName.isEmpty() ? contactPoint.getValue() : domainName).build();
    Session session = cluster.connect(keyspace.getValue());

    mappingManager = new MappingManager(session);
    queryAccessor = mappingManager.createAccessor(QueryAccessor.class);
  }

  public static CassandraAccountService getInstance(HashMap<String, Optional<String>> archaiusProperties) {
    if (instance == null) {
      instance = new CassandraAccountService(archaiusProperties);
    }
    return instance;
  }

  public static CassandraAccountService getInstance() {
    HashMap<String, Optional<String>> archaiusProperties = new HashMap<>();
    return getInstance(archaiusProperties);
  }

  /*
   * ==============================================================================================
   * USER ==========================================================================================
   */
  /**
   * Adds or update a user
   *
   * @param user UserDto
   */
  public Observable<Void> addOrUpdateUser(UserDto user) {
    UserQuery.add(queryAccessor, user);
    return Observable.empty();
  }

  /**
   * Retrieves a specific user by its userId
   *
   * @param username String
   * @return UserDto
   */
  public Observable<UserDto> getUserByUsername(String username) {
    Optional<UserDto> results = UserQuery.get(queryAccessor, mappingManager, username);
    if (results.isPresent()) {
      return Observable.just(results.get());
    }
    return Observable.empty();
  }

  public Observable<UserDto> getUserByUUID(UUID id) {
    Optional<UserDto> results = UserQuery.get(queryAccessor, mappingManager, id);
    if (results.isPresent()) {
      return Observable.just(results.get());
    }
    return Observable.empty();
  }

  /**
   * Deletes a user by its userId
   *
   * @param username String
   */
  public Observable<Void> deleteUser(String username) {
    UserQuery.remove(queryAccessor, username);
    return Observable.empty();
  }

  /*
   * 
   * ======================================================================================== SONGS
   * BY USER
   * ========================================================================================
   */

  /**
   * Adds a song to a given userId library
   *
   * @param song SongsByUserDto
   */
  public Observable<Void> addOrUpdateSongsByUser(SongsByUserDto song) {
    SongsByUserQuery.add(queryAccessor, song);
    return Observable.empty();
  }

  /**
   * Retrieves a song from a user library given a userId, songId and a timestamp
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param songUuid java.util.UUID
   * @return Observable&lt;SongsByUserDto&gt;
   */
  public Observable<SongsByUserDto> getSongsByUser(UUID userId, String timestamp, UUID songUuid) {
    Optional<SongsByUserDto> results =
        SongsByUserQuery.getUserSong(queryAccessor, mappingManager, userId, timestamp, songUuid);
    if (results.isPresent()) {
      return Observable.just(results.get());
    }
    return Observable.empty();
  }

  /**
   * Retrieves a resultSet of songs from a user library given a userId and optionally a timestamp
   * and limit of results
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param limit Integer
   * @return Observable&lt;ResultSet&gt;
   */
  public Observable<ResultSet> getSongsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit) {
    return Observable.just(SongsByUserQuery
        .getUserSongList(queryAccessor, userId, timestamp, limit));
  }

  /**
   * Maps a resultSet object into a SongsByUser result array
   *
   * @param object com.datastax.driver.core.ResultSet
   * @return Observable&lt;Result&lt;SongsByUserDto&gt;&gt;
   */
  public Observable<Result<SongsByUserDto>> mapSongsByUser(Observable<ResultSet> object) {
    return Observable.just(mappingManager.mapper(SongsByUserDto.class).map(
        object.toBlocking().first()));
  }

  /**
   * Removes a given song from a user library given a userId, the songId and the song timestamp
   *
   * @param userId userId java.util.UUID
   * @param timestamp String
   * @param songUuid userId java.util.UUID
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> deleteSongsByUser(UUID userId, Date timestamp, UUID songUuid) {
    SongsByUserQuery.remove(queryAccessor, userId, timestamp, songUuid);
    return Observable.empty();
  }

  /*
   * ==============================================================================================
   * ALBUMS BY USER ================================================================================
   */

  /**
   * Adds an album to a given userId library
   *
   * @param album AlbumsByUserDto
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDto album) {
    AlbumsByUserQuery.add(queryAccessor, album);
    return Observable.empty();
  }

  /**
   * Retrieves a album from a user library given a userId, albumId and a timestamp
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param albumUuid java.util.UUID
   * @return Observable&lt;AlbumsByUserDto&gt;
   */
  public Observable<AlbumsByUserDto> getAlbumsByUser(UUID userId, String timestamp, UUID albumUuid) {
    Optional<AlbumsByUserDto> results =
        AlbumsByUserQuery.getUserAlbum(queryAccessor, mappingManager, userId, timestamp, albumUuid);
    if (results.isPresent()) {
      return Observable.just(results.get());
    }
    return Observable.empty();
  }

  /**
   * Retrieves a resultSet of albums from a user library given a userId and optionally a timestamp
   * and limit of results
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param limit Integer
   * @return Observable&lt;ResultSet&gt;
   */
  public Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit) {
    return Observable.just(AlbumsByUserQuery.getUserAlbumList(queryAccessor, userId, timestamp,
        limit));
  }

  /**
   * Maps a resultSet object into a albumsByUserDto result array
   *
   * @param object com.datastax.driver.core.ResultSet
   * @return Observable&lt;Result&lt;AlbumsByUserDto&gt;&gt;
   */
  public Observable<Result<AlbumsByUserDto>> mapAlbumsByUser(Observable<ResultSet> object) {
    return Observable.just(mappingManager.mapper(AlbumsByUserDto.class).map(
        object.toBlocking().first()));
  }

  /**
   * Removes a given album from a user library given a userId, the albumId and the album timestamp
   *
   * @param userId userId java.util.UUID
   * @param timestamp String
   * @param albumUuid userId java.util.UUID
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> deleteAlbumsByUser(UUID userId, Date timestamp, UUID albumUuid) {
    AlbumsByUserQuery.remove(queryAccessor, userId, timestamp, albumUuid);
    return Observable.empty();
  }

  /*
   * ==============================================================================================
   * ARTISTS BY USER ==============================================================================
   */

  /**
   * Adds an artist to a given userId library
   *
   * @param artist ArtistsByUserDto
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDto artist) {
    ArtistsByUserQuery.add(queryAccessor, artist);
    return Observable.empty();
  }

  /**
   * Retrieves a artist from a user library given a userId, artistId and a timestamp
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param artistUuid java.util.UUID
   * @return Observable&lt;ArtistsByUserDto&gt;
   */
  public Observable<ArtistsByUserDto> getArtistsByUser(UUID userId, String timestamp,
      UUID artistUuid) {
    Optional<ArtistsByUserDto> results =
        ArtistsByUserQuery.getUserArtist(queryAccessor, mappingManager, userId, timestamp,
            artistUuid);
    if (results.isPresent()) {
      return Observable.just(results.get());
    }
    return Observable.empty();
  }

  /**
   * Retrieves a resultSet of artists from a user library given a userId and optionally a timestamp
   * and limit of results
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param limit Integer
   * @return Observable&lt;ResultSet&gt;
   */
  public Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit) {
    return Observable.just(ArtistsByUserQuery.getUserArtistList(queryAccessor, userId, timestamp,
        limit));
  }

  /**
   * Maps a resultSet object into a artistsByUserDto result array
   *
   * @param object com.datastax.driver.core.ResultSet
   * @return Observable&lt;Result&lt;ArtistsByUserDto&gt;&gt;
   */
  public Observable<Result<ArtistsByUserDto>> mapArtistByUser(Observable<ResultSet> object) {
    return Observable.just(mappingManager.mapper(ArtistsByUserDto.class).map(
        object.toBlocking().first()));
  }

  /**
   * Removes a given artist from a user library given a userId, the artistId and the artist
   * timestamp
   *
   * @param userId java.util.UUID
   * @param timestamp String
   * @param artistUuid java.util.UUID
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> deleteArtistsByUser(UUID userId, Date timestamp, UUID artistUuid) {
    ArtistsByUserQuery.remove(queryAccessor, userId, timestamp, artistUuid);
    return Observable.empty();
  }
}
