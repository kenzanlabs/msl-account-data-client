package com.kenzan.msl.account.client.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.client.cassandra.QueryAccessor;

import java.util.UUID;

public class UserQuery {

  /**
   * Adds a user
   *
   * @param queryAccessor QueryAccessor
   * @param user String
   */
  public static void add(final QueryAccessor queryAccessor, final UserDto user) {
    queryAccessor.addUser(user.getUsername(), user.getCreationTimestamp(), user.getPassword(),
        user.getUserId());
  }

  /**
   * Retrieves a user by username
   *
   * @param queryAccessor QueryAccessor
   * @param username String
   * @return Optional&lt;UserDto&gt;
   */
  public static Optional<UserDto> get(final QueryAccessor queryAccessor,
      final MappingManager manager, final String username) {
    ResultSet userResults = queryAccessor.getUser(username);
    try {
      Result<UserDto> results = manager.mapper(UserDto.class).map(userResults);
      if (results != null) {
        return Optional.of(results.one());
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return Optional.absent();
  }

  /**
   * Retrieves a user by username
   *
   * @param queryAccessor QueryAccessor
   * @param id UUID
   * @return Optional&lt;UserDto&gt;
   */
  public static Optional<UserDto> get(final QueryAccessor queryAccessor,
      final MappingManager manager, final UUID id) {
    ResultSet userResults = queryAccessor.getUser(id);
    try {
      Result<UserDto> results = manager.mapper(UserDto.class).map(userResults);
      if (results != null) {
        return Optional.of(results.one());
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return Optional.absent();
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
