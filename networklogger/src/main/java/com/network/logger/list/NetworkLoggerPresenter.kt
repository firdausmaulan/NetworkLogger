package com.network.logger.list

import com.network.logger.database.AppDatabase
import com.network.logger.database.NetworkLoggerModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class NetworkLoggerPresenter internal constructor(
    private var view: NetworkLoggerView?,
    private val database: AppDatabase?
) {

    fun unBind() {
        view = null
    }

    suspend fun getListData() {
        withContext(Dispatchers.Main) {
            view?.showLoading()
        }

        val list: List<NetworkLoggerModel?>?
        withContext(Dispatchers.IO) {
            val threeDaysAgo = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -3)
            }.timeInMillis
            database?.networkLoggerDao()?.deleteSomeData(threeDaysAgo)
            list = database?.networkLoggerDao()?.getAll()
        }

        withContext(Dispatchers.Main) {
            view?.showData(list)
            view?.hideLoading()
        }
    }

    suspend fun getSearchData(query: String) {
        withContext(Dispatchers.Main) {
            view?.showLoading()
        }

        val list = withContext(Dispatchers.IO) {
            val formattedQuery = "%$query%"
            database?.networkLoggerDao()?.getSearch(formattedQuery)
        }

        withContext(Dispatchers.Main) {
            view?.showData(list)
            view?.hideLoading()
        }
    }

    suspend fun deleteAllData() {
        withContext(Dispatchers.IO) {
            database?.networkLoggerDao()?.deleteAll()
        }
    }
}
