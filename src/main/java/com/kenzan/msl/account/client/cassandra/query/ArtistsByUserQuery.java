/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;

import javax.management.RuntimeErrorException;
import java.util.Date;
import java.util.UUID;

public class ArtistsByUserQuery {

    /**
     * Retrieves a artist from a user library given a userId, artistId and a timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param manager com.datastax.driver.mapping.MappingManager
     * @param userId java.util.UUID
     * @param timestamp String
     * @param artistUuid java.util.UUID
     * @return ArtistsByUserDao
     */
    public static ArtistsByUserDao getUserArtist(final QueryAccessor queryAccessor, final MappingManager manager,
                                                 final UUID userId, final String timestamp, final UUID artistUuid) {
        Result<ArtistsByUserDao> results = manager.mapper(ArtistsByUserDao.class)
            .map(queryAccessor.artistsByUser(userId, new Date(Long.parseLong(timestamp)), artistUuid));
        return results.one();
    }

    /**
     * Retrieves a resultSet of artists from a user library given a userId and optionally a
     * timestamp and limit of results
     *
     * @param queryAccessor QueryAccessor
     * @param userId java.util.UUID
     * @param timestamp String
     * @param limit Integer
     * @return com.datastax.driver.core.ResultSet
     */
    public static ResultSet getUserArtistList(final QueryAccessor queryAccessor, final UUID userId,
                                              final Optional<String> timestamp, final Optional<Integer> limit) {
        if ( limit.isPresent() && timestamp.isPresent() ) {
            return queryAccessor.artistsByUser(userId, new Date(Long.parseLong(timestamp.get())), limit.get());
        }
        else if ( limit.isPresent() ) {
            return queryAccessor.artistsByUser(userId, limit.get());
        }
        else if ( timestamp.isPresent() ) {
            return queryAccessor.artistsByUser(userId, new Date(Long.parseLong(timestamp.get())));
        }
        else {
            return queryAccessor.artistsByUser(userId);
        }
    }

    /**
     * Adds an artist to a given userId library
     *
     * @param queryAccessor QueryAccessor
     * @param artist ArtistsByUserDao
     */
    public static void add(final QueryAccessor queryAccessor, final ArtistsByUserDao artist) {
        queryAccessor.addLibraryArtist(artist.getUserId(), "Artist", new Date(), artist.getArtistId(),
                                       artist.getArtistMbid(), artist.getArtistName());
    }

    /**
     * Removes a given artist from a user library given a userId, the artistId and the artist
     * timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param userId java.util.UUID
     * @param timestamp java.util.Date
     * @param artistUuid java.util.UUID
     */
    public static void remove(final QueryAccessor queryAccessor, final UUID userId, final Date timestamp,
                              final UUID artistUuid) {
        queryAccessor.deleteLibraryArtist(artistUuid, timestamp, userId);
    }
}
