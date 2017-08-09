package com.bubna.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

abstract class CustomPair<K, V> {

    private K v1;
    private V v2;

    public CustomPair(K v1, V v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public K getV1() {
        return v1;
    }

    public V getV2() {
        return v2;
    }

    public String serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static <V extends CustomPair> CustomPair deserialize(String data, Class<V> castClass) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(data, castClass);
    }
}
