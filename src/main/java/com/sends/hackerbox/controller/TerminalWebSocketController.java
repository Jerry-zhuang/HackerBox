package com.sends.hackerbox.controller;

import com.sends.hackerbox.service.TerminalService;
import com.sends.hackerbox.service.impl.TerminalServiceImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class TerminalWebSocketController extends TextWebSocketHandler {

    private final TerminalService terminalService;

    public TerminalWebSocketController() {
        this.terminalService = new TerminalServiceImpl();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String command = message.getPayload();
        String sessionId = session.getId();
        String output = terminalService.executeCommand(sessionId, command);
        session.sendMessage(new TextMessage(output));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        terminalService.closeSession(sessionId);
        super.afterConnectionClosed(session, status);
    }
}
