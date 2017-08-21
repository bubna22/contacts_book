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

@WebServlet(urlPatterns = "/login", loadOnStartup = 1)
public class LoginServlet extends AbstractServlet {

    private Controller controller;

    @Override
    public void init() throws ServletException {
        ObservablePart observablePart = new ObservablePart();
        observablePart.addObserver(this);
        try {
            Model userModel = new UserModel(observablePart, DBDAOFactory.INSTANCE.getEntityDAO(User.class));
            controller = new AbstractController(userModel);
        } catch (CustomException e) {
            e.printStackTrace();//TODO: err service
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/main");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("username");
        String pass = req.getParameter("password");

        if (login == null || pass == null) {
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }

        User u = new User();
        u.setLogin(login);
        u.setPass(pass);
        u.setIp("1");

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
            Cookie loginCoolie = new Cookie("user", login);
            loginCoolie.setMaxAge(30 * 60);
            resp.addCookie(loginCoolie);
            resp.sendRedirect(req.getContextPath() + "/main");
            return;
        }
        //TODO: show err msg and err service
    }
}
