package com.bubna.view.server;

import com.bubna.controller.Controller;
import com.bubna.exception.CustomException;
import com.bubna.server.SocketChecker;
import com.bubna.utils.TransferObject;
import com.bubna.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerView implements View {

    protected Socket socket;
    private ConcurrentLinkedQueue<TransferObject> wMessages;
    private Thread rThread;
    private Thread wThread;

    public ServerView(Controller controller) {
        wMessages = new ConcurrentLinkedQueue<>();
        SocketChecker.INSTANCE.getObservable().addObserver(this);
        rThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (socket == null) continue;
                        if (socket.isClosed()) break;
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        controller.listen(dis.readUTF());
                    } catch (CustomException | IOException e) {
                        close();
                        e.printStackTrace();//TODO: logs
                    }
                }
            }
        });
        wThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (wMessages.isEmpty()) {
                        synchronized (wThread) {
                            try {
                                wThread.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();//TODO: logs
                            }
                        }
                    }
                    TransferObject transferObject = wMessages.poll();
                    try {
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF(transferObject.serialize());
                    } catch (IOException e) {
                        close();
                        e.printStackTrace();//TODO: logs
                    }
                }
            }
        });

    }

    private void close() {
        if (socket != null && !socket.isClosed()) try {
            socket.close();
        } catch (IOException e1) {
            e1.printStackTrace();//TODO: logs
        }
        wThread.interrupt();
        rThread.interrupt();
        SocketChecker.INSTANCE.getObservable().deleteObserver(this);
    }

    protected void addMessage(TransferObject transferObject) {
        wMessages.offer(transferObject);
        synchronized (wThread) {
            wThread.notify();
        }
    }

    @Override
    public void setData(int key, Object data) throws CustomException {
        if (key != View.SOCKET) throw new CustomException("incorrect input data by key " + key);
        this.socket = (Socket) data;
        rThread.start();
        wThread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Exception) {
            Exception e = (Exception) arg;
            TransferObject transferObject =
                    new TransferObject("err", "throw", e.getMessage()==null?"error":e.getMessage());
            addMessage(transferObject);
        } else if (arg instanceof TransferObject) {
            addMessage((TransferObject) arg);
        }
    }
}
