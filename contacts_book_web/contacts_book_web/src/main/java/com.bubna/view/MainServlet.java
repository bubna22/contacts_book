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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "MainServlet", urlPatterns = {"/contacts", "/groups"}, loadOnStartup = 2)
public class MainServlet extends AbstractServlet {

    public static HttpServletResponse resp;

    private Controller contactController;
    private Controller groupController;

    @Override
    public void init() throws ServletException {
        ObservablePart observablePart = new ObservablePart();
        observablePart.addObserver(this);
        try {
            Model contactModel = new EntityModel(observablePart, DBDAOFactory.INSTANCE.getEntityDAO(Contact.class));
            contactController = new AbstractController(contactModel);

            Model groupModel = new EntityModel(observablePart, DBDAOFactory.INSTANCE.getEntityDAO(Group.class));
            groupController = new AbstractController(groupModel);
        } catch (CustomException e) {
            e.printStackTrace();//TODO: err service
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String s = null;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("user")) {
                s = c.getValue();
                break;
            }
        }
        if (s == null) {
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }
        final String user = s;
        if (req.getRequestURL().toString().endsWith("contacts")) {
            this.resp = resp;
            try {
                waitAnswer("list", () -> {
                    contactController.addInput("user", new User(user, null, "1"));
                    contactController.listen("list");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<Contact> contacts = (ArrayList<Contact>) getAnswer("list");
            req.setAttribute("contacts", contacts);
            req.getRequestDispatcher("Contacts.jsp").forward(req, resp);
        } else if (req.getRequestURL().toString().endsWith("groups")) {
            try {
                waitAnswer("list", () -> {
                    groupController.addInput("user", new User(user, null, "1"));
                    groupController.listen("list");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Group> groups = (ArrayList<Group>) getAnswer("list");
            req.setAttribute("groups", groups);
            req.getRequestDispatcher("Groups.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String s = null;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("user")) {
                s = c.getValue();
                break;
            }
        }
        if (s == null) {
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }
        final String user = s;
        String action = req.getParameter("action");
        String entityType = req.getParameter("type");
        if (entityType.equals("contact")) {
            String name = req.getParameter("c_name");
            String email = req.getParameter("c_email");
            Integer num = req.getParameter("c_num")==null?0:Integer.parseInt(req.getParameter("c_num"));
            String skype = req.getParameter("c_skype");
            String telegram = req.getParameter("c_telegram");
            String groupName = req.getParameter("g_name");

            try {
                waitAnswer(action, () -> {
                    synchronized (contactController) {
                        contactController.addInput("user", new User(user, null, "1"));
                        contactController.addInput("entity", new Contact(name, email, num, skype, telegram, groupName));
                        contactController.listen(action);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/contacts");
        } else if (entityType.equals("group")) {
            String name = req.getParameter("g_name");
            Integer color = req.getParameter("g_color")==null?0:Integer.parseInt(req.getParameter("g_color"));

            try {
                waitAnswer(action, () -> {
                    synchronized (contactController) {
                        groupController.addInput("user", new User(user, null, "1"));
                        groupController.addInput("entity", new Group(name, color));
                        groupController.listen(action);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/groups");
        }
    }
}
