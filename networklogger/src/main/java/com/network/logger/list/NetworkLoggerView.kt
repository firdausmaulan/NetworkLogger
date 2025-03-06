package com.network.logger.list

import com.network.logger.database.NetworkLoggerModel

interface NetworkLoggerView {
    fun showLoading()
    fun hideLoading()
    fun showData(list: List<NetworkLoggerModel?>?)
}
