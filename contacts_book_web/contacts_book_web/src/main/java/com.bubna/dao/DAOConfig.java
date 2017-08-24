package com.bubna.dao;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class DAOConfig {

    @Bean(name = "contactDAO")
    @Scope("application")
    @PersistenceContext
    public EntityDAO contactEntityDAO() {
        return new ContactEntityDAO();
    }

    @Bean(name = "groupDAO")
    @Scope("application")
    @PersistenceContext
    public EntityDAO groupEntityDAO() {
        return new GroupEntityDAO();
    }

    @Bean(name = "userDAO")
    @Scope("application")
    @PersistenceContext
    public EntityDAO userEntityDAO() {
        return new UserEntityDAO();
    }

    @Bean(name = "adminDAO")
    @Scope("application")
    @PersistenceContext
    public AdminDAO adminDAO() {
        return new CustomAdminDAO();
    }
}
