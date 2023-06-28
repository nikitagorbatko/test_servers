package com.nikitagorbatko.test_servers.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE uid = 0")
    LiveData<UserDbo>getUser();


    @Query("UPDATE user SET balance = balance + 15 WHERE uid = 0")
    void updateBalance();
}