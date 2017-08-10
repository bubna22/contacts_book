package com.bubna.utils;

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
        try {
            return Utils.INSTANCE.getGson().toJson(this);
        } finally {
            Utils.INSTANCE.putGson();
        }
    }

    public static CustomPair deserialize(String data) {
        try {
            return Utils.INSTANCE.getGson().fromJson(data, CustomPair.class);
        } finally {
            Utils.INSTANCE.putGson();
        }
    }
}
