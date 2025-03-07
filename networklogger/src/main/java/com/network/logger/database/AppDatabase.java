package com.network.logger.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.network.logger.NetworkLoggerApp;
import com.network.logger.R;

@Database(entities = {NetworkLoggerModel.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NetworkLoggerDao networkLoggerDao();

    public static AppDatabase getAppDatabase() {
        if (INSTANCE == null) {
            Context context = NetworkLoggerApp.get();
            String dbName = context.getString(R.string.app_name).trim().toLowerCase().replace(" ", "_") + "_nl_db";
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, dbName)
                    .addMigrations(DatabaseMigration.INSTANCE.getMIGRATION_1_2())
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}