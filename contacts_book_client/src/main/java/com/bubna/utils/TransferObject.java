package com.bubna.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TransferObject {

    private String who;
    private String what;
    private String data;

    public TransferObject(String who, String what, String data) {
        this.who = who;
        this.what = what;
        this.data = data;
    }

    public String serialize() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static TransferObject deserialize(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, TransferObject.class);
    }

    public String getWho() {
        return who;
    }

    public String getWhat() {
        return what;
    }

    public String getData() {
        return data;
    }
}
