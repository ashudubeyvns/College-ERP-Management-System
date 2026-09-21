package attendancemgt;

/**
 * Callback interface for dashboard navigation.
 * Used by module panels (MarkAttendance, ViewAttendance, etc.) to navigate
 * back to the home screen or attendance section without coupling to a specific dashboard implementation.
 */
public interface DashboardCallback {
    /**
     * Navigate back to the home/dashboard view.
     */
    void showHome();

    /**
     * Navigate to the attendance management section.
     */
    void showAttendance();
}
