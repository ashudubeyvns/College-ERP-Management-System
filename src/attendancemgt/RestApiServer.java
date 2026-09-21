package attendancemgt;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lightweight REST API server for the Attendance Management System.
 * Uses only Java built-in com.sun.net.httpserver APIs - no external dependencies.
 *
 * Endpoints:
 *   GET /api/health          - Health check
 *   GET /api/session         - Current session info (JSON)
 *   GET /api/courses         - List of courses (JSON)
 *   GET /api/students        - Student list (JSON)
 *   GET /api/stats           - Dashboard statistics (JSON)
 *   GET /api/attendance?course=X&amp;sem=Y  - Attendance records (JSON)
 */
public class RestApiServer {

    private static final int PRIMARY_PORT = 8080;
    private static final int FALLBACK_PORT = 8081;
    private static int activePort = -1;
    private static HttpServer server;
    private static boolean running = false;

    /**
     * Start the REST API server on a background thread.
     * Tries primary port first, falls back to alternative if busy.
     */
    public static void start() {
        if (running) return;

        // Try primary port first, then fallback
        int[] portsToTry = {PRIMARY_PORT, FALLBACK_PORT};

        for (int port : portsToTry) {
            try {
                server = HttpServer.create(new InetSocketAddress(port), 0);
                activePort = port;
                break; // Success
            } catch (IOException e) {
                // Port in use, try next
                continue;
            }
        }

        if (server == null) {
            Logger.getLogger(RestApiServer.class.getName()).warning("Both ports " + PRIMARY_PORT + " and " + FALLBACK_PORT + " are in use. REST API not available.");
            return;
        }

        // Register endpoints
        server.createContext("/api/health", new HealthHandler());
        server.createContext("/api/session", new SessionHandler());
        server.createContext("/api/courses", new CoursesHandler());
        server.createContext("/api/students", new StudentsHandler());
        server.createContext("/api/stats", new StatsHandler());
        server.createContext("/api/attendance", new AttendanceHandler());

        server.setExecutor(java.util.concurrent.Executors.newSingleThreadExecutor());
        server.start();
        running = true;

        System.out.println("[REST API] Server started on http://localhost:" + activePort);
        System.out.println("[REST API] Available endpoints:");
        System.out.println("  GET http://localhost:" + activePort + "/api/health");
        System.out.println("  GET http://localhost:" + activePort + "/api/session");
        System.out.println("  GET http://localhost:" + activePort + "/api/courses");
        System.out.println("  GET http://localhost:" + activePort + "/api/students");
        System.out.println("  GET http://localhost:" + activePort + "/api/stats");
        System.out.println("  GET http://localhost:" + activePort + "/api/attendance?course=BCA&sem=1");
    }

    /**
     * Stop the REST API server.
     */
    public static void stop() {
        if (server != null && running) {
            server.stop(1);
            running = false;
            System.out.println("[REST API] Server stopped.");
        }
    }

    public static boolean isRunning() { return running; }
    public static int getPort() { return activePort > 0 ? activePort : PRIMARY_PORT; }

    // ===================== HELPER =====================

    private static void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization");

        byte[] bytes = json.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static String jsonEscape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static Map<String, String> getQueryParams(URI uri) {
        Map<String, String> params = new HashMap<>();
        String query = uri.getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] pair = param.split("=", 2);
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                } else if (pair.length == 1) {
                    params.put(pair[0], "");
                }
            }
        }
        return params;
    }

    // ===================== HANDLERS =====================

    /**
     * GET /api/health - Health check
     */
    static class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "{\n"
                + "  \"status\": \"ok\",\n"
                + "  \"service\": \"Attendance Management System\",\n"
                + "  \"version\": \"2.0\",\n"
                + "  \"timestamp\": \"" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\",\n"
                + "  \"serverRunning\": true,\n"
                + "  \"sessionActive\": " + (UserSession.getInstance() != null) + "\n"
                + "}";
            sendJson(exchange, 200, json);
        }
    }

    /**
     * GET /api/session - Current session info
     */
    static class SessionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            UserSession session = UserSession.getInstance();
            String json;
            if (session != null) {
                json = "{\n"
                    + "  \"active\": true,\n"
                    + "  \"username\": \"" + jsonEscape(session.getUsername()) + "\",\n"
                    + "  \"fullName\": \"" + jsonEscape(session.getFullName()) + "\",\n"
                    + "  \"role\": \"" + jsonEscape(session.getRole().name()) + "\",\n"
                    + "  \"roleDisplay\": \"" + jsonEscape(session.getRole().getDisplayName()) + "\",\n"
                    + "  \"sessionId\": \"" + jsonEscape(session.getSessionId()) + "\",\n"
                    + "  \"email\": \"" + jsonEscape(session.getEmail()) + "\",\n"
                    + "  \"department\": \"" + jsonEscape(session.getDepartment()) + "\",\n"
                    + "  \"loginTime\": \"" + jsonEscape(session.getFormattedLoginTime()) + "\",\n"
                    + "  \"canManageAttendance\": " + session.canManageAttendance() + "\n"
                    + "}";
            } else {
                json = "{\n"
                    + "  \"active\": false,\n"
                    + "  \"message\": \"No active session. Please log in.\"\n"
                    + "}";
            }
            sendJson(exchange, 200, json);
        }
    }

    /**
     * GET /api/courses - List of courses
     */
    static class CoursesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append("{\n  \"count\": ").append(SubjectData.COURSE_NAMES.length).append(",\n");
            sb.append("  \"courses\": [\n");
            for (int i = 0; i < SubjectData.COURSE_NAMES.length; i++) {
                sb.append("    {\n");
                sb.append("      \"id\": \"").append(jsonEscape(SubjectData.COURSE_IDS[i])).append("\",\n");
                sb.append("      \"name\": \"").append(jsonEscape(SubjectData.COURSE_NAMES[i])).append("\",\n");
                sb.append("      \"fullName\": \"").append(jsonEscape(SubjectData.COURSE_FULL_NAMES[i])).append("\",\n");
                sb.append("      \"semesters\": ").append(SubjectData.COURSE_SEMESTERS[i]).append(",\n");
                sb.append("      \"department\": \"").append(jsonEscape(SubjectData.COURSE_DEPARTMENTS[i])).append("\",\n");
                sb.append("      \"academicYear\": \"").append(jsonEscape(SubjectData.COURSE_ACADEMIC_YEARS[i])).append("\"\n");
                sb.append("    }");
                if (i < SubjectData.COURSE_NAMES.length - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("  ]\n}");
            sendJson(exchange, 200, sb.toString());
        }
    }

    /**
     * GET /api/students - Student list (static sample data)
     */
    static class StudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = "{\n"
                + "  \"count\": 12,\n"
                + "  \"students\": [\n"
                + "    {\"rollNo\": \"24SCSE1180456\", \"name\": \"Aarav Sharma\", \"course\": \"B.Tech CSE\", \"semester\": 3, \"section\": \"A\", \"email\": \"aarav.sharma@example.com\"},\n"
                + "    {\"rollNo\": \"24SCSE1180381\", \"name\": \"Priya Patel\", \"course\": \"B.Tech CSE\", \"semester\": 3, \"section\": \"A\", \"email\": \"priya.patel@example.com\"},\n"
                + "    {\"rollNo\": \"24SCSE2030222\", \"name\": \"Rohit Singh\", \"course\": \"BCA\", \"semester\": 4, \"section\": \"B\", \"email\": \"rohit.singh@example.com\"},\n"
                + "    {\"rollNo\": \"101\", \"name\": \"Ashutosh Dubey\", \"course\": \"MCA\", \"semester\": 2, \"section\": \"A\", \"email\": \"ashutosh.dubey@example.com\"},\n"
                + "    {\"rollNo\": \"102\", \"name\": \"Aditya Verma\", \"course\": \"MCA\", \"semester\": 2, \"section\": \"B\", \"email\": \"aditya.verma@example.com\"},\n"
                + "    {\"rollNo\": \"103\", \"name\": \"Ananya Reddy\", \"course\": \"B.Tech IT\", \"semester\": 5, \"section\": \"A\", \"email\": \"ananya.reddy@example.com\"},\n"
                + "    {\"rollNo\": \"104\", \"name\": \"Arjun Nair\", \"course\": \"B.Tech IT\", \"semester\": 5, \"section\": \"B\", \"email\": \"arjun.nair@example.com\"},\n"
                + "    {\"rollNo\": \"105\", \"name\": \"Diya Joshi\", \"course\": \"B.Sc CS\", \"semester\": 4, \"section\": \"A\", \"email\": \"diya.joshi@example.com\"},\n"
                + "    {\"rollNo\": \"106\", \"name\": \"Kabir Das\", \"course\": \"B.Sc CS\", \"semester\": 4, \"section\": \"B\", \"email\": \"kabir.das@example.com\"},\n"
                + "    {\"rollNo\": \"107\", \"name\": \"Ishita Mehta\", \"course\": \"B.Tech CSE\", \"semester\": 3, \"section\": \"A\", \"email\": \"ishita.mehta@example.com\"},\n"
                + "    {\"rollNo\": \"108\", \"name\": \"Vikram Rao\", \"course\": \"MCA\", \"semester\": 2, \"section\": \"A\", \"email\": \"vikram.rao@example.com\"},\n"
                + "    {\"rollNo\": \"109\", \"name\": \"Neha Kapoor\", \"course\": \"BCA\", \"semester\": 4, \"section\": \"B\", \"email\": \"neha.kapoor@example.com\"}\n"
                + "  ]\n"
                + "}";
            sendJson(exchange, 200, json);
        }
    }

    /**
     * GET /api/stats - Dashboard statistics
     */
    static class StatsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String today = LocalDate.now().toString();
            String json = "{\n"
                + "  \"date\": \"" + today + "\",\n"
                + "  \"totalStudents\": 320,\n"
                + "  \"totalFaculty\": 25,\n"
                + "  \"todayPresent\": 287,\n"
                + "  \"todayAbsent\": 33,\n"
                + "  \"attendancePercentage\": 89.7,\n"
                + "  \"totalCourses\": 6,\n"
                + "  \"activeSemesters\": \"1-8\",\n"
                + "  \"overallAttendance\": 87.5,\n"
                + "  \"highestCourse\": \"BCA\",\n"
                + "  \"highestAttendance\": 92.3,\n"
                + "  \"lowestCourse\": \"B.Tech IT\",\n"
                + "  \"lowestAttendance\": 78.1\n"
                + "}";
            sendJson(exchange, 200, json);
        }
    }

    /**
     * GET /api/attendance?course=BCA&sem=1 - Attendance records
     */
    static class AttendanceHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = getQueryParams(exchange.getRequestURI());
            String course = params.getOrDefault("course", "BCA");
            String sem = params.getOrDefault("sem", "1");

            // Get subjects for the course and semester
            String[][] subjects = SubjectData.getSubjectsForCourseSemester(course, Integer.parseInt(sem));

            StringBuilder sb = new StringBuilder();
            sb.append("{\n");
            sb.append("  \"course\": \"").append(jsonEscape(course)).append("\",\n");
            sb.append("  \"semester\": ").append(sem).append(",\n");
            sb.append("  \"date\": \"").append(LocalDate.now()).append("\",\n");
            sb.append("  \"subjectCount\": ").append(subjects.length).append(",\n");
            sb.append("  \"subjects\": [\n");
            for (int i = 0; i < subjects.length; i++) {
                String[] subj = subjects[i];
                sb.append("    {\n");
                sb.append("      \"id\": \"").append(jsonEscape(subj[0])).append("\",\n");
                sb.append("      \"code\": \"").append(jsonEscape(subj[3])).append("\",\n");
                sb.append("      \"name\": \"").append(jsonEscape(subj[4])).append("\",\n");
                sb.append("      \"type\": \"").append(jsonEscape(subj[6])).append("\",\n");
                sb.append("      \"credits\": ").append(subj[7]).append(",\n");
                sb.append("      \"attendance\": \"").append((80 + (i * 3) % 20)).append("%\"\n");
                sb.append("    }");
                if (i < subjects.length - 1) sb.append(",");
                sb.append("\n");
            }
            sb.append("  ]\n}");

            sendJson(exchange, 200, sb.toString());
        }
    }
}
