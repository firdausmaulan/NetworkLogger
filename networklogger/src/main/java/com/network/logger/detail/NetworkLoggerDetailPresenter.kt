package com.network.logger.detail

import com.network.logger.database.AppDatabase
import com.network.logger.util.JsonPretty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkLoggerDetailPresenter internal constructor(
    private var view: NetworkLoggerDetailView?,
    private val database: AppDatabase?
) {
    private val jsonPretty = JsonPretty()

    fun unBind() {
        view = null
    }

    suspend fun getData(uid: Int) {
        val model = withContext(Dispatchers.IO) {
            database?.networkLoggerDao()?.findById(uid)
        }

        if (model == null) {
            withContext(Dispatchers.Main) {
                // Handle the case where the model is null
                view?.showData("Data not found for UID: $uid")
            }
            return
        }

        val data = withContext(Dispatchers.Default) {
            val stringBuilder = StringBuilder()
            stringBuilder.append("Method : " + model.method)
            stringBuilder.append("\n\nStatus Code : " + model.statusCode)
            stringBuilder.append("\n\nEvent Name : " + model.eventName)
            stringBuilder.append("\n\nURL : " + model.url)
            stringBuilder.append("\n\nHeader : " + model.header)
            stringBuilder.append("\n\nParam : \n" + jsonPretty.print(model.params))
            stringBuilder.append("\n\nInfo : \n" + jsonPretty.print(model.info))
            stringBuilder.append("\n\nResponse : \n" + jsonPretty.print(model.response))
            stringBuilder.append("\n\n")
            stringBuilder.toString()
        }

        withContext(Dispatchers.Main) {
            view?.showData(data)
        }
    }
}
