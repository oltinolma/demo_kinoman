package uz.oltinolma.producer.security.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
   public static Logger getInstance() {
      String callingClassName = 
         Thread.currentThread().getStackTrace()[2].getClass().getCanonicalName();
      return LoggerFactory.getLogger(callingClassName);
   }
}