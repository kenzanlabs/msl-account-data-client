package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dao.UserDao;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;

public class UserQuery {

    /**
     * Adds a user
     *
     * @param queryAccessor QueryAccessor
     * @param user String
     */
    public static void add(final QueryAccessor queryAccessor, final UserDao user) {
        queryAccessor.addUser(user.getUsername(), user.getCreationTimestamp(), user.getPassword(), user.getUserId());
    }

    /**
     * Retrieves a user by username
     *
     * @param queryAccessor QueryAccessor
     * @param username String
     * @return UserDao
     */
    public static Optional<UserDao> get(final QueryAccessor queryAccessor, final MappingManager manager,
                                        final String username) {
        Result<UserDao> results = manager.mapper(UserDao.class).map(queryAccessor.getUser(username));
        if ( results != null ) {
            return Optional.of(results.one());
        }
        else {
            return Optional.absent();
        }
    }

    /**
     * Deletes a user by username
     *
     * @param queryAccessor QueryAccessor
     * @param username String
     */
    public static void remove(final QueryAccessor queryAccessor, final String username) {
        queryAccessor.deleteUser(username);
    }

}
