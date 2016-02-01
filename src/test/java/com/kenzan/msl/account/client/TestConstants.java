package com.kenzan.msl.account.client;

import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.dto.UserDto;

import java.util.Date;
import java.util.UUID;

public class TestConstants {

    private static TestConstants instance = null;

    public final UUID SONG_ID = UUID.fromString("ec217f7e-01f4-415b-b8ff-0b3b020e485f");
    public final UUID ARTIST_ID = UUID.fromString("ec217f7e-01f4-415b-b8ff-0b3b020e485f");
    public final UUID ARTIST_MBID = UUID.fromString("ec217f7e-01f4-415b-b8ff-0b3b020e485f");
    public final UUID USER_ID = UUID.fromString("ec217f7e-01f4-415b-b8ff-0b3b020e485f");
    public final UUID ALBUM_ID = UUID.fromString("389f9181-99f9-4377-9114-c63b53245355");
    public final Date TIMESTAMP = new Date();
    public final int LIMIT = 5;
    public final String USERNAME = "username";
    public final String IMAGE_LINK = "http://www.somelink.com/image.png";

    public AlbumsByUserDto ALBUM_BY_USER_DTO = new AlbumsByUserDto();
    public ArtistsByUserDto ARTIST_BY_USER_DTO = new ArtistsByUserDto();
    public SongsByUserDto SONGS_BY_USER_DTO = new SongsByUserDto();
    public UserDto USER_DTO = new UserDto();

    private TestConstants() {
        ALBUM_BY_USER_DTO = new AlbumsByUserDto();
        ALBUM_BY_USER_DTO.setUserId(USER_ID);
        ALBUM_BY_USER_DTO.setArtistName("ArtistName");
        ALBUM_BY_USER_DTO.setAlbumId(ALBUM_ID);
        ALBUM_BY_USER_DTO.setArtistId(ARTIST_ID);
        ALBUM_BY_USER_DTO.setAlbumName("albumName");
        ALBUM_BY_USER_DTO.setAlbumYear(1988);
        ALBUM_BY_USER_DTO.setArtistMbid(ARTIST_MBID);
        ALBUM_BY_USER_DTO.setContentType("Album");
        ALBUM_BY_USER_DTO.setFavoritesTimestamp(TIMESTAMP);
        ALBUM_BY_USER_DTO.setImageLink(IMAGE_LINK);

        ARTIST_BY_USER_DTO.setFavoritesTimestamp(TIMESTAMP);
        ARTIST_BY_USER_DTO.setContentType("Artist");
        ARTIST_BY_USER_DTO.setArtistMbid(ARTIST_MBID);
        ARTIST_BY_USER_DTO.setArtistId(ARTIST_ID);
        ARTIST_BY_USER_DTO.setArtistName("ArtistName");
        ARTIST_BY_USER_DTO.setUserId(USER_ID);
        ARTIST_BY_USER_DTO.setImageLink(IMAGE_LINK);

        SONGS_BY_USER_DTO.setContentType("Song");
        SONGS_BY_USER_DTO.setSongDuration(100);
        SONGS_BY_USER_DTO.setSongId(SONG_ID);
        SONGS_BY_USER_DTO.setSongName("SongName");
        SONGS_BY_USER_DTO.setUserId(USER_ID);
        SONGS_BY_USER_DTO.setArtistMbid(ARTIST_MBID);
        SONGS_BY_USER_DTO.setArtistId(ARTIST_ID);
        SONGS_BY_USER_DTO.setArtistName("ArtistName");
        SONGS_BY_USER_DTO.setFavoritesTimestamp(TIMESTAMP);
        SONGS_BY_USER_DTO.setAlbumId(ALBUM_ID);
        SONGS_BY_USER_DTO.setAlbumYear(1988);
        SONGS_BY_USER_DTO.setAlbumName("AlbumName");
        SONGS_BY_USER_DTO.setImageLink(IMAGE_LINK);

        USER_DTO.setUserId(USER_ID);
        USER_DTO.setCreationTimestamp(TIMESTAMP);
        USER_DTO.setPassword("password");
        USER_DTO.setUsername(USERNAME);
    }

    public static TestConstants getInstance() {
        if ( instance == null ) {
            instance = new TestConstants();
        }
        return instance;
    }
}