package com.bubna.backend;

import com.bubna.exceptions.ObserverNotRegisteredException;
import com.bubna.frontend.Observer;

/**
 * Created by test on 15.07.2017.
 */
public interface Observable {

    void registerObserver(Observer o);
    void unregisterObserver(Observer o) throws ObserverNotRegisteredException;
    void notifyObservers(Object data);
}
