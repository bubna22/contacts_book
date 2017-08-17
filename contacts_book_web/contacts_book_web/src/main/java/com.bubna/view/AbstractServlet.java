package com.bubna.view;

import com.bubna.exception.CustomException;
import com.bubna.util.Answer;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Logger;

abstract class AbstractServlet extends HttpServlet implements View {
    private HashMap<String, Object> answers = new HashMap<>();

    protected void waitAnswer(String key, Runnable runnable) throws InterruptedException {
//        synchronized (this) {
            answers.put(key, null);
            runnable.run();
//            wait();
//        }
    }

    private void notifyAnswer(String key, Object val) throws CustomException {
//        try {
        answers.put(key, val);

//        } finally {
//            synchronized (this) {
//                notify();
//            }
//        }
    }

    protected Object getAnswer(String key) {
        return answers.get(key);
    }

    @Override
    public void update(Observable o, Object arg) {
        Answer answer = (Answer) arg;
        try {
            notifyAnswer(answer.getKey(), answer.getVal());
        } catch (CustomException e) {
            e.printStackTrace();//TODO: err service
        }
    }
}
