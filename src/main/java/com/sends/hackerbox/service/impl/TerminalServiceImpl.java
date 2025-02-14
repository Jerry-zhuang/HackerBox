package com.sends.hackerbox.service.impl;

import com.sends.hackerbox.service.TerminalService;
import com.sends.hackerbox.util.TerminalUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TerminalServiceImpl implements TerminalService {

    private final Map<String, TerminalUtil> sessionExecutors = new HashMap<>();

    @Override
    public String executeCommand(String sessionId, String command) {
        TerminalUtil executor = sessionExecutors.computeIfAbsent(sessionId, k -> new TerminalUtil());
        return executor.executeCommand(command);
    }

    @Override
    public void closeSession(String sessionId) {
        TerminalUtil executor = sessionExecutors.remove(sessionId);
        if (executor != null) {
            executor.close();
        }
    }
}
