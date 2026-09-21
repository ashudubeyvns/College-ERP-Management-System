package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Student Dashboard - for student users.
 * Students can only view their attendance, grades, assignments, and sessionals.
 * No edit/mark permissions.
 */
public class StudentDashboard {

    private JFrame frame;
    private JPanel contentPanel;

    public StudentDashboard() {
        UserSession session = UserSession.getInstance();
        String displayName = (session != null) ? session.getFullName() : "Student";
        String roleName = (session != null) ? session.getRole().getDisplayName() : "Student";

        frame = new JFrame("College ERP - " + roleName + " Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(UIStyles.BG_LIGHT);
        frame.setLayout(new BorderLayout());

        // Top bar
        JPanel topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(UIStyles.PRIMARY_DARK);

        JLabel title = new JLabel("\uD83C\uDF93 " + roleName + " Dashboard");
        title.setBounds(25, 0, 400, 60);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(Color.WHITE);
        topBar.add(title);

        JLabel userLabel = new JLabel("\uD83D\uDC64 " + displayName + " (" + roleName + ")");
        userLabel.setBounds(450, 0, 250, 60);
        userLabel.setFont(UIStyles.FONT_PLAIN_LG);
        userLabel.setForeground(UIStyles.TEXT_LIGHT);
        topBar.add(userLabel);

        JButton logoutBtn = new JButton("\uD83D\uDEAA Logout");
        logoutBtn.setBounds(frame.getWidth() - 120, 12, 100, 36);
        logoutBtn.setBackground(UIStyles.DANGER);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(UIStyles.FONT_BOLD_MD);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setOpaque(true);
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            UserSession.destroySession();
            try { new login_frame(); } catch (IOException ex) {
                Logger.getLogger(StudentDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        topBar.add(logoutBtn);

        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                logoutBtn.setBounds(frame.getWidth() - 120, 12, 100, 36);
            }
        });

        frame.add(topBar, BorderLayout.NORTH);

        // Main content scrollable
        contentPanel = new JPanel(null);
        contentPanel.setBackground(UIStyles.BG_LIGHT);
        buildStudentContent(contentPanel);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void buildStudentContent(JPanel main) {
        UserSession session = UserSession.getInstance();

        JLabel welcome = new JLabel("\uD83D\uDC4B Welcome, " + (session != null ? session.getFullName() : "Student"));
        welcome.setBounds(30, 20, 600, 35);
        welcome.setFont(UIStyles.FONT_BOLD_3XL);
        welcome.setForeground(UIStyles.TEXT_DARK);
        main.add(welcome);

        JLabel subtext = new JLabel("Roll No: " + (session != null ? session.getUsername() : "N/A")
            + " | Session: " + (session != null ? session.getSessionId() : "N/A")
            + " | " + (session != null ? session.getRole().getDisplayName() : "Student"));
        subtext.setBounds(30, 55, 600, 20);
        subtext.setFont(UIStyles.FONT_PLAIN_SM);
        subtext.setForeground(UIStyles.TEXT_MUTED);
        main.add(subtext);

        // Stats Cards
        int yCards = 90;
        String[][] statCards = {
            {"\uD83D\uDCC5", "87.5%", "Overall Attendance"},
            {"\u2705", "35", "Days Present"},
            {"\u274C", "5", "Days Absent"},
            {"\uD83C\uDF93", "BCA", "Course (Sem 4)"}
        };
        Color[] colors = {UIStyles.SUCCESS, UIStyles.PRIMARY, UIStyles.DANGER, new Color(139, 92, 246)};

        int cardW = 200;
        int cardH = 100;
        int gap = 20;

        for (int i = 0; i < statCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(30 + i * (cardW + gap), yCards, cardW, cardH);
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
            main.add(card);

            JLabel icon = new JLabel(statCards[i][0]);
            icon.setBounds(15, 10, 50, 35);
            icon.setFont(new Font("Segoe UI", Font.PLAIN, 26));
            card.add(icon);

            JLabel val = new JLabel(statCards[i][1]);
            val.setBounds(15, 45, 170, 28);
            val.setFont(UIStyles.FONT_BOLD_3XL);
            val.setForeground(colors[i]);
            card.add(val);

            JLabel t = new JLabel(statCards[i][2]);
            t.setBounds(15, 72, 170, 18);
            t.setFont(UIStyles.FONT_PLAIN_MD);
            t.setForeground(UIStyles.TEXT_DARK);
            card.add(t);
        }

        // Subject-wise attendance table using ModernTable
        int yTable = yCards + cardH + 30;
        JLabel tableTitle = new JLabel("\uD83D\uDCCA  My Attendance Record");
        tableTitle.setBounds(30, yTable, 400, 30);
        tableTitle.setFont(UIStyles.FONT_BOLD_XL);
        tableTitle.setForeground(UIStyles.TEXT_DARK);
        main.add(tableTitle);

        String[] headers = {"Sr#", "Subject Code", "Subject Name", "Type", "Total", "Present", "Absent", "%"};
        Object[][] attendance = {
            {"1", "BCA301", "Operating Systems", "T", "30", "27", "3", "90%"},
            {"2", "BCA302", "Computer Networks", "T", "28", "24", "4", "86%"},
            {"3", "BCA303", "Software Engineering", "T", "25", "20", "5", "80%"},
            {"4", "BCA304", "Python Programming", "T", "26", "24", "2", "92%"},
            {"5", "BCA351", "Python Lab", "P", "15", "15", "0", "100%"},
            {"6", "BCA352", "Networks Lab", "P", "14", "12", "2", "86%"},
        };

        ModernTable modernTable = new ModernTable(headers, attendance);
        modernTable.setBounds(20, yTable + 40, 800, 280);
        main.add(modernTable);

        // Quick info panels
        int yInfo = yTable + 350;
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBounds(30, yInfo, 650, 110);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        main.add(infoPanel);

        JLabel infoTitle = new JLabel("\u2139\uFE0F  Information");
        infoTitle.setBounds(20, 12, 200, 22);
        infoTitle.setFont(UIStyles.FONT_BOLD_LG);
        infoTitle.setForeground(UIStyles.TEXT_DARK);
        infoPanel.add(infoTitle);

        JLabel info1 = new JLabel("\u2022 Minimum 75% attendance is required in each subject.");
        info1.setBounds(20, 40, 400, 20);
        info1.setFont(UIStyles.FONT_PLAIN_MD);
        info1.setForeground(UIStyles.TEXT_DARK);
        infoPanel.add(info1);

        JLabel info2 = new JLabel("\u2022 Contact your faculty/HOD for any discrepancies in attendance.");
        info2.setBounds(20, 62, 400, 20);
        info2.setFont(UIStyles.FONT_PLAIN_MD);
        info2.setForeground(UIStyles.TEXT_DARK);
        infoPanel.add(info2);

        JLabel info3 = new JLabel("\u2022 Attendance is updated daily by faculty members.");
        info3.setBounds(20, 84, 400, 20);
        info3.setFont(UIStyles.FONT_PLAIN_MD);
        info3.setForeground(UIStyles.TEXT_DARK);
        infoPanel.add(info3);

        // Quick action buttons for student
        int yActions = yInfo + 130;
        JLabel actionsTitle = new JLabel("\u26A1  Quick Actions");
        actionsTitle.setBounds(30, yActions, 300, 30);
        actionsTitle.setFont(UIStyles.FONT_BOLD_XL);
        actionsTitle.setForeground(UIStyles.TEXT_DARK);
        main.add(actionsTitle);

        ModernButton viewReportBtn = new ModernButton("  \uD83D\uDCCA  View Full Report", ModernButton.ButtonVariant.PRIMARY);
        viewReportBtn.setBounds(30, yActions + 45, 200, 40);
        viewReportBtn.addActionListener(e -> NotificationToast.show(frame, "Opening full attendance report...", NotificationToast.ToastType.INFO));
        main.add(viewReportBtn);

        ModernButton printBtn = new ModernButton("  \uD83D\uDDA8\uFE0F  Print Report", ModernButton.ButtonVariant.SECONDARY);
        printBtn.setBounds(250, yActions + 45, 180, 40);
        printBtn.addActionListener(e -> NotificationToast.show(frame, "Print feature coming soon!", NotificationToast.ToastType.INFO));
        main.add(printBtn);

        int totalHeight = yActions + 130;
        main.setPreferredSize(new Dimension(1000, totalHeight));
    }

    public static void open() {
        SwingUtilities.invokeLater(() -> new StudentDashboard());
    }
}
