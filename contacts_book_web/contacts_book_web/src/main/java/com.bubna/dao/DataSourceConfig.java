package com.bubna.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DataSourceConfig {

    @Bean(name = "sessionFactory")
    @Scope("application")
    public SessionFactory sessionFactory() {
        try {
            return new org.hibernate.cfg.Configuration().configure(
                    "hibernate/hibernate.cfg.xml")
                    .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Bean(name = "session")
    @Scope("prototype")
    public Session session() {
        return sessionFactory().openSession();
    }
}
