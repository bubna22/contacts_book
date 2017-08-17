package com.bubna.dao;

import com.bubna.exception.CustomException;

import java.util.ArrayList;

public interface DAO {
    void prepare() throws CustomException;
    void close() throws CustomException;
}
