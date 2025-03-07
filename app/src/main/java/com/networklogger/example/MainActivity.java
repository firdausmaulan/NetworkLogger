package com.networklogger.example;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.network.logger.NetworkLogger;
import com.network.logger.database.NetworkLoggerModel;

public class MainActivity extends AppCompatActivity {

    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PermissionHelper().requestPermissionNotification(this);
        test();
    }

    private void test() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            String statusCode = "200";
            if (counter % 4 == 0) {
                statusCode = "400";
            } else if (counter % 5 == 0) {
                statusCode = "500";
            }

            NetworkLogger networkLogger = new NetworkLogger();
            NetworkLoggerModel model;
            model = new NetworkLoggerModel();
            model.setMethod("GET");
            model.setStatusCode(statusCode);
            model.setEventName("test " + counter++);
            model.setUrl("https://www.test.com/");
            model.setHeader("Authorization Bearer abcde12345");
            model.setParams("{\n" +
                    "    \"name\":\"VGVzdA==\",\n" +
                    "    \"Age\":MjA=\n" +
                    "}");
            model.setInfo("{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"Age\":20\n" +
                    "}");
            model.setResponse("{\n" +
                    "    \"name\":\"Test\",\n" +
                    "    \"Age\":20\n" +
                    "}");
            networkLogger.add(model, "sandbox");
            test();
        }, 5 * 1000);
    }
}
