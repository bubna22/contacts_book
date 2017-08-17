package com.bubna.view;

import com.bubna.util.Answer;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Observable;

abstract class AbstractServlet extends HttpServlet implements View {
    private HashMap<String, Object> answers = new HashMap<>();

    void waitAnswer(String key, Runnable runnable) throws InterruptedException {
        answers.put(key, null);
        runnable.run();
    }

    Object getAnswer(String key) {
        return answers.get(key);
    }

    @Override
    public void update(Observable o, Object arg) {
        Answer answer = (Answer) arg;
        answers.put(answer.getKey(), answer.getVal());
    }
}
