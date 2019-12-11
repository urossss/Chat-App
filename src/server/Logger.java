package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

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

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH) + 1, day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%d.%02d.%02d.", year, month, day);
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE), second = cal.get(Calendar.SECOND);
        return String.format("[%02d:%02d:%02d]", hour, minute, second);
    }

    public void log(String log, int id) {
        String dateAndTime = getTime();
        log = dateAndTime + " " + log;

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
