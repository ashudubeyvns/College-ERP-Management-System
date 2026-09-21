<div align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Swing-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing"/>
  <img src="https://img.shields.io/badge/UI_Redesign-Complete-22C55E?style=for-the-badge&logo=checkmarx&logoColor=white" alt="UI Redesign"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge" alt="License"/>
  <a href="https://ashudubeyvns.github.io/attendance-management-system/" target="_blank">
    <img src="https://img.shields.io/badge/GitHub%20Pages-Live-2C3E50?style=for-the-badge&logo=githubpages&logoColor=white" alt="GitHub Pages"/>
  </a>
</div>

<br/>

<div align="center">
  <h1>🏫 College ERP - Attendance Management System</h1>
  <p><strong>A comprehensive Java Swing-based College ERP module with modern UI design, featuring student attendance tracking, assignment management, sessional marks, faculty management, and reporting — all with a professional glassmorphism interface and dark mode support.</strong></p>

  <p>
    🌐 <strong><a href="https://ashudubeyvns.github.io/attendance-management-system/" target="_blank">Visit the Project Website</a></strong> •
    <a href="#-features">Features</a> •
    <a href="#-design-system">Design System</a> •
    <a href="#-screenshots">Screenshots</a> •
    <a href="#-tech-stack">Tech Stack</a> •
    <a href="#-getting-started">Getting Started</a> •
    <a href="#-project-structure">Structure</a> •
    <a href="#-courses-supported">Courses</a>
  </p>
</div>

---

## ✨ Features

<table>
  <tr>
    <td align="center">🔐</td>
    <td><b>Secure Login</b> — Blue gradient background with glassmorphism login card, password show/hide, remember me, forgot password, role-based authentication (Admin, Faculty, Student)</td>
  </tr>
  <tr>
    <td align="center">📊</td>
    <td><b>Dashboard</b> — 6 stat cards (Students 1,250, Faculty 95, Attendance 91%, Departments 12, Subjects 48, Today's Classes 6), animated charts (attendance trend, department-wise pie, faculty workload bar), recent attendance table</td>
  </tr>
  <tr>
    <td align="center">🌙</td>
    <td><b>Dark Mode</b> — Full dark theme with #111827 background, #1F2937 cards, #3B82F6 primary, white text. Toggle from top bar or settings</td>
  </tr>
  <tr>
    <td align="center">✅</td>
    <td><b>Mark Attendance</b> — Subject/date/section selector, student list with Present/Absent/Late toggle, Mark All buttons, toast notifications on save</td>
  </tr>
  <tr>
    <td align="center">📝</td>
    <td><b>Assignments</b> — Upload marks, manage max marks, update records with subject-wise sections and action buttons</td>
  </tr>
  <tr>
    <td align="center">📋</td>
    <td><b>Sessionals</b> — Enter Sessional-1, 2, 3 marks with auto-calculated totals and Pass/Fail status</td>
  </tr>
  <tr>
    <td align="center">🔑</td>
    <td><b>Change Password</b> — Secure password update with validation checks</td>
  </tr>
  <tr>
    <td align="center">📱</td>
    <td><b>Modern UI</b> — Glassmorphism, rounded buttons with hover effects, animated charts, toast notifications, enhanced tables with sticky headers</td>
  </tr>
  <tr>
    <td align="center">🎓</td>
    <td><b>Multi-Course Support</b> — BCA, MCA, B.Tech CSE/IT, B.Sc CS, M.Sc CS with semester-wise subjects</td>
  </tr>
  <tr>
    <td align="center">📄</td>
    <td><b>Reports</b> — Export to PDF/Excel/Print with formatted headers, summaries, and color-coded data</td>
  </tr>
  <tr>
    <td align="center">🔔</td>
    <td><b>Toast Notifications</b> — Animated success/error/warning/info notifications replacing JOptionPane dialogs</td>
  </tr>
  <tr>
    <td align="center">🎬</td>
    <td><b>Splash Screen</b> — College ERP branded splash with logo, version info, animated loading progress bar</td>
  </tr>
</table>

---

## 🎨 Design System

### Color Palette

| Token | Color | Hex | Usage |
|-------|-------|-----|-------|
| Primary | 🟦 | `#2563EB` | Buttons, links, accents |
| Primary Dark | 🟦 | `#1E3A8A` | Login gradient start |
| Primary Light | 🟦 | `#3B82F6` | Hover states, login gradient end |
| Background | ⬜ | `#F8FAFC` | Main content background |
| Cards | ⬜ | `#FFFFFF` | Card backgrounds |
| Success | 🟩 | `#22C55E` | Present, success messages |
| Danger | 🟥 | `#EF4444` | Absent, delete, errors |
| Warning | 🟨 | `#F59E0B` | Late, warnings |
| Dark BG | ⬛ | `#111827` | Dark mode background |
| Dark Card | ⬛ | `#1F2937` | Dark mode card backgrounds |

### Typography
- **Font Family**: Segoe UI (Primary), Inter (Fallback)
- **Sizes**: 10px to 28px range for hierarchy

### Components
- **Glassmorphism**: Translucent panels with backdrop blur for login cards
- **Ripple Buttons**: Rounded buttons with shadow, hover scale effect, ripple animation
- **Modern Tables**: Sticky gradient headers, alternate row colors, built-in search/sort/filter
- **Animated Charts**: Bar (vertical/horizontal), Pie (donut), Line charts with smooth animations

---

## 🖼️ Screenshots

> _The application features a modern design with:_
> - **Primary Color**: Blue `#2563EB`
> - **Background**: Soft Gray `#F8FAFC`
> - **Dark Mode**: Dark Slate `#111827`

### 🔐 Login Screen
```
┌─────────────────────────────────────────────────┐
│    Blue Gradient Background (#1E3A8A → #2563EB) │
│    ┌─────────────────────────────────────┐      │
│    │    🎓 Glassmorphism Login Card       │      │
│    │         🏛️ College Logo              │      │
│    │                                       │      │
│    │    👤 Username ┌──────────────┐       │      │
│    │    🔒 Password ┌──────────────┐ [👁]  │      │
│    │    📥 Login As [Student ▼]           │      │
│    │    ☑ Remember Me                     │      │
│    │                                       │      │
│    │    ┌─ Animated Sign In Button ──┐     │      │
│    │    │        🚀 Sign In           │     │      │
│    │    └────────────────────────────┘     │      │
│    │         🔗 Forgot Password?           │      │
│    └─────────────────────────────────────┘      │
└─────────────────────────────────────────────────┘
```

### 🏠 Dashboard
```
┌──────────────────────────────────────────────────────────┐
│ 🏠 Dashboard Overview        🌙 🔔 👤 Ashutosh Dubey ▼  │
├────────────┬──────────┬──────────┬──────────┬────────────┤
│ 👤Students │ 👨Faculty │📅Attend. │🎓Dept.   │📚Subjects  │
│   1,250    │   95     │   91%    │   12     │    48      │
├────────────┴──────────┴──────────┴──────────┴────────────┤
│ 📊 Attendance Trend    📊 Dept Students    📊 Workload   │
│ [═══LINE═══CHART═══]  [═══PIE═══CHART═══] [═══BAR═══]  │
├──────────────────────────────────────────────────────────┤
│ 📋 Recent Attendance Records                             │
│ ┌────────┬──────────┬─────────┬───────┬─────────┬──────┐ │
│ │Roll No │ Name     │ Status  │ Time  │ Course  │ Sem  │ │
│ ├────────┼──────────┼─────────┼───────┼─────────┼──────┤ │
│ │1180456 │ Aarav    │ ✅ Pres │09:15AM│ B.Tech  │  3   │ │
│ │1180381 │ Priya    │ ✅ Pres │09:10AM│ B.Tech  │  3   │ │
│ │2030222 │ Rohit    │ ❌ Abs  │   -   │ BCA     │  4   │ │
│ └────────┴──────────┴─────────┴───────┴─────────┴──────┘ │
└──────────────────────────────────────────────────────────┘
```

### 📱 Faculty & Student Dashboards
Role-based dashboards with stat cards, quick action modules, and attendance records.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 26** | Core programming language |
| **Java Swing** | GUI framework (JFrame, JPanel, CardLayout, Java2D) |
| **NetBeans / Ant** | Build system |
| **JDT LSP** | VSCode Java extension |

### Key Libraries & APIs
- `javax.swing.*` — UI components
- `java.awt.*` — Layout, colors, Java2D rendering
- `CardLayout` — Single-window multi-panel navigation
- `javax.swing.Timer` — Animations for charts, notifications, splash screen
- `java.awt.Graphics2D` — Custom chart rendering (bar, pie, line)
- `java.awt.print.PrinterJob` — PDF/Print export
- `com.sun.net.httpserver` — Lightweight REST API server (port 8080)

---

## 🚀 Getting Started

### 🖥️ Quick Download & Run (No IDE Required)

1. **Download the latest JAR** from the [Releases](https://github.com/ashudubeyvns/attendance-management-system/releases) page.

2. **Run it** (requires **JDK 26+**):
   ```bash
   java -jar AttendanceMgt.jar
   ```

That's it — the splash screen will appear, followed by the login window!

---

### 🔧 Build from Source (Developer Setup)

#### Prerequisites
- **JDK 26+** (Java Development Kit)
- Any Java IDE (VS Code with Java extension, NetBeans, IntelliJ, Eclipse)

#### Clone & Run
```bash
# 1. Clone the repository
git clone https://github.com/ashudubeyvns/attendance-management-system.git
cd attendance-management-system/AttendanceMgt_1

# 2. Compile all source files
javac -d build/classes src/attendancemgt/*.java

# 3. Run the application
java -cp build/classes attendancemgt.AttendanceMgt
```

#### Build executable JAR
```bash
javac -d build/classes src/attendancemgt/*.java
jar cfm dist/AttendanceMgt.jar manifest.mf -C build/classes .
java -jar dist/AttendanceMgt.jar
```

### 🔑 Test Credentials

| Role | Username | Password |
|------|----------|----------|
| **Administrator** | `admin` | `Admin@123` |
| **Faculty** | `faculty1` | `Fac@12345` |
| **Faculty** | `faculty2` | `Fac@12345` |
| **Student** | `24SCSE1180456` | `12345` |
| **Student** | `24SCSE1180381` | `12345` |
| **Student** | `101` | `12345` |
| **Student** | `102` | `12345` |

---

## 📁 Project Structure

```
AttendanceMgt_1/
├── src/
│   ├── attendancemgt/
│   │   ├── UIStyles.java              # Design system (colors, fonts, themes)
│   │   ├── GlassPanel.java            # Glassmorphism panel component
│   │   ├── ModernButton.java          # Rounded buttons with hover/ripple
│   │   ├── NotificationToast.java     # Animated toast notifications
│   │   ├── ModernTable.java           # Enhanced table with search/sort/filter
│   │   ├── ChartPanel.java            # Animated bar/pie/line charts
│   │   ├── SplashScreen.java          # Splash screen with loading bar
│   │   ├── LoadingScreen.java         # Loading overlay component
│   │   ├── AttendanceMgt.java         # Main entry point
│   │   ├── login_frame.java           # Login screen (redesigned)
│   │   ├── HomeScreen.java            # Dashboard with CardLayout navigation
│   │   ├── FacultyDashboard.java      # Faculty role dashboard
│   │   ├── StudentDashboard.java      # Student role dashboard
│   │   ├── MarkAttendance.java        # Attendance marking module
│   │   ├── ViewAttendance.java        # Attendance viewing/reporting
│   │   ├── AssignmentModule.java      # Assignment marks management
│   │   ├── SessionalsModule.java      # Sessional marks management
│   │   ├── Change_Password.java       # Password change form
│   │   ├── ReportExporter.java        # PDF/Excel/Print export utilities
│   │   ├── SubjectData.java           # Course & subject data repository
│   │   ├── UserSession.java           # Session management (singleton)
│   │   ├── PasswordUtils.java         # SHA-256 password hashing
│   │   └── resources/
│   │       └── images.png             # Banner image
│   └── login_frame.java               # Standalone login (alternate)
├── build/
│   └── classes/                       # Compiled .class files
├── nbproject/                         # NetBeans project config
├── docs/                              # GitHub Pages website
│   ├── index.html                     # Landing page
│   └── assets/
│       ├── css/style.css
│       └── js/main.js
├── build.xml                          # Ant build script
├── manifest.mf                        # JAR manifest
├── .gitignore
└── README.md
```

---

## 🎓 Courses Supported

| # | Course | Semesters | Department |
|---|--------|-----------|------------|
| 1 | **BCA** — Bachelor of Computer Applications | 6 Semesters | CSA |
| 2 | **MCA** — Master of Computer Applications | 4 Semesters | MCA |
| 3 | **B.Tech CSE** — Computer Science & Engineering | 8 Semesters | CSE |
| 4 | **B.Tech IT** — Information Technology | 8 Semesters | IT |
| 5 | **B.Sc CS** — Computer Science | 6 Semesters | CS |
| 6 | **M.Sc CS** — Computer Science | 4 Semesters | CS |

Each course includes a comprehensive curriculum with theory and lab subjects per semester.

---

## 🔧 Development

### Building with NetBeans (Recommended)
1. Open project in Apache NetBeans IDE
2. Clean & Build (`Shift+F11`)
3. Run (`F6`)

### Building manually with Ant
```bash
ant clean
ant build
ant run
```

### REST API (Built-in)
The application starts a lightweight REST API server on port 8080:
```bash
# Health check
curl http://localhost:8080/api/health

# Session info
curl http://localhost:8080/api/session

# Course list
curl http://localhost:8080/api/courses

# Attendance records
curl "http://localhost:8080/api/attendance?course=BCA&sem=1"
```

---

## 📌 Resume-Worthy Features

| Feature | Description |
|---------|-------------|
| **Role-Based Login** | Admin, Faculty, Student with different permissions |
| **Dashboard Analytics** | Animated charts (bar, pie, line) using pure Java2D |
| **Dark/Light Theme** | Full dark mode with persisted preference toggle |
| **PDF/Excel Export** | Print-to-PDF and CSV export with formatted reports |
| **Toast Notifications** | Animated slide-in notifications replacing JOptionPane |
| **Glassmorphism UI** | Modern translucent panels with blur effects |
| **Splash/Loading Screens** | Branded startup experience with progress indicators |
| **SHA-256 Auth** | Salted password hashing for secure authentication |
| **REST API** | Built-in HTTP server for external integrations |
| **Multi-Course Support** | 6 courses with 30+ semester-wise subjects |

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">
  <p>Made with ❤️ by <a href="https://github.com/ashudubeyvns">Ashutosh Dubey</a></p>
  <p>
    <img src="https://img.shields.io/github/stars/ashudubeyvns/attendance-management-system?style=social" alt="Stars"/>
    <img src="https://img.shields.io/github/forks/ashudubeyvns/attendance-management-system?style=social" alt="Forks"/>
  </p>
</div>
