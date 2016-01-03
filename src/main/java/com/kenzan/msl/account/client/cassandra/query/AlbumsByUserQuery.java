/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.dao.AlbumsByUserDao;

import javax.management.RuntimeErrorException;
import java.util.Date;
import java.util.UUID;

public class AlbumsByUserQuery {

    /**
     * Retrieves a album from a user library given a userId, albumId and a timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param manager com.datastax.driver.mapping.MappingManager
     * @param userId java.util.UUID
     * @param timestamp String
     * @param albumUuid java.util.UUID
     * @return AlbumsByUserDao
     */
    public static AlbumsByUserDao get(final QueryAccessor queryAccessor, final MappingManager manager,
                                      final UUID userId, final String timestamp, final UUID albumUuid) {
        Result<AlbumsByUserDao> results = manager.mapper(AlbumsByUserDao.class)
            .map(queryAccessor.albumsByUser(userId, new Date(Long.parseLong(timestamp)), albumUuid));
        return results.one();

    }

    /**
     * Retrieves a resultSet of albums from a user library given a userId and optionally a timestamp
     * and limit of results
     *
     * @param queryAccessor QueryAccessor
     * @param userId java.util.UUID
     * @param timestamp String
     * @param limit Integer
     * @return com.datastax.driver.core.ResultSet
     */
    public static ResultSet get(final QueryAccessor queryAccessor, final UUID userId, final Optional<String> timestamp,
                                final Optional<Integer> limit) {
        if ( limit.isPresent() && timestamp.isPresent() ) {
            return queryAccessor.albumsByUser(userId, new Date(Long.parseLong(timestamp.get())), limit.get());
        }
        else if ( timestamp.isPresent() ) {
            return queryAccessor.albumsByUser(userId, new Date(Long.parseLong(timestamp.get())));
        }
        else if ( limit.isPresent() ) {
            return queryAccessor.albumsByUser(userId, limit.get());
        }
        else {
            return queryAccessor.albumsByUser(userId);
        }
    }

    /**
     * Adds an album to a given userId library
     *
     * @param queryAccessor QueryAccessor
     * @param album AlbumsByUserDao
     */
    public static void add(final QueryAccessor queryAccessor, final AlbumsByUserDao album) {
        try {
            queryAccessor.addLibraryAlbum(album.getUserId(), "Album", new Date(), album.getAlbumId(),
                                          album.getAlbumName(), album.getAlbumYear(), album.getArtistId(),
                                          album.getArtistMbid(), album.getArtistName());
        }
        catch ( Exception error ) {
            throw error;
        }
    }

    /**
     * Removes a given album from a user library given a userId, the albumId and the album timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param userId userId java.util.UUID
     * @param timestamp java.util.Date
     * @param albumUuid userId java.util.UUID
     */
    public static void remove(final QueryAccessor queryAccessor, final UUID userId, final Date timestamp,
                              final UUID albumUuid) {
        try {
            queryAccessor.deleteLibraryAlbum(albumUuid, timestamp, userId);
        }
        catch ( RuntimeErrorException err ) {
            throw err;
        }
    }
}
