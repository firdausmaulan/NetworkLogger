package com.network.logger.list;

import com.network.logger.database.NetworkLoggerModel;

import java.util.List;

public interface NetworkLoggerView {
    void showLoading();
    void hideLoading();
    void showData(List<NetworkLoggerModel> list);
}
