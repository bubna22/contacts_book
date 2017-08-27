package com.bubna.dao;

import com.bubna.exception.CustomException;

import java.util.Collection;

public interface EntityDAO<V> extends DAO {
    void addExtra(String key, Object extra);
    void create() throws CustomException;
    void delete() throws CustomException;
    void update() throws CustomException;
    V get() throws CustomException;
    Collection<V> list() throws CustomException;
}
