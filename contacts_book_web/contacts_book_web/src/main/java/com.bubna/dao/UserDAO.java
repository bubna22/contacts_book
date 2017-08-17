package com.bubna.dao;

import com.bubna.dao.map.UserMap;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO<User> {

    UserDAO() {
        super();
    }

    @Override
    public void delete() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT unlogin( ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    @Override
    public void update() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT login( ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public Integer userCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_count;");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

}
