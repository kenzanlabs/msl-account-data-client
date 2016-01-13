package com.kenzan.msl.account.client.translate;

import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountClientTranslatorsTest {

    TestConstants tc = TestConstants.getInstance();

    AlbumsByUserDao ALBUM_1 = tc.ALBUM_BY_USER_DAO;
    AlbumsByUserDao ALBUM_2 = tc.ALBUM_BY_USER_DAO;

    ArtistsByUserDao ARTIST_1 = tc.ARTIST_BY_USER_DAO;
    ArtistsByUserDao ARTIST_2 = tc.ARTIST_BY_USER_DAO;

    SongsByUserDao SONG_1 = tc.SONGS_BY_USER_DAO;
    SongsByUserDao SONG_2 = tc.SONGS_BY_USER_DAO;

    List<AlbumsByUserDao> albums = new ArrayList<>();
    List<ArtistsByUserDao> artists = new ArrayList<>();
    List<SongsByUserDao> songs = new ArrayList<>();

    @Before
    public void init() {
        albums.add(ALBUM_1);
        albums.add(ALBUM_2);
        albums.get(0).setAlbumName("Album1");
        albums.get(1).setAlbumName("Album2");

        artists.add(ARTIST_1);
        artists.add(ARTIST_2);
        artists.get(0).setArtistName("Artist1");
        artists.get(1).setArtistName("Artist2");

        songs.add(SONG_1);
        songs.add(SONG_2);
        songs.get(0).setSongName("Song1");
        songs.get(1).setSongName("Song2");
    }

    @Test
    public void testTranslateAlbumsByUserDao() {
        List<AlbumInfo> model = AccountClientTranslators.translateAlbumsByUserDao(albums);
        assertNotNull(model);
        assertEquals(2, model.size());
        assertEquals(ALBUM_1.getAlbumName(), model.get(0).getAlbumName());
        assertEquals(ALBUM_2.getAlbumName(), model.get(1).getAlbumName());
    }

    @Test(expected = NullPointerException.class)
    public void testTranslateAlbumsByUserDaoException() {
        AccountClientTranslators.translateAlbumsByUserDao(null);
    }

    @Test
    public void testTranslateArtistByUserDao() {
        List<ArtistInfo> model = AccountClientTranslators.translateArtistByUserDao(artists);
        assertNotNull(model);
        assertEquals(2, model.size());
        assertEquals(ARTIST_1.getArtistName(), model.get(0).getArtistName());
        assertEquals(ARTIST_1.getArtistName(), model.get(0).getArtistName());
        assertEquals(ARTIST_2.getArtistName(), model.get(1).getArtistName());
    }

    @Test(expected = NullPointerException.class)
    public void testTranslateArtistsByUserDaoException() {
        AccountClientTranslators.translateArtistByUserDao(null);
    }

    @Test
    public void testTranslateSongsByUserDao() {
        List<SongInfo> model = AccountClientTranslators.translateSongsByUserDao(songs);
        assertNotNull(model);
        assertEquals(2, model.size());
        assertEquals(SONG_1.getArtistName(), model.get(0).getArtistName());
        assertEquals(SONG_1.getSongName(), model.get(0).getSongName());
        assertEquals(SONG_2.getSongName(), model.get(1).getSongName());
    }

    @Test(expected = NullPointerException.class)
    public void testTranslateSongsByUserDaoException() {
        AccountClientTranslators.translateSongsByUserDao(null);
    }
}
