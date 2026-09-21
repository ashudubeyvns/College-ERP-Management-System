package attendancemgt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeScreen {
    JFrame frame;
    JPanel cardPanel;
    JPanel contentPanel;
    CardLayout cardLayout;

    // Color themes from UIStyles
    private boolean darkMode = false;
    private static HomeScreen instance;

    // Sidebar constants
    private static final int SIDEBAR_WIDTH = UIStyles.SIDEBAR_WIDTH;
    private static final int TOP_BAR_HEIGHT = UIStyles.TOPBAR_HEIGHT;
    private JPanel sidebarPanel;
    private JPanel[] navItemPanels;
    private JLabel[] navLabels;
    private String[] navItems = {
        "\uD83C\uDFE0  Dashboard",
        "\uD83D\uDC64  Students",
        "\uD83D\uDC69\u200D\uD83C\uDFEB  Faculty",
        "\uD83D\uDCC5  Attendance",
        "\uD83D\uDCCA  Reports",
        "\u2699\uFE0F  Settings"
    };
    private int activeNavIndex = 0;
    private JLabel pageTitle;
    private JLabel userAvatarLabel;

    // Top bar components
    private JToggleButton darkModeToggle;
    private JPanel topBar;

    public static HomeScreen getInstance() {
        return instance;
    }

    public static void open() {
        SwingUtilities.invokeLater(() -> {
            if (instance == null || instance.frame == null || !instance.frame.isVisible()) {
                instance = new HomeScreen();
            } else {
                instance.frame.toFront();
            }
        });
    }

    public HomeScreen() {
        instance = this;
        frame = new JFrame("College ERP - Attendance Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        buildSidebar();
        buildTopBar();
        buildMainContent();

        // Apply theme
        applyTheme();

        frame.setVisible(true);
    }

    // ===================== APPLY THEME =====================
    private void applyTheme() {
        Color bg = darkMode ? UIStyles.DARK_BG : UIStyles.BG_LIGHT;
        frame.getContentPane().setBackground(bg);
        contentPanel.setBackground(bg);
        sidebarPanel.setBackground(darkMode ? UIStyles.DARK_SIDEBAR : UIStyles.SIDEBAR_BG);
        topBar.setBackground(darkMode ? UIStyles.DARK_TOPBAR : UIStyles.TOPBAR_BG);
        darkModeToggle.setText(darkMode ? "\uD83C\uDF19" : "\u2600\uFE0F");
    }

    // ===================== BUILD SIDEBAR =====================
    private void buildSidebar() {
        sidebarPanel = new JPanel(null);
        sidebarPanel.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        sidebarPanel.setBackground(UIStyles.SIDEBAR_BG);
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(20, 30, 45)));

        // App Logo/Title area
        JLabel logoIcon = new JLabel("\uD83C\uDF93", SwingConstants.CENTER);
        logoIcon.setBounds(0, 15, SIDEBAR_WIDTH, 40);
        logoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        logoIcon.setForeground(UIStyles.PRIMARY);
        sidebarPanel.add(logoIcon);

        JLabel logoLabel = new JLabel("College ERP", SwingConstants.CENTER);
        logoLabel.setBounds(0, 52, SIDEBAR_WIDTH, 24);
        logoLabel.setFont(UIStyles.FONT_BOLD_XL);
        logoLabel.setForeground(Color.WHITE);
        sidebarPanel.add(logoLabel);

        JLabel subLogo = new JLabel("Management System", SwingConstants.CENTER);
        subLogo.setBounds(0, 72, SIDEBAR_WIDTH, 18);
        subLogo.setFont(UIStyles.FONT_PLAIN_XS);
        subLogo.setForeground(UIStyles.SIDEBAR_TEXT);
        sidebarPanel.add(subLogo);

        // Separator
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(20, 100, SIDEBAR_WIDTH - 40, 1);
        sep1.setForeground(new Color(60, 75, 90));
        sidebarPanel.add(sep1);

        // Navigation items
        navItemPanels = new JPanel[navItems.length];
        navLabels = new JLabel[navItems.length];
        int navY = 115;
        for (int i = 0; i < navItems.length; i++) {
            final int idx = i;
            JPanel navItem = new JPanel(null);
            navItem.setBounds(8, navY, SIDEBAR_WIDTH - 16, 44);
            navItem.setBackground(i == 0 ? UIStyles.SIDEBAR_ACTIVE : new Color(0, 0, 0, 0));
            navItem.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Left accent bar for active item
            if (i == 0) {
                JPanel accentBar = new JPanel();
                accentBar.setBounds(0, 0, 4, 44);
                accentBar.setBackground(UIStyles.PRIMARY_LIGHT);
                navItem.add(accentBar);
            }

            JLabel navLabel = new JLabel(navItems[i]);
            navLabel.setBounds(20, 0, SIDEBAR_WIDTH - 50, 44);
            navLabel.setFont(UIStyles.FONT_PLAIN_LG);
            navLabel.setForeground(i == 0 ? Color.WHITE : UIStyles.SIDEBAR_TEXT);
            navItem.add(navLabel);
            navLabels[i] = navLabel;

            navItem.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    setActiveNav(idx);
                }

                public void mouseEntered(java.awt.event.MouseEvent e) {
                    if (idx != activeNavIndex) {
                        navItem.setBackground(new Color(30, 58, 138, 80));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (idx != activeNavIndex) {
                        navItem.setBackground(new Color(0, 0, 0, 0));
                    }
                }
            });
            sidebarPanel.add(navItem);
            navItemPanels[i] = navItem;

            navY += 50;
        }

        // User profile section at bottom
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(20, 520, SIDEBAR_WIDTH - 40, 1);
        sep2.setForeground(new Color(60, 75, 90));
        sidebarPanel.add(sep2);

        // Profile avatar
        JLabel profilePic = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        profilePic.setBounds(15, 535, 35, 35);
        profilePic.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        sidebarPanel.add(profilePic);

        UserSession session = UserSession.getInstance();
        String displayName = (session != null) ? session.getFullName() : "Admin User";
        String roleName = (session != null) ? session.getRole().getDisplayName() : "Administrator";

        JLabel profileName = new JLabel(displayName);
        profileName.setBounds(55, 535, 160, 18);
        profileName.setFont(UIStyles.FONT_BOLD_MD);
        profileName.setForeground(Color.WHITE);
        sidebarPanel.add(profileName);

        JLabel profileRole = new JLabel(roleName);
        profileRole.setBounds(55, 553, 160, 16);
        profileRole.setFont(UIStyles.FONT_PLAIN_XS);
        profileRole.setForeground(UIStyles.SIDEBAR_TEXT);
        sidebarPanel.add(profileRole);

        // Logout button
        JPanel logoutPanel = new JPanel(null);
        logoutPanel.setBounds(8, 590, SIDEBAR_WIDTH - 16, 44);
        logoutPanel.setBackground(new Color(0, 0, 0, 0));
        logoutPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel logoutLabel = new JLabel("\uD83D\uDEAA  Logout");
        logoutLabel.setBounds(20, 0, SIDEBAR_WIDTH - 50, 44);
        logoutLabel.setFont(UIStyles.FONT_PLAIN_LG);
        logoutLabel.setForeground(new Color(239, 68, 68, 180));
        logoutPanel.add(logoutLabel);

        logoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    instance = null;
                    UserSession.destroySession();
                    try { new login_frame(); } catch (IOException ex) {
                        Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                logoutPanel.setBackground(new Color(239, 68, 68, 30));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                logoutPanel.setBackground(new Color(0, 0, 0, 0));
            }
        });
        sidebarPanel.add(logoutPanel);

        // Version label
        JLabel versionLabel = new JLabel("v2.0 - Educational ERP", SwingConstants.CENTER);
        versionLabel.setBounds(0, 645, SIDEBAR_WIDTH, 20);
        versionLabel.setFont(UIStyles.FONT_PLAIN_XS);
        versionLabel.setForeground(new Color(100, 116, 139));
        sidebarPanel.add(versionLabel);

        frame.add(sidebarPanel, BorderLayout.WEST);
    }

    private void setActiveNav(int index) {
        if (index == activeNavIndex) return;
        activeNavIndex = index;

        // Update sidebar visuals
        for (int i = 0; i < navItemPanels.length; i++) {
            navItemPanels[i].removeAll();
            JPanel navItem = navItemPanels[i];

            // Reset background
            if (i == index) {
                navItem.setBackground(UIStyles.SIDEBAR_ACTIVE);
                navLabels[i].setForeground(Color.WHITE);

                // Left accent bar
                JPanel accentBar = new JPanel();
                accentBar.setBounds(0, 0, 4, 44);
                accentBar.setBackground(UIStyles.PRIMARY_LIGHT);
                navItem.add(accentBar);
            } else {
                navItem.setBackground(new Color(0, 0, 0, 0));
                navLabels[i].setForeground(darkMode ? UIStyles.DARK_NAV_TEXT : UIStyles.SIDEBAR_TEXT);
            }

            JLabel navLabel = navLabels[i];
            navLabel.setBounds(20, 0, SIDEBAR_WIDTH - 50, 44);
            navItem.add(navLabel);
            navItem.revalidate();
            navItem.repaint();
        }

        // Update page title
        String[] titles = {
            "\uD83C\uDFE0  Dashboard Overview",
            "\uD83D\uDC64  Students Management",
            "\uD83D\uDC69\u200D\uD83C\uDFEB  Faculty Management",
            "\uD83D\uDCC5  Attendance Management",
            "\uD83D\uDCCA  Reports & Analytics",
            "\u2699\uFE0F  Settings"
        };
        if (pageTitle != null) pageTitle.setText(titles[index]);

        // Show corresponding card
        String[] cardNames = {"dashboard", "students", "faculty", "attendance", "reports", "settings"};
        cardLayout.show(contentPanel, cardNames[index]);
    }

    // ===================== BUILD TOP BAR =====================
    private void buildTopBar() {
        topBar = new JPanel(null);
        topBar.setPreferredSize(new Dimension(0, TOP_BAR_HEIGHT));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));

        // Page title (dynamic)
        pageTitle = new JLabel("\uD83C\uDFE0  Dashboard Overview");
        pageTitle.setBounds(25, 0, 400, TOP_BAR_HEIGHT);
        pageTitle.setFont(UIStyles.FONT_BOLD_2XL);
        pageTitle.setForeground(UIStyles.TEXT_DARK);
        topBar.add(pageTitle);

        // Right side controls
        int rightX = frame.getWidth() - 320;

        // Dark Mode Toggle
        darkModeToggle = new JToggleButton("\u2600\uFE0F");
        darkModeToggle.setBounds(rightX, 12, 44, 40);
        darkModeToggle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        darkModeToggle.setBackground(new Color(241, 245, 249));
        darkModeToggle.setForeground(UIStyles.TEXT_DARK);
        darkModeToggle.setFocusPainted(false);
        darkModeToggle.setToolTipText("Toggle Dark Mode");
        darkModeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        topBar.add(darkModeToggle);

        // Notification bell with badge
        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.setBounds(rightX + 55, 12, 44, 40);
        notificationPanel.setBackground(new Color(241, 245, 249));
        notificationPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel notificationLabel = new JLabel("\uD83D\uDD14", SwingConstants.CENTER);
        notificationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        notificationPanel.add(notificationLabel, BorderLayout.CENTER);

        // Badge
        JLabel badgeLabel = new JLabel("3", SwingConstants.CENTER);
        badgeLabel.setFont(UIStyles.FONT_BOLD_XS);
        badgeLabel.setForeground(Color.WHITE);
        badgeLabel.setBackground(UIStyles.DANGER);
        badgeLabel.setOpaque(true);
        badgeLabel.setBounds(28, 2, 16, 16);
        notificationPanel.setLayout(null);
        notificationPanel.add(notificationLabel);
        notificationPanel.add(badgeLabel);

        topBar.add(notificationPanel);

        // User Profile section
        JPanel userPanel = new JPanel(new BorderLayout(8, 0));
        userPanel.setBounds(rightX + 115, 0, 180, TOP_BAR_HEIGHT);
        userPanel.setOpaque(false);
        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        UserSession session = UserSession.getInstance();
        String displayName = (session != null) ? session.getFullName() : "Admin User";
        String roleName = (session != null) ? session.getRole().getDisplayName() : "Administrator";

        JLabel avatarLbl = new JLabel("\uD83D\uDC64", SwingConstants.CENTER);
        avatarLbl.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        avatarLbl.setPreferredSize(new Dimension(40, 40));

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);
        JLabel userName = new JLabel(displayName);
        userName.setFont(UIStyles.FONT_BOLD_MD);
        userName.setForeground(UIStyles.TEXT_DARK);
        JLabel userRole = new JLabel(roleName);
        userRole.setFont(UIStyles.FONT_PLAIN_XS);
        userRole.setForeground(UIStyles.TEXT_MUTED);

        namePanel.add(userName, BorderLayout.NORTH);
        namePanel.add(userRole, BorderLayout.SOUTH);

        JLabel dropdownIcon = new JLabel("\u25BC");
        dropdownIcon.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        dropdownIcon.setForeground(UIStyles.TEXT_MUTED);

        userPanel.add(avatarLbl, BorderLayout.WEST);
        userPanel.add(namePanel, BorderLayout.CENTER);
        userPanel.add(dropdownIcon, BorderLayout.EAST);

        // Dropdown menu
        JPopupMenu userMenu = new JPopupMenu();
        userMenu.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

        JMenuItem profileItem = new JMenuItem("\uD83D\uDC64  Profile");
        profileItem.setFont(UIStyles.FONT_PLAIN_MD);
        profileItem.addActionListener(e -> NotificationToast.show(frame, "Profile page coming soon!", NotificationToast.ToastType.INFO));

        JMenuItem settingsItem = new JMenuItem("\u2699\uFE0F  Settings");
        settingsItem.setFont(UIStyles.FONT_PLAIN_MD);
        settingsItem.addActionListener(e -> setActiveNav(5));

        JMenuItem logoutItem = new JMenuItem("\uD83D\uDEAA  Logout");
        logoutItem.setFont(UIStyles.FONT_PLAIN_MD);
        logoutItem.setForeground(UIStyles.DANGER);
        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                instance = null;
                UserSession.destroySession();
                try { new login_frame(); } catch (IOException ex) {
                    Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        userMenu.add(profileItem);
        userMenu.add(settingsItem);
        userMenu.addSeparator();
        userMenu.add(logoutItem);

        userPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                userMenu.show(userPanel, 0, userPanel.getHeight());
            }
        });

        topBar.add(userPanel);

        // Reposition on resize
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent e) {
                int rx = frame.getWidth() - 320;
                darkModeToggle.setBounds(rx, 12, 44, 40);
                notificationPanel.setBounds(rx + 55, 12, 44, 40);
                userPanel.setBounds(rx + 115, 0, 180, TOP_BAR_HEIGHT);
            }
        });

        frame.add(topBar, BorderLayout.NORTH);
    }

    // ===================== BUILD MAIN CONTENT =====================
    private void buildMainContent() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UIStyles.BG_LIGHT);

        contentPanel.add(createDashboardPanel(), "dashboard");
        contentPanel.add(createStudentsPanel(), "students");
        contentPanel.add(createFacultyPanel(), "faculty");
        contentPanel.add(createAttendancePanel(), "attendance");
        contentPanel.add(createReportsPanel(), "reports");
        contentPanel.add(createSettingsPanel(), "settings");

        frame.add(contentPanel, BorderLayout.CENTER);
    }

    // ===================== TOGGLE DARK MODE =====================
    private void toggleDarkMode() {
        darkMode = !darkMode;
        applyTheme();
        // Rebuild panels with new theme
        contentPanel.removeAll();
        buildMainContent();
        setActiveNav(activeNavIndex);
        // Refresh
        contentPanel.revalidate();
        contentPanel.repaint();
        NotificationToast.show(frame, "Dark Mode " + (darkMode ? "enabled" : "disabled"), NotificationToast.ToastType.SUCCESS);
    }

    // ===================== DASHBOARD PANEL =====================
    private JPanel createDashboardPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        // ===== STATS CARDS ROW =====
        int cardW = 175;
        int cardH = 110;
        int gap = 16;
        int startX = 25;
        int yCards = 25;

        String[][] statCards = {
            {"\uD83D\uDC64", "1,250", "Total Students"},
            {"\uD83D\uDC68\u200D\uD83C\uDFEB", "95", "Faculty"},
            {"\uD83D\uDCC5", "91%", "Attendance"},
            {"\uD83C\uDF93", "12", "Departments"},
            {"\uD83D\uDCDA", "48", "Subjects"},
            {"\uD83C\uDF92", "6", "Today's Classes"}
        };
        Color[] cardColors = {
            UIStyles.PRIMARY,
            UIStyles.SUCCESS,
            UIStyles.WARNING,
            new Color(139, 92, 246),
            new Color(236, 72, 153),
            new Color(20, 184, 166)
        };
        String[] cardIcons = {
            "\uD83D\uDC64", "\uD83D\uDC68\u200D\uD83C\uDFEB",
            "\uD83D\uDCC5", "\uD83C\uDF93",
            "\uD83D\uDCDA", "\uD83C\uDF92"
        };

        Color cardBg = darkMode ? UIStyles.DARK_CARD : Color.WHITE;
        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color mutedColor = darkMode ? UIStyles.DARK_TEXT_MUTED : UIStyles.TEXT_MUTED;

        for (int i = 0; i < statCards.length; i++) {
            JPanel card = createStatCard(cardIcons[i], statCards[i][1], statCards[i][2], cardColors[i], cardBg, textColor, mutedColor);
            card.setBounds(startX + i % 6 * (cardW + gap), yCards + (i / 6) * (cardH + gap), cardW, cardH);
            scrollContent.add(card);
        }

        // ===== CHARTS ROW =====
        int yCharts = yCards + cardH + 25;

        // Attendance Trend Chart (Line)
        ChartPanel attendanceChart = new ChartPanel("Attendance Trend (Weekly)", ChartPanel.ChartType.LINE);
        attendanceChart.setBounds(startX, yCharts, 400, 250);
        attendanceChart.setData(
            new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"},
            new double[]{85, 78, 92, 88, 76, 90}
        );
        scrollContent.add(attendanceChart);

        // Department-wise Students (Pie)
        ChartPanel deptChart = new ChartPanel("Department-wise Students", ChartPanel.ChartType.PIE);
        deptChart.setBounds(startX + 420, yCharts, 350, 250);
        deptChart.setData(
            new String[]{"BCA", "MCA", "B.Tech CSE", "B.Tech IT", "B.Sc CS", "M.Sc CS"},
            new double[]{320, 180, 420, 280, 150, 120}
        );
        scrollContent.add(deptChart);

        // Faculty Workload (Horizontal Bar)
        ChartPanel workloadChart = new ChartPanel("Faculty Workload", ChartPanel.ChartType.BAR_HORIZONTAL);
        workloadChart.setBounds(startX + 790, yCharts, 350, 250);
        workloadChart.setData(
            new String[]{"CSE", "MCA", "IT", "CS", "CSA"},
            new double[]{18, 14, 12, 10, 8}
        );
        scrollContent.add(workloadChart);

        // ===== RECENT ATTENDANCE TABLE =====
        int yTable = yCharts + 270;

        JLabel recentTitle = new JLabel("\uD83D\uDCCB  Recent Attendance Records");
        recentTitle.setBounds(startX, yTable, 400, 30);
        recentTitle.setFont(UIStyles.FONT_BOLD_XL);
        recentTitle.setForeground(textColor);
        scrollContent.add(recentTitle);

        // Quick action buttons
        int yActions = yTable + 40;
        String[][] actions = {
            {"\uD83D\uDCC4  Export PDF", "#2563EB"},
            {"\uD83D\uDCC5  Export Excel", "#16A34A"},
            {"\uD83D\uDD0D  Search Students", "#9333EA"}
        };
        for (int i = 0; i < actions.length; i++) {
            JButton btn = new JButton(actions[i][0]);
            btn.setBounds(startX + i * 160, yActions, 145, 36);
            btn.setBackground(UIStyles.hexToColor(actions[i][1]));
            btn.setForeground(Color.WHITE);
            btn.setFont(UIStyles.FONT_BOLD_SM);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
            int finalI = i;
            btn.addActionListener(e -> {
                if (finalI == 0) {
                    exportDashboardReport("PDF");
                } else if (finalI == 1) {
                    exportDashboardReport("Excel");
                } else if (finalI == 2) {
                    setActiveNav(1);
                }
            });
            scrollContent.add(btn);
        }

        // Modern table
        int yTableHeader = yActions + 50;
        String[] headers = {"Roll No", "Student Name", "Status", "Time", "Course", "Semester"};
        Object[][] sampleData = {
            {"24SCSE1180456", "Aarav Sharma", "\u2705 Present", "09:15 AM", "B.Tech CSE", "3"},
            {"24SCSE1180381", "Priya Patel", "\u2705 Present", "09:10 AM", "B.Tech CSE", "3"},
            {"24SCSE2030222", "Rohit Singh", "\u274C Absent", "-", "BCA", "4"},
            {"101", "Ashutosh Dubey", "\u2705 Present", "09:20 AM", "MCA", "2"},
            {"102", "Aditya Verma", "\u23F0 Late", "09:45 AM", "MCA", "2"},
            {"103", "Ananya Reddy", "\u2705 Present", "09:05 AM", "B.Tech IT", "5"},
            {"104", "Arjun Nair", "\u2705 Present", "09:12 AM", "B.Tech IT", "5"},
            {"105", "Diya Joshi", "\u274C Absent", "-", "B.Sc CS", "4"},
        };

        ModernTable modernTable = new ModernTable(headers, sampleData);
        modernTable.setBounds(startX - 10, yTableHeader, 1050, 350);
        scrollContent.add(modernTable);

        int totalHeight = yTableHeader + 380;
        scrollContent.setPreferredSize(new Dimension(1200, totalHeight));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    private JPanel createStatCard(String icon, String value, String label, Color accent, Color bg, Color textColor, Color mutedColor) {
        JPanel card = new JPanel(null);
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setBounds(12, 8, 50, 35);
        iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        card.add(iconLbl);

        JLabel valLbl = new JLabel(value);
        valLbl.setBounds(12, 42, 150, 28);
        valLbl.setFont(UIStyles.FONT_BOLD_3XL);
        valLbl.setForeground(accent);
        card.add(valLbl);

        JLabel titleLbl = new JLabel(label);
        titleLbl.setBounds(12, 72, 150, 18);
        titleLbl.setFont(UIStyles.FONT_PLAIN_MD);
        titleLbl.setForeground(textColor);
        card.add(titleLbl);

        return card;
    }

    // ===================== STUDENTS PANEL =====================
    private JPanel createStudentsPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color mutedColor = darkMode ? UIStyles.DARK_TEXT_MUTED : UIStyles.TEXT_MUTED;

        JLabel title = new JLabel("\uD83D\uDC64  Student Management");
        title.setBounds(25, 20, 400, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(textColor);
        scrollContent.add(title);

        // Action buttons row
        JButton addBtn = new JButton("  \u2795  Add Student");
        addBtn.setBounds(500, 20, 140, 36);
        styleActionBtn(addBtn, UIStyles.PRIMARY);
        addBtn.addActionListener(e -> {
            // Show a dialog to add a new student
            JDialog addDialog = new JDialog(frame, "Add New Student", true);
            addDialog.setSize(500, 400);
            addDialog.setLocationRelativeTo(frame);
            addDialog.setLayout(new BorderLayout());

            JPanel formPanel = new JPanel(null);
            formPanel.setBackground(Color.WHITE);

            JLabel titleLbl = new JLabel("\u2795  Add New Student", SwingConstants.CENTER);
            titleLbl.setBounds(0, 15, 500, 30);
            titleLbl.setFont(UIStyles.FONT_BOLD_2XL);
            titleLbl.setForeground(UIStyles.TEXT_DARK);
            formPanel.add(titleLbl);

            String[] fields = {"Full Name:", "Roll No:", "Course:", "Semester:", "Section:", "Email:", "Phone:"};
            int fy = 65;
            for (int fi = 0; fi < fields.length; fi++) {
                JLabel lbl = new JLabel(fields[fi]);
                lbl.setBounds(40, fy, 120, 28);
                lbl.setFont(UIStyles.FONT_BOLD_MD);
                lbl.setForeground(UIStyles.TEXT_DARK);
                formPanel.add(lbl);

                JTextField tf = new JTextField();
                tf.setBounds(170, fy, 270, 28);
                tf.setFont(UIStyles.FONT_PLAIN_MD);
                tf.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
                formPanel.add(tf);

                fy += 38;
            }

            JButton saveBtn = new JButton("  \u2714\uFE0F  Save Student");
            saveBtn.setBounds(150, fy + 10, 200, 38);
            styleActionBtn(saveBtn, UIStyles.SUCCESS);
            saveBtn.addActionListener(ev -> {
                NotificationToast.show(frame, "Student added successfully!", NotificationToast.ToastType.SUCCESS);
                addDialog.dispose();
            });
            formPanel.add(saveBtn);

            addDialog.add(formPanel, BorderLayout.CENTER);
            addDialog.setVisible(true);
        });
        scrollContent.add(addBtn);

        JButton exportBtn = new JButton("  \uD83D\uDCC4  Export");
        exportBtn.setBounds(650, 20, 120, 36);
        styleActionBtn(exportBtn, UIStyles.SUCCESS);
        exportBtn.addActionListener(e -> {
            String[][] expData = {
                {"24SCSE1180456", "Aarav Sharma", "B.Tech CSE", "3", "A"},
                {"24SCSE1180381", "Priya Patel", "B.Tech CSE", "3", "A"},
                {"24SCSE2030222", "Rohit Singh", "BCA", "4", "B"},
                {"101", "Ashutosh Dubey", "MCA", "2", "A"},
                {"102", "Aditya Verma", "MCA", "2", "B"},
            };
            String[] expHeaders = {"Roll No", "Name", "Course", "Semester", "Section"};
            String reportTitle = "Student List - " + java.time.LocalDate.now();
            String fileName = "Students_" + java.time.LocalDate.now();
            ReportExporter.exportToCSV(frame, expData, expHeaders, fileName, reportTitle);
        });
        scrollContent.add(exportBtn);

        // Student count
        JLabel countLabel = new JLabel("Showing 12 students");
        countLabel.setBounds(800, 25, 200, 22);
        countLabel.setFont(UIStyles.FONT_PLAIN_MD);
        countLabel.setForeground(mutedColor);
        scrollContent.add(countLabel);

        // Modern table
        String[] headers = {"Sr#", "Roll No", "Student Name", "Course", "Semester", "Section", "Email", "Contact", "Actions"};
        Object[][] studentData = {
            {"1", "24SCSE1180456", "Aarav Sharma", "B.Tech CSE", "3", "A", "aarav.sharma@example.com", "+91-9876543210", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"2", "24SCSE1180381", "Priya Patel", "B.Tech CSE", "3", "A", "priya.patel@example.com", "+91-9876543211", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"3", "24SCSE2030222", "Rohit Singh", "BCA", "4", "B", "rohit.singh@example.com", "+91-9876543212", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"4", "101", "Ashutosh Dubey", "MCA", "2", "A", "ashutosh.dubey@example.com", "+91-9876543213", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"5", "102", "Aditya Verma", "MCA", "2", "B", "aditya.verma@example.com", "+91-9876543214", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"6", "103", "Ananya Reddy", "B.Tech IT", "5", "A", "ananya.reddy@example.com", "+91-9876543215", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"7", "104", "Arjun Nair", "B.Tech IT", "5", "B", "arjun.nair@example.com", "+91-9876543216", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
            {"8", "105", "Diya Joshi", "B.Sc CS", "4", "A", "diya.joshi@example.com", "+91-9876543217", "\u270F\uFE0F  \uD83D\uDDD1\uFE0F"},
        };

        ModernTable modernTable = new ModernTable(headers, studentData);
        modernTable.setBounds(15, 70, 1100, 400);
        scrollContent.add(modernTable);

        int totalHeight = 520;
        scrollContent.setPreferredSize(new Dimension(1200, totalHeight));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // ===================== FACULTY PANEL =====================
    private JPanel createFacultyPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color mutedColor = darkMode ? UIStyles.DARK_TEXT_MUTED : UIStyles.TEXT_MUTED;
        Color cardBg = darkMode ? UIStyles.DARK_CARD : Color.WHITE;

        JLabel title = new JLabel("\uD83D\uDC69\u200D\uD83C\uDFEB  Faculty Management");
        title.setBounds(25, 20, 400, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(textColor);
        scrollContent.add(title);

        // Stat cards row
        int cardW = 230;
        int cardH = 90;
        int gap = 20;
        int yCards = 70;

        String[][] facCards = {
            {"\uD83D\uDC68\u200D\uD83C\uDFEB", "25", "Total Faculty"},
            {"\uD83C\uDF93", "18", "Professors"},
            {"\uD83D\uDC69\u200D\uD83C\uDFEB", "7", "Associate Professors"},
            {"\uD83C\uDFC6", "5+", "Avg Experience (Yrs)"}
        };
        Color[] facColors = {UIStyles.PRIMARY, UIStyles.SUCCESS, UIStyles.WARNING, new Color(139, 92, 246)};

        for (int i = 0; i < facCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(25 + i * (cardW + gap), yCards, cardW, cardH);
            card.setBackground(cardBg);
            card.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

            JLabel iconLbl = new JLabel(facCards[i][0]);
            iconLbl.setBounds(12, 10, 50, 35);
            iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 26));
            card.add(iconLbl);

            JLabel valLbl = new JLabel(facCards[i][1]);
            valLbl.setBounds(12, 42, 170, 25);
            valLbl.setFont(UIStyles.FONT_BOLD_2XL);
            valLbl.setForeground(facColors[i]);
            card.add(valLbl);

            JLabel titleLbl = new JLabel(facCards[i][2]);
            titleLbl.setBounds(12, 66, 200, 18);
            titleLbl.setFont(UIStyles.FONT_PLAIN_MD);
            titleLbl.setForeground(textColor);
            card.add(titleLbl);

            scrollContent.add(card);
        }

        // Faculty Cards
        int yCardsRow = yCards + cardH + 25;
        JLabel facListTitle = new JLabel("\uD83D\uDCDD  Faculty Directory");
        facListTitle.setBounds(25, yCardsRow, 300, 30);
        facListTitle.setFont(UIStyles.FONT_BOLD_XL);
        facListTitle.setForeground(textColor);
        scrollContent.add(facListTitle);

        // Faculty card items
        String[][] facultyData = {
            {"FAC001", "Dr. Rajesh Kumar", "CSE", "Professor & HOD", "5", "rajesh@example.com", "+91-9988776655"},
            {"FAC002", "Dr. Sunita Verma", "CSA", "Professor", "4", "sunita@example.com", "+91-9988776656"},
            {"FAC003", "Prof. Amit Singh", "IT", "Associate Professor", "3", "amit@example.com", "+91-9988776657"},
            {"FAC004", "Dr. Priya Sharma", "MCA", "Associate Professor", "4", "priya@example.com", "+91-9988776658"},
            {"FAC005", "Prof. Vikram Patel", "CSE", "Assistant Professor", "3", "vikram@example.com", "+91-9988776659"},
            {"FAC006", "Dr. Ananya Gupta", "CS", "Professor", "4", "ananya@example.com", "+91-9988776660"},
        };

        int facCardW = 350;
        int facCardH = 130;
        int facGapX = 20;
        int facGapY = 15;
        int facStartX = 25;

        for (int fi = 0; fi < facultyData.length; fi++) {
            int col = fi % 3;
            int row = fi / 3;
            int fx = facStartX + col * (facCardW + facGapX);
            int fy = yCardsRow + 40 + row * (facCardH + facGapY);
            final String facultyName = facultyData[fi][1];

            JPanel card = new JPanel(null);
            card.setBounds(fx, fy, facCardW, facCardH);
            card.setBackground(cardBg);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
            ));

            // Avatar
            JLabel avatar = new JLabel("\uD83D\uDC68\u200D\uD83C\uDFEB", SwingConstants.CENTER);
            avatar.setBounds(12, 12, 50, 50);
            avatar.setFont(new Font("Segoe UI", Font.PLAIN, 36));
            avatar.setBackground(UIStyles.withAlpha(UIStyles.PRIMARY, 30));
            avatar.setOpaque(true);
            card.add(avatar);

            // Name
            JLabel fName = new JLabel(facultyName);
            fName.setBounds(72, 10, 250, 22);
            fName.setFont(UIStyles.FONT_BOLD_LG);
            fName.setForeground(textColor);
            card.add(fName);

            // Department
            JLabel fDept = new JLabel(facultyData[fi][2] + " | " + facultyData[fi][3]);
            fDept.setBounds(72, 32, 250, 18);
            fDept.setFont(UIStyles.FONT_PLAIN_SM);
            fDept.setForeground(mutedColor);
            card.add(fDept);

            // Subjects count
            JLabel fSubj = new JLabel("\uD83D\uDCDA  " + facultyData[fi][4] + " Subjects");
            fSubj.setBounds(72, 52, 120, 18);
            fSubj.setFont(UIStyles.FONT_PLAIN_SM);
            fSubj.setForeground(UIStyles.PRIMARY);
            card.add(fSubj);

            // Email
            JLabel fEmail = new JLabel("\u2709\uFE0F  " + facultyData[fi][5]);
            fEmail.setBounds(12, 72, 200, 18);
            fEmail.setFont(UIStyles.FONT_PLAIN_XS);
            fEmail.setForeground(mutedColor);
            card.add(fEmail);

            // Phone
            JLabel fPhone = new JLabel("\uD83D\uDCF1  " + facultyData[fi][6]);
            fPhone.setBounds(12, 90, 200, 18);
            fPhone.setFont(UIStyles.FONT_PLAIN_XS);
            fPhone.setForeground(mutedColor);
            card.add(fPhone);

            // View Profile button
            JButton viewBtn = new JButton("View Profile");
            viewBtn.setBounds(facCardW - 110, facCardH - 38, 95, 28);
            viewBtn.setBackground(UIStyles.PRIMARY);
            viewBtn.setForeground(Color.WHITE);
            viewBtn.setFont(UIStyles.FONT_BOLD_SM);
            viewBtn.setFocusPainted(false);
            viewBtn.setBorderPainted(false);
            viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            viewBtn.setOpaque(true);
            viewBtn.addActionListener(e ->
                NotificationToast.show(frame, "Profile: " + facultyName, NotificationToast.ToastType.INFO));
            card.add(viewBtn);

            scrollContent.add(card);
        }

        int totalHeight = yCardsRow + 40 + ((facultyData.length + 2) / 3) * (facCardH + facGapY) + 30;
        scrollContent.setPreferredSize(new Dimension(1200, Math.max(totalHeight, 500)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // ===================== ATTENDANCE PANEL =====================
    private JPanel createAttendancePanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color cardBg = darkMode ? UIStyles.DARK_CARD : Color.WHITE;

        JLabel title = new JLabel("\uD83D\uDCC5  Attendance Management");
        title.setBounds(25, 20, 400, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(textColor);
        scrollContent.add(title);

        // Course & Semester selector
        JLabel lblCourse = new JLabel("Course:");
        lblCourse.setBounds(25, 70, 60, 35);
        lblCourse.setFont(UIStyles.FONT_BOLD_MD);
        lblCourse.setForeground(textColor);
        scrollContent.add(lblCourse);

        JComboBox<String> courseDD = new JComboBox<>(SubjectData.COURSE_NAMES);
        courseDD.setBounds(85, 70, 160, 35);
        courseDD.setFont(UIStyles.FONT_PLAIN_MD);
        courseDD.setBackground(cardBg);
        scrollContent.add(courseDD);

        JLabel lblSem = new JLabel("Semester:");
        lblSem.setBounds(260, 70, 70, 35);
        lblSem.setFont(UIStyles.FONT_BOLD_MD);
        lblSem.setForeground(textColor);
        scrollContent.add(lblSem);

        JComboBox<Integer> semDD = new JComboBox<>();
        semDD.setBounds(330, 70, 70, 35);
        semDD.setFont(UIStyles.FONT_PLAIN_MD);
        semDD.setBackground(cardBg);
        for (int s = 1; s <= 8; s++) semDD.addItem(s);
        scrollContent.add(semDD);

        courseDD.addActionListener(e -> {
            String course = (String) courseDD.getSelectedItem();
            int courseIdx = -1;
            for (int i = 0; i < SubjectData.COURSE_NAMES.length; i++) {
                if (SubjectData.COURSE_NAMES[i].equals(course)) { courseIdx = i; break; }
            }
            semDD.removeAllItems();
            int maxSem = (courseIdx >= 0) ? SubjectData.COURSE_SEMESTERS[courseIdx] : 6;
            for (int s = 1; s <= maxSem; s++) semDD.addItem(s);
        });

        // Quick action cards
        int yActions = 120;
        String[] attActionNames = {"\u2705  Mark Attendance", "\uD83D\uDCCA  View Attendance", "\uD83D\uDCCB  Assignments", "\uD83D\uDCDD  Sessionals"};
        String[] attActionDescs = {"Mark attendance for lectures & practicals", "View daily & consolidated reports", "Upload marks, records & details", "Manage sessional marks"};
        Color[] attActionColors = {UIStyles.SUCCESS, UIStyles.PRIMARY, new Color(139, 92, 246), new Color(230, 126, 34)};

        int attCardW = 240;
        int attCardH = 100;
        int attGap = 16;

        final int attCount = attActionNames.length;
        for (int i = 0; i < attCount; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(25 + i * (attCardW + attGap), yActions, attCardW, attCardH);
            card.setBackground(cardBg);
            card.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));
            scrollContent.add(card);

            JLabel iconLbl = new JLabel(attActionNames[i].substring(0, 2));
            iconLbl.setBounds(12, 12, 50, 35);
            iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            card.add(iconLbl);

            JLabel actTitle = new JLabel(attActionNames[i].substring(2).trim());
            actTitle.setBounds(12, 48, 200, 20);
            actTitle.setFont(UIStyles.FONT_BOLD_MD);
            actTitle.setForeground(textColor);
            card.add(actTitle);

            ModernButton openBtn = new ModernButton("Open", ModernButton.ButtonVariant.PRIMARY);
            openBtn.setBounds(attCardW - 90, attCardH - 40, 75, 28);
            final int actionIdx = i;
            openBtn.addActionListener(e -> {
                String course = (String) courseDD.getSelectedItem();
                int sem = (Integer) semDD.getSelectedItem();
                try {
                    String cardName = "";
                    JPanel modulePanel = null;
                    switch (actionIdx) {
                        case 0:
                            modulePanel = MarkAttendance.getPanel(HomeScreen.this, course, sem);
                            cardName = "markattendance_" + course.replace(" ", "_") + "_" + sem;
                            break;
                        case 1:
                            modulePanel = ViewAttendance.getPanel(HomeScreen.this, course, sem);
                            cardName = "viewattendance_" + course.replace(" ", "_") + "_" + sem;
                            break;
                        case 2:
                            modulePanel = AssignmentModule.getPanel(HomeScreen.this, course, sem);
                            cardName = "assignment_" + course.replace(" ", "_") + "_" + sem;
                            break;
                        case 3:
                            modulePanel = SessionalsModule.getPanel(HomeScreen.this, course, sem);
                            cardName = "sessional_" + course.replace(" ", "_") + "_" + sem;
                            break;
                    }
                    if (modulePanel != null && !cardName.isEmpty()) {
                        JScrollPane sp = new JScrollPane(modulePanel);
                        sp.setBorder(null);
                        sp.getVerticalScrollBar().setUnitIncrement(16);
                        sp.getHorizontalScrollBar().setUnitIncrement(16);
                        contentPanel.add(sp, cardName);
                        cardLayout.show(contentPanel, cardName);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            card.add(openBtn);
        }

        // Summary stats
        int ySummary = yActions + attCardH + 25;
        JLabel summaryTitle = new JLabel("\uD83D\uDCCA  Attendance Summary (Today)");
        summaryTitle.setBounds(25, ySummary, 400, 30);
        summaryTitle.setFont(UIStyles.FONT_BOLD_XL);
        summaryTitle.setForeground(textColor);
        scrollContent.add(summaryTitle);

        int ySummaryCards = ySummary + 40;
        String[][] summaryCards = {
            {"\uD83C\uDF93 Total Present", "287"},
            {"\u274C Total Absent", "33"},
            {"\uD83D\uDCC5 Attendance %", "89.7%"},
            {"\uD83D\uDCC5 Today's Date", java.time.LocalDate.now().toString()},
        };
        Color[] sumColors = {UIStyles.SUCCESS, UIStyles.DANGER, UIStyles.PRIMARY, UIStyles.TEXT_MUTED};
        for (int i = 0; i < summaryCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(25 + i * 160, ySummaryCards, 145, 70);
            card.setBackground(cardBg);
            card.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

            JLabel title2 = new JLabel(summaryCards[i][0]);
            title2.setBounds(8, 4, 130, 18);
            title2.setFont(UIStyles.FONT_PLAIN_XS);
            title2.setForeground(mutedColor());
            card.add(title2);

            JLabel val = new JLabel(summaryCards[i][1]);
            val.setBounds(8, 22, 130, 30);
            val.setFont(UIStyles.FONT_BOLD_2XL);
            val.setForeground(sumColors[i]);
            card.add(val);

            scrollContent.add(card);
        }

        int totalHeight = ySummaryCards + 120;
        scrollContent.setPreferredSize(new Dimension(1200, Math.max(totalHeight, 400)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // ===================== REPORTS PANEL =====================
    private JPanel createReportsPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color cardBg = darkMode ? UIStyles.DARK_CARD : Color.WHITE;

        JLabel title = new JLabel("\uD83D\uDCCA  Reports & Analytics");
        title.setBounds(25, 20, 400, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(textColor);
        scrollContent.add(title);

        // Export buttons
        ModernButton btnExportPDF = new ModernButton("  \uD83D\uDCC4  Export PDF Report", ModernButton.ButtonVariant.SUCCESS);
        btnExportPDF.setBounds(25, 65, 200, 38);
        btnExportPDF.addActionListener(e -> exportReportsReport("PDF"));
        scrollContent.add(btnExportPDF);

        ModernButton btnExportExcel = new ModernButton("  \uD83D\uDCC5  Export Excel", ModernButton.ButtonVariant.SUCCESS);
        btnExportExcel.setBounds(240, 65, 180, 38);
        btnExportExcel.addActionListener(e -> exportReportsReport("Excel"));
        scrollContent.add(btnExportExcel);

        // Analytics Cards
        int yCards = 120;
        int cardW = 230;
        int cardH = 130;

        String[][] reportCards = {
            {"\uD83D\uDCCA", "87.5%", "Overall Attendance", "This Semester"},
            {"\uD83D\uDCC8", "92.3%", "BCA Attendance", "Highest Performing"},
            {"\uD83D\uDCC9", "78.1%", "B.Tech IT", "Needs Improvement"},
            {"\uD83D\uDCCA", "12", "Subjects Tracked", "All Courses"},
        };
        Color[] reportColors = {UIStyles.SUCCESS, UIStyles.PRIMARY, UIStyles.DANGER, new Color(139, 92, 246)};

        for (int i = 0; i < reportCards.length; i++) {
            JPanel card = new JPanel(null);
            card.setBounds(25 + i * (cardW + 16), yCards, cardW, cardH);
            card.setBackground(cardBg);
            card.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

            JLabel iconLbl = new JLabel(reportCards[i][0]);
            iconLbl.setBounds(12, 8, 50, 35);
            iconLbl.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            card.add(iconLbl);

            JLabel valLbl = new JLabel(reportCards[i][1]);
            valLbl.setBounds(12, 42, 200, 30);
            valLbl.setFont(UIStyles.FONT_BOLD_3XL);
            valLbl.setForeground(reportColors[i]);
            card.add(valLbl);

            JLabel t1 = new JLabel(reportCards[i][2]);
            t1.setBounds(12, 72, 200, 18);
            t1.setFont(UIStyles.FONT_BOLD_MD);
            t1.setForeground(textColor);
            card.add(t1);

            JLabel t2 = new JLabel(reportCards[i][3]);
            t2.setBounds(12, 92, 200, 18);
            t2.setFont(UIStyles.FONT_PLAIN_XS);
            t2.setForeground(mutedColor());
            card.add(t2);

            scrollContent.add(card);
        }

        // Charts section
        int yCharts = yCards + cardH + 25;
        JLabel chartsTitle = new JLabel("\uD83D\uDCCA  Attendance Analytics");
        chartsTitle.setBounds(25, yCharts, 400, 30);
        chartsTitle.setFont(UIStyles.FONT_BOLD_XL);
        chartsTitle.setForeground(textColor);
        scrollContent.add(chartsTitle);

        // Weekly bar chart
        ChartPanel weeklyChart = new ChartPanel("Weekly Attendance Trend", ChartPanel.ChartType.BAR_VERTICAL);
        weeklyChart.setBounds(25, yCharts + 40, 480, 260);
        weeklyChart.setData(
            new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"},
            new double[]{85, 78, 92, 88, 76}
        );
        scrollContent.add(weeklyChart);

        // Course comparison horizontal bar
        ChartPanel courseChart = new ChartPanel("Course-wise Attendance", ChartPanel.ChartType.BAR_HORIZONTAL);
        courseChart.setBounds(530, yCharts + 40, 480, 260);
        courseChart.setData(
            new String[]{"BCA", "MCA", "B.Tech CSE", "B.Tech IT", "B.Sc CS"},
            new double[]{92, 88, 85, 78, 90}
        );
        scrollContent.add(courseChart);

        int totalHeight = yCharts + 350;
        scrollContent.setPreferredSize(new Dimension(1200, Math.max(totalHeight, 500)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    // ===================== SETTINGS PANEL =====================
    private JPanel createSettingsPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(contentPanel.getBackground());

        JPanel scrollContent = new JPanel(null);
        scrollContent.setBackground(main.getBackground());

        Color textColor = darkMode ? UIStyles.DARK_TEXT : UIStyles.TEXT_DARK;
        Color mutedColor = darkMode ? UIStyles.DARK_TEXT_MUTED : UIStyles.TEXT_MUTED;
        Color cardBg = darkMode ? UIStyles.DARK_CARD : Color.WHITE;

        JLabel title = new JLabel("\u2699\uFE0F  Settings");
        title.setBounds(25, 20, 300, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(textColor);
        scrollContent.add(title);

        // Profile Section
        JPanel profileCard = new JPanel(null);
        profileCard.setBounds(25, 70, 550, 220);
        profileCard.setBackground(cardBg);
        profileCard.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

        JLabel profileTitle = new JLabel("\uD83D\uDC64  User Profile");
        profileTitle.setBounds(20, 15, 200, 25);
        profileTitle.setFont(UIStyles.FONT_BOLD_XL);
        profileTitle.setForeground(textColor);
        profileCard.add(profileTitle);

        UserSession session = UserSession.getInstance();
        String displayName = (session != null) ? session.getFullName() : "Admin User";
        String roleName = (session != null) ? session.getRole().getDisplayName() : "System Administrator";
        String email = (session != null) ? session.getEmail() : "admin@attendance-system.com";
        String dept = (session != null) ? session.getDepartment() : "Computer Science";

        JLabel lblName = new JLabel("Name: " + displayName);
        lblName.setBounds(20, 55, 300, 25);
        lblName.setFont(UIStyles.FONT_PLAIN_LG);
        lblName.setForeground(textColor);
        profileCard.add(lblName);

        JLabel lblRole = new JLabel("Role: " + roleName);
        lblRole.setBounds(20, 85, 300, 25);
        lblRole.setFont(UIStyles.FONT_PLAIN_LG);
        lblRole.setForeground(textColor);
        profileCard.add(lblRole);

        JLabel lblEmail = new JLabel("Email: " + email);
        lblEmail.setBounds(20, 115, 350, 25);
        lblEmail.setFont(UIStyles.FONT_PLAIN_LG);
        lblEmail.setForeground(textColor);
        profileCard.add(lblEmail);

        JLabel lblDept = new JLabel("Department: " + dept);
        lblDept.setBounds(20, 145, 350, 25);
        lblDept.setFont(UIStyles.FONT_PLAIN_LG);
        lblDept.setForeground(textColor);
        profileCard.add(lblDept);

        ModernButton editProfileBtn = new ModernButton("  \u270F\uFE0F  Edit Profile", ModernButton.ButtonVariant.PRIMARY);
        editProfileBtn.setBounds(380, 160, 150, 38);
        editProfileBtn.addActionListener(e -> NotificationToast.show(frame, "Edit profile feature coming soon!", NotificationToast.ToastType.INFO));
        profileCard.add(editProfileBtn);
        scrollContent.add(profileCard);

        // Security Section
        JPanel securityCard = new JPanel(null);
        securityCard.setBounds(25, 310, 550, 200);
        securityCard.setBackground(cardBg);
        securityCard.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

        JLabel securityTitle = new JLabel("\uD83D\uDD12  Security");
        securityTitle.setBounds(20, 15, 200, 25);
        securityTitle.setFont(UIStyles.FONT_BOLD_XL);
        securityTitle.setForeground(textColor);
        securityCard.add(securityTitle);

        ModernButton changePassBtn = new ModernButton("  \uD83D\uDD11  Change Password", ModernButton.ButtonVariant.PRIMARY);
        changePassBtn.setBounds(20, 55, 220, 42);
        changePassBtn.addActionListener(e -> {
            JPanel cpPanel = Change_Password.getPanel(HomeScreen.this);
            if (cpPanel != null) {
                NotificationToast.show(frame, "Opening password change module...", NotificationToast.ToastType.INFO);
            }
        });
        securityCard.add(changePassBtn);

        ModernButton backupBtn = new ModernButton("  \uD83D\uDCBE  Backup Database", ModernButton.ButtonVariant.SECONDARY);
        backupBtn.setBounds(20, 110, 220, 42);
        backupBtn.addActionListener(e -> NotificationToast.show(frame, "Database backup started!", NotificationToast.ToastType.SUCCESS));
        securityCard.add(backupBtn);

        ModernButton restoreBtn = new ModernButton("  \uD83D\uDCC2  Restore Database", ModernButton.ButtonVariant.SECONDARY);
        restoreBtn.setBounds(280, 55, 220, 42);
        restoreBtn.addActionListener(e -> NotificationToast.show(frame, "Database restore feature coming soon!", NotificationToast.ToastType.INFO));
        securityCard.add(restoreBtn);

        ModernButton logoutBtn = new ModernButton("  \uD83D\uDEAA  Logout", ModernButton.ButtonVariant.DANGER);
        logoutBtn.setBounds(280, 110, 220, 42);
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                instance = null;
                UserSession.destroySession();
                try { new login_frame(); } catch (IOException ex) {
                    Logger.getLogger(HomeScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        securityCard.add(logoutBtn);
        scrollContent.add(securityCard);

        // Features & Preferences Section
        JPanel featuresCard = new JPanel(null);
        featuresCard.setBounds(25, 530, 550, 220);
        featuresCard.setBackground(cardBg);
        featuresCard.setBorder(BorderFactory.createLineBorder(darkMode ? UIStyles.DARK_TABLE_BORDER : new Color(226, 232, 240), 1));

        JLabel featuresTitle = new JLabel("\uD83D\uDEE0\uFE0F  Features & Preferences");
        featuresTitle.setBounds(20, 15, 300, 25);
        featuresTitle.setFont(UIStyles.FONT_BOLD_XL);
        featuresTitle.setForeground(textColor);
        featuresCard.add(featuresTitle);

        // Dark Mode toggle
        JLabel darkModeLabel = new JLabel("\uD83C\uDF19  Dark Mode");
        darkModeLabel.setBounds(20, 55, 150, 30);
        darkModeLabel.setFont(UIStyles.FONT_PLAIN_MD);
        darkModeLabel.setForeground(textColor);
        featuresCard.add(darkModeLabel);

        JToggleButton darkToggle = new JToggleButton(darkMode ? "ON" : "OFF");
        darkToggle.setBounds(380, 55, 60, 30);
        darkToggle.setFont(UIStyles.FONT_BOLD_SM);
        darkToggle.setBackground(darkMode ? UIStyles.SUCCESS : new Color(100, 116, 139));
        darkToggle.setForeground(Color.WHITE);
        darkToggle.setFocusPainted(false);
        darkToggle.setSelected(darkMode);
        darkToggle.addActionListener(e -> toggleDarkMode());
        featuresCard.add(darkToggle);

        // Notifications toggle
        JLabel notifLabel = new JLabel("\uD83D\uDD14  Notifications");
        notifLabel.setBounds(20, 95, 150, 30);
        notifLabel.setFont(UIStyles.FONT_PLAIN_MD);
        notifLabel.setForeground(textColor);
        featuresCard.add(notifLabel);

        JToggleButton notifToggle = new JToggleButton("ON");
        notifToggle.setBounds(380, 95, 60, 30);
        notifToggle.setFont(UIStyles.FONT_BOLD_SM);
        notifToggle.setBackground(UIStyles.SUCCESS);
        notifToggle.setForeground(Color.WHITE);
        notifToggle.setFocusPainted(false);
        notifToggle.setSelected(true);
        featuresCard.add(notifToggle);

        // Features coming soon
        String[][] features = {
            {"\uD83D\uDCF1  QR Attendance", "Scan QR for quick attendance"},
            {"\uD83D\uDDFA\uFE0F  Face Recognition", "AI-based attendance (Future)"},
        };
        int fy = 135;
        for (String[] f : features) {
            JLabel fName = new JLabel(f[0]);
            fName.setBounds(20, fy, 200, 22);
            fName.setFont(UIStyles.FONT_PLAIN_MD);
            fName.setForeground(textColor);
            featuresCard.add(fName);

            JLabel fStatus = new JLabel(f[1]);
            fStatus.setBounds(220, fy, 200, 22);
            fStatus.setFont(UIStyles.FONT_PLAIN_SM);
            fStatus.setForeground(mutedColor);
            featuresCard.add(fStatus);
            fy += 35;
        }
        scrollContent.add(featuresCard);

        int totalHeight = 800;
        scrollContent.setPreferredSize(new Dimension(1200, Math.max(totalHeight, 600)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        main.add(scrollPane, BorderLayout.CENTER);

        return main;
    }

    private Color mutedColor() {
        return darkMode ? UIStyles.DARK_TEXT_MUTED : UIStyles.TEXT_MUTED;
    }

    private void styleActionBtn(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(UIStyles.FONT_BOLD_SM);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    // ===================== NAVIGATION HELPERS =====================

    public void showCard(String cardName) {
        if (cardLayout != null) {
            cardLayout.show(contentPanel, cardName);
        }
    }

    public void addCard(JPanel panel, String name) {
        if (contentPanel != null && cardLayout != null) {
            contentPanel.add(panel, name);
            cardLayout.show(contentPanel, name);
        }
    }

    public void showHome() {
        setActiveNav(0);
    }

    public void showAttendance() {
        activeNavIndex = -1;
        setActiveNav(3);
    }

    // ===================== MAIN =====================

    public static void main(String[] args) {
        try {
            AttendanceMgt.main(args);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "<html><b>Error starting application:</b><br>" + e.getMessage() + "</html>",
                "Startup Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== EXPORT HELPERS =====================

    private void exportDashboardReport(String format) {
        String[][] data = {
            {"24SCSE1180456", "Aarav Sharma", "Present", "09:15 AM", "B.Tech CSE", "3"},
            {"24SCSE1180381", "Priya Patel", "Present", "09:10 AM", "B.Tech CSE", "3"},
            {"24SCSE2030222", "Rohit Singh", "Absent", "-", "BCA", "4"},
            {"101", "Ashutosh Dubey", "Present", "09:20 AM", "MCA", "2"},
            {"102", "Aditya Verma", "Late", "09:45 AM", "MCA", "2"},
            {"103", "Ananya Reddy", "Present", "09:05 AM", "B.Tech IT", "5"},
            {"104", "Arjun Nair", "Present", "09:12 AM", "B.Tech IT", "5"},
            {"105", "Diya Joshi", "Absent", "-", "B.Sc CS", "4"},
        };

        String[] headers = {"Roll No", "Student Name", "Status", "Time", "Course", "Semester"};
        String title = "Attendance Dashboard Report";
        String subtitle = "Generated on " + java.time.LocalDate.now();
        String fileName = "Dashboard_Attendance_" + java.time.LocalDate.now();

        switch (format) {
            case "PDF":
                ReportExporter.exportToPrint(frame, data, headers, title, subtitle);
                break;
            case "Excel":
                ReportExporter.exportToCSV(frame, data, headers, fileName, title);
                break;
        }
    }

    private void exportReportsReport(String format) {
        String[][] data = {
            {"Data Structures", "92%", "45", "38", "5", "2"},
            {"Java Programming", "88%", "40", "35", "3", "2"},
            {"DBMS", "85%", "42", "36", "4", "2"},
            {"Operating Systems", "78%", "38", "30", "6", "2"},
            {"Computer Networks", "90%", "35", "32", "2", "1"},
            {"Python Programming", "95%", "20", "19", "1", "0"},
            {"Software Engineering", "82%", "30", "25", "4", "1"},
        };

        String[] headers = {"Subject", "Attendance %", "Total Days", "Present", "Absent", "Late"};
        String title = "Reports & Analytics - Attendance Summary";
        String subtitle = "Generated on " + java.time.LocalDate.now();
        String fileName = "Reports_Analytics_" + java.time.LocalDate.now();

        switch (format) {
            case "PDF":
                ReportExporter.exportToPrint(frame, data, headers, title, subtitle);
                break;
            case "Excel":
                ReportExporter.exportToCSV(frame, data, headers, fileName, title);
                break;
        }
    }
}
