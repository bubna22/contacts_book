package com.bubna.dao;

import com.bubna.exception.CustomException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public interface AdminDAO extends DAO {
    BigInteger userCount() throws CustomException;
    ArrayList<String> userContactsCount() throws CustomException;
    ArrayList<String> userGroupsCount() throws CustomException;
    BigDecimal userAVGGroupsCount() throws CustomException;
    BigDecimal userAVGContactsCount() throws CustomException;
    ArrayList<String> inactiveUsers() throws CustomException;
}
