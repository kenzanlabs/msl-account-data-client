package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Optional;

import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;

public class AlbumsByUserQueryTest {

  private TestConstants tc = TestConstants.getInstance();
  private long LONG_TIMESTAMP = tc.TIMESTAMP.getTime();

  @Mock
  private QueryAccessor queryAccessor;
  @Mock
  private MappingManager mappingManager;

  @Before
  public void init() {
    queryAccessor = mock(QueryAccessor.class);
    when(queryAccessor.albumsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ALBUM_ID)).thenThrow(
        new RuntimeException("TEST_EXPECTED_EXCEPTION"));
  }

  @Test(expected = RuntimeException.class)
  public void testGetAlbumsByUser() {
    AlbumsByUserQuery.getUserAlbum(queryAccessor, mappingManager, tc.USER_ID,
        Long.toString(LONG_TIMESTAMP), tc.ALBUM_ID);
  }

  @Test
  public void testGetAlbumListByUserWithTimeStampAndLimit() {
    AlbumsByUserQuery.getUserAlbumList(queryAccessor, tc.USER_ID,
        Optional.of(Long.toString(LONG_TIMESTAMP)), Optional.of(tc.LIMIT));
    verify(queryAccessor, atLeastOnce()).albumsByUser(tc.USER_ID, tc.TIMESTAMP, tc.LIMIT);
  }

  @Test
  public void testGetAlbumListByUserWithLimit() {
    AlbumsByUserQuery.getUserAlbumList(queryAccessor, tc.USER_ID, Optional.absent(),
        Optional.of(tc.LIMIT));
    verify(queryAccessor, atLeastOnce()).albumsByUser(tc.USER_ID, tc.LIMIT);
  }

  @Test
  public void testGetAlbumListByUserWithTimestamp() {
    AlbumsByUserQuery.getUserAlbumList(queryAccessor, tc.USER_ID,
        Optional.of(Long.toString(LONG_TIMESTAMP)), Optional.absent());
    verify(queryAccessor, atLeastOnce()).albumsByUser(tc.USER_ID, tc.TIMESTAMP);
  }

  @Test
  public void tesGetAlbumListByUser() {
    AlbumsByUserQuery.getUserAlbumList(queryAccessor, tc.USER_ID, Optional.absent(),
        Optional.absent());
    verify(queryAccessor, atLeastOnce()).albumsByUser(tc.USER_ID);
  }

  @Test
  public void testAddAlbumByUser() {
    AlbumsByUserQuery.add(queryAccessor, tc.ALBUM_BY_USER_DTO);
    verify(queryAccessor, atLeastOnce()).addLibraryAlbum(eq(tc.ALBUM_BY_USER_DTO.getUserId()),
        eq(tc.ALBUM_BY_USER_DTO.getContentType()), any(Date.class),
        eq(tc.ALBUM_BY_USER_DTO.getAlbumId()), eq(tc.ALBUM_BY_USER_DTO.getAlbumName()),
        eq(tc.ALBUM_BY_USER_DTO.getAlbumYear()), eq(tc.ALBUM_BY_USER_DTO.getArtistId()),
        eq(tc.ALBUM_BY_USER_DTO.getArtistMbid()), eq(tc.ALBUM_BY_USER_DTO.getArtistName()),
        eq(tc.ALBUM_BY_USER_DTO.getImageLink()));
  }

  @Test(expected = Exception.class)
  public void testAddAlbumByUserException() {
    AlbumsByUserQuery.add(null, tc.ALBUM_BY_USER_DTO);
  }

  @Test
  public void testRemoveAlbumByUser() {
    AlbumsByUserQuery.remove(queryAccessor, tc.USER_ID, tc.TIMESTAMP, tc.ALBUM_ID);
    verify(queryAccessor, atLeastOnce()).deleteLibraryAlbum(tc.ALBUM_ID, tc.TIMESTAMP, tc.USER_ID);
  }

  @Test(expected = Exception.class)
  public void testRemoveAlbumByUserException() {
    AlbumsByUserQuery.remove(null, tc.USER_ID, tc.TIMESTAMP, tc.ALBUM_ID);
  }
}
