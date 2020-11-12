package com.network.logger.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.network.logger.NetworkLoggerApp;
import com.network.logger.R;

@Database(entities = {NetworkLoggerModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NetworkLoggerDao networkLoggerDao();

    public static AppDatabase getAppDatabase() {
        if (INSTANCE == null) {
            Context context = NetworkLoggerApp.get();
            String dbName = context.getString(R.string.app_name).trim().toLowerCase() + "db";
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbName).build();
        }
        return INSTANCE;
    }
}