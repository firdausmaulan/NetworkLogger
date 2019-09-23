package com.network.logger.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NetworkLoggerDao {
    //@Query("SELECT * FROM t_log LIMIT 10 OFFSET :currentPosition")
    //List<NetworkLoggerModel> getAll(int currentPosition);

    @Query("SELECT * FROM t_log ORDER BY uid DESC")
    List<NetworkLoggerModel> getAll();

    @Query("SELECT * FROM t_log where uid=:uid")
    NetworkLoggerModel findById(int uid);

    @Insert
    void insert(NetworkLoggerModel networkLoggerModel);

    @Query("DELETE FROM t_log")
    void deleteAll();
}
