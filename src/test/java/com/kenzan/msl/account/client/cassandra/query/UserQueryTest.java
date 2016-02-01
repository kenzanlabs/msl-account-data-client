package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.mapping.MappingManager;

import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class UserQueryTest {

    private TestConstants tc = TestConstants.getInstance();

    @Mock
    private QueryAccessor queryAccessor;
    @Mock
    private MappingManager mappingManager;

    @Before
    public void init() {
        queryAccessor = mock(QueryAccessor.class);
        when(queryAccessor.getUser(tc.USERNAME)).thenThrow(new RuntimeException("TEST_EXPECTED_EXCEPTION"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetSongListByUser() {
        UserQuery.get(queryAccessor, mappingManager, tc.USERNAME);
        verify(queryAccessor, atLeastOnce()).getUser(tc.USERNAME);
    }

    @Test
    public void testAddUser() {
        UserQuery.add(queryAccessor, tc.USER_DTO);
        verify(queryAccessor, atLeastOnce()).addUser(eq(tc.USER_DTO.getUsername()),
                                                     eq(tc.USER_DTO.getCreationTimestamp()),
                                                     eq(tc.USER_DTO.getPassword()), eq(tc.USER_DTO.getUserId()));
    }

    @Test
    public void testRemoveUser() {
        UserQuery.remove(queryAccessor, tc.USERNAME);
        verify(queryAccessor, atLeastOnce()).deleteUser(tc.USERNAME);
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveUserException() {
        UserQuery.remove(null, tc.USERNAME);
    }
}