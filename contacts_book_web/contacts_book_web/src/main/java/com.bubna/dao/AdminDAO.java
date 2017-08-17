package com.bubna.dao;

import com.bubna.exception.CustomException;

import java.util.ArrayList;

public interface AdminDAO extends DAO {
    Integer userCount() throws CustomException;
    ArrayList<String> userContactsCount() throws CustomException;
    ArrayList<String> userGroupsCount() throws CustomException;
    Integer userAVGGroupsCount() throws CustomException;
    Integer userAVGContactsCount() throws CustomException;
    ArrayList<String> inactiveUsers() throws CustomException;
}
