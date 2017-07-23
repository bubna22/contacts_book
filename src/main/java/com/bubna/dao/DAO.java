package com.bubna.dao;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by test on 17.07.2017.
 */
public interface DAO<K, V, T> {
    DAO setUpdatedSource(T source) throws InitException;
    V get(K pk) throws NoSuchElementException, InitException;
    void modify(K pk, V e) throws IOException, InitException, NoSuchElementException, IncorrectInputException;
    ArrayList<V> list(Predicate<V> p) throws InitException, NoSuchElementException;
}
