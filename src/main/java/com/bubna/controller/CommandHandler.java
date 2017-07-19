package com.bubna.controller;

interface CommandHandler {

    void setNext(CommandHandler next);
    void handle(String cmd);
}
