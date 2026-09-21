package attendancemgt;

import java.io.IOException;
import javax.swing.SwingUtilities;

/**
 * College ERP - Attendance Management System
 * Main entry point with splash screen and role-based access.
 *
 * @author ashutoshdubey
 */
public class AttendanceMgt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Show splash screen, then start the app when loading completes
        SplashScreen.showSplashAndRun(() -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    // Start the REST API server on a background thread
                    RestApiServer.start();

                    // Open the login screen
                    new login_frame();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(AttendanceMgt.class.getName())
                        .log(java.util.logging.Level.SEVERE, "Failed to start application", ex);
                }
            });
        });
    }
}
