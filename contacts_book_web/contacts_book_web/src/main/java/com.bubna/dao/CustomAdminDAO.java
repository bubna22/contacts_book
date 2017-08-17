package com.bubna.dao;

import com.bubna.exception.CustomException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomAdminDAO implements AdminDAO {

    private Connection connection;

    CustomAdminDAO() {
        super();
    }

    @Override
    public void prepare() throws CustomException {
        try {
            InitialContext ctx = new InitialContext();
            Context initCtx = (Context) ctx.lookup("java:/comp/env");
            DataSource ds = (DataSource) initCtx.lookup("jdbc/postgres");
            connection = ds.getConnection();
        } catch (NamingException | SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while getting connection":e.getMessage());
        }
    }

    @Override
    public void close() throws CustomException {
        try {
            if (connection != null && connection.isClosed()) connection.close();
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while closing connection":e.getMessage());
        }
    }

    public Integer userCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_count;");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public ArrayList<String> userContactsCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_contacts_count;");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<String> dataReturned = new ArrayList<>();
            while (rs.next()) {
                dataReturned.add(rs.getString(1) + " - " + rs.getInt(2));
            }
            return dataReturned;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public ArrayList<String> userGroupsCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user_groups_count;");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<String> dataReturned = new ArrayList<>();
            while (rs.next()) {
                dataReturned.add(rs.getString(1) + " - " + rs.getInt(2));
            }
            return dataReturned;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public Integer userAVGGroupsCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM avg_users_in_group_count;");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public Integer userAVGContactsCount() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM avg_contacts_by_user_count;");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

    public ArrayList<String> inactiveUsers() throws CustomException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM get_inactive_users;");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<String> dataReturned = new ArrayList<>();
            while (rs.next()) {
                dataReturned.add(rs.getString(2));
            }
            return dataReturned;
        } catch (SQLException e) {
            throw new CustomException(e.getMessage()==null?"error while creating contact":e.getMessage());
        }
    }

}
