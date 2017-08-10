package com.bubna.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by test on 11.07.2017.
 */
public enum Utils {

    INSTANCE;

    Utils() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        gsonLocker = new ReentrantLock();
    }

    private Gson gson;
    private ReentrantLock gsonLocker;

    public Gson getGson() {
        gsonLocker.lock();
        return gson;
    }

    public void putGson() {
        gsonLocker.unlock();
    }
}
