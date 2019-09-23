package com.networklogger.example;

import android.app.Application;

import com.network.logger.NetworkLoggerApp;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkLoggerApp.getInstance().init(this);
    }
}
