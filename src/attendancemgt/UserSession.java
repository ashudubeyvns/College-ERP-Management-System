package attendancemgt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Singleton session manager for tracking the current logged-in user.
 * Stores user identity, role, and session metadata.
 */
public class UserSession {

    public enum Role {
        ADMIN("Administrator"),
        FACULTY("Faculty"),
        STUDENT("Student");

        private final String displayName;
        Role(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }

    private static UserSession instance;

    private String username;
    private String fullName;
    private Role role;
    private String sessionId;
    private LocalDateTime loginTime;
    private String email;
    private String department;

    private UserSession() {}

    /**
     * Get the current session. Returns null if no user is logged in.
     */
    public static UserSession getInstance() {
        return instance;
    }

    /**
     * Create a new session for the given user.
     */
    public static UserSession createSession(String username, String fullName, Role role) {
        UserSession session = new UserSession();
        session.username = username;
        session.fullName = fullName;
        session.role = role;
        session.sessionId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        session.loginTime = LocalDateTime.now();
        session.email = username.contains("@") ? username : username + "@attendance-system.com";
        session.department = "Computer Science & Applications";
        instance = session;
        return session;
    }

    /**
     * Destroy the current session (logout).
     */
    public static void destroySession() {
        instance = null;
    }

    // Getters
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public Role getRole() { return role; }
    public String getSessionId() { return sessionId; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }

    /**
     * Returns formatted login time string.
     */
    public String getFormattedLoginTime() {
        return loginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Check if the current user has admin privileges.
     */
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    /**
     * Check if the current user is faculty.
     */
    public boolean isFaculty() {
        return role == Role.FACULTY;
    }

    /**
     * Check if the current user is a student.
     */
    public boolean isStudent() {
        return role == Role.STUDENT;
    }

    /**
     * Check if the current user can edit/manage attendance.
     */
    public boolean canManageAttendance() {
        return role == Role.ADMIN || role == Role.FACULTY;
    }

    /**
     * Return a summary string for the session.
     */
    public String toSummaryString() {
        return String.format("[%s] %s (%s) - Logged in: %s",
            sessionId, fullName, role.getDisplayName(), getFormattedLoginTime());
    }
}
