package com.bubna.server;

import com.bubna.utils.ObservablePart;
import com.bubna.utils.TransferObject;

import java.util.Observable;

public enum SocketChecker {

    INSTANCE;

    private Observable observable;
    private TransferObject pingObj;

    SocketChecker() {
        observable = new ObservablePart();
        pingObj = new TransferObject("server", "ping", null);
    }

    public Observable getObservable() {
        return observable;
    }

    public void ping() {
        observable.notifyObservers(pingObj);
    }

}
