package com.bubna.model.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan
public class EntityConfig {

    @Bean("user")
    @Scope("prototype")
    public User user() {
        return new User();
    }

    @Bean("group")
    @Scope("prototype")
    public Group group() {
        return new Group();
    }

    @Bean("contact")
    @Scope("prototype")
    public Contact contact() {
        return new Contact();
    }
}
