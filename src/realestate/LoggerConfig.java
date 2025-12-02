package realestate;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    private static final Logger LOGGER = Logger.getLogger("realestate");

    static {
        try {
            // Remove default console handlers
            LogManager.getLogManager().reset();
            LOGGER.setLevel(Level.ALL);

            // Console handler
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            ch.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(ch);

            // File handler – creates realEstateApp.log in project root
            FileHandler fh = new FileHandler("realEstateApp.log", true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);

        } catch (IOException e) {
            System.err.println("Could not set up logger: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}