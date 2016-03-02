package com.kenzan.msl.account.client.translate;

import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
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

  AlbumsByUserDto ALBUM_1 = tc.ALBUM_BY_USER_DTO;
  AlbumsByUserDto ALBUM_2 = tc.ALBUM_BY_USER_DTO;

  ArtistsByUserDto ARTIST_1 = tc.ARTIST_BY_USER_DTO;
  ArtistsByUserDto ARTIST_2 = tc.ARTIST_BY_USER_DTO;

  SongsByUserDto SONG_1 = tc.SONGS_BY_USER_DTO;
  SongsByUserDto SONG_2 = tc.SONGS_BY_USER_DTO;

  List<AlbumsByUserDto> albums = new ArrayList<>();
  List<ArtistsByUserDto> artists = new ArrayList<>();
  List<SongsByUserDto> songs = new ArrayList<>();

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
  public void testTranslateAlbumsByUserDto() {
    List<AlbumInfo> model = AccountClientTranslators.translateAlbumsByUserDto(albums);
    assertNotNull(model);
    assertEquals(2, model.size());
    assertEquals(ALBUM_1.getAlbumName(), model.get(0).getAlbumName());
    assertEquals(ALBUM_2.getAlbumName(), model.get(1).getAlbumName());
  }

  @Test(expected = NullPointerException.class)
  public void testTranslateAlbumsByUserDtoException() {
    AccountClientTranslators.translateAlbumsByUserDto(null);
  }

  @Test
  public void testTranslateArtistByUserDto() {
    List<ArtistInfo> model = AccountClientTranslators.translateArtistByUserDto(artists);
    assertNotNull(model);
    assertEquals(2, model.size());
    assertEquals(ARTIST_1.getArtistName(), model.get(0).getArtistName());
    assertEquals(ARTIST_1.getArtistName(), model.get(0).getArtistName());
    assertEquals(ARTIST_2.getArtistName(), model.get(1).getArtistName());
  }

  @Test(expected = NullPointerException.class)
  public void testTranslateArtistsByUserDtoException() {
    AccountClientTranslators.translateArtistByUserDto(null);
  }

  @Test
  public void testTranslateSongsByUserDto() {
    List<SongInfo> model = AccountClientTranslators.translateSongsByUserDto(songs);
    assertNotNull(model);
    assertEquals(2, model.size());
    assertEquals(SONG_1.getArtistName(), model.get(0).getArtistName());
    assertEquals(SONG_1.getSongName(), model.get(0).getSongName());
    assertEquals(SONG_2.getSongName(), model.get(1).getSongName());
  }

  @Test(expected = NullPointerException.class)
  public void testTranslateSongsByUserDtoException() {
    AccountClientTranslators.translateSongsByUserDto(null);
  }
}
