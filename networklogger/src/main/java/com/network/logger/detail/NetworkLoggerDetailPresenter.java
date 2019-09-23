package com.network.logger.detail;

import android.os.AsyncTask;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;
import com.network.logger.util.JsonPretty;

public class NetworkLoggerDetailPresenter {

    private NetworkLoggerDetailView view;
    private AppDatabase database;
    private JsonPretty jsonPretty = new JsonPretty();

    NetworkLoggerDetailPresenter(NetworkLoggerDetailView view, AppDatabase database) {
        this.view = view;
        this.database = database;
    }

    void unBind() {
        view = null;
    }

    void getData(int uid) {
        new GetDetailTask().execute(uid);
    }

    private class GetDetailTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... integers) {
            NetworkLoggerModel model = database.networkLoggerDao().findById(integers[0]);
            String data = "Method : " + model.getMethod()
                    + "\n\nStatus Code : " + model.getStatusCode()
                    + "\n\nEvent Name : " + model.getEventName()
                    + "\n\nURL : " + model.getUrl()
                    + "\n\nHeader : " + model.getHeader()
                    + "\n\nParam : \n" + jsonPretty.print(model.getParams())
                    + "\n\nInfo : \n" + jsonPretty.print(model.getInfo())
                    + "\n\nResponse : \n" + jsonPretty.print(model.getResponse());
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if (view != null) view.showData(data);
        }
    }
}
