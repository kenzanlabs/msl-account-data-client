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
import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import com.kenzan.msl.account.client.dao.UserDao;
import org.easymock.EasyMock;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
    UserQuery.class,
    SongsByUserQuery.class,
    AlbumsByUserQuery.class,
    ArtistsByUserQuery.class,
    CassandraAccountService.class,
    Session.class,
    Cluster.class,
    MappingManager.class })
public class CassandraAccountServiceTest {

    private TestConstants tc = TestConstants.getInstance();
    private CassandraAccountService cassandraAccountService;
    private ResultSet resultSet;
    private Observable<ResultSet> observableResultSet;
    private MappingManager manager;

    @Before
    public void init()
        throws Exception {
        resultSet = createMock(ResultSet.class);
        observableResultSet = Observable.just(resultSet);

        Session session = PowerMock.createMock(Session.class);
        Cluster cluster = PowerMock.createMock(Cluster.class);
        Cluster.Builder builder = PowerMock.createMock(Cluster.Builder.class);

        PowerMock.mockStatic(Cluster.class);
        EasyMock.expect(Cluster.builder()).andReturn(builder);
        EasyMock.expect(builder.addContactPoint(EasyMock.anyString())).andReturn(builder);
        EasyMock.expect(builder.build()).andReturn(cluster);
        EasyMock.expect(cluster.connect(EasyMock.anyString())).andReturn(session);

        manager = PowerMockito.mock(MappingManager.class);
        PowerMockito.whenNew(MappingManager.class).withAnyArguments().thenReturn(manager);
        Mapper myMapper = PowerMockito.mock(Mapper.class);
        PowerMockito.when(manager.mapper(SongsByUserDao.class)).thenReturn(myMapper);
        PowerMockito.when(manager.mapper(AlbumsByUserDao.class)).thenReturn(myMapper);
        PowerMockito.when(manager.mapper(ArtistsByUserDao.class)).thenReturn(myMapper);
        PowerMockito.when(myMapper.map(resultSet)).thenReturn(null);

        PowerMockito.mockStatic(UserQuery.class);
        PowerMockito.mockStatic(SongsByUserQuery.class);
        PowerMockito.mockStatic(AlbumsByUserQuery.class);
        PowerMockito.mockStatic(ArtistsByUserQuery.class);
    }

    @Test
    public void testAddOrUpdateUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.addOrUpdateUser(tc.USER_DAO);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    @Test
    public void testGetUser() {
        PowerMockito.when(UserQuery.get(Mockito.any(QueryAccessor.class), Mockito.any(MappingManager.class),
                                        Mockito.eq(tc.USERNAME))).thenReturn(tc.USER_DAO);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<UserDao> results = cassandraAccountService.getUser(tc.USERNAME);
        assertEquals(results.toBlocking().first(), tc.USER_DAO);
    }

    @Test
    public void testDeleteUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.deleteUser(tc.USERNAME);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    /*
     * ========================================================================================
     * SONGS BY USER
     * ========================================================================================
     */

    @Test
    public void testAddOrUpdateSongsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.addOrUpdateSongsByUser(tc.SONGS_BY_USER_DAO);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    @Test
    public void testGetSongsByUser() {
        PowerMockito.when(SongsByUserQuery.getUserSong(Mockito.any(QueryAccessor.class),
                                                       Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
                                                       Mockito.eq(tc.TIMESTAMP.toString()), Mockito.eq(tc.SONG_ID)))
            .thenReturn(tc.SONGS_BY_USER_DAO);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<SongsByUserDao> results = cassandraAccountService
            .getSongsByUser(tc.USER_ID, tc.TIMESTAMP.toString(), tc.SONG_ID);
        assertEquals(results.toBlocking().first(), tc.SONGS_BY_USER_DAO);
    }

    @Test
    public void testGetSongsByUserWithLimit() {
        PowerMockito.when(SongsByUserQuery.getUserSongList(Mockito.any(QueryAccessor.class), Mockito.eq(tc.USER_ID),
                                                           Mockito.eq(Optional.of(tc.TIMESTAMP.toString())),
                                                           Mockito.eq(Optional.of(tc.LIMIT)))).thenReturn(resultSet);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<ResultSet> results = cassandraAccountService.getSongsByUser(tc.USER_ID,
                                                                               Optional.of(tc.TIMESTAMP.toString()),
                                                                               Optional.of(tc.LIMIT));
        assertEquals(results.toBlocking().first(), resultSet);
    }

    @Test
    public void testMapSongsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Result<SongsByUserDao>> results = cassandraAccountService.mapSongsByUser(Observable.just(resultSet));
        assertNull(results.toBlocking().first());
    }

    @Test
    public void testDeleteSongByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.deleteSongsByUser(tc.USER_ID, tc.TIMESTAMP, tc.SONG_ID);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    /*
     * ==============================================================================================
     * ALBUMS BY USER
     * ================================================================================
     */

    @Test
    public void testAddOrUpdateAlbumsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.addOrUpdateAlbumsByUser(tc.ALBUM_BY_USER_DAO);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    @Test
    public void testGetAlbumsByUser() {
        PowerMockito.when(AlbumsByUserQuery.getUserAlbum(Mockito.any(QueryAccessor.class),
                                                         Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
                                                         Mockito.eq(tc.TIMESTAMP.toString()), Mockito.eq(tc.ALBUM_ID)))
            .thenReturn(tc.ALBUM_BY_USER_DAO);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<AlbumsByUserDao> results = cassandraAccountService.getAlbumsByUser(tc.USER_ID,
                                                                                      tc.TIMESTAMP.toString(),
                                                                                      tc.ALBUM_ID);
        assertEquals(results.toBlocking().first(), tc.ALBUM_BY_USER_DAO);
    }

    @Test
    public void testGetAlbumsByUserWithLimit() {
        PowerMockito.when(AlbumsByUserQuery.getUserAlbumList(Mockito.any(QueryAccessor.class), Mockito.eq(tc.USER_ID),
                                                             Mockito.eq(Optional.of(tc.TIMESTAMP.toString())),
                                                             Mockito.eq(Optional.of(tc.LIMIT)))).thenReturn(resultSet);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<ResultSet> results = cassandraAccountService.getAlbumsByUser(tc.USER_ID,
                                                                                Optional.of(tc.TIMESTAMP.toString()),
                                                                                Optional.of(tc.LIMIT));
        assertEquals(results.toBlocking().first(), resultSet);
    }

    @Test
    public void testMapAlbumsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Result<AlbumsByUserDao>> results = cassandraAccountService.mapAlbumsByUser(Observable
            .just(resultSet));
        assertNull(results.toBlocking().first());
    }

    @Test
    public void testDeleteAlbumsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.deleteAlbumsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ALBUM_ID);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    /*
     * ==============================================================================================
     * ARTISTS BY USER
     * ==============================================================================
     */

    @Test
    public void testAddOrUpdateArtistsByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.addOrUpdateArtistsByUser(tc.ARTIST_BY_USER_DAO);
        assertTrue(results.isEmpty().toBlocking().first());
    }

    @Test
    public void testGetArtistsByUser() {
        PowerMockito.when(ArtistsByUserQuery.getUserArtist(Mockito.any(QueryAccessor.class),
                                                           Mockito.any(MappingManager.class), Mockito.eq(tc.USER_ID),
                                                           Mockito.eq(tc.TIMESTAMP.toString()),
                                                           Mockito.eq(tc.ARTIST_ID))).thenReturn(tc.ARTIST_BY_USER_DAO);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<ArtistsByUserDao> results = cassandraAccountService.getArtistsByUser(tc.USER_ID,
                                                                                        tc.TIMESTAMP.toString(),
                                                                                        tc.ARTIST_ID);
        assertEquals(results.toBlocking().first(), tc.ARTIST_BY_USER_DAO);
    }

    @Test
    public void testGetArtistsByUserWithLimit() {
        PowerMockito.when(ArtistsByUserQuery.getUserArtistList(Mockito.any(QueryAccessor.class),
                                                               Mockito.eq(tc.USER_ID),
                                                               Mockito.eq(Optional.of(tc.TIMESTAMP.toString())),
                                                               Mockito.eq(Optional.of(tc.LIMIT))))
            .thenReturn(resultSet);
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<ResultSet> results = cassandraAccountService.getArtistsByUser(tc.USER_ID,
                                                                                 Optional.of(tc.TIMESTAMP.toString()),
                                                                                 Optional.of(tc.LIMIT));
        assertEquals(results.toBlocking().first(), resultSet);
    }

    @Test
    public void testMapArtistByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Result<ArtistsByUserDao>> results = cassandraAccountService.mapArtistByUser(Observable
            .just(resultSet));
        assertNull(results.toBlocking().first());
    }

    @Test
    public void testDeleteArtistByUser() {
        PowerMock.replayAll();
        cassandraAccountService = CassandraAccountService.getInstance();
        Observable<Void> results = cassandraAccountService.deleteArtistsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ARTIST_ID);
        assertTrue(results.isEmpty().toBlocking().first());
    }

}
