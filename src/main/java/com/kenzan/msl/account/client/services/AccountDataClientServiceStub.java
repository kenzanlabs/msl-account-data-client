package com.kenzan.msl.account.client.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.dto.UserDto;
import rx.Observable;

import java.util.Date;
import java.util.UUID;

/**
 * @author Kenzan
 */
public class AccountDataClientServiceStub implements AccountDataClientService {

  public QueryAccessor getQueryAccessor () {
    return null;
  }

  public MappingManager getMappingManager () {
    return null;
  }

  public Observable<Void> addOrUpdateUser(UserDto user) {
    return Observable.empty();
  }

  public Observable<UserDto> getUserByUsername(String username) {
    return Observable.empty();
  }

  public Observable<UserDto> getUserByUUID(UUID id) {
    return Observable.empty();
  }

  public Observable<Void> deleteUser(String username) {
    return Observable.empty();
  }

  /* SONGS BY USER ============================================ */
  /* ========================================================== */

  public Observable<Void> addOrUpdateSongsByUser(SongsByUserDto song) {
    return Observable.empty();
  }

  public Observable<SongsByUserDto> getSongsByUser(UUID userId, String timestamp, UUID songUuid) {
    return Observable.empty();
  }

  public Observable<ResultSet> getSongsByUser(UUID userId, Optional<String> timestamp,
                                              Optional<Integer> limit) {
    return Observable.empty();
  }

  public Observable<Result<SongsByUserDto>> mapSongsByUser(Observable<ResultSet> object) {
    return Observable.empty();
  }

  public Observable<Void> deleteSongsByUser(UUID userId, Date timestamp, UUID songUuid) {
    return Observable.empty();
  }

  /* ALBUMS BY USER =========================================== */
  /* ========================================================== */

  public Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDto album) {
    return Observable.empty();
  }

  public Observable<AlbumsByUserDto> getAlbumsByUser(UUID userId, String favoritesTimestamp, UUID albumUuid) {
    return Observable.empty();
  }

  public Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> timestamp,
                                               Optional<Integer> limit) {
    return Observable.empty();
  }

  public Observable<Result<AlbumsByUserDto>> mapAlbumsByUser(Observable<ResultSet> object) {
    return Observable.empty();
  }

  public Observable<Void> deleteAlbumsByUser(UUID userId, Date timestamp, UUID albumUuid) {
    return Observable.empty();
  }

  /* ARTISTS BY USER ========================================== */
  /* ========================================================== */

  public Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDto artist) {
    return Observable.empty();
  }

  public Observable<ArtistsByUserDto> getArtistsByUser(UUID userId, String favoritesTimestamp,
                                                       UUID artistUuid) {
    return Observable.empty();
  }

  public Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> timestamp,
                                                Optional<Integer> limit) {
    return Observable.empty();
  }

  public Observable<Result<ArtistsByUserDto>> mapArtistByUser(Observable<ResultSet> object) {
    return Observable.empty();
  }

  public Observable<Void> deleteArtistsByUser(UUID userId, Date timestamp, UUID artistUuid) {
    return Observable.empty();
  }
}
