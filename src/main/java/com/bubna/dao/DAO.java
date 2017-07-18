package com.bubna.dao;

import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by test on 17.07.2017.
 */
public interface DAO<K, V, T> {
    DAO setUpdatedSource(T source);
    V get(K pk) throws NoSuchElementException, InitException;
    void edit(V e) throws InitException, IOException;
    void add(V e) throws IOException;
    void rem(K pk) throws NoSuchElementException;
    ArrayList<V> list(Predicate<V> p) throws InitException;
}
