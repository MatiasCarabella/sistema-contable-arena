import view.ConsoleMenu;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application entry point for the Accounting Management System.
 * Initializes and starts the console-based user interface.
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.info("Starting Accounting Management System");
            ConsoleMenu menu = new ConsoleMenu();
            menu.start();
            LOGGER.info("Application terminated successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Fatal error in application", e);
            System.err.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }
}
