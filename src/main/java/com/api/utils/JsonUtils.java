package com.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class JsonUtils {
    public static String getAsStringSafely(JsonObject jsonObject, String member) {
        JsonElement jsonElement = jsonObject.get(member);
        return jsonElement instanceof JsonNull ? null : jsonElement.getAsString();
    }
}
