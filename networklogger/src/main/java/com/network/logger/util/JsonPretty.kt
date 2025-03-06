package com.network.logger.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class JsonPretty {
    public String print(String data) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(JsonParser.parseString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
