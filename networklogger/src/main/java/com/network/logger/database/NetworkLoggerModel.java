package com.network.logger.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "t_log")
public class NetworkLoggerModel {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "method")
    private String method;
    @ColumnInfo(name = "status_code")
    private String statusCode;
    @ColumnInfo(name = "event_name")
    private String eventName;
    @ColumnInfo(name = "header")
    private String header;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "param")
    private String params;
    @ColumnInfo(name = "info")
    private String info;
    @ColumnInfo(name = "response")
    private String response;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
