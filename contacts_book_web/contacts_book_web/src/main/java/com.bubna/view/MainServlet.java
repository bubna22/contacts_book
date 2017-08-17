package com.bubna.view;

import com.bubna.controller.AbstractController;
import com.bubna.controller.Controller;
import com.bubna.dao.DBDAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.EntityModel;
import com.bubna.model.Model;
import com.bubna.model.ObservablePart;
import com.bubna.model.entity.Contact;
import com.bubna.model.entity.Group;
import com.bubna.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observer;

@WebServlet(name = "MainServlet", urlPatterns = "/main", loadOnStartup = 2)
public class MainServlet extends AbstractServlet {

    private Controller contactController;
    private Controller groupController;

    @Override
    public void init() throws ServletException {
        ObservablePart observablePart = new ObservablePart();
        observablePart.addObserver(this);
        try {
            Model contactModel = new EntityModel(observablePart, DBDAOFactory.INSTANCE.getDAO(Contact.class));
            contactController = new AbstractController(contactModel);

            Model groupModel = new EntityModel(observablePart, DBDAOFactory.INSTANCE.getDAO(Group.class));
            groupController = new AbstractController(groupModel);
        } catch (CustomException e) {
            e.printStackTrace();//TODO: err service
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        Cookie[] cookies = req.getCookies();
//        if (cookies == null) return;
//        boolean isLoggedIn = false;
//        for (int i = 0; i < cookies.length; i++) {
//            Cookie c = cookies[i];
//            if (c.getName().equals("user")) {
//                isLoggedIn = true;
//                break;
//            }
//        }
//        if (!isLoggedIn) {
//            resp.sendRedirect("/");
//            return;
//        }

        PrintWriter printWriter = resp.getWriter();

        try {
            waitAnswer("list", () -> {
                groupController.addInput("user", new User("bubna", "test", "1"));
                groupController.listen("list");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Group> groups = (ArrayList<Group>) getAnswer("list");

        try {
            waitAnswer("list", () -> {
                contactController.addInput("user", new User("bubna", "test", "1"));
                contactController.listen("list");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Contact> contacts = (ArrayList<Contact>) getAnswer("list");
        for (int i = 0; i < contacts.size(); i++) {
            printWriter.println(contacts.get(i).toString());
        }
        printWriter.println("--------------------------");
        for (int i = 0; i < groups.size(); i++) {
            printWriter.println(groups.get(i).toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
