import javafx.application.Application;
import olivia.Olivia;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Olivia.class, args);
    }
}
