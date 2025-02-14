package com.sends.hackerbox.service;

public interface TerminalService {


    String executeCommand(String sessionId, String command);

    void closeSession(String sessionId);
}
