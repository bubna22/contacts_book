package com.bubna.view;

import com.bubna.controller.AbstractController;
import com.bubna.controller.Controller;
import com.bubna.dao.DBDAOFactory;
import com.bubna.model.AdminModel;
import com.bubna.model.Model;
import com.bubna.model.ObservablePart;
import com.bubna.util.Answer;

import java.util.Observable;

public class JSPBean implements View {

    private static Controller controller;
    private static ObservablePart observable;

    static {
        observable = new ObservablePart();
        Model adminModel = new AdminModel(observable, DBDAOFactory.INSTANCE.getAdminDAO());
        controller = new AbstractController(adminModel);
    }

    private Object answer;

    public JSPBean() {
        observable.addObserver(this);
    }

    public void setListen(String cmd) {
        controller.listen(cmd);
    }

    @Override
    public void update(Observable o, Object arg) {
        answer = ((Answer) arg).getVal();
    }

    public Object getAnswer() {
        return answer;
    }
}
