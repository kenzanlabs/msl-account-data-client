package com.kenzan.msl.account.client;

import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import com.kenzan.msl.account.client.dao.UserDao;

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

    public AlbumsByUserDao ALBUM_BY_USER_DAO = new AlbumsByUserDao();
    public ArtistsByUserDao ARTIST_BY_USER_DAO = new ArtistsByUserDao();
    public SongsByUserDao SONGS_BY_USER_DAO = new SongsByUserDao();
    public UserDao USER_DAO = new UserDao();

    private TestConstants() {
        ALBUM_BY_USER_DAO = new AlbumsByUserDao();
        ALBUM_BY_USER_DAO.setUserId(USER_ID);
        ALBUM_BY_USER_DAO.setArtistName("ArtistName");
        ALBUM_BY_USER_DAO.setAlbumId(ALBUM_ID);
        ALBUM_BY_USER_DAO.setArtistId(ARTIST_ID);
        ALBUM_BY_USER_DAO.setAlbumName("albumName");
        ALBUM_BY_USER_DAO.setAlbumYear(1988);
        ALBUM_BY_USER_DAO.setArtistMbid(ARTIST_MBID);
        ALBUM_BY_USER_DAO.setContentType("Album");
        ALBUM_BY_USER_DAO.setFavoritesTimestamp(TIMESTAMP);
        ALBUM_BY_USER_DAO.setImageLink(IMAGE_LINK);

        ARTIST_BY_USER_DAO.setFavoritesTimestamp(TIMESTAMP);
        ARTIST_BY_USER_DAO.setContentType("Artist");
        ARTIST_BY_USER_DAO.setArtistMbid(ARTIST_MBID);
        ARTIST_BY_USER_DAO.setArtistId(ARTIST_ID);
        ARTIST_BY_USER_DAO.setArtistName("ArtistName");
        ARTIST_BY_USER_DAO.setUserId(USER_ID);
        ARTIST_BY_USER_DAO.setImageLink(IMAGE_LINK);

        SONGS_BY_USER_DAO.setContentType("Song");
        SONGS_BY_USER_DAO.setSongDuration(100);
        SONGS_BY_USER_DAO.setSongId(SONG_ID);
        SONGS_BY_USER_DAO.setSongName("SongName");
        SONGS_BY_USER_DAO.setUserId(USER_ID);
        SONGS_BY_USER_DAO.setArtistMbid(ARTIST_MBID);
        SONGS_BY_USER_DAO.setArtistId(ARTIST_ID);
        SONGS_BY_USER_DAO.setArtistName("ArtistName");
        SONGS_BY_USER_DAO.setFavoritesTimestamp(TIMESTAMP);
        SONGS_BY_USER_DAO.setAlbumId(ALBUM_ID);
        SONGS_BY_USER_DAO.setAlbumYear(1988);
        SONGS_BY_USER_DAO.setAlbumName("AlbumName");
        SONGS_BY_USER_DAO.setImageLink(IMAGE_LINK);

        USER_DAO.setUserId(USER_ID);
        USER_DAO.setCreationTimestamp(TIMESTAMP);
        USER_DAO.setPassword("password");
        USER_DAO.setUsername(USERNAME);
    }

    public static TestConstants getInstance() {
        if ( instance == null ) {
            instance = new TestConstants();
        }
        return instance;
    }
}