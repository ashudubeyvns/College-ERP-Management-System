package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignmentModule {

    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color DROPDOWN_BG = new Color(52, 152, 219);
    private static final Color HEADER_BG = new Color(52, 73, 94);
    private static final Color ROW_ALT = new Color(245, 245, 245);

    /**
     * Returns a JPanel for the Assignment Module that can be embedded in HomeScreen's CardLayout.
     */
    public static JPanel getPanel(HomeScreen homeScreen, String course, int semester) {
        String[][] subjects = SubjectData.getSubjectsForCourseSemester(course, semester);
        String[] sections = {"A", "B", "C"};

        JPanel main = new JPanel(null);
        main.setBackground(BG_LIGHT);

        // Header / Image
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, 1200, 180);
        imagePanel.setBackground(WHITE);
        try {
            java.net.URL imgUrl = AssignmentModule.class.getResource("/attendancemgt/resources/images.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(1200, 180, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaled)));
            } else {
                JLabel fallback = new JLabel("ASSIGNMENT MODULE", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
                fallback.setForeground(new Color(44, 62, 80));
                imagePanel.add(fallback);
            }
        } catch (Exception ex) {
            JLabel fallback = new JLabel("ASSIGNMENT MODULE", SwingConstants.CENTER);
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
            fallback.setForeground(new Color(44, 62, 80));
            imagePanel.add(fallback);
        }
        main.add(imagePanel);

        // Navigation Bar with Home button
        JPanel navPanel = new JPanel(null);
        navPanel.setBounds(0, 180, 1200, 50);
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

        JLabel infoLbl = new JLabel(course + " | Semester " + semester + " | Assignment Module", SwingConstants.CENTER);
        infoLbl.setBounds(250, 5, 600, 40);
        infoLbl.setForeground(WHITE);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        navPanel.add(infoLbl);

        // Title
        JLabel title = new JLabel("ASSIGNMENT MODULE - " + course.toUpperCase() + " (Sem " + semester + ")", SwingConstants.CENTER);
        title.setBounds(0, 235, 1200, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY);
        main.add(title);

        // Column Headers
        String[] headers = {"Sr#", "Section", "Subject Code", "Subject Name", "Type", "Credits", "Add Max Marks", "Upload Marks", "Update Marks", "Details"};
        int[] colWidths = {40, 70, 100, 250, 50, 60, 130, 150, 150, 140};

        int yHeader = 280;
        int rowH = 38;
        int dataH = 45;

        int xStart = 15;
        for (int i = 0; i < headers.length; i++) {
            JLabel h = new JLabel(headers[i], SwingConstants.CENTER);
            h.setBounds(xStart, yHeader, colWidths[i], rowH);
            h.setBackground(HEADER_BG);
            h.setForeground(WHITE);
            h.setFont(new Font("Segoe UI", Font.BOLD, 11));
            h.setOpaque(true);
            main.add(h);
            xStart += colWidths[i];
        }

        int yData = yHeader + rowH + 5;
        int btnColStart = 15 + colWidths[0] + colWidths[1] + colWidths[2] + colWidths[3] + colWidths[4] + colWidths[5];

        if (subjects.length == 0) {
            JLabel noData = new JLabel("No subjects found for " + course + " Semester " + semester, SwingConstants.CENTER);
            noData.setBounds(15, yData, 1150, 50);
            noData.setFont(new Font("Segoe UI", Font.BOLD, 16));
            noData.setForeground(Color.RED);
            main.add(noData);
            yData += 60;
        } else {
            for (int r = 0; r < subjects.length; r++) {
                String[] subj = subjects[r];
                Color bgColor = (r % 2 == 0) ? WHITE : ROW_ALT;
                String[] rowData = {
                    String.valueOf(r + 1),
                    sections[r % sections.length],
                    subj[3],
                    subj[4],
                    subj[6],
                    subj[7]
                };

                int xPos = 15;
                for (int c = 0; c < rowData.length; c++) {
                    JLabel cell = new JLabel(rowData[c], SwingConstants.CENTER);
                    cell.setBounds(xPos, yData, colWidths[c], dataH);
                    cell.setBackground(bgColor);
                    cell.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    cell.setOpaque(true);
                    cell.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                    main.add(cell);
                    xPos += colWidths[c];
                }

                String[] btnTexts = {"AddMaxMarks", "UploadMarks", "UpdateRecord", "Details"};
                int[] btnWidths = {130, 150, 150, 140};
                int btnX = btnColStart;
                final String subjName = subj[4];
                for (int bi = 0; bi < 4; bi++) {
                    final int bIdx = bi;
                    JButton btn = new JButton(btnTexts[bi]);
                    btn.setBounds(btnX, yData, btnWidths[bi], dataH);
                    btn.setBackground(ACCENT);
                    btn.setForeground(WHITE);
                    btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    btn.setFocusPainted(false);
                    btn.setBorderPainted(false);
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setOpaque(true);
                    String msg = "<html><b>" + btnTexts[bIdx] + "</b><br>" + subjName + "<br>" + course + " Sem " + semester + "</html>";
                    btn.addActionListener(ev -> JOptionPane.showMessageDialog(main, msg, "Action", JOptionPane.INFORMATION_MESSAGE));
                    main.add(btn);
                    btnX += btnWidths[bi];
                }
                yData += dataH + 3;
            }
        }

        int totalHeight = yData + 60;
        main.setPreferredSize(new Dimension(1200, totalHeight));
        return main;
    }
}
