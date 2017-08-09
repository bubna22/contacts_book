package com.bubna.server;

import com.bubna.server.builder.MVCBuilder;
import com.bubna.server.builder.MVCDirector;
import com.bubna.server.builder.MVCServerBuilder;
import com.bubna.server.builder.MVCServerDirector;
import com.bubna.exception.CustomException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public enum ServerListener {

    INSTANCE;

    private ServerSocket serverSocket;

    ServerListener() {
        try {
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();//TODO: logs
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public void listen() throws CustomException {
        try {
            Socket newSocket = serverSocket.accept();
            MVCBuilder mvcServerBuilder = new MVCServerBuilder();
            MVCDirector mvcServerDirector = new MVCServerDirector(mvcServerBuilder, newSocket);
            mvcServerDirector.construct();
        } catch (IOException e) {
            e.printStackTrace();//TODO: logs
        }
    }
}
