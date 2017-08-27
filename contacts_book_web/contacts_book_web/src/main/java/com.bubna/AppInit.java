package com.bubna;

import com.bubna.controller.*;
import com.bubna.dao.DAOConfig;
import com.bubna.dao.DataSourceConfig;
import com.bubna.dao.cmd.CmdConfig;
import com.bubna.model.ModelConfig;
import com.bubna.model.entity.EntityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                DataSourceConfig.class,
                DAOConfig.class,
                CmdConfig.class,
                ModelConfig.class,
                EntityConfig.class,
                DefaultController.class,
                AdminToolsController.class,
                ContactController.class,
                GroupController.class,
                UserController.class,
                RESTContactController.class,
                RESTGroupController.class
        };
    }

    // Тут добавляем конфигурацию, в которой инициализируем ViewResolver
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                SecurityConfig.class,
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
