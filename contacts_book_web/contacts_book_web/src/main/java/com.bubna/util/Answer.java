package com.bubna.util;

public class Answer {

    private String key;
    private Object val;

    public Answer(String key, Object val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public Object getVal() {
        return val;
    }
}
