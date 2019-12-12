package server;

import common.DateTimeUtil;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private BufferedWriter logWriter;
    private boolean logConsole = true, logFile = false;

    public Logger(String logFileName) {
        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName, true));
            logFile = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enableConsoleLogging() {
        logConsole = true;
    }

    public void enableFileLogging() {
        logFile = true;
    }

    public void disableConsoleLogging() {
        logConsole = false;
    }

    public void disableFileLogging() {
        logFile = false;
    }

    public void log(String log, int id) {
        String time = DateTimeUtil.getTimeStringHMS();
        log = time + " " + log;

        if (logConsole) {
            if (id > 0) {
                log = "[" + id + "] " + log;
            } else {
                log = "[Server] " + log;
            }
            System.out.println(log);
        }
        if (logFile) {
            try {
                logWriter.write(log + "\n");
                logWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
