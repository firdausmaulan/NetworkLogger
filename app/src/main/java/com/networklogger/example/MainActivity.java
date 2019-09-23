package com.networklogger.example;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.network.logger.NetworkLogger;
import com.network.logger.NetworkLoggerApp;
import com.network.logger.database.NetworkLoggerModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NetworkLoggerApp.getInstance().init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }, 2000);
    }

    private void test() {
        NetworkLogger networkLogger = new NetworkLogger();
        NetworkLoggerModel model = null;
        for (int i = 0; i < 100; i++) {
            model = new NetworkLoggerModel();
            model.setMethod("GET");
            model.setStatusCode("200");
            model.setEventName("test " + i);
            model.setUrl("https://www.test.com/");
            model.setHeader("Authorization Bearer abcde12345");
            model.setParams("{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"Age\":20\n" +
                    "}");
            model.setInfo("{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"Age\":20\n" +
                    "}");
            model.setResponse("{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"Age\":20\n" +
                    "}");
            networkLogger.add(model);
        }
    }
}
