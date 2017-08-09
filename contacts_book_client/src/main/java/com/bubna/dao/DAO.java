package com.bubna.dao;

import com.bubna.exceptions.IncorrectInputException;
import com.bubna.exceptions.InitException;
import com.bubna.exceptions.NoSuchElementException;
import com.bubna.model.entities.User;
import com.bubna.utils.TransferObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by test on 17.07.2017.
 */
public interface DAO<K, V, T> {
    void sendRequest(TransferObject transferObject) throws IOException;
    DAO setUpdatedSource(T source) throws InitException;
    User login(User acc) throws InitException, IOException;
    User unlogin(User acc) throws InitException, IOException;
    V get(User acc, K pk) throws NoSuchElementException, InitException;
    void modify(User acc, K pk, V e) throws IOException, InitException, NoSuchElementException, IncorrectInputException;
    ArrayList<V> list(User acc, Predicate<V> p) throws InitException, NoSuchElementException;
}
