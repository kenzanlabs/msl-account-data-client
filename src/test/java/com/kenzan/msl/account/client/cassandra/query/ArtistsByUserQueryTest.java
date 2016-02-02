package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Optional;

import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArtistsByUserQueryTest {

    private TestConstants tc = TestConstants.getInstance();
    private long LONG_TIMESTAMP = tc.TIMESTAMP.getTime();

    @Mock
    private QueryAccessor queryAccessor;
    @Mock
    private MappingManager mappingManager;

    @Before
    public void init() {
        queryAccessor = Mockito.mock(QueryAccessor.class);
        when(queryAccessor.artistsByUser(tc.USER_ID, tc.TIMESTAMP, tc.ARTIST_ID))
                .thenThrow(new RuntimeException("TEST_EXPECTED_EXCEPTION"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetArtistsByUser() {
        ArtistsByUserQuery.getUserArtist(queryAccessor, mappingManager, tc.USER_ID, Long.toString(LONG_TIMESTAMP),
                tc.ARTIST_ID);
    }

    @Test
    public void testGetArtistListByUser() {
        ArtistsByUserQuery.getUserArtistList(queryAccessor, tc.USER_ID, Optional.absent(), Optional.absent());
        verify(queryAccessor, atLeastOnce()).artistsByUser(tc.USER_ID);
    }

    @Test
    public void testGetArtistListByUserWithLimitAndTimestamp() {
        ArtistsByUserQuery.getUserArtistList(queryAccessor, tc.USER_ID, Optional.of(Long.toString(LONG_TIMESTAMP)),
                Optional.of(tc.LIMIT));
        verify(queryAccessor, atLeastOnce()).artistsByUser(tc.USER_ID, tc.TIMESTAMP, tc.LIMIT);
    }

    @Test
    public void testGetArtistListByUserWithTimestamp() {
        ArtistsByUserQuery.getUserArtistList(queryAccessor, tc.USER_ID, Optional.of(Long.toString(LONG_TIMESTAMP)),
                Optional.absent());
        verify(queryAccessor, atLeastOnce()).artistsByUser(tc.USER_ID, tc.TIMESTAMP);
    }

    @Test
    public void testGetArtistListByUserWithLimit() {
        ArtistsByUserQuery.getUserArtistList(queryAccessor, tc.USER_ID, Optional.absent(), Optional.of(tc.LIMIT));
        verify(queryAccessor, atLeastOnce()).artistsByUser(tc.USER_ID, tc.LIMIT);
    }

    @Test
    public void testAddArtistByUser() {
        ArtistsByUserQuery.add(queryAccessor, tc.ARTIST_BY_USER_DTO);
        verify(queryAccessor, atLeastOnce()).addLibraryArtist(eq(tc.ARTIST_BY_USER_DTO.getUserId()),
                eq(tc.ARTIST_BY_USER_DTO.getContentType()),
                any(Date.class), eq(tc.ARTIST_BY_USER_DTO.getArtistId()),
                eq(tc.ARTIST_BY_USER_DTO.getArtistMbid()),
                eq(tc.ARTIST_BY_USER_DTO.getArtistName()),
                eq(tc.ARTIST_BY_USER_DTO.getImageLink()));
    }

    @Test(expected = Exception.class)
    public void testAddArtistByUserException() {
        ArtistsByUserQuery.add(null, tc.ARTIST_BY_USER_DTO);
    }

    @Test
    public void testRemoveArtistByUser() {
        ArtistsByUserQuery.remove(queryAccessor, tc.USER_ID, tc.TIMESTAMP, tc.ARTIST_ID);
        verify(queryAccessor, atLeastOnce()).deleteLibraryArtist(tc.ARTIST_ID, tc.TIMESTAMP, tc.USER_ID);
    }

    @Test(expected = Exception.class)
    public void testRemoveArtistByUserException() {
        ArtistsByUserQuery.remove(null, tc.USER_ID, tc.TIMESTAMP, tc.ARTIST_ID);
    }
}
