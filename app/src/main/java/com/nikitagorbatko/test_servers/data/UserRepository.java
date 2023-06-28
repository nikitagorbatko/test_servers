package com.nikitagorbatko.test_servers.data;

import androidx.lifecycle.LiveData;

import com.nikitagorbatko.test_servers.database.AppDatabase;
import com.nikitagorbatko.test_servers.database.UserDao;
import com.nikitagorbatko.test_servers.database.UserDbo;


public class UserRepository {
    private final UserDao dao;
    private final LiveData<UserDbo> user;

    public UserRepository(UserDao dao) {
        this.dao = dao;
        user = dao.getUser();
    }

    public LiveData<UserDbo> getUser() {
        return user;
    }

    public void updateBalance() {
        AppDatabase.databaseWriteExecutor.execute(dao::updateBalance);
    }
}