package com.bubna.view;

import com.bubna.controller.AbstractController;
import com.bubna.controller.Controller;
import com.bubna.dao.DBDAOFactory;
import com.bubna.exception.CustomException;
import com.bubna.model.Model;
import com.bubna.model.ObservablePart;
import com.bubna.model.UserModel;
import com.bubna.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends AbstractServlet {

    private Controller controller;
    private ArrayList<String> loggedIn;//from hackers :))

    @Override
    public void init() throws ServletException {
        loggedIn = new ArrayList<>();
        ObservablePart observablePart = new ObservablePart();
        observablePart.addObserver(this);
        try {
            Model userModel = new UserModel(observablePart, DBDAOFactory.INSTANCE.getDAO(User.class));
            controller = new AbstractController(userModel);
        } catch (CustomException e) {
            e.printStackTrace();//TODO: err service
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equals("user")) {
                    boolean equals = false;
                    for (String login : loggedIn) {
                        if (login.equals(c.getValue())) {
                            equals = true;
                            break;
                        }
//                        if (!equals) resp.sendRedirect("/contacts_book_web/");
                    }
                    //TODO: redirect to main servlet
                }
            }
        }
        PrintWriter out = resp.getWriter();
        out.print("<form method=\"post\">\n" +
                "    <input type=\"text\" name=\"username\">\n" +
                "    <input type=\"password\" name=\"password\">\n" +
                "    <input type=\"submit\">\n" +
                "</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("username");
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
//            resp.sendRedirect("/contacts_book_web/");
            return;
        }

        User u = new User(login, pass, "1");

        try {
            waitAnswer("login", () -> {
                controller.addInput("user", u);
                controller.listen("login");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();//TODO: err service
        }
        resp.getWriter().print(getAnswer("login"));
        if (getAnswer("login").equals(Boolean.TRUE)) {
            loggedIn.add(u.getName());
            Cookie loginCoolie = new Cookie("user", getAnswer("login").toString());//TODO: user entity
            loginCoolie.setMaxAge(30 * 60);
            resp.addCookie(loginCoolie);
//            resp.sendRedirect("/contacts_book_web/main");
            return;
        }
        //TODO: show err msg and err service
    }
}
