package attendancemgt;

import javax.swing.*;
import java.awt.*;

public class MarkAttendance {

    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color HEADER_BG = new Color(52, 73, 94);
    private static final Color ROW_ALT = new Color(245, 245, 245);
    private static final Color PRESENT_COLOR = new Color(39, 174, 96);
    private static final Color ABSENT_COLOR = new Color(192, 57, 43);

    /**
     * Returns a JPanel for Mark Attendance that can be embedded in HomeScreen's CardLayout.
     */
    public static JPanel getPanel(HomeScreen homeScreen, String course, int semester) {
        String[][] subjects = SubjectData.getSubjectsForCourseSemester(course, semester);
        String[] sections = {"A", "B", "C"};

        JPanel main = new JPanel(null);
        main.setBackground(BG_LIGHT);

        // Header
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, 1200, 150);
        imagePanel.setBackground(WHITE);
        try {
            java.net.URL imgUrl = MarkAttendance.class.getResource("/attendancemgt/resources/images.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(1200, 150, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaled)));
            } else {
                JLabel fallback = new JLabel("ATTENDANCE MANAGEMENT SYSTEM", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
                fallback.setForeground(new Color(44, 62, 80));
                imagePanel.add(fallback);
            }
        } catch (Exception ex) {
            JLabel fallback = new JLabel("ATTENDANCE MANAGEMENT SYSTEM", SwingConstants.CENTER);
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
            fallback.setForeground(new Color(44, 62, 80));
            imagePanel.add(fallback);
        }
        main.add(imagePanel);

        // Nav Bar with Home button
        JPanel navPanel = new JPanel(null);
        navPanel.setBounds(0, 150, 1200, 50);
        navPanel.setBackground(PRIMARY);
        main.add(navPanel);

        JButton btnHome = new JButton("← Home");
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

        JButton btnAttendance = new JButton("← Attendance");
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

        JLabel infoLbl = new JLabel(course + " | Semester " + semester + " | Mark Attendance", SwingConstants.CENTER);
        infoLbl.setBounds(300, 5, 600, 40);
        infoLbl.setForeground(WHITE);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        navPanel.add(infoLbl);

        // Date selector
        JLabel lblDate = new JLabel("Date:");
        lblDate.setBounds(20, 210, 50, 30);
        lblDate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDate.setForeground(PRIMARY);
        main.add(lblDate);

        JTextField txtDate = new JTextField(java.time.LocalDate.now().toString());
        txtDate.setBounds(70, 210, 140, 30);
        txtDate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDate.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        txtDate.setHorizontalAlignment(JTextField.CENTER);
        main.add(txtDate);

        JLabel lblSection = new JLabel("Section:");
        lblSection.setBounds(250, 210, 60, 30);
        lblSection.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSection.setForeground(PRIMARY);
        main.add(lblSection);

        JComboBox<String> sectionDD = new JComboBox<>(sections);
        sectionDD.setBounds(315, 210, 80, 30);
        sectionDD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sectionDD.setBackground(WHITE);
        main.add(sectionDD);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(420, 210, 50, 30);
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblType.setForeground(PRIMARY);
        main.add(lblType);

        String[] types = {"Lecture", "Practical/Tutorial"};
        JComboBox<String> typeDD = new JComboBox<>(types);
        typeDD.setBounds(470, 210, 150, 30);
        typeDD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        typeDD.setBackground(WHITE);
        main.add(typeDD);

        // Title
        JLabel title = new JLabel("MARK ATTENDANCE - " + course.toUpperCase() + " (Sem " + semester + ")", SwingConstants.CENTER);
        title.setBounds(0, 250, 1200, 35);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PRIMARY);
        main.add(title);

        // Table headers
        String[] headers = {"Sr#", "Roll No", "Student Name", "Section", "Status", "Action"};
        int[] colWidths = {40, 100, 250, 80, 100, 200};
        int yHeader = 290;
        int rowH = 35;
        int dataH = 40;

        // Sample students
        String[][] sampleStudents = {
            {"101", "Aarav Sharma"},
            {"102", "Priya Patel"},
            {"103", "Rohit Singh"},
            {"104", "Ashutosh Dubey"},
            {"105", "Aditya Verma"},
            {"106", "Ananya Reddy"},
            {"107", "Arjun Nair"},
            {"108", "Diya Joshi"},
            {"109", "Kabir Das"},
            {"110", "Ishita Mehta"},
            {"111", "Vikram Rao"},
            {"112", "Neha Kapoor"},
            {"113", "Rahul Mishra"},
            {"114", "Pooja Desai"},
            {"115", "Manish Tiwari"}
        };

        int xStart = 15;
        for (int i = 0; i < headers.length; i++) {
            JLabel h = new JLabel(headers[i], SwingConstants.CENTER);
            h.setBounds(xStart, yHeader, colWidths[i], rowH);
            h.setBackground(HEADER_BG);
            h.setForeground(WHITE);
            h.setFont(new Font("Segoe UI", Font.BOLD, 12));
            h.setOpaque(true);
            main.add(h);
            xStart += colWidths[i];
        }

        int yData = yHeader + rowH + 5;
        java.util.List<JToggleButton> toggleBtns = new java.util.ArrayList<>();

        for (int r = 0; r < sampleStudents.length; r++) {
            Color bgColor = (r % 2 == 0) ? WHITE : ROW_ALT;
            String section = sections[r % sections.length];

            String[] rowData = {
                String.valueOf(r + 1),
                sampleStudents[r][0],
                sampleStudents[r][1],
                section,
                "Present"
            };

            int xPos = 15;
            for (int c = 0; c < 4; c++) {
                JLabel cell = new JLabel(rowData[c], SwingConstants.CENTER);
                cell.setBounds(xPos, yData, colWidths[c], dataH);
                cell.setBackground(bgColor);
                cell.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                main.add(cell);
                xPos += colWidths[c];
            }

            // Status label
            JLabel statusLbl = new JLabel("Present", SwingConstants.CENTER);
            statusLbl.setBounds(xPos, yData, colWidths[4], dataH);
            statusLbl.setBackground(PRESENT_COLOR);
            statusLbl.setForeground(WHITE);
            statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            statusLbl.setOpaque(true);
            main.add(statusLbl);
            xPos += colWidths[4];

            // Toggle button
            JToggleButton toggleBtn = new JToggleButton("Mark Absent");
            toggleBtn.setBounds(xPos + 20, yData + 2, 140, dataH - 4);
            toggleBtn.setBackground(ACCENT);
            toggleBtn.setForeground(WHITE);
            toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
            toggleBtn.setFocusPainted(false);
            toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            toggleBtn.setOpaque(true);
            toggleBtn.addActionListener(ev -> {
                if (toggleBtn.isSelected()) {
                    toggleBtn.setText("Mark Present");
                    toggleBtn.setBackground(ABSENT_COLOR);
                    statusLbl.setText("Absent");
                    statusLbl.setBackground(ABSENT_COLOR);
                } else {
                    toggleBtn.setText("Mark Absent");
                    toggleBtn.setBackground(ACCENT);
                    statusLbl.setText("Present");
                    statusLbl.setBackground(PRESENT_COLOR);
                }
            });
            toggleBtns.add(toggleBtn);
            main.add(toggleBtn);

            yData += dataH + 3;
        }

        // Save button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        btnPanel.setBounds(0, yData + 10, 1200, 60);
        btnPanel.setBackground(BG_LIGHT);
        main.add(btnPanel);

        JButton markAllPresent = new JButton("Mark All Present");
        markAllPresent.setBackground(new Color(39, 174, 96));
        markAllPresent.setForeground(WHITE);
        markAllPresent.setFont(new Font("Segoe UI", Font.BOLD, 13));
        markAllPresent.setFocusPainted(false);
        markAllPresent.setBorderPainted(false);
        markAllPresent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        markAllPresent.setOpaque(true);
        markAllPresent.addActionListener(ev -> {
            for (JToggleButton tb : toggleBtns) {
                if (tb.isSelected()) {
                    tb.doClick();
                }
            }
        });
        btnPanel.add(markAllPresent);

        JButton markAllAbsent = new JButton("Mark All Absent");
        markAllAbsent.setBackground(ABSENT_COLOR);
        markAllAbsent.setForeground(WHITE);
        markAllAbsent.setFont(new Font("Segoe UI", Font.BOLD, 13));
        markAllAbsent.setFocusPainted(false);
        markAllAbsent.setBorderPainted(false);
        markAllAbsent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        markAllAbsent.setOpaque(true);
        markAllAbsent.addActionListener(ev -> {
            for (JToggleButton tb : toggleBtns) {
                if (!tb.isSelected()) {
                    tb.doClick();
                }
            }
        });
        btnPanel.add(markAllAbsent);

        JButton saveBtn = new JButton("Save Attendance");
        saveBtn.setBackground(ACCENT);
        saveBtn.setForeground(WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setOpaque(true);
        saveBtn.addActionListener(ev -> JOptionPane.showMessageDialog(main,
            "<html><b style='color:#27ae60'>Attendance saved successfully!</b><br>"
            + "Course: " + course + " | Semester: " + semester + "<br>"
            + "Date: " + txtDate.getText() + " | Type: " + typeDD.getSelectedItem() + "</html>",
            "Attendance Saved", JOptionPane.INFORMATION_MESSAGE));
        btnPanel.add(saveBtn);

        int totalHeight = yData + 90;
        main.setPreferredSize(new Dimension(1200, totalHeight));
        return main;
    }
}
