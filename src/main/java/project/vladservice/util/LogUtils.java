package project.vladservice.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {

    private static final String LOG_FILE = "chatbot_logs.txt";

    public static void log(String message) {
        try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            printWriter.println("[" + timestamp + "] " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logRequest(String question) {
        log("User asked: " + question);
    }

    public static void logResponse(String response) {
        log("Bot responded: " + response);
    }
}
