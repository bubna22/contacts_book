package com.bubna.dao;

import com.bubna.exception.CustomException;

import java.util.Collection;

public interface DAO <V> {
    void prepare() throws CustomException;
    void addExtra(String key, Object extra);
    void create() throws CustomException;
    void delete() throws CustomException;
    void update() throws CustomException;
    Collection<V> list() throws CustomException;
    void close() throws CustomException;
}
