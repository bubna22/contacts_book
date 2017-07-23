package com.bubna.controller;

interface CoRCommandHandler {

    void setNext(CoRCommandHandler next);
    void handle(String cmd);
}
