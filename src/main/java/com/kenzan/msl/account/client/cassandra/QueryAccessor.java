package com.kenzan.msl.account.client.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface QueryAccessor {

    /* USERS ==================================================== */
    /* ========================================================== */
    @Query("INSERT INTO users (username, creation_timestamp, password, user_id) VALUES (:username, :creation_timestamp, :password, :user_id)")
    public void addUser(@Param("username") String username, @Param("creation_timestamp") Date creation_timestamp,
                        @Param("password") String password, @Param("user_id") UUID user_id);

    @Query("SELECT * FROM users WHERE username = :username")
    public ResultSet getUser(@Param("username") String username);

    @Query("DELETE FROM users WHERE username = :username")
    public void deleteUser(@Param("username") String username);

    /* ALBUMS BY USER =========================================== */
    /* ========================================================== */

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Album' LIMIT :max")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id,
                                  @Param("favorites_timestamp") Date favorites_timestamp, @Param("max") int limit);

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND album_id = :album_id AND content_type = 'Album'")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id,
                                  @Param("favorites_timestamp") Date favorites_timestamp,
                                  @Param("album_id") UUID album_id);

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Album'")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id, @Param("favorites_timestamp") Date favorites_timestamp);

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND content_type = 'Album' LIMIT :max")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id, @Param("max") int limit);

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND content_type = 'Album'")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id);

    @Query("INSERT INTO albums_by_user " + "(user_id, content_type, favorites_timestamp, "
        + "album_id, album_name, album_year, " + "artist_id, artist_mbid, artist_name, image_link ) " + "VALUES "
        + "(:user_id,:content_type,:favorites_timestamp," + ":album_id, :album_name, :album_year,"
        + ":artist_id, :artist_mbid, :artist_name, :image_link)")
    public void addLibraryAlbum(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                                @Param("favorites_timestamp") Date favorites_timestamp,
                                @Param("album_id") UUID album_id, @Param("album_name") String album_name,
                                @Param("album_year") Integer album_year, @Param("artist_id") UUID artist_id,
                                @Param("artist_mbid") UUID artist_mbid, @Param("artist_name") String artist_name,
                                @Param("image_link") String image_link);

    @Query("DELETE FROM albums_by_user WHERE user_id = :user_id AND content_type='Album' AND favorites_timestamp = :favorites_timestamp AND album_id=:album_id")
    public void deleteLibraryAlbum(@Param("album_id") UUID album_id,
                                   @Param("favorites_timestamp") Date favorites_timestamp,
                                   @Param("user_id") UUID user_id);

    /* SONGS BY USER =========================================== */
    /* ========================================================== */
    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Song' LIMIT :max")
    public ResultSet songsByUser(@Param("user_id") UUID user_id,
                                 @Param("favorites_timestamp") Date favorites_timestamp, @Param("max") int limit);

    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND song_id = :song_id AND content_type = 'Song'")
    public ResultSet songsByUser(@Param("user_id") UUID user_id,
                                 @Param("favorites_timestamp") Date favorites_timestamp, @Param("song_id") UUID song_id);

    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Song'")
    public ResultSet songsByUser(@Param("user_id") UUID user_id, @Param("favorites_timestamp") Date favorites_timestamp);

    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND content_type = 'Song' LIMIT :max")
    public ResultSet songsByUser(@Param("user_id") UUID user_id, @Param("max") int limit);

    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND content_type = 'Song'")
    public ResultSet songsByUser(@Param("user_id") UUID user_id);

    @Query("INSERT INTO songs_by_user " + "(user_id,content_type,favorites_timestamp, "
        + "album_id, album_name, album_year, " + "artist_id, artist_mbid, artist_name, "
        + "song_id, song_duration, song_name, image_link) " + "VALUES "
        + "(:user_id,:content_type,:favorites_timestamp," + ":album_id, :album_name, :album_year,"
        + ":artist_id, :artist_mbid, :artist_name," + ":song_id, :song_duration, :song_name, :image_link)")
    public void addLibrarySong(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                               @Param("favorites_timestamp") Date favorites_timestamp, @Param("song_id") UUID song_id,
                               @Param("song_name") String song_name, @Param("song_duration") Integer song_duration,
                               @Param("album_id") UUID album_id, @Param("album_name") String album_name,
                               @Param("album_year") Integer album_year, @Param("artist_id") UUID artist_id,
                               @Param("artist_mbid") UUID artist_mbid, @Param("artist_name") String artist_name,
                               @Param("image_link") String image_link);

    @Query("DELETE FROM songs_by_user WHERE user_id = :user_id AND content_type = 'Song' AND favorites_timestamp = :favorites_timestamp AND song_id = :song_id")
    public void deleteLibrarySong(@Param("song_id") UUID song_id,
                                  @Param("favorites_timestamp") Date favorites_timestamp, @Param("user_id") UUID user_id);

    /* ARTISTS BY USER =========================================== */
    /* ========================================================== */
    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Artist' LIMIT :max")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id,
                                   @Param("favorites_timestamp") Date favorites_timestamp, @Param("max") int limit);

    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND artist_id = :artist_id AND content_type = 'Artist'")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id,
                                   @Param("favorites_timestamp") Date favorites_timestamp,
                                   @Param("artist_id") UUID artist_id);

    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND favorites_timestamp = :favorites_timestamp AND content_type = 'Artist'")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id,
                                   @Param("favorites_timestamp") Date favorites_timestamp);

    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND content_type = 'Artist' LIMIT :max")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id, @Param("max") int limit);

    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND content_type = 'Artist'")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id);

    @Query("INSERT INTO artists_by_user " + "(user_id, content_type, favorites_timestamp, "
        + "artist_id, artist_mbid, artist_name, image_link ) " + "VALUES "
        + "(:user_id,:content_type,:favorites_timestamp," + ":artist_id, :artist_mbid, :artist_name, :image_link)")
    public void addLibraryArtist(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                                 @Param("favorites_timestamp") Date favorites_timestamp,
                                 @Param("artist_id") UUID artist_id, @Param("artist_mbid") UUID artist_mbid,
                                 @Param("artist_name") String artist_name, @Param("image_link") String image_link);

    @Query("DELETE FROM artists_by_user WHERE user_id=:user_id AND content_type='Artist' AND favorites_timestamp = :favorites_timestamp AND artist_id=:artist_id")
    public void deleteLibraryArtist(@Param("artist_id") UUID artist_id,
                                    @Param("favorites_timestamp") Date favorites_timestamp,
                                    @Param("user_id") UUID user_id);

}