package com.network.logger.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.network.logger.NetworkLoggerApp;
import com.network.logger.R;

@Database(entities = {NetworkLoggerModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NetworkLoggerDao networkLoggerDao();

    public static AppDatabase getAppDatabase() {
        if (INSTANCE == null) {
            Context context =  NetworkLoggerApp.get();
            String dbName = context.getString(R.string.app_name).trim().toLowerCase() + "db";
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbName).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}