import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class BurhanLogger {
    private static BurhanLogger instance;
    private String filePath = "guild_history.log"; 

    private BurhanLogger() {}

    public static BurhanLogger getInstance() {
        if (instance == null) {
        instance = new BurhanLogger();
        }
        return instance;
    }

    public void setFilePath(String path) {
        this.filePath = path;
    }

    public void log(LogCategory category, String actor, String action) {
        // TODO
    }

    public void log(LogCategory category, String actor, String action, Exception e) {
        // TODO  
    }

    private void writeToFile(String message) {
        // TODO
    }
}
