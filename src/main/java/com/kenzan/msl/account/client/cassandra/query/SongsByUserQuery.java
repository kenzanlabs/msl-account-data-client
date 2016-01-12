/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;

import javax.management.RuntimeErrorException;
import java.util.Date;
import java.util.UUID;

public class SongsByUserQuery {

    /**
     * Retrieves a song from a user library given a userId, songId and a timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param manager com.datastax.driver.mapping.MappingManager
     * @param userId java.util.UUID
     * @param timestamp String
     * @param songUuid java.util.UUID
     * @return SongsByUserDao
     */
    public static SongsByUserDao getUserSong(final QueryAccessor queryAccessor, final MappingManager manager,
                                     final UUID userId, final String timestamp, final UUID songUuid) {
        Result<SongsByUserDao> results = manager.mapper(SongsByUserDao.class)
            .map(queryAccessor.songsByUser(userId, new Date(Long.parseLong(timestamp)), songUuid));
        return results.one();
    }

    /**
     * Retrieves a resultSet of songs from a user library given a userId and optionally a timestamp
     * and limit of results
     *
     * @param queryAccessor QueryAccessor
     * @param userId java.util.UUID
     * @param timestamp String
     * @param limit Integer
     * @return com.datastax.driver.core.ResultSet
     */
    public static ResultSet getUserSongList(final QueryAccessor queryAccessor, final UUID userId, final Optional<String> timestamp,
                                final Optional<Integer> limit) {
        if ( limit.isPresent() && timestamp.isPresent() ) {
            return queryAccessor.songsByUser(userId, new Date(Long.parseLong(timestamp.get())), limit.get());
        }
        else if ( limit.isPresent() ) {
            return queryAccessor.songsByUser(userId, limit.get());
        }
        else if ( timestamp.isPresent() ) {
            return queryAccessor.songsByUser(userId, new Date(Long.parseLong(timestamp.get())));
        }
        else {
            return queryAccessor.songsByUser(userId);
        }
    }

    /**
     * Adds a song to a given userId library
     *
     * @param queryAccessor QueryAccessor
     * @param song SongsByUserDao
     */
    public static void add(final QueryAccessor queryAccessor, final SongsByUserDao song) {
        try {
            queryAccessor.addLibrarySong(song.getUserId(), "Song", new Date(), song.getSongId(), song.getSongName(),
                                         song.getSongDuration(), song.getAlbumId(), song.getAlbumName(),
                                         song.getAlbumYear(), song.getArtistId(), song.getArtistMbid(),
                                         song.getArtistName());
        }
        catch ( Exception error ) {
            throw error;
        }
    }

    /**
     * Removes a given song from a user library given a userId, the songId and the song timestamp
     *
     * @param queryAccessor QueryAccessor
     * @param userId userId java.util.UUID
     * @param timestamp java.util.Date
     * @param songUuid userId java.util.UUID
     */
    public static void remove(final QueryAccessor queryAccessor, final UUID userId, final Date timestamp,
                              final UUID songUuid) {
        try {
            queryAccessor.deleteLibrarySong(songUuid, timestamp, userId);
        }
        catch ( RuntimeErrorException err ) {
            throw err;
        }
    }
}
