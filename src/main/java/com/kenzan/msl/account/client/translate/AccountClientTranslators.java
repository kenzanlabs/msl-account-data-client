/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.translate;

import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;

import java.util.ArrayList;
import java.util.List;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

public class AccountClientTranslators {

    // ==========================================================================================================
    // ALBUMS
    // ==========================================================================================================

    public static List<AlbumInfo> translateAlbumsByUserDto(Iterable<AlbumsByUserDto> input) {
        List<AlbumInfo> output = new ArrayList<>();
        for ( AlbumsByUserDto dto : input ) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setYear(dto.getAlbumYear());
            albumInfo.setArtistName(dto.getArtistName());
            albumInfo.setAlbumName(dto.getAlbumName());
            albumInfo.setAlbumId(dto.getAlbumId() == null ? null : dto.getAlbumId().toString());
            albumInfo.setArtistId(dto.getArtistId() == null ? null : dto.getArtistId().toString());
            albumInfo.setArtistMbid(dto.getArtistMbid() == null ? null : dto.getArtistMbid().toString());
            albumInfo.setFavoritesTimestamp(dto.getFavoritesTimestamp() == null ? null : String.valueOf(dto
                .getFavoritesTimestamp().getTime()));
            albumInfo.setImageLink(dto.getImageLink());
            output.add(albumInfo);
        }
        return output;
    }

    // =========================================================================================================
    // ARTISTS
    // =========================================================================================================

    public static List<ArtistInfo> translateArtistByUserDto(Iterable<ArtistsByUserDto> input) {
        List<ArtistInfo> output = new ArrayList<>();

        for ( ArtistsByUserDto dto : input ) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(dto.getArtistName());
            artistInfo.setArtistId(dto.getArtistId() == null ? null : dto.getArtistId().toString());
            artistInfo.setArtistMbid(dto.getArtistMbid() == null ? null : dto.getArtistMbid().toString());
            artistInfo.setFavoritesTimestamp(dto.getFavoritesTimestamp() == null ? null : String.valueOf(dto
                .getFavoritesTimestamp().getTime()));
            artistInfo.setImageLink(dto.getImageLink());
            output.add(artistInfo);
        }
        return output;
    }

    // ===========================================================================================================
    // SONGS
    // ===========================================================================================================

    public static List<SongInfo> translateSongsByUserDto(Iterable<SongsByUserDto> input) {
        List<SongInfo> output = new ArrayList<>();
        for ( SongsByUserDto dto : input ) {
            SongInfo songInfo = new SongInfo();
            songInfo.setArtistName(dto.getArtistName());
            songInfo.setAlbumName(dto.getAlbumName());
            songInfo.setSongName(dto.getSongName());
            songInfo.setDuration(dto.getSongDuration());
            songInfo.setYear(dto.getAlbumYear());
            songInfo.setSongId(dto.getSongId() == null ? null : dto.getSongId().toString());
            songInfo.setAlbumId(dto.getAlbumId() == null ? null : dto.getAlbumId().toString());
            songInfo.setArtistId(dto.getArtistId() == null ? null : dto.getArtistId().toString());
            songInfo.setArtistMbid(dto.getArtistMbid() == null ? null : dto.getArtistMbid().toString());
            songInfo.setFavoritesTimestamp(dto.getFavoritesTimestamp() == null ? null : String.valueOf(dto
                .getFavoritesTimestamp().getTime()));
            songInfo.setImageLink(dto.getImageLink());
            output.add(songInfo);
        }
        return output;
    }
}
