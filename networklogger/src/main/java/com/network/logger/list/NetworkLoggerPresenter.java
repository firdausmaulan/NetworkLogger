package com.network.logger.list;

import android.app.Activity;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;

import java.util.List;

public class NetworkLoggerPresenter {

    private Activity context;
    private NetworkLoggerView view;
    private AppDatabase database;

    NetworkLoggerPresenter(Activity context, NetworkLoggerView view, AppDatabase database) {
        this.context = context;
        this.view = view;
        this.database = database;
    }

    void unBind() {
        view = null;
    }

    void getListData() {
        if (view != null) view.showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                final List<NetworkLoggerModel> list = database.networkLoggerDao().getAll();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.showData(list);
                            view.hideLoading();
                        }
                    }
                });
            }
        }.start();
    }

    void getSearchData(final String query) {
        if (view != null) view.showLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                final List<NetworkLoggerModel> list = database.networkLoggerDao().getSearch("%" + query + "%");
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.showData(list);
                            view.hideLoading();
                        }
                    }
                });
            }
        }.start();
    }

    void deleteAllData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                database.networkLoggerDao().deleteAll();
            }
        }.start();
    }
}
