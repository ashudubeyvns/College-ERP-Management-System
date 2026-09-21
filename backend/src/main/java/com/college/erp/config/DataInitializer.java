package com.college.erp.config;
import com.college.erp.model.entity.*; import com.college.erp.repository.*; import com.college.erp.util.PasswordUtil; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import java.time.LocalDate;
@Configuration public class DataInitializer {
 @Bean CommandLineRunner seed(UserRepository users, StudentRepository students, FacultyRepository faculty, AttendanceRepository attendance, NotificationRepository notifications){return args->{
  seedUser(users,"admin","System Administrator","Admin@123","ADMIN","admin@attendance-system.com","Administration");
  seedUser(users,"faculty1","Dr. Rajesh Kumar","Fac@12345","FACULTY","rajesh.kumar@example.com","Computer Science");
  seedUser(users,"faculty2","Prof. Amit Singh","Fac@12345","FACULTY","amit.singh@example.com","Computer Science");
  seedUser(users,"24SCSE1180456","Aarav Sharma","12345","STUDENT","aarav.sharma@example.com","Computer Science");
  seedUser(users,"24SCSE1180381","Priya Patel","12345","STUDENT","priya.patel@example.com","Computer Science");
  seedUser(users,"24SCSE2030222","Rohit Singh","12345","STUDENT","rohit.singh@example.com","Computer Applications");
  seedUser(users,"101","Ashutosh Dubey","12345","STUDENT","ashutosh.dubey@example.com","Computer Applications");
  seedUser(users,"102","Aditya Verma","12345","STUDENT","aditya.verma@example.com","Computer Applications");
  if(faculty.count()==0){faculty.save(fac("faculty1","Dr. Rajesh Kumar","Computer Science","rajesh.kumar@example.com"));faculty.save(fac("faculty2","Prof. Amit Singh","Computer Science","amit.singh@example.com"));}
  if(students.count()==0){students.save(student("101","Ashutosh Dubey","BCA",4,"A","ashutosh.dubey@example.com"));students.save(student("102","Aditya Verma","BCA",4,"A","aditya.verma@example.com"));students.save(student("24SCSE1180456","Aarav Sharma","BCA",4,"B","aarav.sharma@example.com"));students.save(student("24SCSE1180381","Priya Patel","BCA",4,"B","priya.patel@example.com"));students.save(student("24SCSE2030222","Rohit Singh","BCA",4,"C","rohit.singh@example.com"));}
  if(attendance.count()==0){attendance.save(att("101","Ashutosh Dubey","BCA301","Operating Systems","A","Present"));attendance.save(att("101","Ashutosh Dubey","BCA302","Computer Networks","A","Present"));attendance.save(att("101","Ashutosh Dubey","BCA303","Software Engineering","A","Absent"));attendance.save(att("102","Aditya Verma","BCA301","Operating Systems","A","Present"));}
  if(notifications.count()==0){NotificationDocument n=new NotificationDocument();n.setUsername("101");n.setTitle("Attendance Updated");n.setMessage("Your latest attendance records are now available.");n.setType("info");notifications.save(n);}
 };}
 private void seedUser(UserRepository r,String u,String name,String pw,String role,String email,String dept){UserAccount x=r.findByUsername(u).orElseGet(UserAccount::new);x.setUsername(u);x.setFullName(name);x.setPasswordHash(PasswordUtil.hash(pw));x.setRole(role);x.setEmail(email);x.setDepartment(dept);r.save(x);}
 private Faculty fac(String u,String n,String d,String e){Faculty f=new Faculty();f.setUsername(u);f.setName(n);f.setDepartment(d);f.setEmail(e);f.setStatus("Active");return f;}
 private Student student(String r,String n,String c,int s,String sec,String e){Student x=new Student();x.setRollNo(r);x.setName(n);x.setCourse(c);x.setSemester(s);x.setSection(sec);x.setEmail(e);x.setStatus("Active");return x;}
 private AttendanceRecord att(String r,String n,String code,String sub,String sec,String status){AttendanceRecord a=new AttendanceRecord();a.setRollNo(r);a.setStudentName(n);a.setCourse("BCA");a.setSubjectCode(code);a.setSubjectName(sub);a.setSection(sec);a.setStatus(status);a.setDate(LocalDate.now());a.setTime("10:30 AM");a.setMarkedBy("faculty1");return a;}
}
