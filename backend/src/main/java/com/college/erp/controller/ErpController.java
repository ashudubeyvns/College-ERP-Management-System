package com.college.erp.controller;

import com.college.erp.model.entity.*;
import com.college.erp.repository.*;
import com.college.erp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ErpController {
    private final AuthService auth;
    private final StudentRepository students;
    private final FacultyRepository faculty;
    private final AttendanceRepository attendance;
    private final AssignmentRepository assignments;
    private final SessionalRepository sessionals;
    private final NotificationRepository notifications;
    private final AuditRepository audits;

    public ErpController(AuthService auth, StudentRepository students, FacultyRepository faculty,
                         AttendanceRepository attendance, AssignmentRepository assignments,
                         SessionalRepository sessionals, NotificationRepository notifications,
                         AuditRepository audits) {
        this.auth = auth; this.students = students; this.faculty = faculty;
        this.attendance = attendance; this.assignments = assignments; this.sessionals = sessionals;
        this.notifications = notifications; this.audits = audits;
    }

    @GetMapping("/health")
    public Map<String,Object> health() {
        return Map.of("status","ok","service","College ERP Backend","mysql","configured","mongodb","configured");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        try {
            return ResponseEntity.ok(auth.login(
                body.getOrDefault("username",""),
                body.getOrDefault("password",""),
                body.getOrDefault("role","")
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("success",false,"message",e.getMessage()));
        }
    }

    // ---------------- Students ----------------
    @GetMapping("/students")
    public List<Student> students() { return students.findAll(); }

    @GetMapping("/students/{rollNo}")
    public ResponseEntity<?> student(@PathVariable String rollNo) {
        return students.findByRollNo(rollNo)
            .<ResponseEntity<?>>map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student s) { return students.save(s); }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student s) {
        s.setIdForApi(id); return students.save(s);
    }

    @PutMapping("/students/profile/{rollNo}")
    public ResponseEntity<?> updateStudentProfile(@PathVariable String rollNo, @RequestBody Student incoming) {
        return students.findByRollNo(rollNo).map(existing -> {
            if (incoming.getName()!=null) existing.setName(incoming.getName());
            if (incoming.getEmail()!=null) existing.setEmail(incoming.getEmail());
            if (incoming.getCourse()!=null) existing.setCourse(incoming.getCourse());
            if (incoming.getSemester()!=null) existing.setSemester(incoming.getSemester());
            if (incoming.getSection()!=null) existing.setSection(incoming.getSection());
            return ResponseEntity.ok(students.save(existing));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) { students.deleteById(id); }

    // ---------------- Faculty ----------------
    @GetMapping("/faculty")
    public List<Faculty> faculty() { return faculty.findAll(); }

    @PostMapping("/faculty")
    public Faculty addFaculty(@RequestBody Faculty f) { return faculty.save(f); }

    @PutMapping("/faculty/{id}")
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty f) {
        f.setIdForApi(id); return faculty.save(f);
    }

    @DeleteMapping("/faculty/{id}")
    public void deleteFaculty(@PathVariable Long id) { faculty.deleteById(id); }

    // ---------------- Attendance ----------------
    @GetMapping("/attendance")
    public List<AttendanceRecord> attendance(@RequestParam(required=false) String rollNo) {
        return rollNo == null ? attendance.findAllByOrderByDateDesc() : attendance.findByRollNoOrderByDateDesc(rollNo);
    }

    @GetMapping("/attendance/today")
    public List<AttendanceRecord> todayAttendance(@RequestParam String subjectCode) {
        return attendance.findByDateAndSubjectCodeOrderByRollNoAsc(LocalDate.now(), subjectCode);
    }

    @PostMapping("/attendance")
    public AttendanceRecord saveAttendance(@RequestBody AttendanceRecord a) {
        if (a.getDate()==null) a.setDate(LocalDate.now());
        return attendance.save(a);
    }

    @PutMapping("/attendance/{id}")
    public AttendanceRecord updateAttendance(@PathVariable Long id, @RequestBody AttendanceRecord a) {
        a.setIdForApi(id); return attendance.save(a);
    }

    @DeleteMapping("/attendance/{id}")
    public void deleteAttendance(@PathVariable Long id) { attendance.deleteById(id); }

    // ---------------- Assignments ----------------
    @GetMapping("/assignments")
    public List<Assignment> assignments() { return assignments.findAllByOrderByIdDesc(); }

    @PostMapping("/assignments")
    public Assignment addAssignment(@RequestBody Assignment a) { return assignments.save(a); }

    @PutMapping("/assignments/{id}")
    public Assignment updateAssignment(@PathVariable Long id, @RequestBody Assignment a) {
        a.setIdForApi(id); return assignments.save(a);
    }

    @DeleteMapping("/assignments/{id}")
    public void deleteAssignment(@PathVariable Long id) { assignments.deleteById(id); }

    // ---------------- Sessionals ----------------
    @GetMapping("/sessionals")
    public List<Sessional> sessionals(@RequestParam(required=false) String rollNo) {
        return rollNo == null ? sessionals.findAllByOrderByIdDesc() : sessionals.findByRollNoOrderByIdDesc(rollNo);
    }

    @PostMapping("/sessionals")
    public Sessional saveSessional(@RequestBody Sessional s) { return calculateAndSaveSessional(s); }

    @PutMapping("/sessionals/{id}")
    public Sessional updateSessional(@PathVariable Long id, @RequestBody Sessional s) {
        s.setIdForApi(id); return calculateAndSaveSessional(s);
    }

    @DeleteMapping("/sessionals/{id}")
    public void deleteSessional(@PathVariable Long id) { sessionals.deleteById(id); }

    private Sessional calculateAndSaveSessional(Sessional s) {
        int total = Objects.requireNonNullElse(s.getS1(),0)
                 + Objects.requireNonNullElse(s.getS2(),0)
                 + Objects.requireNonNullElse(s.getS3(),0);
        s.setTotal(total);
        s.setStatus(total==0 ? "Pending" : total>=30 ? "Pass" : "Needs Review");
        return sessionals.save(s);
    }

    // ---------------- Notifications / MongoDB ----------------
    @GetMapping("/notifications/{username}")
    public List<NotificationDocument> notifications(@PathVariable String username) {
        return notifications.findByUsernameOrderByCreatedAtDesc(username);
    }

    @PostMapping("/notifications/{id}/read")
    public Map<String,Object> readNotification(@PathVariable String id) {
        NotificationDocument n = notifications.findById(id).orElseThrow();
        n.setRead(true); notifications.save(n);
        return Map.of("success",true);
    }

    // ---------------- Analytics ----------------
    @GetMapping("/analytics")
    public Map<String,Object> analytics() {
        long total = students.count();
        long present = attendance.countByStatus("Present");
        long absent = attendance.countByStatus("Absent");
        double pct = present+absent == 0 ? 0 : present*100.0/(present+absent);
        return Map.of(
            "totalStudents", total,
            "totalFaculty", faculty.count(),
            "present", present,
            "absent", absent,
            "attendancePercentage", Math.round(pct*10)/10.0,
            "monthly", monthlyAnalytics()
        );
    }

    @GetMapping("/analytics/student/{rollNo}")
    public Map<String,Object> studentAnalytics(@PathVariable String rollNo) {
        long present = attendance.countByRollNoAndStatus(rollNo,"Present");
        long absent = attendance.countByRollNoAndStatus(rollNo,"Absent");
        long total = present+absent;
        double pct = total==0 ? 0 : present*100.0/total;
        return Map.of("rollNo",rollNo,"totalClasses",total,"present",present,"absent",absent,
                      "attendancePercentage",Math.round(pct*10)/10.0);
    }

    private List<Map<String,Object>> monthlyAnalytics() {
        List<Map<String,Object>> result = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i=5; i>=0; i--) {
            YearMonth ym = current.minusMonths(i);
            long p=0,a=0;
            for (AttendanceRecord r : attendance.findAll()) {
                if (r.getDate()!=null && YearMonth.from(r.getDate()).equals(ym)) {
                    if ("Present".equalsIgnoreCase(r.getStatus())) p++;
                    else if ("Absent".equalsIgnoreCase(r.getStatus())) a++;
                }
            }
            double pct = p+a==0 ? 0 : p*100.0/(p+a);
            result.add(Map.of("month",ym.getMonth().toString().substring(0,3).toLowerCase(),
                              "attendance",Math.round(pct*10)/10.0,
                              "present",p,"absent",a));
        }
        return result;
    }
}
