package ua.oa.taras.fm.filemanager;


import java.util.logging.Level;
import java.util.logging.Logger;

public class L {

    private static final Logger logger = Logger.getLogger("file_manager");

    private static final boolean IN_DEBUG = true;

    public static void debug(String message) {
        if (IN_DEBUG) {
            logger.log(Level.INFO, message);
        }
    }

    public static void warning(String message, Throwable error) {
        logger.log(Level.WARNING, message, error);
    }

    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void error(String message, Throwable error) {
        logger.log(Level.SEVERE, message, error);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }
}
