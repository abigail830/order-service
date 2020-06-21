package com.github.abigail830.ecommerce.ordercontext.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonUtil {
    private final static Gson DEFAULT_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private final static Gson WITHOUT_ENCODE_GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static <T> T toObject(String json, Type obj) {
        return DEFAULT_GSON.fromJson(json, obj);
    }

    public static <T> T toObject(String json, Class<T> obj) {
        return DEFAULT_GSON.fromJson(json, obj);
    }

    public static String toJson(Object obj) {
        return DEFAULT_GSON.toJson(obj);
    }

    public static String toJsonWithoutEncode(Object obj) {
        return WITHOUT_ENCODE_GSON.toJson(obj);
    }

}
