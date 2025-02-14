package com.sends.hackerbox.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TerminalUtil {
    private Process process;
    private OutputStream outputStream;
    private BlockingQueue<String> outputQueue;
    private Thread readerThread;

    public TerminalUtil() {
        try {
            // 根据不同操作系统启动不同的 shell
            String os = System.getProperty("os.name").toLowerCase();
            String shell;
            if (os.contains("win")) {
                shell = "cmd.exe";
            } else {
                shell = "/bin/bash";
            }
            ProcessBuilder processBuilder = new ProcessBuilder(shell);
            process = processBuilder.start();
            outputStream = process.getOutputStream();
            outputQueue = new LinkedBlockingQueue<>();

            // 启动一个线程来持续读取进程的输出流
            readerThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputQueue.put(line + "\n");
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String executeCommand(String command) {
        try {
            // 向进程的输入流写入命令
            outputStream.write((command + "\n").getBytes());
            outputStream.flush();

            Thread.sleep(100);

            StringBuilder output = new StringBuilder();
            String line;
            // 从队列中取出输出信息
            while ((line = outputQueue.poll()) != null ) {
                output.append(line);
            }
            return output.toString();
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (process != null) {
                process.destroy();
            }
            if (readerThread != null) {
                readerThread.interrupt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
