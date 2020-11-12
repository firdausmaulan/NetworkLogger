package com.network.logger.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NetworkLoggerDao {
    //@Query("SELECT * FROM t_log LIMIT 10 OFFSET :currentPosition")
    //List<NetworkLoggerModel> getAll(int currentPosition);

    @Query("SELECT * FROM t_log ORDER BY uid DESC LIMIT 1000")
    List<NetworkLoggerModel> getAll();

    @Query("SELECT * FROM t_log WHERE event_name LIKE :query ORDER BY uid DESC LIMIT 1000")
    List<NetworkLoggerModel> getSearch(String query);

    @Query("SELECT * FROM t_log where uid=:uid")
    NetworkLoggerModel findById(int uid);

    @Insert
    void insert(NetworkLoggerModel networkLoggerModel);

    @Query("DELETE FROM t_log")
    void deleteAll();
}
