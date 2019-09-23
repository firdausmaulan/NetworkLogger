package com.network.logger;

import android.content.Context;

public class NetworkLoggerApp {

    private Context appContext;

    public NetworkLoggerApp(){}

    public void init(Context context){
        if(appContext == null){
            appContext = context;
        }
    }

    private Context getContext(){
        return appContext;
    }

    public static Context get(){
        return getInstance().getContext();
    }

    private static NetworkLoggerApp instance;

    public static NetworkLoggerApp getInstance(){
        return instance == null ? (instance = new NetworkLoggerApp()): instance;
    }
}
