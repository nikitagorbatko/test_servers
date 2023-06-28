package com.nikitagorbatko.test_servers;

import android.app.Application;

import com.nikitagorbatko.test_servers.database.AppDatabase;

public class App extends Application {
    public static AppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getDatabase(this);
    }
}
