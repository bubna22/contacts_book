package com.bubna.dao;

import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by test on 17.07.2017.
 */
public interface DAO<K, V, T> {
    DAO setUpdatedSource(T source) throws CustomException;
    User login(User acc) throws CustomException;
    User unlogin(User acc) throws CustomException;
    V get(User acc, K pk) throws CustomException;
    void modify(User acc, K pk, V e) throws CustomException;
    ArrayList<V> list(User acc, Predicate<V> p) throws CustomException;
}
