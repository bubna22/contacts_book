package com.bubna;

import com.bubna.controller.EntityController;
import com.bubna.dao.AbstractFactory;
import com.bubna.dao.DAOFactory;
import com.bubna.exceptions.InitException;
import com.bubna.model.ContactModel;
import com.bubna.model.GroupModel;
import com.bubna.model.ObservablePart;
import com.bubna.model.entities.Contact;
import com.bubna.model.entities.Group;
import com.bubna.view.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Observable;

/**
 * Created by bubna on 11.07.2017.
 *
 */
public class Init extends Application {

    public Init() {}

    /**
     * start JavaFX program; create MainView and LogView
     * @param primaryStage from superclass
     */

    @Override
    public void start(Stage primaryStage) throws InitException {
        StackPane root = new StackPane();
        primaryStage.setResizable(true);

        Scene scene = new Scene(root,800,400);

        ObservablePart observable = new ObservablePart();

        DAOFactory daoFactory = AbstractFactory.INSTANCE.getFactory();

        EntityController<Contact> contactEntityController = new EntityController<>(new ContactModel(daoFactory, observable));
        EntityController<Group> groupEntityController = new EntityController<>(new GroupModel(daoFactory, observable));

        ViewFactory.INSTANCE.Init(root);
        ViewFactory.INSTANCE.addView(observable, contactEntityController, Contact.class);
        ViewFactory.INSTANCE.addView(observable, groupEntityController, Group.class);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("work hard, don't play");
        launch(args);
    }

}
