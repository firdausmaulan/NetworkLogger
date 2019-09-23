package com.network.logger.list;

import android.os.AsyncTask;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;

import java.util.List;

public class NetworkLoggerPresenter {

    private NetworkLoggerView view;
    private AppDatabase database;

    NetworkLoggerPresenter(NetworkLoggerView view, AppDatabase database) {
        this.view = view;
        this.database = database;
    }

    void unBind() {
        view = null;
    }

    void getListData(int currentPosition) {
        new GetAllTask().execute(currentPosition);
    }

    private class GetAllTask extends AsyncTask<Integer, Void, List<NetworkLoggerModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.showLoading();
        }

        @Override
        protected List<NetworkLoggerModel> doInBackground(Integer... integers) {
            return database.networkLoggerDao().getAll();
        }

        @Override
        protected void onPostExecute(List<NetworkLoggerModel> list) {
            super.onPostExecute(list);
            if (view != null){
                view.showData(list);
                view.hideLoading();
            }
        }
    }

    void deleteAllData() {
        new DeleteAllTask().execute();
    }

    private class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            database.networkLoggerDao().deleteAll();
            return null;
        }
    }
}
