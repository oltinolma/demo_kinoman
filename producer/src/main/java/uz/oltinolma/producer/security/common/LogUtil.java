package uz.oltinolma.producer.security.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final String TAG = "KINOMAN";
    public static Logger getInstance() {
        String loggerName = TAG + "_" + callingClassShortName();
        return LoggerFactory.getLogger(loggerName);
    }

    private static String callingClassShortName() {
        String callingClassName =
                Thread.currentThread().getStackTrace()[3].getClassName();
        int i = callingClassName.lastIndexOf(".");
        if (i > 0)
            callingClassName = callingClassName.substring(i + 1);
        return callingClassName;
    }
}