package tests;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    public static Logger setupLogger(String className) {
        Logger logger = Logger.getLogger(className);
        try {
            FileHandler fileHandler = new FileHandler("game_logs.log", true);  // Лог записывается в файл "game_logs.log"
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.severe("Ошибка при настройке логгера: " + e.getMessage());
        }
        return logger;
    }
}