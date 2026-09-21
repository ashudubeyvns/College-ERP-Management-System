package attendancemgt;

import java.util.ArrayList;
import java.util.List;

/**
 * Central data repository for all course and subject information.
 */
public class SubjectData {

    // ==================== COURSE MASTER ====================
    public static final String[] COURSE_IDS = {"CRS001", "CRS002", "CRS003", "CRS004", "CRS005", "CRS006"};
    public static final String[] COURSE_NAMES = {"BCA", "MCA", "B.Tech CSE", "B.Tech IT", "B.Sc CS", "M.Sc CS"};
    public static final String[] COURSE_FULL_NAMES = {
        "Bachelor of Computer Applications",
        "Master of Computer Applications",
        "B.Tech Computer Science & Engineering",
        "B.Tech Information Technology",
        "B.Sc Computer Science",
        "M.Sc Computer Science"
    };
    public static final int[] COURSE_SEMESTERS = {6, 4, 8, 8, 6, 4};
    public static final String[] COURSE_DEPARTMENTS = {"CSA", "MCA", "CSE", "IT", "CS", "CS"};
    public static final String[] COURSE_ACADEMIC_YEARS = {"2024-2027", "2024-2026", "2024-2028", "2024-2028", "2024-2027", "2024-2026"};

    // ==================== SUBJECT DATA ====================
    // Structure: [courseIndex][semester-1] = array of {subjectId, subjectName, type(T/P), credits, subjectCode}

    // ---------- BCA (Index 0) - 6 Semesters ----------
    public static final String[][] BCA_SEM1 = {
        {"BCA101", "Programming in C", "T", "4", "BCA101"},
        {"BCA102", "Discrete Mathematics", "T", "4", "BCA102"},
        {"BCA103", "Digital Electronics", "T", "3", "BCA103"},
        {"BCA104", "Communication Skills", "T", "3", "BCA104"},
        {"BCA105", "Computer Fundamentals", "T", "3", "BCA105"},
        {"BCA151", "C Programming Lab", "P", "2", "BCA151"}
    };
    public static final String[][] BCA_SEM2 = {
        {"BCA201", "Data Structures", "T", "4", "BCA201"},
        {"BCA202", "Object Oriented Programming (C++)", "T", "4", "BCA202"},
        {"BCA203", "Database Management Systems", "T", "4", "BCA203"},
        {"BCA204", "Statistics & Probability", "T", "3", "BCA204"},
        {"BCA205", "Web Technologies (HTML/CSS/JS)", "T", "3", "BCA205"},
        {"BCA251", "Data Structures Lab", "P", "2", "BCA251"},
        {"BCA252", "DBMS Lab", "P", "2", "BCA252"}
    };
    public static final String[][] BCA_SEM3 = {
        {"BCA301", "Operating Systems", "T", "4", "BCA301"},
        {"BCA302", "Computer Networks", "T", "4", "BCA302"},
        {"BCA303", "Software Engineering", "T", "3", "BCA303"},
        {"BCA304", "Python Programming", "T", "3", "BCA304"},
        {"BCA305", "Data Analytics", "T", "3", "BCA305"},
        {"BCA351", "Python Programming Lab", "P", "2", "BCA351"},
        {"BCA352", "Computer Networks Lab", "P", "2", "BCA352"}
    };
    public static final String[][] BCA_SEM4 = {
        {"BCA401", "Java Programming", "T", "4", "BCA401"},
        {"BCA402", "Cyber Security & Ethics", "T", "3", "BCA402"},
        {"BCA403", "Cloud Computing", "T", "3", "BCA403"},
        {"BCA404", "Data Mining & Warehousing", "T", "3", "BCA404"},
        {"BCA405", "Minor Project", "P", "4", "BCA405"},
        {"BCA451", "Java Programming Lab", "P", "2", "BCA451"}
    };
    public static final String[][] BCA_SEM5 = {
        {"BCA501", "Artificial Intelligence", "T", "4", "BCA501"},
        {"BCA502", "Full Stack Development", "T", "4", "BCA502"},
        {"BCA503", "Mobile Application Development", "T", "3", "BCA503"},
        {"BCA504", "DevOps & CI/CD", "T", "3", "BCA504"},
        {"BCA505", "Minor Project - II", "P", "4", "BCA505"},
        {"BCA551", "Full Stack Development Lab", "P", "2", "BCA551"}
    };
    public static final String[][] BCA_SEM6 = {
        {"BCA601", "Blockchain Technology", "T", "3", "BCA601"},
        {"BCA602", "Big Data Analytics", "T", "3", "BCA602"},
        {"BCA603", "Internet of Things", "T", "3", "BCA603"},
        {"BCA604", "Major Project / Internship", "P", "6", "BCA604"},
        {"BCA605", "Aptitude & Soft Skills", "T", "2", "BCA605"}
    };

    // ---------- MCA (Index 1) - 4 Semesters ----------
    public static final String[][] MCA_SEM1 = {
        {"MCA101", "Advanced Programming in C", "T", "4", "MCA101"},
        {"MCA102", "Data Structures & Algorithms", "T", "4", "MCA102"},
        {"MCA103", "Computer Organization & Architecture", "T", "3", "MCA103"},
        {"MCA104", "Discrete Mathematics & Graph Theory", "T", "3", "MCA104"},
        {"MCA105", "Database Management Systems", "T", "4", "MCA105"},
        {"MCA151", "Data Structures Lab", "P", "2", "MCA151"},
        {"MCA152", "DBMS Lab", "P", "2", "MCA152"}
    };
    public static final String[][] MCA_SEM2 = {
        {"MCA201", "Object Oriented Programming (Java)", "T", "4", "MCA201"},
        {"MCA202", "Operating Systems", "T", "4", "MCA202"},
        {"MCA203", "Computer Networks", "T", "4", "MCA203"},
        {"MCA204", "Software Engineering & Project Management", "T", "3", "MCA204"},
        {"MCA205", "Web Technologies & Frameworks", "T", "3", "MCA205"},
        {"MCA251", "Java Programming Lab", "P", "2", "MCA251"},
        {"MCA252", "Web Technologies Lab", "P", "2", "MCA252"}
    };
    public static final String[][] MCA_SEM3 = {
        {"MCA301", "Python for Data Science", "T", "4", "MCA301"},
        {"MCA302", "Cloud Computing & Virtualization", "T", "3", "MCA302"},
        {"MCA303", "Cyber Security & Cryptography", "T", "3", "MCA303"},
        {"MCA304", "Machine Learning", "T", "4", "MCA304"},
        {"MCA305", "Spring Boot & Microservices", "T", "3", "MCA305"},
        {"MCA351", "Machine Learning Lab", "P", "2", "MCA351"},
        {"MCA352", "Spring Boot Lab", "P", "2", "MCA352"}
    };
    public static final String[][] MCA_SEM4 = {
        {"MCA401", "Full Stack Development with React", "T", "4", "MCA401"},
        {"MCA402", "Data Analytics & Visualization", "T", "3", "MCA402"},
        {"MCA403", "DevOps & Cloud Deployment", "T", "3", "MCA403"},
        {"MCA404", "Major Project / Dissertation", "P", "8", "MCA404"},
        {"MCA405", "Seminar & Technical Presentation", "P", "2", "MCA405"}
    };

    // ---------- B.Tech CSE (Index 2) - 8 Semesters ----------
    public static final String[][] BTECH_CSE_SEM1 = {
        {"CSE101", "Engineering Mathematics - I", "T", "4", "CSE101"},
        {"CSE102", "Engineering Physics", "T", "3", "CSE102"},
        {"CSE103", "Programming for Problem Solving (C)", "T", "4", "CSE103"},
        {"CSE104", "Basic Electrical Engineering", "T", "3", "CSE104"},
        {"CSE105", "Engineering Chemistry", "T", "3", "CSE105"},
        {"CSE151", "Programming Lab (C)", "P", "2", "CSE151"},
        {"CSE152", "Engineering Physics Lab", "P", "1", "CSE152"},
        {"CSE153", "Engineering Chemistry Lab", "P", "1", "CSE153"}
    };
    public static final String[][] BTECH_CSE_SEM2 = {
        {"CSE201", "Engineering Mathematics - II", "T", "4", "CSE201"},
        {"CSE202", "Data Structures", "T", "4", "CSE202"},
        {"CSE203", "Object Oriented Programming (C++)", "T", "4", "CSE203"},
        {"CSE204", "Digital Electronics & Logic Design", "T", "3", "CSE204"},
        {"CSE205", "Environmental Science", "T", "2", "CSE205"},
        {"CSE251", "Data Structures Lab", "P", "2", "CSE251"},
        {"CSE252", "OOPs Lab (C++)", "P", "2", "CSE252"},
        {"CSE253", "Digital Electronics Lab", "P", "1", "CSE253"}
    };
    public static final String[][] BTECH_CSE_SEM3 = {
        {"CSE301", "Engineering Mathematics - III", "T", "4", "CSE301"},
        {"CSE302", "Discrete Structures & Graph Theory", "T", "3", "CSE302"},
        {"CSE303", "Database Management Systems", "T", "4", "CSE303"},
        {"CSE304", "Computer Organization & Architecture", "T", "3", "CSE304"},
        {"CSE305", "Python Programming", "T", "3", "CSE305"},
        {"CSE351", "DBMS Lab", "P", "2", "CSE351"},
        {"CSE352", "Python Programming Lab", "P", "2", "CSE352"}
    };
    public static final String[][] BTECH_CSE_SEM4 = {
        {"CSE401", "Operating Systems", "T", "4", "CSE401"},
        {"CSE402", "Design & Analysis of Algorithms", "T", "4", "CSE402"},
        {"CSE403", "Java Programming", "T", "4", "CSE403"},
        {"CSE404", "Computer Networks", "T", "3", "CSE404"},
        {"CSE405", "Theory of Computation", "T", "3", "CSE405"},
        {"CSE451", "OS & Networks Lab", "P", "2", "CSE451"},
        {"CSE452", "Java Lab", "P", "2", "CSE452"}
    };
    public static final String[][] BTECH_CSE_SEM5 = {
        {"CSE501", "Software Engineering", "T", "3", "CSE501"},
        {"CSE502", "Artificial Intelligence", "T", "4", "CSE502"},
        {"CSE503", "Web Technologies & Frameworks", "T", "3", "CSE503"},
        {"CSE504", "Compiler Design", "T", "3", "CSE504"},
        {"CSE505", "Data Communication", "T", "3", "CSE505"},
        {"CSE551", "Web Technologies Lab", "P", "2", "CSE551"},
        {"CSE552", "Mini Project - I", "P", "2", "CSE552"}
    };
    public static final String[][] BTECH_CSE_SEM6 = {
        {"CSE601", "Machine Learning", "T", "4", "CSE601"},
        {"CSE602", "Cyber Security", "T", "3", "CSE602"},
        {"CSE603", "Cloud Computing", "T", "3", "CSE603"},
        {"CSE604", "Data Mining", "T", "3", "CSE604"},
        {"CSE605", "Mobile Application Development", "T", "3", "CSE605"},
        {"CSE651", "Machine Learning Lab", "P", "2", "CSE651"},
        {"CSE652", "Mobile App Lab", "P", "2", "CSE652"}
    };
    public static final String[][] BTECH_CSE_SEM7 = {
        {"CSE701", "Full Stack Development", "T", "4", "CSE701"},
        {"CSE702", "Big Data Analytics", "T", "3", "CSE702"},
        {"CSE703", "Internet of Things", "T", "3", "CSE703"},
        {"CSE704", "DevOps & CI/CD", "T", "3", "CSE704"},
        {"CSE751", "Full Stack Lab", "P", "2", "CSE751"},
        {"CSE752", "Major Project - Phase I", "P", "4", "CSE752"}
    };
    public static final String[][] BTECH_CSE_SEM8 = {
        {"CSE801", "Blockchain Technology", "T", "3", "CSE801"},
        {"CSE802", "Data Science & Analytics", "T", "3", "CSE802"},
        {"CSE803", "Enterprise Application with Spring Boot", "T", "3", "CSE803"},
        {"CSE804", "Major Project / Internship", "P", "8", "CSE804"},
        {"CSE805", "Seminar & Technical Writing", "P", "2", "CSE805"}
    };

    // ---------- B.Tech IT (Index 3) - 8 Semesters ----------
    public static final String[][] BTECH_IT_SEM1 = {
        {"IT101", "Engineering Mathematics - I", "T", "4", "IT101"},
        {"IT102", "Programming in C", "T", "4", "IT102"},
        {"IT103", "Digital Logic & Design", "T", "3", "IT103"},
        {"IT104", "Communication Skills", "T", "3", "IT104"},
        {"IT151", "C Programming Lab", "P", "2", "IT151"},
        {"IT152", "Digital Logic Lab", "P", "1", "IT152"}
    };
    public static final String[][] BTECH_IT_SEM2 = {
        {"IT201", "Engineering Mathematics - II", "T", "4", "IT201"},
        {"IT202", "Data Structures", "T", "4", "IT202"},
        {"IT203", "Object Oriented Programming (C++)", "T", "4", "IT203"},
        {"IT204", "Computer Organization", "T", "3", "IT204"},
        {"IT251", "Data Structures Lab", "P", "2", "IT251"},
        {"IT252", "OOPs Lab", "P", "2", "IT252"}
    };
    public static final String[][] BTECH_IT_SEM3 = {
        {"IT301", "Database Management Systems", "T", "4", "IT301"},
        {"IT302", "Operating Systems", "T", "4", "IT302"},
        {"IT303", "Discrete Mathematics", "T", "3", "IT303"},
        {"IT304", "Web Technologies", "T", "3", "IT304"},
        {"IT351", "DBMS Lab", "P", "2", "IT351"},
        {"IT352", "Web Technologies Lab", "P", "2", "IT352"}
    };
    public static final String[][] BTECH_IT_SEM4 = {
        {"IT401", "Computer Networks", "T", "4", "IT401"},
        {"IT402", "Java Programming", "T", "4", "IT402"},
        {"IT403", "Software Engineering", "T", "3", "IT403"},
        {"IT404", "Design & Analysis of Algorithms", "T", "4", "IT404"},
        {"IT451", "Computer Networks Lab", "P", "2", "IT451"},
        {"IT452", "Java Lab", "P", "2", "IT452"}
    };
    public static final String[][] BTECH_IT_SEM5 = {
        {"IT501", "Cyber Security", "T", "3", "IT501"},
        {"IT502", "Cloud Computing", "T", "3", "IT502"},
        {"IT503", "Python for Data Science", "T", "3", "IT503"},
        {"IT504", "Artificial Intelligence", "T", "4", "IT504"},
        {"IT551", "Python Lab", "P", "2", "IT551"},
        {"IT552", "Mini Project", "P", "2", "IT552"}
    };
    public static final String[][] BTECH_IT_SEM6 = {
        {"IT601", "Machine Learning", "T", "4", "IT601"},
        {"IT602", "Data Mining & Analytics", "T", "3", "IT602"},
        {"IT603", "Mobile Application Development", "T", "3", "IT603"},
        {"IT604", "Information Security", "T", "3", "IT604"},
        {"IT651", "ML & Data Mining Lab", "P", "2", "IT651"},
        {"IT652", "Mobile App Lab", "P", "2", "IT652"}
    };
    public static final String[][] BTECH_IT_SEM7 = {
        {"IT701", "Full Stack Development", "T", "4", "IT701"},
        {"IT702", "Big Data & Hadoop", "T", "3", "IT702"},
        {"IT703", "DevOps & Cloud Deployment", "T", "3", "IT703"},
        {"IT704", "Internet of Things", "T", "3", "IT704"},
        {"IT751", "Full Stack Lab", "P", "2", "IT751"},
        {"IT752", "Major Project - Phase I", "P", "4", "IT752"}
    };
    public static final String[][] BTECH_IT_SEM8 = {
        {"IT801", "Blockchain & Cryptocurrency", "T", "3", "IT801"},
        {"IT802", "Data Science & Visualization", "T", "3", "IT802"},
        {"IT803", "Enterprise Java (Spring Boot)", "T", "3", "IT803"},
        {"IT804", "Major Project / Internship", "P", "8", "IT804"}
    };

    // ---------- B.Sc CS (Index 4) - 6 Semesters ----------
    public static final String[][] BSC_CS_SEM1 = {
        {"BSC101", "Programming in C", "T", "4", "BSC101"},
        {"BSC102", "Mathematics - I (Algebra & Calculus)", "T", "4", "BSC102"},
        {"BSC103", "Computer Fundamentals & Office Tools", "T", "3", "BSC103"},
        {"BSC104", "English Communication", "T", "3", "BSC104"},
        {"BSC151", "C Programming Lab", "P", "2", "BSC151"},
        {"BSC152", "Office Tools Lab", "P", "1", "BSC152"}
    };
    public static final String[][] BSC_CS_SEM2 = {
        {"BSC201", "Data Structures", "T", "4", "BSC201"},
        {"BSC202", "Mathematics - II (Discrete Math)", "T", "4", "BSC202"},
        {"BSC203", "Digital Electronics", "T", "3", "BSC203"},
        {"BSC204", "Object Oriented Programming (C++)", "T", "3", "BSC204"},
        {"BSC251", "Data Structures Lab", "P", "2", "BSC251"},
        {"BSC252", "OOPs Lab", "P", "2", "BSC252"}
    };
    public static final String[][] BSC_CS_SEM3 = {
        {"BSC301", "Database Management Systems", "T", "4", "BSC301"},
        {"BSC302", "Operating Systems", "T", "4", "BSC302"},
        {"BSC303", "Computer Networks", "T", "3", "BSC303"},
        {"BSC304", "Python Programming", "T", "3", "BSC304"},
        {"BSC351", "DBMS Lab", "P", "2", "BSC351"},
        {"BSC352", "Python Lab", "P", "2", "BSC352"}
    };
    public static final String[][] BSC_CS_SEM4 = {
        {"BSC401", "Java Programming", "T", "4", "BSC401"},
        {"BSC402", "Software Engineering", "T", "3", "BSC402"},
        {"BSC403", "Web Technologies", "T", "3", "BSC403"},
        {"BSC404", "Computer Organization", "T", "3", "BSC404"},
        {"BSC451", "Java Lab", "P", "2", "BSC451"},
        {"BSC452", "Web Technologies Lab", "P", "2", "BSC452"}
    };
    public static final String[][] BSC_CS_SEM5 = {
        {"BSC501", "Artificial Intelligence", "T", "4", "BSC501"},
        {"BSC502", "Cyber Security", "T", "3", "BSC502"},
        {"BSC503", "Cloud Computing", "T", "3", "BSC503"},
        {"BSC504", "Minor Project", "P", "4", "BSC504"},
        {"BSC551", "AI & Cyber Security Lab", "P", "2", "BSC551"}
    };
    public static final String[][] BSC_CS_SEM6 = {
        {"BSC601", "Full Stack Development", "T", "4", "BSC601"},
        {"BSC602", "Data Analytics", "T", "3", "BSC602"},
        {"BSC603", "Machine Learning Basics", "T", "3", "BSC603"},
        {"BSC604", "Major Project", "P", "6", "BSC604"}
    };

    // ---------- M.Sc CS (Index 5) - 4 Semesters ----------
    public static final String[][] MSC_CS_SEM1 = {
        {"MSC101", "Advanced Data Structures", "T", "4", "MSC101"},
        {"MSC102", "Mathematical Foundations of CS", "T", "4", "MSC102"},
        {"MSC103", "Advanced Database Systems", "T", "4", "MSC103"},
        {"MSC104", "Object Oriented Analysis & Design (Java)", "T", "3", "MSC104"},
        {"MSC151", "Advanced Data Structures Lab", "P", "2", "MSC151"},
        {"MSC152", "Advanced DBMS Lab", "P", "2", "MSC152"}
    };
    public static final String[][] MSC_CS_SEM2 = {
        {"MSC201", "Advanced Computer Networks", "T", "4", "MSC201"},
        {"MSC202", "Design & Analysis of Algorithms", "T", "4", "MSC202"},
        {"MSC203", "Python for Data Science", "T", "3", "MSC203"},
        {"MSC204", "Research Methodology", "T", "3", "MSC204"},
        {"MSC251", "Algorithms Lab", "P", "2", "MSC251"},
        {"MSC252", "Data Science Lab (Python)", "P", "2", "MSC252"}
    };
    public static final String[][] MSC_CS_SEM3 = {
        {"MSC301", "Machine Learning", "T", "4", "MSC301"},
        {"MSC302", "Cloud Computing & Big Data", "T", "4", "MSC302"},
        {"MSC303", "Cyber Security & Forensics", "T", "3", "MSC303"},
        {"MSC304", "Web Frameworks (Spring Boot/React)", "T", "3", "MSC304"},
        {"MSC351", "Machine Learning Lab", "P", "2", "MSC351"},
        {"MSC352", "Web Frameworks Lab", "P", "2", "MSC352"}
    };
    public static final String[][] MSC_CS_SEM4 = {
        {"MSC401", "Full Stack Development & DevOps", "T", "4", "MSC401"},
        {"MSC402", "Data Analytics & Visualization", "T", "3", "MSC402"},
        {"MSC403", "Dissertation / Major Project", "P", "10", "MSC403"}
    };

    // ==================== HELPER: Get subjects for a specific course & semester ====================
    // Returns 9-column array: {subjectId, courseName, section, subjectCode, subjectName, semester, type, credits, courseId}
    public static String[][] getSubjectsForCourseSemester(String courseName, int semester) {
        int courseIdx = -1;
        for (int i = 0; i < COURSE_NAMES.length; i++) {
            if (COURSE_NAMES[i].equals(courseName)) { courseIdx = i; break; }
        }
        if (courseIdx < 0) return new String[0][0];

        String[][] rawData = getRawSemesterData(courseIdx, semester);
        if (rawData == null || rawData.length == 0) return new String[0][0];

        String[] sections = {"A", "B", "C", "A", "A", "A"};
        String section = sections[courseIdx];
        String semStr = String.valueOf(semester);

        List<String[]> result = new ArrayList<>();
        for (String[] subj : rawData) {
            result.add(new String[]{
                subj[0],                   // subjectId
                COURSE_NAMES[courseIdx],   // courseName
                section,                   // section
                subj[4],                   // subjectCode
                subj[1],                   // subjectName
                semStr,                    // semester
                subj[2],                   // type
                subj[3],                   // credits
                COURSE_IDS[courseIdx]      // courseId
            });
        }
        return result.toArray(new String[0][]);
    }

    private static String[][] getRawSemesterData(int courseIdx, int semester) {
        switch (courseIdx) {
            case 0:
                switch (semester) {
                    case 1: return BCA_SEM1; case 2: return BCA_SEM2; case 3: return BCA_SEM3;
                    case 4: return BCA_SEM4; case 5: return BCA_SEM5; case 6: return BCA_SEM6;
                    default: return new String[0][0];
                }
            case 1:
                switch (semester) {
                    case 1: return MCA_SEM1; case 2: return MCA_SEM2;
                    case 3: return MCA_SEM3; case 4: return MCA_SEM4;
                    default: return new String[0][0];
                }
            case 2:
                switch (semester) {
                    case 1: return BTECH_CSE_SEM1; case 2: return BTECH_CSE_SEM2;
                    case 3: return BTECH_CSE_SEM3; case 4: return BTECH_CSE_SEM4;
                    case 5: return BTECH_CSE_SEM5; case 6: return BTECH_CSE_SEM6;
                    case 7: return BTECH_CSE_SEM7; case 8: return BTECH_CSE_SEM8;
                    default: return new String[0][0];
                }
            case 3:
                switch (semester) {
                    case 1: return BTECH_IT_SEM1; case 2: return BTECH_IT_SEM2;
                    case 3: return BTECH_IT_SEM3; case 4: return BTECH_IT_SEM4;
                    case 5: return BTECH_IT_SEM5; case 6: return BTECH_IT_SEM6;
                    case 7: return BTECH_IT_SEM7; case 8: return BTECH_IT_SEM8;
                    default: return new String[0][0];
                }
            case 4:
                switch (semester) {
                    case 1: return BSC_CS_SEM1; case 2: return BSC_CS_SEM2;
                    case 3: return BSC_CS_SEM3; case 4: return BSC_CS_SEM4;
                    case 5: return BSC_CS_SEM5; case 6: return BSC_CS_SEM6;
                    default: return new String[0][0];
                }
            case 5:
                switch (semester) {
                    case 1: return MSC_CS_SEM1; case 2: return MSC_CS_SEM2;
                    case 3: return MSC_CS_SEM3; case 4: return MSC_CS_SEM4;
                    default: return new String[0][0];
                }
            default: return new String[0][0];
        }
    }
}
