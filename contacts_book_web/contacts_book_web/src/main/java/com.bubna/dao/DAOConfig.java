package com.bubna.dao;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class DAOConfig {

    @Bean(name = "contactDAO")
    @Scope("application")
    public EntityDAO contactEntityDAO() {
        return new ContactEntityDAO();
    }

    @Bean(name = "groupDAO")
    @Scope("application")
    public EntityDAO groupEntityDAO() {
        return new GroupEntityDAO();
    }

    @Bean(name = "userDAO")
    @Scope("application")
    public EntityDAO userEntityDAO() {
        return new UserEntityDAO();
    }

    @Bean(name = "adminDAO")
    @Scope("application")
    public AdminDAO adminDAO() {
        return new CustomAdminDAO();
    }
}
