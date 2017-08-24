package com.bubna.model;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class ModelConfig {

    @Bean("adminModel")
    @Scope("application")
    public Model adminModel() {
        return new AdminModel();
    }

    @Bean("contactModel")
    @Scope("application")
    public Model contactModel() {
        return new ContactModel();
    }

    @Bean("groupModel")
    @Scope("application")
    public Model groupModel() {
        return new GroupModel();
    }

    @Bean("userModel")
    @Scope("application")
    public Model userModel() {
        return new UserModel();
    }
}
