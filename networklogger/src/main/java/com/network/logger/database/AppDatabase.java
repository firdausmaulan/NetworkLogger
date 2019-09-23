package com.network.logger.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.network.logger.NetworkLoggerApp;

@Database(entities = {NetworkLoggerModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NetworkLoggerDao networkLoggerDao();

    public static AppDatabase getAppDatabase() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(NetworkLoggerApp.get(), AppDatabase.class, "network-logger-database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}