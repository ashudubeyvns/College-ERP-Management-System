package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ViewAttendance {

    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color HEADER_BG = new Color(52, 73, 94);
    private static final Color ROW_ALT = new Color(245, 245, 245);
    private static final Color SUCCESS = new Color(39, 174, 96);
    private static final Color DANGER = new Color(192, 57, 43);
    private static final Color WARNING = new Color(243, 156, 18);

    public static JPanel getPanel(HomeScreen homeScreen, String course, int semester) {
        // Use null layout like other modules - scrolling handled by HomeScreen wrapper
        JPanel main = new JPanel(null);
        main.setBackground(BG_LIGHT);

        // Header
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, 1200, 120);
        imagePanel.setBackground(WHITE);
        try {
            java.net.URL imgUrl = ViewAttendance.class.getResource("/attendancemgt/resources/images.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(1200, 120, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaled)));
            } else {
                JLabel fallback = new JLabel("VIEW ATTENDANCE", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
                fallback.setForeground(new Color(44, 62, 80));
                imagePanel.add(fallback);
            }
        } catch (Exception ex) {
            JLabel fallback = new JLabel("VIEW ATTENDANCE", SwingConstants.CENTER);
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
            fallback.setForeground(new Color(44, 62, 80));
            imagePanel.add(fallback);
        }
        main.add(imagePanel);

        // Nav Bar
        JPanel navPanel = new JPanel(null);
        navPanel.setBounds(0, 120, 1200, 50);
        navPanel.setBackground(PRIMARY);
        main.add(navPanel);

        JButton btnHome = new JButton("\u2190 Home");
        btnHome.setBounds(10, 5, 100, 40);
        btnHome.setBackground(ACCENT);
        btnHome.setForeground(WHITE);
        btnHome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHome.setFocusPainted(false);
        btnHome.setBorderPainted(false);
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHome.setOpaque(true);
        btnHome.addActionListener(ev -> {
            if (homeScreen != null) homeScreen.showHome();
        });
        navPanel.add(btnHome);

        JButton btnAttendance = new JButton("\u2190 Attendance");
        btnAttendance.setBounds(120, 5, 140, 40);
        btnAttendance.setBackground(new Color(39, 174, 96));
        btnAttendance.setForeground(WHITE);
        btnAttendance.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAttendance.setFocusPainted(false);
        btnAttendance.setBorderPainted(false);
        btnAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAttendance.setOpaque(true);
        btnAttendance.addActionListener(ev -> {
            if (homeScreen != null) homeScreen.showAttendance();
        });
        navPanel.add(btnAttendance);

        JLabel infoLbl = new JLabel(course + " | Semester " + semester + " | Attendance Report", SwingConstants.CENTER);
        infoLbl.setBounds(300, 5, 600, 40);
        infoLbl.setForeground(WHITE);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        navPanel.add(infoLbl);

        // Title
        JLabel title = new JLabel("\uD83D\uDCCA ATTENDANCE REPORT - " + course.toUpperCase() + " (Sem " + semester + ")", SwingConstants.CENTER);
        title.setBounds(0, 185, 1200, 35);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY);
        main.add(title);

        // Date & Filter Section
        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(30, 230, 50, 30);
        lblDate.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDate.setForeground(PRIMARY);
        main.add(lblDate);

        JTextField txtDate = new JTextField(LocalDate.now().toString());
        txtDate.setBounds(80, 230, 130, 30);
        txtDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDate.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        txtDate.setHorizontalAlignment(JTextField.CENTER);
        main.add(txtDate);

        JLabel lblFilter = new JLabel("Section:");
        lblFilter.setBounds(240, 230, 60, 30);
        lblFilter.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFilter.setForeground(PRIMARY);
        main.add(lblFilter);

        String[] sections = {"All", "A", "B", "C"};
        JComboBox<String> sectionDD = new JComboBox<>(sections);
        sectionDD.setBounds(305, 230, 80, 30);
        sectionDD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sectionDD.setBackground(WHITE);
        main.add(sectionDD);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(410, 230, 50, 30);
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblType.setForeground(PRIMARY);
        main.add(lblType);

        String[] types = {"Daily", "Weekly", "Monthly", "Consolidated"};
        JComboBox<String> typeDD = new JComboBox<>(types);
        typeDD.setBounds(460, 230, 130, 30);
        typeDD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        typeDD.setBackground(WHITE);
        main.add(typeDD);

        JButton refreshBtn = new JButton("\uD83D\uDD04 Refresh");
        refreshBtn.setBounds(610, 228, 110, 35);
        refreshBtn.setBackground(ACCENT);
        refreshBtn.setForeground(WHITE);
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.setOpaque(true);
        refreshBtn.addActionListener(ev ->
            JOptionPane.showMessageDialog(main,
                "<html><b>Report Refreshed</b><br>" + course + " Sem " + semester + "<br>" + typeDD.getSelectedItem() + " Report</html>",
                "Refresh", JOptionPane.INFORMATION_MESSAGE));
        main.add(refreshBtn);

        // Summary Stats Cards
        int yStats = 280;
        String[][] statCards = {
            {"\uD83D\uDCC5 Total Days", "45"},
            {"\u2705 Present", "38"},
            {"\u274C Absent", "5"},
            {"\u23F0 Late", "2"},
            {"\uD83D\uDCCA Attendance %", "84.4%"}
        };
        Color[] statColors = {ACCENT, SUCCESS, DANGER, WARNING, ACCENT};

        for (int i = 0; i < statCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(30 + i * 155, yStats, 140, 70);
            card.setBackground(WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

            JLabel stIcon = new JLabel(statCards[i][0]);
            stIcon.setBounds(10, 5, 30, 25);
            stIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            card.add(stIcon);

            JLabel stVal = new JLabel(statCards[i][1]);
            stVal.setBounds(10, 30, 120, 25);
            stVal.setFont(new Font("Segoe UI", Font.BOLD, 20));
            stVal.setForeground(statColors[i]);
            card.add(stVal);

            main.add(card);
        }

        // Table
        int yTableHeader = yStats + 90;
        String[] headers = {"Sr#", "Date", "Roll No", "Student Name", "Section", "Subject", "Status", "Time"};
        int[] colWidths = {40, 110, 120, 200, 70, 200, 100, 100};
        int xStart = 30;

        for (int i = 0; i < headers.length; i++) {
            JLabel h = new JLabel(headers[i], SwingConstants.CENTER);
            h.setBounds(xStart, yTableHeader, colWidths[i], 35);
            h.setBackground(HEADER_BG);
            h.setForeground(WHITE);
            h.setFont(new Font("Segoe UI", Font.BOLD, 11));
            h.setOpaque(true);
            main.add(h);
            xStart += colWidths[i];
        }

        // Sample attendance records
        String[][] records = {
            {"1", "2024-03-01", "24SCSE1180456", "Aarav Sharma", "A", "Data Structures", "\u2705 Present", "09:15 AM"},
            {"2", "2024-03-01", "24SCSE1180381", "Priya Patel", "A", "Data Structures", "\u2705 Present", "09:10 AM"},
            {"3", "2024-03-01", "24SCSE2030222", "Rohit Singh", "B", "Java Programming", "\u274C Absent", "-"},
            {"4", "2024-03-01", "101", "Ashutosh Dubey", "A", "DBMS", "\u2705 Present", "09:20 AM"},
            {"5", "2024-03-02", "102", "Aditya Verma", "B", "Operating Systems", "\u23F0 Late", "09:45 AM"},
            {"6", "2024-03-02", "103", "Ananya Reddy", "A", "Computer Networks", "\u2705 Present", "09:05 AM"},
            {"7", "2024-03-02", "104", "Arjun Nair", "B", "Software Engg", "\u2705 Present", "09:12 AM"},
            {"8", "2024-03-02", "105", "Diya Joshi", "A", "Python", "\u274C Absent", "-"},
            {"9", "2024-03-03", "106", "Kabir Das", "B", "Data Structures", "\u2705 Present", "09:08 AM"},
            {"10", "2024-03-03", "107", "Ishita Mehta", "A", "DBMS", "\u2705 Present", "09:15 AM"},
            {"11", "2024-03-03", "108", "Vikram Rao", "A", "Java Programming", "\u2705 Present", "09:22 AM"},
            {"12", "2024-03-03", "109", "Neha Kapoor", "B", "Operating Systems", "\u23F0 Late", "09:50 AM"},
            {"13", "2024-03-04", "24SCSE1180456", "Aarav Sharma", "A", "Computer Networks", "\u2705 Present", "09:10 AM"},
            {"14", "2024-03-04", "24SCSE1180381", "Priya Patel", "A", "Computer Networks", "\u274C Absent", "-"},
            {"15", "2024-03-04", "24SCSE2030222", "Rohit Singh", "B", "Python", "\u2705 Present", "09:18 AM"},
        };

        int yData = yTableHeader + 40;
        int rowH = 35;
        for (int r = 0; r < records.length; r++) {
            Color bgColor = (r % 2 == 0) ? WHITE : ROW_ALT;
            xStart = 30;
            for (int c = 0; c < records[r].length; c++) {
                JLabel cell = new JLabel(records[r][c], SwingConstants.CENTER);
                cell.setBounds(xStart, yData, colWidths[c], rowH);
                cell.setBackground(bgColor);
                cell.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
                // Color-code status
                if (c == 6) {
                    if (records[r][c].contains("Present")) {
                        cell.setForeground(SUCCESS);
                        cell.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    } else if (records[r][c].contains("Absent")) {
                        cell.setForeground(DANGER);
                        cell.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    } else if (records[r][c].contains("Late")) {
                        cell.setForeground(WARNING);
                        cell.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    }
                } else {
                    cell.setForeground(PRIMARY);
                }
                main.add(cell);
                xStart += colWidths[c];
            }
            yData += rowH + 3;
        }

        // Export buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnPanel.setBounds(0, yData + 10, 1200, 55);
        btnPanel.setBackground(BG_LIGHT);
        main.add(btnPanel);

        JButton exportPDF = new JButton("\uD83D\uDCC4 Export PDF");
        exportPDF.setBackground(SUCCESS);
        exportPDF.setForeground(WHITE);
        exportPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exportPDF.setFocusPainted(false);
        exportPDF.setBorderPainted(false);
        exportPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportPDF.setOpaque(true);
        exportPDF.addActionListener(ev -> exportReport(course, semester, records, headers, "PDF"));
        btnPanel.add(exportPDF);

        JButton exportExcel = new JButton("\uD83D\uDCC5 Export Excel");
        exportExcel.setBackground(new Color(33, 140, 85));
        exportExcel.setForeground(WHITE);
        exportExcel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        exportExcel.setFocusPainted(false);
        exportExcel.setBorderPainted(false);
        exportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exportExcel.setOpaque(true);
        exportExcel.addActionListener(ev -> exportReport(course, semester, records, headers, "Excel"));
        btnPanel.add(exportExcel);

        JButton printBtn = new JButton("\uD83D\uDDA8\uFE0F Print");
        printBtn.setBackground(ACCENT);
        printBtn.setForeground(WHITE);
        printBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        printBtn.setFocusPainted(false);
        printBtn.setBorderPainted(false);
        printBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        printBtn.setOpaque(true);
        printBtn.addActionListener(ev -> exportReport(course, semester, records, headers, "Print"));
        btnPanel.add(printBtn);

        int totalHeight = yData + 90;
        main.setPreferredSize(new Dimension(1200, totalHeight));
        return main;
    }

    /**
     * Handles exporting the report to PDF, Excel (CSV), or Print.
     */
    private static void exportReport(String course, int semester, String[][] records,
                                      String[] headers, String format) {
        String title = "Attendance Report - " + course + " (Sem " + semester + ")";
        String subtitle = "Generated on " + java.time.LocalDate.now();
        String fileName = "Attendance_" + course.replace(" ", "_") + "_Sem" + semester;

        // Create a clean headers array for export (without emojis)
        String[] cleanHeaders = new String[headers.length];
        for (int i = 0; i < headers.length; i++) {
            cleanHeaders[i] = headers[i].replaceAll("[^\\p{Print}]", "").trim();
        }

        // Get parent frame from a visible window
        JFrame parentFrame = null;
        for (Window w : Window.getWindows()) {
            if (w instanceof JFrame && w.isVisible()) {
                parentFrame = (JFrame) w;
                break;
            }
        }

        switch (format) {
            case "PDF":
                // Via print-to-PDF
                ReportExporter.exportToPrint(parentFrame, records, cleanHeaders, title, subtitle);
                break;
            case "Excel":
                ReportExporter.exportToCSV(parentFrame, records, cleanHeaders, fileName, title);
                break;
            case "Print":
                ReportExporter.exportToPrint(parentFrame, records, cleanHeaders, title, subtitle);
                break;
        }
    }
}
