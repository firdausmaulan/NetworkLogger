package com.network.logger.list;

import android.app.Activity;

import com.network.logger.database.AppDatabase;
import com.network.logger.database.NetworkLoggerModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkLoggerPresenter {

    private final Activity context;
    private NetworkLoggerView view;
    private final AppDatabase database;
    private final int threadCt = Runtime.getRuntime().availableProcessors() + 1;
    private final ExecutorService executor = Executors.newFixedThreadPool(threadCt);

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
        executor.execute(() -> {
            final List<NetworkLoggerModel> list = database.networkLoggerDao().getAll();
            context.runOnUiThread(() -> {
                if (view != null) {
                    view.showData(list);
                    view.hideLoading();
                }
            });
            if (list != null && list.size() > 0) deleteSomeData(list);
        });
    }

    void getSearchData(final String query) {
        if (view != null) view.showLoading();
        executor.execute(() -> {
            String formattedQuery = "%" + query + "%";
            final List<NetworkLoggerModel> list = database.networkLoggerDao().getSearch(formattedQuery);
            context.runOnUiThread(() -> {
                if (view != null) {
                    view.showData(list);
                    view.hideLoading();
                }
            });
        });
    }

    void deleteSomeData(final List<NetworkLoggerModel> list) {
        executor.execute(() -> {
            if (list.size() >= 10) { // 100
                int uid = list.get(0).getUid();
                uid = uid - 100; // 1000
                if (uid > 0){
                    database.networkLoggerDao().deleteSomeData(uid);
                }
            }
        });
    }

    void deleteAllData() {
        executor.execute(() -> database.networkLoggerDao().deleteAll());
    }
}
