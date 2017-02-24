package com.cyanidex.subsistence.json;

import com.google.gson.JsonObject;

public class JsonHelper {

    public static int getInt(JsonObject jsonObject, String member, int def) {
        if (jsonObject.has(member))
            return jsonObject.get(member).getAsInt();

        return def;
    }

    public static String getString(JsonObject jsonObject, String member, String def) {
        if (jsonObject.has(member))
            return jsonObject.get(member).getAsString();

        return def;
    }
}
