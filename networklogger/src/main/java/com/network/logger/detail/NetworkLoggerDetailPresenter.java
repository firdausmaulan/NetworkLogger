package com.network.logger.detail;

import android.app.Activity;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.util.JsonPretty;

public class NetworkLoggerDetailPresenter {

    private Activity context;
    private NetworkLoggerDetailView view;
    private AppDatabase database;
    private JsonPretty jsonPretty = new JsonPretty();

    NetworkLoggerDetailPresenter(Activity context, NetworkLoggerDetailView view, AppDatabase database) {
        this.context = context;
        this.view = view;
        this.database = database;
    }

    void unBind() {
        view = null;
    }

    void getData(final int uid) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                NetworkLoggerModel model = database.networkLoggerDao().findById(uid);
                final String data = "Method : " + model.getMethod()
                        + "\n\nStatus Code : " + model.getStatusCode()
                        + "\n\nEvent Name : " + model.getEventName()
                        + "\n\nURL : " + model.getUrl()
                        + "\n\nHeader : " + model.getHeader()
                        + "\n\nParam : \n" + jsonPretty.print(model.getParams())
                        + "\n\nInfo : \n" + jsonPretty.print(model.getInfo())
                        + "\n\nResponse : \n" + jsonPretty.print(model.getResponse()
                        + "\n\n"
                );
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) view.showData(data);
                    }
                });
            }
        }.start();
    }
}
