package com.bubna.dao;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public interface TemplateDAO <K, V, T> extends DAO<K, V, T> {
    default void modify(K pk, V e) throws IOException, InitException, NoSuchElementException, IncorrectInputException {
        HashMap<K, V> values = new HashMap<>();
        values.put(pk, e);
        write(values);
    }

    default ArrayList<V> list(Predicate<V> p) throws InitException, NoSuchElementException {
        HashMap<K, V> values = read(null, p);
        return new ArrayList<>(values.values());
    }

    default V get(K pk) throws NoSuchElementException, InitException {
        Predicate<K> pKey = k -> k != null && k.equals(pk);
        HashMap<K, V> values = read(pKey, null);
        if (!values.containsKey(pk)) throw new NoSuchElementException("no elem " + pk);
        return read(pKey, null).get(pk);
    }

    HashMap<K, V> read(Predicate<K> pKey, Predicate<V> pValue) throws InitException, NoSuchElementException;
    void write(HashMap<K, V> values) throws InitException, NoSuchElementException, IncorrectInputException;
}
