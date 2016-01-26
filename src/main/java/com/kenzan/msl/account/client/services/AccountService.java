/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.UserDao;
import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import rx.Observable;

import java.util.Date;
import java.util.UUID;

public interface AccountService {

    Observable<Void> addOrUpdateUser(UserDao user);

    Observable<UserDao> getUser(String username);

    Observable<Void> deleteUser(String username);

    /* SONGS BY USER ============================================ */
    /* ========================================================== */

    Observable<Void> addOrUpdateSongsByUser(SongsByUserDao song);

    Observable<SongsByUserDao> getSongsByUser(UUID userId, String timestamp, UUID songUuid);

    Observable<ResultSet> getSongsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit);

    Observable<Result<SongsByUserDao>> mapSongsByUser(Observable<ResultSet> object);

    Observable<Void> deleteSongsByUser(UUID userId, Date timestamp, UUID songUuid);

    /* ALBUMS BY USER =========================================== */
    /* ========================================================== */

    Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDao album);

    Observable<AlbumsByUserDao> getAlbumsByUser(UUID userId, String favoritesTimestamp, UUID albumUuid);

    Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit);

    Observable<Result<AlbumsByUserDao>> mapAlbumsByUser(Observable<ResultSet> object);

    Observable<Void> deleteAlbumsByUser(UUID userId, Date timestamp, UUID albumUuid);

    /* ARTISTS BY USER ========================================== */
    /* ========================================================== */

    Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDao artist);

    Observable<ArtistsByUserDao> getArtistsByUser(UUID userId, String favoritesTimestamp, UUID artistUuid);

    Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> timestamp, Optional<Integer> limit);

    Observable<Result<ArtistsByUserDao>> mapArtistByUser(Observable<ResultSet> object);

    Observable<Void> deleteArtistsByUser(UUID userId, Date timestamp, UUID artistUuid);
}
