package com.network.logger.list;

import android.app.Activity;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;

import java.util.List;

public class NetworkLoggerPresenter {

    private final Activity context;
    private NetworkLoggerView view;
    private final AppDatabase database;

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
        new Thread(() -> {
            final List<NetworkLoggerModel> list = database.networkLoggerDao().getAll();
            context.runOnUiThread(() -> {
                if (view != null) {
                    view.showData(list);
                    view.hideLoading();
                }
            });
            if (list != null && list.size() > 0) deleteSomeData(list);
        }).start();
    }

    void getSearchData(final String query) {
        if (view != null) view.showLoading();
        new Thread(() -> {
            String formattedQuery = "%" + query + "%";
            final List<NetworkLoggerModel> list = database.networkLoggerDao().getSearch(formattedQuery);
            context.runOnUiThread(() -> {
                if (view != null) {
                    view.showData(list);
                    view.hideLoading();
                }
            });
        }).start();
    }

    void deleteSomeData(final List<NetworkLoggerModel> list) {
        new Thread(() -> {
            if (list.size() >= 100) { // 100
                int uid = list.get(0).getUid();
                uid = uid - 1000; // 1000
                if (uid > 0){
                    database.networkLoggerDao().deleteSomeData(uid);
                }
            }
        }).start();
    }

    void deleteAllData() {
        new Thread(() -> database.networkLoggerDao().deleteAll()).start();
    }
}
