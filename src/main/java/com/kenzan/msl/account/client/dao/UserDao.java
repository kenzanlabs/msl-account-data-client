/*
 * Copyright 2015, Charter Communications, All rights reserved.
 */
package com.kenzan.msl.account.client.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.UUID;

@Table(name = "users")
public class UserDao {
    @PartitionKey(value = 0)
    @Column(name = "username")
    private String username;
    @Column(name = "creation_timestamp")
    private Date creationTimestamp;
    @Column(name = "password")
    private String password;
    @Column(name = "user_id")
    private UUID userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
