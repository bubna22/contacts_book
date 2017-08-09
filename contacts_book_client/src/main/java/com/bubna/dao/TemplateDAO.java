package com.bubna.dao;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public interface TemplateDAO <K, V, T> extends DAO<K, V, T> {
    default void modify(User acc, K pk, V e) throws IOException, InitException, NoSuchElementException, IncorrectInputException {
        HashMap<K, V> values = new HashMap<>();
        values.put(pk, e);
        write(acc, values);
    }

    default ArrayList<V> list(User acc, Predicate<V> p) throws InitException, NoSuchElementException {
        HashMap<K, V> values = read(acc, null, p);
        return new ArrayList<>(values.values());
    }

    default V get(User acc, K pk) throws NoSuchElementException, InitException {
        Predicate<K> pKey = k -> k != null && k.equals(pk);
        HashMap<K, V> values = read(acc, pKey, null);
        if (!values.containsKey(pk)) throw new NoSuchElementException("no elem " + pk);
        return values.get(pk);
    }

    HashMap<K, V> read(User acc, Predicate<K> pKey, Predicate<V> pValue) throws InitException, NoSuchElementException;
    void write(User acc, HashMap<K, V> values) throws InitException, NoSuchElementException, IncorrectInputException, IOException;
}
