package com.nikitagorbatko.test_servers.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserDbo {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "balance")
    public int balance;
}