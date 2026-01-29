package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler {
    private static final String ERROR_LOG_PATH = "error.log";
    public static void writeToErrorLog(Exception e) {
        try (var out = new PrintWriter(new BufferedWriter(new FileWriter(ERROR_LOG_PATH, true)))) {
            out.print(e.getMessage() + ": ");
            e.printStackTrace(out);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
