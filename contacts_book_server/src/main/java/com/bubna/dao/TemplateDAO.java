package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public interface TemplateDAO <K, V, T> extends DAO<K, V, T> {
    default void modify(User acc, K pk, V e) throws CustomException {
        HashMap<K, V> values = new HashMap<>();
        values.put(pk, e);
        write(acc, values);
    }

    default ArrayList<V> list(User acc, Predicate<V> p) throws CustomException {
        HashMap<K, V> values = read(acc, null, p);
        return new ArrayList<>(values.values());
    }

    default V get(User acc, K pk) throws CustomException {
        Predicate<K> pKey = k -> k != null && k.equals(pk);
        HashMap<K, V> values = read(acc, pKey, null);
        if (!values.containsKey(pk)) throw new CustomException("no elem " + pk);
        return values.get(pk);
    }

    HashMap<K, V> read(User acc, Predicate<K> pKey, Predicate<V> pValue) throws CustomException;
    void write(User acc, HashMap<K, V> values) throws CustomException;
}
