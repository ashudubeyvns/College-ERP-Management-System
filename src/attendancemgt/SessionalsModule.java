package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionalsModule {

    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color DROPDOWN_BG = new Color(52, 152, 219);
    private static final Color HEADER_BG = new Color(52, 73, 94);
    private static final Color ROW_ALT = new Color(245, 245, 245);

    /**
     * Returns a JPanel for the Sessionals Module that can be embedded in HomeScreen's CardLayout.
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
            java.net.URL imgUrl = SessionalsModule.class.getResource("/attendancemgt/resources/images.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(1200, 180, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaled)));
            } else {
                JLabel fallback = new JLabel("SESSIONALS MODULE", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
                fallback.setForeground(new Color(44, 62, 80));
                imagePanel.add(fallback);
            }
        } catch (Exception ex) {
            JLabel fallback = new JLabel("SESSIONALS MODULE", SwingConstants.CENTER);
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

        JLabel infoLbl = new JLabel(course + " | Semester " + semester + " | Sessionals Module", SwingConstants.CENTER);
        infoLbl.setBounds(250, 5, 600, 40);
        infoLbl.setForeground(WHITE);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        navPanel.add(infoLbl);

        // Title
        JLabel title = new JLabel("SESSIONALS MODULE - " + course.toUpperCase() + " (Sem " + semester + ")", SwingConstants.CENTER);
        title.setBounds(0, 235, 1200, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY);
        main.add(title);

        // Column Headers
        String[] headers = {"Sr#", "Section", "Subject Code", "Subject Name", "Type", "Credits", "Sessional-1 (20)", "Sessional-2 (20)", "Sessional-3 (20)", "Total (60)", "Status"};
        int[] colWidths = {35, 65, 95, 230, 45, 55, 120, 120, 120, 100, 100};

        int yHeader = 280;
        int rowH = 38;
        int dataH = 45;

        int xStart = 15;
        for (int i = 0; i < headers.length; i++) {
            JLabel h = new JLabel(headers[i], SwingConstants.CENTER);
            h.setBounds(xStart, yHeader, colWidths[i], rowH);
            h.setBackground(HEADER_BG);
            h.setForeground(WHITE);
            h.setFont(new Font("Segoe UI", Font.BOLD, 10));
            h.setOpaque(true);
            main.add(h);
            xStart += colWidths[i];
        }

        int yData = yHeader + rowH + 5;

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

                // Sessional entry fields
                JTextField sf1 = new JTextField("");
                sf1.setBounds(xPos, yData, colWidths[6], dataH);
                sf1.setHorizontalAlignment(JTextField.CENTER);
                sf1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                sf1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                main.add(sf1);
                xPos += colWidths[6];

                JTextField sf2 = new JTextField("");
                sf2.setBounds(xPos, yData, colWidths[7], dataH);
                sf2.setHorizontalAlignment(JTextField.CENTER);
                sf2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                sf2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                main.add(sf2);
                xPos += colWidths[7];

                JTextField sf3 = new JTextField("");
                sf3.setBounds(xPos, yData, colWidths[8], dataH);
                sf3.setHorizontalAlignment(JTextField.CENTER);
                sf3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                sf3.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                main.add(sf3);
                xPos += colWidths[8];

                JLabel totalLbl = new JLabel("", SwingConstants.CENTER);
                totalLbl.setBounds(xPos, yData, colWidths[9], dataH);
                totalLbl.setBackground(bgColor);
                totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                totalLbl.setOpaque(true);
                totalLbl.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                main.add(totalLbl);
                xPos += colWidths[9];

                JLabel statusLbl = new JLabel("", SwingConstants.CENTER);
                statusLbl.setBounds(xPos, yData, colWidths[10], dataH);
                statusLbl.setBackground(bgColor);
                statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
                statusLbl.setOpaque(true);
                statusLbl.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
                main.add(statusLbl);

                // Calculate total and status when any field changes
                java.awt.event.KeyAdapter calc = new java.awt.event.KeyAdapter() {
                    public void keyReleased(java.awt.event.KeyEvent e) {
                        calcTotalFromFields(sf1, sf2, sf3, totalLbl, statusLbl);
                    }
                };
                sf1.addKeyListener(calc);
                sf2.addKeyListener(calc);
                sf3.addKeyListener(calc);

                yData += dataH + 3;
            }
        }

        // Save Button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBounds(0, yData + 10, 1200, 50);
        btnPanel.setBackground(BG_LIGHT);
        main.add(btnPanel);

        JButton saveBtn = new JButton("Save Sessionals");
        saveBtn.setBackground(new Color(39, 174, 96));
        saveBtn.setForeground(WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveBtn.setOpaque(true);
        saveBtn.addActionListener(ev -> JOptionPane.showMessageDialog(main,
            "<html><b style='color:#27ae60'>Sessionals saved successfully!</b></html>", "Saved", JOptionPane.INFORMATION_MESSAGE));
        btnPanel.add(saveBtn);

        int totalHeight = yData + 80;
        main.setPreferredSize(new Dimension(1200, totalHeight));
        return main;
    }

    private static void calcTotalFromFields(JTextField sf1, JTextField sf2, JTextField sf3, JLabel totalLbl, JLabel statusLbl) {
        try {
            int v1 = sf1.getText().isEmpty() ? 0 : Integer.parseInt(sf1.getText());
            int v2 = sf2.getText().isEmpty() ? 0 : Integer.parseInt(sf2.getText());
            int v3 = sf3.getText().isEmpty() ? 0 : Integer.parseInt(sf3.getText());
            int total = v1 + v2 + v3;
            totalLbl.setText(String.valueOf(total));
            totalLbl.setForeground(total > 0 ? new Color(39, 174, 96) : Color.GRAY);
            if (total >= 60) {
                statusLbl.setText("Pass");
                statusLbl.setForeground(new Color(39, 174, 96));
            } else if (total > 0) {
                statusLbl.setText("Fail");
                statusLbl.setForeground(Color.RED);
            } else {
                statusLbl.setText("");
                statusLbl.setForeground(Color.GRAY);
            }
        } catch (NumberFormatException ex) {
            totalLbl.setText("Invalid");
            totalLbl.setForeground(Color.RED);
            statusLbl.setText("");
        }
    }
}
