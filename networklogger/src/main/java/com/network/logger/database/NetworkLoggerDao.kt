package com.network.logger.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface NetworkLoggerDao {
    @Insert
    suspend fun insert(networkLoggerModel: NetworkLoggerModel)

    @Query("SELECT * FROM t_log ORDER BY uid DESC LIMIT 100")
    suspend fun getAll() : List<NetworkLoggerModel?>

    @Query("SELECT * FROM t_log WHERE event_name LIKE :query ORDER BY uid DESC LIMIT 100")
    suspend fun getSearch(query: String?): List<NetworkLoggerModel?>

    @Query("SELECT * FROM t_log where uid=:uid")
    suspend fun findById(uid: Int): NetworkLoggerModel

    @Query("DELETE FROM t_log WHERE created_at < :expiredTime")
    suspend fun deleteSomeData(expiredTime: Long?)

    @Query("DELETE FROM t_log")
    suspend fun deleteAll()
}
