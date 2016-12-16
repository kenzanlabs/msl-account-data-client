AccountDataClientServiceImplTest

package com.kenzan.msl.account.client.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.cassandra.query.AlbumsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.ArtistsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.SongsByUserQuery;
import com.kenzan.msl.account.client.cassandra.query.UserQuery;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserQuery.class, SongsByUserQuery.class, AlbumsByUserQuery.class,
    ArtistsByUserQuery.class, AccountDataClientServiceImpl.class, Session.class, Cluster.class,
    MappingManager.class})
public class AccountDataClientServiceImplTest {

  private TestConstants tc = TestConstants.getInstance();
  private AccountDataClientServiceImpl accountDataClientService;
  private ResultSet resultSet;
  private MappingManager manager;

  @Before
  public void init() throws Exception {
    resultSet = createMock(ResultSet.class);
    manager = PowerMockito.mock(MappingManager.class);

    Mapper<SongsByUserDto> mySongsByUserMapper = PowerMockito.mock(Mapper.class);
    PowerMockito.when(manager.mapper(SongsByUserDto.class)).thenReturn(mySongsByUserMapper);
    PowerMockito.when(mySongsByUserMapper.map(resultSet)).thenReturn(null);

    Mapper<AlbumsByUserDto> myAlbumsByUserMapper = PowerMockito.mock(Mapper.class);
    PowerMockito.when(manager.mapper(AlbumsByUserDto.class)).thenReturn(myAlbumsByUserMapper);
    PowerMockito.when(myAlbumsByUserMapper.map(resultSet)).thenReturn(null);

    Mapper<ArtistsByUserDto> myArtistsByUserMapper = PowerMockito.mock(Mapper.class);
    PowerMockito.when(manager.mapper(ArtistsByUserDto.class)).thenReturn(myArtistsByUserMapper);
    PowerMockito.when(myArtistsByUserMapper.map(resultSet)).thenReturn(null);

    PowerMockito.mockStatic(UserQuery.class);
    PowerMockito.mockStatic(SongsByUserQuery.class);
    PowerMockito.mockStatic(AlbumsByUserQuery.class);
    PowerMockito.mockStatic(ArtistsByUserQuery.class);

    accountDataClientService = new AccountDataClientServiceImpl(manager);
  }

  @Test
  public void testAddOrUpdateUser() {
    PowerMock.replayAll();
    Observable<Void> results = accountDataClientService.addOrUpdateUser(tc.USER_DTO);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  @Test
  public void testGetUser() {
    PowerMockito.when(
        UserQuery.get(Mockito.any(QueryAccessor.class), Mockito.any(MappingManager.class),
            Mockito.eq(tc.USERNAME))).thenReturn(Optional.of(tc.USER_DTO));
    PowerMock.replayAll();
    Observable<UserDto> results = accountDataClientService.getUserByUsername(tc.USERNAME);
    assertEquals(results.toBlocking().first(), tc.USER_DTO);
  }

  @Test
  public void testDeleteUser() {
    PowerMock.replayAll();
    Observable<Void> results = accountDataClientService.deleteUser(tc.USERNAME);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  /*
   * ======================================================================================== SONGS
   * BY USER
   * ========================================================================================
   */

  @Test
  public void testAddOrUpdateSongsByUser() {
    PowerMock.replayAll();
    Observable<Void> results = accountDataClientService.addOrUpdateSongsByUser(tc.SONGS_BY_USER_DTO);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  @Test
  public void testGetSongsByUser() {
    PowerMockito.when(
        SongsByUserQuery.getUserSong(Mockito.any(QueryAccessor.class),
            Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
            Mockito.eq(tc.TIMESTAMP.toString()), Mockito.eq(tc.SONG_ID))).thenReturn(
        Optional.of(tc.SONGS_BY_USER_DTO));
    PowerMock.replayAll();
    Observable<SongsByUserDto> results =
        accountDataClientService.getSongsByUser(tc.USER_ID, tc.TIMESTAMP.toString(), tc.SONG_ID);
    assertEquals(results.toBlocking().first(), tc.SONGS_BY_USER_DTO);
  }

  @Test
  public void testGetSongsByUserWithLimit() {
    PowerMockito.when(
        SongsByUserQuery.getUserSongList(Mockito.any(QueryAccessor.class), Mockito.eq(tc.USER_ID),
            Mockito.eq(Optional.of(tc.TIMESTAMP.toString())), Mockito.eq(Optional.of(tc.LIMIT))))
        .thenReturn(resultSet);
    PowerMock.replayAll();
    Observable<ResultSet> results =
        accountDataClientService.getSongsByUser(tc.USER_ID, Optional.of(tc.TIMESTAMP.toString()),
            Optional.of(tc.LIMIT));
    assertEquals(results.toBlocking().first(), resultSet);
  }

  @Test
  public void testMapSongsByUser() {
    PowerMock.replayAll();
    Observable<Result<SongsByUserDto>> results =
        accountDataClientService.mapSongsByUser(Observable.just(resultSet));
    assertNull(results.toBlocking().first());
  }

  @Test
  public void testDeleteSongByUser() {
    PowerMock.replayAll();
    Observable<Void> results =
        accountDataClientService.deleteSongsByUser(tc.USER_ID, tc.TIMESTAMP, tc.SONG_ID);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  /*
   * ==============================================================================================
   * ALBUMS BY USER ================================================================================
   */

  @Test
  public void testAddOrUpdateAlbumsByUser() {
    PowerMock.replayAll();
    Observable<Void> results =
        accountDataClientService.addOrUpdateAlbumsByUser(tc.ALBUM_BY_USER_DTO);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  @Test
  public void testGetAlbumsByUser() {
    PowerMockito.when(
        AlbumsByUserQuery.getUserAlbum(Mockito.any(QueryAccessor.class),
            Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
            Mockito.eq(tc.TIMESTAMP.toString()), Mockito.eq(tc.ALBUM_ID))).thenReturn(
        Optional.of(tc.ALBUM_BY_USER_DTO));
    PowerMock.replayAll();
    Observable<AlbumsByUserDto> results =
        accountDataClientService.getAlbumsByUser(tc.USER_ID, tc.TIMESTAMP.toString(), tc.ALBUM_ID);
    assertEquals(results.toBlocking().first(), tc.ALBUM_BY_USER_DTO);
  }

  @Test
  public void testGetAlbumsByUserWithLimit() {
    PowerMockito.when(
        AlbumsByUserQuery.getUserAlbumList(Mockito.any(QueryAccessor.class),
            Mockito.eq(tc.USER_ID), Mockito.eq(Optional.of(tc.TIMESTAMP.toString())),
            Mockito.eq(Optional.of(tc.LIMIT)))).thenReturn(resultSet);
    PowerMock.replayAll();
    Observable<ResultSet> results =
        accountDataClientService.getAlbumsByUser(tc.USER_ID, Optional.of(tc.TIMESTAMP.toString()),
            Optional.of(tc.LIMIT));
    assertEquals(results.toBlocking().first(), resultSet);
  }

  @Test
  public void testMapAlbumsByUser() {
    PowerMock.replayAll();
    Observable<Result<AlbumsByUserDto>> results =
        accountDataClientService.mapAlbumsByUser(Observable.just(resultSet));
    assertNull(results.toBlocking().first());
  }

  @Test
  public void testDeleteAlbumsByUser() {
    PowerMock.replayAll();
    Observable<Void> results =
        accountDataClientService.deleteAlbumsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ALBUM_ID);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  /*
   * ==============================================================================================
   * ARTISTS BY USER ==============================================================================
   */

  @Test
  public void testAddOrUpdateArtistsByUser() {
    PowerMock.replayAll();
    Observable<Void> results =
        accountDataClientService.addOrUpdateArtistsByUser(tc.ARTIST_BY_USER_DTO);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  @Test
  public void testGetArtistsByUser() {
    PowerMockito.when(
        ArtistsByUserQuery.getUserArtist(Mockito.any(QueryAccessor.class),
            Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
            Mockito.eq(tc.TIMESTAMP.toString()), Mockito.eq(tc.ARTIST_ID))).thenReturn(
        Optional.of(tc.ARTIST_BY_USER_DTO));
    PowerMock.replayAll();
    Observable<ArtistsByUserDto> results =
        accountDataClientService.getArtistsByUser(tc.USER_ID, tc.TIMESTAMP.toString(), tc.ARTIST_ID);
    assertEquals(results.toBlocking().first(), tc.ARTIST_BY_USER_DTO);
  }

  @Test
  public void testGetArtistsByUserWithLimit() {
    PowerMockito.when(
        ArtistsByUserQuery.getUserArtistList(Mockito.any(QueryAccessor.class),
            Mockito.eq(tc.USER_ID), Mockito.eq(Optional.of(tc.TIMESTAMP.toString())),
            Mockito.eq(Optional.of(tc.LIMIT)))).thenReturn(resultSet);
    PowerMock.replayAll();
    Observable<ResultSet> results =
        accountDataClientService.getArtistsByUser(tc.USER_ID, Optional.of(tc.TIMESTAMP.toString()),
            Optional.of(tc.LIMIT));
    assertEquals(results.toBlocking().first(), resultSet);
  }

  @Test
  public void testMapArtistByUser() {
    PowerMock.replayAll();
    Observable<Result<ArtistsByUserDto>> results =
        accountDataClientService.mapArtistByUser(Observable.just(resultSet));
    assertNull(results.toBlocking().first());
  }

  @Test
  public void testDeleteArtistByUser() {
    PowerMock.replayAll();
    Observable<Void> results =
        accountDataClientService.deleteArtistsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ARTIST_ID);
    assertTrue(results.isEmpty().toBlocking().first());
  }

  @Test
  public void testHashPassword() {
    PowerMock.replayAll();
    String password = "password";
    String hashed = accountDataClientService.hashPassword(password);
    assertNotSame(password, hashed);
  }

}
