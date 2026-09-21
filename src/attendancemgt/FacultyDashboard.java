package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Faculty Dashboard - for faculty/staff users.
 * Faculty can mark attendance, view reports, manage assignments and sessionals.
 * Uses card layout to embed modules in-panel rather than showing JOptionPane dialogs.
 */
public class FacultyDashboard {

    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public FacultyDashboard() {
        UserSession session = UserSession.getInstance();
        String displayName = (session != null) ? session.getFullName() : "Faculty";
        String roleName = (session != null) ? session.getRole().getDisplayName() : "Faculty";

        frame = new JFrame("College ERP - " + roleName + " Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(UIStyles.BG_LIGHT);
        frame.setLayout(new BorderLayout());

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(UIStyles.PRIMARY_DARK);

        JLabel title = new JLabel("  \uD83C\uDF93 " + roleName + " Dashboard");
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.WEST);

        // Right side panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);

        JLabel userLabel = new JLabel("\uD83D\uDC64 " + displayName);
        userLabel.setFont(UIStyles.FONT_PLAIN_LG);
        userLabel.setForeground(UIStyles.TEXT_LIGHT);
        rightPanel.add(userLabel);

        JButton logoutBtn = new JButton("\uD83D\uDEAA Logout");
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
                Logger.getLogger(FacultyDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rightPanel.add(logoutBtn);

        topBar.add(rightPanel, BorderLayout.EAST);
        frame.add(topBar, BorderLayout.NORTH);

        // Main content with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(UIStyles.BG_LIGHT);
        cardPanel.add(createFacultyHomePanel(), "home");

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createFacultyHomePanel() {
        JPanel main = new JPanel(null);
        main.setBackground(UIStyles.BG_LIGHT);

        UserSession session = UserSession.getInstance();
        JLabel welcome = new JLabel("\uD83D\uDC4B Welcome, " + (session != null ? session.getFullName() : "Faculty"));
        welcome.setBounds(30, 20, 600, 35);
        welcome.setFont(UIStyles.FONT_BOLD_3XL);
        welcome.setForeground(UIStyles.TEXT_DARK);
        main.add(welcome);

        JLabel subtext = new JLabel("Session: " + (session != null ? session.getSessionId() : "N/A")
            + " | Login: " + (session != null ? session.getFormattedLoginTime() : "N/A"));
        subtext.setBounds(30, 55, 600, 20);
        subtext.setFont(UIStyles.FONT_PLAIN_SM);
        subtext.setForeground(UIStyles.TEXT_MUTED);
        main.add(subtext);

        // Stats cards
        int yCards = 90;
        String[][] statCards = {
            {"\uD83D\uDCC5", "6", "Courses Assigned"},
            {"\uD83D\uDC64", "120+", "Students"},
            {"\u2705", "89.7%", "Today's Attendance"},
            {"\uD83D\uDCCA", "87.5%", "Overall Average"}
        };
        Color[] colors = {UIStyles.PRIMARY, UIStyles.SUCCESS, UIStyles.WARNING, new Color(139, 92, 246)};

        int cardW = 200;
        int cardH = 100;
        int gap = 20;

        for (int i = 0; i < statCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(30 + i * (cardW + gap), yCards, cardW, cardH);
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

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

            main.add(card);
        }

        // Quick Action Modules
        int yModules = yCards + cardH + 30;
        JLabel modTitle = new JLabel("\u26A1 Quick Actions");
        modTitle.setBounds(30, yModules, 300, 30);
        modTitle.setFont(UIStyles.FONT_BOLD_XL);
        modTitle.setForeground(UIStyles.TEXT_DARK);
        main.add(modTitle);

        String[] moduleNames = {
            "\u2705 Mark Attendance",
            "\uD83D\uDCCA View Reports",
            "\uD83D\uDCCB Assignments",
            "\uD83D\uDCDD Sessionals",
            "\uD83D\uDD12 Change Password"
        };
        String[] moduleDescs = {
            "Mark attendance for your classes",
            "View attendance reports & analytics",
            "Upload and manage assignments",
            "Manage sessional marks",
            "Update your password"
        };
        Color[] moduleColors = {
            UIStyles.SUCCESS, UIStyles.PRIMARY,
            new Color(139, 92, 246), new Color(230, 126, 34),
            UIStyles.DANGER
        };

        int mCardW = 420;
        int mCardH = 70;
        int mX = 30;
        int mGap = 15;

        for (int i = 0; i < moduleNames.length; i++) {
            int col = i % 2;
            int row = i / 2;
            int mx = mX + col * (mCardW + mGap);
            int my = yModules + 40 + row * (mCardH + 12);
            final int idx = i;

            JPanel mc = new JPanel(null);
            mc.setBounds(mx, my, mCardW, mCardH);
            mc.setBackground(Color.WHITE);
            mc.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
            main.add(mc);

            JLabel icon = new JLabel(moduleNames[i].substring(0, 2));
            icon.setBounds(15, 18, 35, 35);
            icon.setFont(new Font("Segoe UI", Font.PLAIN, 24));
            mc.add(icon);

            JLabel tl = new JLabel(moduleNames[i].substring(2).trim());
            tl.setBounds(55, 12, 200, 22);
            tl.setFont(UIStyles.FONT_BOLD_MD);
            tl.setForeground(UIStyles.TEXT_DARK);
            mc.add(tl);

            JLabel dl = new JLabel(moduleDescs[i]);
            dl.setBounds(55, 36, 250, 20);
            dl.setFont(UIStyles.FONT_PLAIN_SM);
            dl.setForeground(UIStyles.TEXT_MUTED);
            mc.add(dl);

            JButton goBtn = new JButton("Open");
            goBtn.setBounds(mCardW - 85, 18, 70, 34);
            goBtn.setBackground(moduleColors[i]);
            goBtn.setForeground(Color.WHITE);
            goBtn.setFont(UIStyles.FONT_BOLD_SM);
            goBtn.setFocusPainted(false);
            goBtn.setBorderPainted(false);
            goBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            goBtn.setOpaque(true);
            goBtn.addActionListener(e -> openModule(idx));
            mc.add(goBtn);
        }

        int totalHeight = yModules + 240;
        main.setPreferredSize(new Dimension(1000, totalHeight));
        return main;
    }

    /**
     * Opens a module card in the card layout.
     * Each module gets wrapped in a scrollable panel with a top navigation bar.
     */
    private void openModule(int idx) {
        String course = "BCA";
        int sem = 1;
        try {
            JPanel modulePanel = null;
            String cardName = "";
            switch (idx) {
                case 0:
                    modulePanel = MarkAttendance.getPanel(null, course, sem);
                    cardName = "markattendance";
                    break;
                case 1:
                    modulePanel = ViewAttendance.getPanel(null, course, sem);
                    cardName = "viewreports";
                    break;
                case 2:
                    modulePanel = AssignmentModule.getPanel(null, course, sem);
                    cardName = "assignments";
                    break;
                case 3:
                    modulePanel = SessionalsModule.getPanel(null, course, sem);
                    cardName = "sessionals";
                    break;
                case 4:
                    modulePanel = Change_Password.getPanel(null);
                    cardName = "changepass";
                    break;
            }
            if (modulePanel == null || cardName.isEmpty()) return;

            // Create a wrapper panel that fills the viewport
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(UIStyles.BG_LIGHT);

            // Top navigation bar with back button
            JPanel navBar = new JPanel(new BorderLayout());
            navBar.setBackground(UIStyles.PRIMARY_DARK);
            navBar.setPreferredSize(new Dimension(0, 50));

            JPanel navLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            navLeft.setOpaque(false);

            JButton backBtn = new JButton("\u2190  Back to Dashboard");
            backBtn.setBackground(UIStyles.PRIMARY);
            backBtn.setForeground(Color.WHITE);
            backBtn.setFont(UIStyles.FONT_BOLD_MD);
            backBtn.setFocusPainted(false);
            backBtn.setBorderPainted(false);
            backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            backBtn.setOpaque(true);
            backBtn.addActionListener(ev -> cardLayout.show(cardPanel, "home"));
            navLeft.add(backBtn);

            JLabel cardTitle = new JLabel(getModuleTitle(idx));
            cardTitle.setFont(UIStyles.FONT_BOLD_XL);
            cardTitle.setForeground(Color.WHITE);
            navLeft.add(cardTitle);

            navBar.add(navLeft, BorderLayout.WEST);
            wrapper.add(navBar, BorderLayout.NORTH);

            // Module content in a scroll pane
            JScrollPane moduleScroll = new JScrollPane(modulePanel);
            moduleScroll.setBorder(null);
            moduleScroll.getVerticalScrollBar().setUnitIncrement(16);
            moduleScroll.getHorizontalScrollBar().setUnitIncrement(16);
            wrapper.add(moduleScroll, BorderLayout.CENTER);

            // Add to card panel and show
            cardPanel.add(wrapper, cardName);
            cardLayout.show(cardPanel, cardName);

        } catch (Exception ex) {
            Logger.getLogger(FacultyDashboard.class.getName()).log(Level.SEVERE, null, ex);
            NotificationToast.show(frame, "Error opening module: " + ex.getMessage(), NotificationToast.ToastType.ERROR);
        }
    }

    private String getModuleTitle(int idx) {
        switch (idx) {
            case 0: return "  \u2705 Mark Attendance";
            case 1: return "  \uD83D\uDCCA View Reports";
            case 2: return "  \uD83D\uDCCB Assignments";
            case 3: return "  \uD83D\uDCDD Sessionals";
            case 4: return "  \uD83D\uDD12 Change Password";
            default: return "  Module";
        }
    }

    public static void open() {
        SwingUtilities.invokeLater(() -> new FacultyDashboard());
    }
}
