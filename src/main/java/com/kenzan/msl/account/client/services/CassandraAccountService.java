/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package com.kenzan.msl.account.client.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.cassandra.CassandraConstants;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.cassandra.query.AlbumsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.ArtistsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.SongsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.UserQuery;
import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import com.kenzan.msl.account.client.dao.UserDao;
import rx.Observable;

import java.util.Date;
import java.util.UUID;

/**
 * Implementation of the AccountService interface that retrieves its data from a Cassandra cluster.
 */
public class CassandraAccountService
    implements AccountService {

    private QueryAccessor queryAccessor;
    private MappingManager mappingManager;

    private static CassandraAccountService instance = null;

    private CassandraAccountService() {
        // TODO: Get the contact point from config param
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // TODO: Get the keyspace from config param
        Session session = cluster.connect(CassandraConstants.MSL_KEYSPACE);

        mappingManager = new MappingManager(session);
        queryAccessor = mappingManager.createAccessor(QueryAccessor.class);
    }

    public static CassandraAccountService getInstance() {
        if ( instance == null ) {
            instance = new CassandraAccountService();
        }
        return instance;
    }

    /*
     * ==============================================================================================
     * USER
     * ==========================================================================================
     */
    /**
     * Adds or update a user
     *
     * @param user UserDao
     */
    public Observable<Void> addOrUpdateUser(UserDao user) {
        UserQuery.add(queryAccessor, user);
        return Observable.empty();
    }

    /**
     * Retrieves a specific user by its userId
     *
     * @param username String
     * @return UserDao
     */
    public Observable<UserDao> getUser(String username) {
        Optional<UserDao> results = UserQuery.get(queryAccessor, mappingManager, username);
        if ( results.isPresent() ) {
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
     * ========================================================================================
     * SONGS BY USER
     * ========================================================================================
     */

    /**
     * Adds a song to a given userId library
     *
     * @param song SongsByUserDao
     */
    public Observable<Void> addOrUpdateSongsByUser(SongsByUserDao song) {
        SongsByUserQuery.add(queryAccessor, song);
        return Observable.empty();
    }

    /**
     * Retrieves a song from a user library given a userId, songId and a timestamp
     *
     * @param userId java.util.UUID
     * @param timestamp String
     * @param songUuid java.util.UUID
     * @return SongsByUserDao
     */
    public Observable<SongsByUserDao> getSongsByUser(UUID userId, String timestamp, UUID songUuid) {
        Optional<SongsByUserDao> results = SongsByUserQuery.getUserSong(queryAccessor, mappingManager, userId,
                                                                        timestamp, songUuid);
        if ( results.isPresent() ) {
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
     * @return com.datastax.driver.core.ResultSet
     */
    public Observable<ResultSet> getSongsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit) {
        return Observable.just(SongsByUserQuery.getUserSongList(queryAccessor, userId, timestamp, limit));
    }

    /**
     * Maps a resultSet object into a SongsByUser result array
     *
     * @param object com.datastax.driver.core.ResultSet
     * @return Observable<Result<SongsByUserDao>>
     */
    public Observable<Result<SongsByUserDao>> mapSongsByUser(Observable<ResultSet> object) {
        return Observable.just(mappingManager.mapper(SongsByUserDao.class).map(object.toBlocking().first()));
    }

    /**
     * Removes a given song from a user library given a userId, the songId and the song timestamp
     *
     * @param userId userId java.util.UUID
     * @param timestamp String
     * @param songUuid userId java.util.UUID
     */
    public Observable<Void> deleteSongsByUser(UUID userId, Date timestamp, UUID songUuid) {
        SongsByUserQuery.remove(queryAccessor, userId, timestamp, songUuid);
        return Observable.empty();
    }

    /*
     * ==============================================================================================
     * ALBUMS BY USER
     * ================================================================================
     */

    /**
     * Adds an album to a given userId library
     *
     * @param album AlbumsByUserDao
     */
    public Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDao album) {
        AlbumsByUserQuery.add(queryAccessor, album);
        return Observable.empty();
    }

    /**
     * Retrieves a album from a user library given a userId, albumId and a timestamp
     *
     * @param userId java.util.UUID
     * @param timestamp String
     * @param albumUuid java.util.UUID
     * @return AlbumsByUserDao
     */
    public Observable<AlbumsByUserDao> getAlbumsByUser(UUID userId, String timestamp, UUID albumUuid) {
        Optional<AlbumsByUserDao> results = AlbumsByUserQuery.getUserAlbum(queryAccessor, mappingManager, userId,
                                                                           timestamp, albumUuid);
        if ( results.isPresent() ) {
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
     * @return com.datastax.driver.core.ResultSet
     */
    public Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit) {
        return Observable.just(AlbumsByUserQuery.getUserAlbumList(queryAccessor, userId, timestamp, limit));
    }

    /**
     * Maps a resultSet object into a albumsByUserDao result array
     *
     * @param object com.datastax.driver.core.ResultSet
     * @return Observable<Result<AlbumsByUserDao>>
     */
    public Observable<Result<AlbumsByUserDao>> mapAlbumsByUser(Observable<ResultSet> object) {
        return Observable.just(mappingManager.mapper(AlbumsByUserDao.class).map(object.toBlocking().first()));
    }

    /**
     * Removes a given album from a user library given a userId, the albumId and the album timestamp
     *
     * @param userId userId java.util.UUID
     * @param timestamp String
     * @param albumUuid userId java.util.UUID
     */
    public Observable<Void> deleteAlbumsByUser(UUID userId, Date timestamp, UUID albumUuid) {
        AlbumsByUserQuery.remove(queryAccessor, userId, timestamp, albumUuid);
        return Observable.empty();
    }

    /*
     * ==============================================================================================
     * ARTISTS BY USER
     * ==============================================================================
     */

    /**
     * Adds an artist to a given userId library
     *
     * @param artist ArtistsByUserDao
     */
    public Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDao artist) {
        ArtistsByUserQuery.add(queryAccessor, artist);
        return Observable.empty();
    }

    /**
     * Retrieves a artist from a user library given a userId, artistId and a timestamp
     *
     * @param userId java.util.UUID
     * @param timestamp String
     * @param artistUuid java.util.UUID
     * @return ArtistsByUserDao
     */
    public Observable<ArtistsByUserDao> getArtistsByUser(UUID userId, String timestamp, UUID artistUuid) {
        Optional<ArtistsByUserDao> results = ArtistsByUserQuery.getUserArtist(queryAccessor, mappingManager, userId,
                                                                              timestamp, artistUuid);
        if ( results.isPresent() ) {
            return Observable.just(results.get());
        }
        return Observable.empty();
    }

    /**
     * Retrieves a resultSet of artists from a user library given a userId and optionally a
     * timestamp and limit of results
     *
     * @param userId java.util.UUID
     * @param timestamp String
     * @param limit Integer
     * @return com.datastax.driver.core.ResultSet
     */
    public Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit) {
        return Observable.just(ArtistsByUserQuery.getUserArtistList(queryAccessor, userId, timestamp, limit));
    }

    /**
     * Maps a resultSet object into a artistsByUserDao result array
     *
     * @param object com.datastax.driver.core.ResultSet
     * @return Observable<Result<ArtistsByUserDao>>
     */
    public Observable<Result<ArtistsByUserDao>> mapArtistByUser(Observable<ResultSet> object) {
        return Observable.just(mappingManager.mapper(ArtistsByUserDao.class).map(object.toBlocking().first()));
    }

    /**
     * Removes a given artist from a user library given a userId, the artistId and the artist
     * timestamp
     *
     * @param userId java.util.UUID
     * @param timestamp String
     * @param artistUuid java.util.UUID
     */
    public Observable<Void> deleteArtistsByUser(UUID userId, Date timestamp, UUID artistUuid) {
        ArtistsByUserQuery.remove(queryAccessor, userId, timestamp, artistUuid);
        return Observable.empty();
    }
}
