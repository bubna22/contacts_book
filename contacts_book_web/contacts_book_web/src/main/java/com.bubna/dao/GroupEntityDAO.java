package com.bubna.dao;

import com.bubna.dao.handler.RSGroupHandler;
import com.bubna.dao.map.GroupMap;
import com.bubna.dao.map.UserMap;
import com.bubna.exception.CustomException;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class GroupEntityDAO extends AbstractEntityDAO<Group> {

    GroupEntityDAO() {
        super();
    }

    @Override
    public void create() throws CustomException {
        update();
    }

    @Override
    public void delete() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT group_modify( ?, ?, ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));
            Group inputGroup = (Group) extraData.get("entity");
            preparedStatement.setObject(2, inputGroup.getName());
            preparedStatement.setObject(3, null);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating group":e.getMessage());
        }
    }

    @Override
    public void update() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT group_modify( ?, ?, ? );");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));
            Group inputGroup = (Group) extraData.get("entity");
            preparedStatement.setObject(2, inputGroup.getName());
            preparedStatement.setObject(3, new GroupMap(inputGroup));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating group":e.getMessage());
        }
    }

    @Override
    public Collection<Group> list() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT data FROM group_list( ? ) as data;");
            preparedStatement.setObject(1, new UserMap((User) extraData.get("user")));

            RSGroupHandler groupHandler = new RSGroupHandler(preparedStatement.executeQuery());
            return groupHandler.call();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating group":e.getMessage());
        }
    }
}
