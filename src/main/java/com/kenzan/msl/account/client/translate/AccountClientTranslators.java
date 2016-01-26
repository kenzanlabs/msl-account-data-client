/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.translate;

import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;

import java.util.ArrayList;
import java.util.List;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

public class AccountClientTranslators {

    // ==========================================================================================================
    // ALBUMS
    // ==========================================================================================================

    public static List<AlbumInfo> translateAlbumsByUserDao(Iterable<AlbumsByUserDao> input) {
        List<AlbumInfo> output = new ArrayList<>();
        for ( AlbumsByUserDao dao : input ) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setYear(dao.getAlbumYear());
            albumInfo.setArtistName(dao.getArtistName());
            albumInfo.setAlbumName(dao.getAlbumName());
            albumInfo.setAlbumId(dao.getAlbumId() == null ? null : dao.getAlbumId().toString());
            albumInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            albumInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            albumInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(albumInfo);
        }
        return output;
    }

    // =========================================================================================================
    // ARTISTS
    // =========================================================================================================

    public static List<ArtistInfo> translateArtistByUserDao(Iterable<ArtistsByUserDao> input) {
        List<ArtistInfo> output = new ArrayList<>();

        for ( ArtistsByUserDao dao : input ) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(dao.getArtistName());
            artistInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            artistInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            artistInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(artistInfo);
        }
        return output;
    }

    // ===========================================================================================================
    // SONGS
    // ===========================================================================================================

    public static List<SongInfo> translateSongsByUserDao(Iterable<SongsByUserDao> input) {
        List<SongInfo> output = new ArrayList<>();
        for ( SongsByUserDao dao : input ) {
            SongInfo songInfo = new SongInfo();
            songInfo.setArtistName(dao.getArtistName());
            songInfo.setAlbumName(dao.getAlbumName());
            songInfo.setSongName(dao.getSongName());
            songInfo.setDuration(dao.getSongDuration());
            songInfo.setYear(dao.getAlbumYear());
            songInfo.setSongId(dao.getSongId() == null ? null : dao.getSongId().toString());
            songInfo.setAlbumId(dao.getAlbumId() == null ? null : dao.getAlbumId().toString());
            songInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            songInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            songInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(songInfo);
        }
        return output;
    }
}
