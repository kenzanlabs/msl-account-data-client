/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import rx.Observable;

import java.util.Date;
import java.util.UUID;

public interface AccountService {

  Observable<Void> addOrUpdateUser(UserDto user);

  Observable<UserDto> getUserByUsername(String username);

  Observable<UserDto> getUserByUUID(UUID id);

  Observable<Void> deleteUser(String username);

  /* SONGS BY USER ============================================ */
  /* ========================================================== */

  Observable<Void> addOrUpdateSongsByUser(SongsByUserDto song);

  Observable<SongsByUserDto> getSongsByUser(UUID userId, String timestamp, UUID songUuid);

  Observable<ResultSet> getSongsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit);

  Observable<Result<SongsByUserDto>> mapSongsByUser(Observable<ResultSet> object);

  Observable<Void> deleteSongsByUser(UUID userId, Date timestamp, UUID songUuid);

  /* ALBUMS BY USER =========================================== */
  /* ========================================================== */

  Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDto album);

  Observable<AlbumsByUserDto> getAlbumsByUser(UUID userId, String favoritesTimestamp, UUID albumUuid);

  Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit);

  Observable<Result<AlbumsByUserDto>> mapAlbumsByUser(Observable<ResultSet> object);

  Observable<Void> deleteAlbumsByUser(UUID userId, Date timestamp, UUID albumUuid);

  /* ARTISTS BY USER ========================================== */
  /* ========================================================== */

  Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDto artist);

  Observable<ArtistsByUserDto> getArtistsByUser(UUID userId, String favoritesTimestamp,
      UUID artistUuid);

  Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> timestamp,
      Optional<Integer> limit);

  Observable<Result<ArtistsByUserDto>> mapArtistByUser(Observable<ResultSet> object);

  Observable<Void> deleteArtistsByUser(UUID userId, Date timestamp, UUID artistUuid);
}
