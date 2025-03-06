package com.network.logger.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

class JsonPretty {
    fun print(data: String?): String? {
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(JsonParser.parseString(data))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }
}
