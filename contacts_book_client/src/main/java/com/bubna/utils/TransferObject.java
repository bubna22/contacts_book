package com.bubna.utils;

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
        try {
            return Utils.INSTANCE.getGson().toJson(this);
        } finally {
            Utils.INSTANCE.putGson();
        }

    }

    public static TransferObject deserialize(String json) {
        try {
            return Utils.INSTANCE.getGson().fromJson(json, TransferObject.class);
        } finally {
            Utils.INSTANCE.putGson();
        }
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
