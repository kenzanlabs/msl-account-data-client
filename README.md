# MSL account data client

## Overview
Data Client Layer
Simplification to a traditional edge/middle architecture, this project uses a edge/data client architecture instead.
The data clients are jars, each containing all the methods and DAOs for accessing all the tables within a Cassandra cluster.
To enhance scalability and configuration flexibility, the Cassandra tables are split into three independent clusters: account, catalog, and rating.
Each of these clusters has a data client jar dedicated to accessing it: account-data-client, catalog-data-client, and rating-data-client, respectively.

So a microservice that needs to access Cassandra data will include one or more of the data client jars.

| Table           | Method  |
|:-------------:| -----:|
| **users** | Observable<Void> addOrUpdateUser(UserDao) |
| | Observable<UserDao> getUser(UUID userUuid) |
| | Observable<Void> deleteUser(UUID userUuid) |
| **songs_by_user** | Observable<Void> addOrUpdateSongsByUser(SongsByUserDao) |
| | Observable<SongsByUserDao> getSongsByUser(UUID userId, String favoritesTimestamp, UUID songUuid) |
| | Observable<ResultSet> getSongsByUser(UUID userId, Optional<String >favoritesTimestamp, Optional<Integer> limit) |
| | Observable<Result<SongsByUserDao>> map(Observable<ResultSet>) |
| | Observable<Void> deleteSongsByUser(UUID userId, String favoritesTimestamp, UUID songUuid) |
| **albums_by_user** | Observable<Void> addOrUpdateAlbumsByUser(AlbumsByUserDao) |
| | Observable<AlbumsByUserDao> getAlbumsByUser(UUID userId, String favoritesTimestamp, UUID albumUuid) |
| | Observable<ResultSet> getAlbumsByUser(UUID userId, Optional<String> favoritesTimestamp, Optional<Integer> limit) |
| | Observable<Result<AlbumsByUserDao>> map(Observable<ResultSet>) |
| | Observable<Void> deleteAlbumsByUser(UUID userId, String favoritesTimestamp, UUID albumUuid) |
| **artists_by_user** | Observable<Void> addOrUpdateArtistsByUser(ArtistsByUserDao) |
| | Observable<ArtistsByUserDao> getArtistsByUser(UUID userId, String favoritesTimestamp, UUID artistUuid) |
| | Observable<ResultSet> getArtistsByUser(UUID userId, Optional<String> favoritesTimestamp, Optional<Integer> limit) |
| | Observable<Result<ArtistsByUserDao>> map(Observable<ResultSet>) |
| | Observable<Void> deleteArtistsByUser(UUID userId, String favoritesTimestamp, UUID artistUuid) |

## Packaging & Installation

```bash 
mvn clean package && mvn -P install compile
```

To format code
```
mvn clean formatter:format
```

##Reports
###Surefire reports:
```
mvn site
```
report gets generated under /target/site/index.html
 
###Cobertura
```
mvn cobertura:cobertura
```
report gets generated under /target/site/cobertura/index.html