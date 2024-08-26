package org.firstinspires.ftc.teamcode.util.logging;

import androidx.annotation.NonNull;

import java.security.InvalidParameterException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Logger {
    private static Logger instance;
    private ZoneId timeZone = ZoneId.of("America/Montreal");
    private ScheduledExecutorService executorService;

    private Logger() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    private void log(@NonNull LogLevel logLevel, String message) {
        String thingToLog;
        switch (logLevel) {
            case INFO:
                thingToLog = "[INFO]";
            case WARN:
                thingToLog = "[WARN]";
            case ERR:
                thingToLog = "[ERR]";
            default:
                throw new InvalidParameterException("Somehow you passed in a nonexistant log level into log().");
        }
//        ZonedDateTime zdt = instant.atZone( z );

        // write message to file
        //
    }
}
