package com.network.logger.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "t_log")
data class NetworkLoggerModel (
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "method")
    var method: String? = null,

    @ColumnInfo(name = "status_code")
    var statusCode: String? = null,

    @ColumnInfo(name = "event_name")
    var eventName: String? = null,

    @ColumnInfo(name = "header")
    var header: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name = "param")
    var params: String? = null,

    @ColumnInfo(name = "info")
    var info: String? = null,

    @ColumnInfo(name = "response")
    var response: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: Long? = null
)
