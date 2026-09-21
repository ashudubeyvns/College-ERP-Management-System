package attendancemgt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class login_frame {

    JFrame frame;
    private HashMap<String, String> credentials;

    // Modern color palette from UIStyles
    private static final Color PRIMARY = UIStyles.PRIMARY;
    private static final Color PRIMARY_DARK = UIStyles.PRIMARY_DARK;
    private static final Color PRIMARY_LIGHT = UIStyles.PRIMARY_LIGHT;
    private static final Color WHITE = Color.WHITE;
    private static final Color FIELD_BG = new Color(255, 255, 255, 220);
    private static final Color GLASS_BG = new Color(255, 255, 255, 200);
    private static final Color TEXT_DARK = UIStyles.TEXT_DARK;
    private static final Color TEXT_MUTED = UIStyles.TEXT_MUTED;

    // Pre-computed hashed credentials with roles
    private String[][] userDatabase;

    public login_frame() throws IOException {
        // Initialize user database with hashed passwords
        userDatabase = new String[][] {
            {"admin", "System Administrator", PasswordUtils.hashPassword("Admin@123"), "ADMIN", "admin@attendance-system.com"},
            {"faculty1", "Dr. Rajesh Kumar", PasswordUtils.hashPassword("Fac@12345"), "FACULTY", "rajesh.kumar@example.com"},
            {"faculty2", "Prof. Amit Singh", PasswordUtils.hashPassword("Fac@12345"), "FACULTY", "amit.singh@example.com"},
            {"24SCSE1180456", "Aarav Sharma", PasswordUtils.hashPassword("12345"), "STUDENT", "aarav.sharma@example.com"},
            {"24SCSE1180381", "Priya Patel", PasswordUtils.hashPassword("12345"), "STUDENT", "priya.patel@example.com"},
            {"24SCSE2030222", "Rohit Singh", PasswordUtils.hashPassword("12345"), "STUDENT", "rohit.singh@example.com"},
            {"101", "Ashutosh Dubey", PasswordUtils.hashPassword("12345"), "STUDENT", "ashutosh.dubey@example.com"},
            {"102", "Aditya Verma", PasswordUtils.hashPassword("12345"), "STUDENT", "aditya.verma@example.com"},
        };

        frame = new JFrame("College ERP - Attendance Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Blue gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, UIStyles.PRIMARY_GRADIENT_START,
                    w, h, UIStyles.PRIMARY_GRADIENT_END
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, w, h);

                // Decorative circles
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillOval(-100, -100, 400, 400);
                g2d.fillOval(w - 250, h - 250, 400, 400);
                g2d.fillOval(w / 2 - 50, h / 2 - 50, 150, 150);

                // Subtle pattern overlay
                g2d.setColor(new Color(255, 255, 255, 5));
                for (int i = 0; i < w; i += 40) {
                    g2d.drawLine(i, 0, i, h);
                }
                for (int i = 0; i < h; i += 40) {
                    g2d.drawLine(0, i, w, i);
                }

                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);

        // ========== GLASSMORPHISM LOGIN CARD ==========
        JPanel loginCard = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 40));
                g2d.fillRoundRect(5, 8, w - 5, h - 5, 20, 20);

                // Glass background
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.85f));
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, w, h, 20, 20);

                // Glass border
                g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
                g2d.setColor(new Color(255, 255, 255, 180));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);

                // Top accent line
                GradientPaint accentGradient = new GradientPaint(
                    0, 0, UIStyles.PRIMARY, w, 0, UIStyles.PRIMARY_LIGHT
                );
                g2d.setPaint(accentGradient);
                g2d.fillRoundRect(20, 0, w - 40, 4, 2, 2);

                g2d.dispose();
            }
        };
        loginCard.setBounds(300, 80, 400, 540);
        loginCard.setOpaque(false);

        // ========== LOGO & TITLE ==========
        JLabel logoLabel = new JLabel("\uD83C\uDF93", SwingConstants.CENTER);
        logoLabel.setBounds(0, 25, 400, 55);
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        logoLabel.setForeground(UIStyles.PRIMARY);
        loginCard.add(logoLabel);

        JLabel titleLabel = new JLabel("College ERP", SwingConstants.CENTER);
        titleLabel.setBounds(0, 80, 400, 30);
        titleLabel.setFont(UIStyles.FONT_BOLD_3XL);
        titleLabel.setForeground(TEXT_DARK);
        loginCard.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Attendance Management System", SwingConstants.CENTER);
        subtitleLabel.setBounds(0, 108, 400, 20);
        subtitleLabel.setFont(UIStyles.FONT_PLAIN_MD);
        subtitleLabel.setForeground(TEXT_MUTED);
        loginCard.add(subtitleLabel);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 140, 300, 1);
        separator.setForeground(new Color(226, 232, 240));
        separator.setBackground(new Color(226, 232, 240));
        loginCard.add(separator);

        // ========== USERNAME FIELD ==========
        JLabel lbl_username = new JLabel("\uD83D\uDC64  Username / Roll No");
        lbl_username.setBounds(40, 160, 320, 22);
        lbl_username.setFont(UIStyles.FONT_BOLD_LG);
        lbl_username.setForeground(TEXT_DARK);
        loginCard.add(lbl_username);

        JTextField txt_username = new JTextField();
        txt_username.setBounds(40, 185, 320, 42);
        txt_username.setFont(UIStyles.FONT_PLAIN_XL);
        txt_username.setForeground(TEXT_DARK);
        txt_username.setBackground(FIELD_BG);
        txt_username.setCaretColor(UIStyles.PRIMARY);
        txt_username.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 15, 5, 15)));
        txt_username.putClientProperty("JTextField.placeholderText", "Enter your username or roll number");
        loginCard.add(txt_username);

        // ========== PASSWORD FIELD ==========
        JLabel lbl_password = new JLabel("\uD83D\uDD12  Password");
        lbl_password.setBounds(40, 240, 320, 22);
        lbl_password.setFont(UIStyles.FONT_BOLD_LG);
        lbl_password.setForeground(TEXT_DARK);
        loginCard.add(lbl_password);

        // Password panel with show/hide toggle
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBounds(40, 265, 320, 42);
        passwordPanel.setBackground(FIELD_BG);
        passwordPanel.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));

        JPasswordField value = new JPasswordField();
        value.setFont(UIStyles.FONT_PLAIN_XL);
        value.setForeground(TEXT_DARK);
        value.setBackground(FIELD_BG);
        value.setCaretColor(UIStyles.PRIMARY);
        value.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        JToggleButton showPasswordBtn = new JToggleButton("\uD83D\uDC41\uFE0F");
        showPasswordBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        showPasswordBtn.setFocusPainted(false);
        showPasswordBtn.setBorderPainted(false);
        showPasswordBtn.setContentAreaFilled(false);
        showPasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showPasswordBtn.setPreferredSize(new Dimension(42, 42));
        showPasswordBtn.setToolTipText("Show/Hide Password");
        showPasswordBtn.addActionListener(e -> {
            if (showPasswordBtn.isSelected()) {
                value.setEchoChar((char) 0);
                showPasswordBtn.setText("\uD83D\uDC41");
            } else {
                value.setEchoChar('•');
                showPasswordBtn.setText("\uD83D\uDC41\uFE0F");
            }
        });

        passwordPanel.add(value, BorderLayout.CENTER);
        passwordPanel.add(showPasswordBtn, BorderLayout.EAST);
        loginCard.add(passwordPanel);

        // Demo Login Suggestion
            JLabel demoHint = new JLabel(
                "<html><center><b>Demo Login:</b> Username: <b>demo</b> | Password: <b>demo123</b></center></html>",
                SwingConstants.CENTER
            );
            demoHint.setBounds(40, 310, 320, 22);
            demoHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            demoHint.setForeground(UIStyles.PRIMARY);
            loginCard.add(demoHint);

        // ========== ROLE SELECTOR ==========
        JLabel lbl_role = new JLabel("\uD83D\uDC65  Login As");
        lbl_role.setBounds(40, 345, 320, 22);
        lbl_role.setFont(UIStyles.FONT_BOLD_LG);
        lbl_role.setForeground(TEXT_DARK);
        loginCard.add(lbl_role);

        JComboBox<String> roleCombo = new JComboBox<>(new String[] {"Student", "Faculty", "Administrator"});
        roleCombo.setBounds(40, 370, 320, 38);
        roleCombo.setFont(UIStyles.FONT_PLAIN_LG);
        roleCombo.setBackground(FIELD_BG);
        roleCombo.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        ((JLabel) roleCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        loginCard.add(roleCombo);

        // ========== REMEMBER ME & FORGOT PASSWORD ==========
        JCheckBox rememberMe = new JCheckBox("  Remember Me");
        rememberMe.setBounds(40, 395, 160, 25);
        rememberMe.setFont(UIStyles.FONT_PLAIN_MD);
        rememberMe.setForeground(TEXT_DARK);
        rememberMe.setOpaque(false);
        rememberMe.setFocusPainted(false);
        rememberMe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginCard.add(rememberMe);

        JLabel lblForgotPassword = new JLabel("<html><u style='color:#2563EB;'>Forgot Password?</u></html>");
        lblForgotPassword.setBounds(240, 395, 120, 25);
        lblForgotPassword.setFont(UIStyles.FONT_PLAIN_MD);
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgotPassword.setForeground(UIStyles.PRIMARY);
        loginCard.add(lblForgotPassword);

        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openForgotPasswordDialog();
            }
        });

        // ========== LOGIN BUTTON (Animated) ==========
        ModernButton loginBtn = new ModernButton("  \uD83D\uDD11  Sign In", ModernButton.ButtonVariant.PRIMARY);
        loginBtn.setBounds(60, 435, 280, 46);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginCard.add(loginBtn);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = txt_username.getText().trim();
                String enteredPassword = new String(value.getPassword());
                String selectedRole = (String) roleCombo.getSelectedItem();

                // Presentation / Demo Login
if (enteredUsername.equals("demo") && enteredPassword.equals("demo123")) {

    UserSession.Role demoRole;

    switch (selectedRole) {
        case "Administrator":
            demoRole = UserSession.Role.ADMIN;
            break;

        case "Faculty":
            demoRole = UserSession.Role.FACULTY;
            break;

        default:
            demoRole = UserSession.Role.STUDENT;
            break;
    }

    UserSession.createSession(
        "demo",
        "Presentation Demo User",
        demoRole
    );

    frame.dispose();

    switch (demoRole) {
        case ADMIN:
            new HomeScreen();
            break;

        case FACULTY:
            new FacultyDashboard();
            break;

        case STUDENT:
            new StudentDashboard();
            break;
    }

    return;
}

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    NotificationToast.show(frame, "Please enter username and password!", NotificationToast.ToastType.WARNING);
                    return;
                }

                // Find user in database
                boolean authenticated = false;
                String displayName = "";
                String userRole = "";
                String email = "";
                String storedHash = "";

                for (String[] user : userDatabase) {
                    if (user[0].equals(enteredUsername)) {
                        storedHash = user[2];
                        displayName = user[1];
                        userRole = user[3];
                        email = user[4];
                        authenticated = PasswordUtils.verifyPassword(enteredPassword, storedHash);
                        break;
                    }
                }

                if (authenticated) {
                    // Map role combo selection to database role
                    String roleMap = "";
                    switch (selectedRole) {
                        case "Administrator": roleMap = "ADMIN"; break;
                        case "Faculty": roleMap = "FACULTY"; break;
                        default: roleMap = "STUDENT"; break;
                    }

                    // Verify the selected role matches the user's role
                    if (!userRole.equals(roleMap)) {
                        NotificationToast.show(frame,
                            "Role mismatch! User '" + enteredUsername + "' is registered as "
                            + getUserRoleDisplayName(userRole) + ". Please select the correct role.",
                            NotificationToast.ToastType.WARNING);
                        return;
                    }

                    UserSession.Role role = UserSession.Role.valueOf(userRole);
                    UserSession.createSession(enteredUsername, displayName, role);
                    frame.dispose();

                    switch (role) {
                        case ADMIN:
                            new HomeScreen();
                            break;
                        case FACULTY:
                            new FacultyDashboard();
                            break;
                        case STUDENT:
                            new StudentDashboard();
                            break;
                    }
                } else {
                    NotificationToast.show(frame, "Invalid username or password! Please try again.", NotificationToast.ToastType.ERROR);
                }
            }
        });

        // ========== REGISTER LINK ==========
        JLabel registerLabel = new JLabel("<html>Don't have an account? <u style='color:#2563EB;'>Request Credentials</u></html>", SwingConstants.CENTER);
        registerLabel.setBounds(40, 495, 320, 22);
        registerLabel.setFont(UIStyles.FONT_PLAIN_MD);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setForeground(TEXT_MUTED);
        loginCard.add(registerLabel);

        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openEmailRequestDialog();
            }
        });

        // ========== ADD CARD TO MAIN ==========
        mainPanel.add(loginCard);

        // ========== FOOTER ==========
        JLabel footerLabel = new JLabel("Powered by Java Swing  |  Version 2.0", SwingConstants.CENTER);
        footerLabel.setBounds(0, 660, 1000, 25);
        footerLabel.setFont(UIStyles.FONT_PLAIN_SM);
        footerLabel.setForeground(new Color(255, 255, 255, 150));
        mainPanel.add(footerLabel);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Show splash screen on first load
        showSplashScreen();
    }

    private void showSplashScreen() {
        // Show splash only once per session
        if (SplashScreen.class != null) {
            SplashScreen.showSplashAndRun(() -> {
                // Nothing to do - login already visible
            });
        }
    }

    /**
     * Returns the display name for a role code.
     */
    private String getUserRoleDisplayName(String roleCode) {
        switch (roleCode) {
            case "ADMIN": return "Administrator";
            case "FACULTY": return "Faculty";
            case "STUDENT": return "Student";
            default: return "Unknown";
        }
    }

    private void openForgotPasswordDialog() {
        JDialog dialog = new JDialog(frame, "Reset Password", true);
        dialog.setUndecorated(true);
        dialog.setSize(450, 420);
        dialog.setLocationRelativeTo(frame);

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2d.setColor(new Color(226, 232, 240));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);

        // Title
        JLabel title = new JLabel("\uD83D\uDD11  Reset Your Password", SwingConstants.CENTER);
        title.setBounds(0, 20, 450, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(TEXT_DARK);
        mainPanel.add(title);

        JLabel lblDesc = new JLabel("Enter your username/email to receive a password reset link.", SwingConstants.CENTER);
        lblDesc.setBounds(30, 60, 390, 22);
        lblDesc.setFont(UIStyles.FONT_PLAIN_MD);
        lblDesc.setForeground(TEXT_MUTED);
        mainPanel.add(lblDesc);

        // Username field
        JLabel lblUsername = new JLabel("Username / Roll No:");
        lblUsername.setBounds(40, 100, 150, 25);
        lblUsername.setFont(UIStyles.FONT_BOLD_LG);
        lblUsername.setForeground(TEXT_DARK);
        mainPanel.add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(190, 100, 220, 38);
        txtUsername.setFont(UIStyles.FONT_PLAIN_LG);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 12, 5, 12)));
        mainPanel.add(txtUsername);

        // Email field
        JLabel lblEmail = new JLabel("Registered Email:");
        lblEmail.setBounds(40, 155, 140, 25);
        lblEmail.setFont(UIStyles.FONT_BOLD_LG);
        lblEmail.setForeground(TEXT_DARK);
        mainPanel.add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(190, 155, 220, 38);
        txtEmail.setFont(UIStyles.FONT_PLAIN_LG);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 12, 5, 12)));
        mainPanel.add(txtEmail);

        // Reset Button
        ModernButton btnReset = new ModernButton("  \uD83D\uDCE8  Send Reset Link", ModernButton.ButtonVariant.PRIMARY);
        btnReset.setBounds(100, 220, 250, 44);
        mainPanel.add(btnReset);

        btnReset.addActionListener(ev -> {
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();

            if (username.isEmpty() || email.isEmpty()) {
                NotificationToast.show(dialog, "Please enter both Username and Email.", NotificationToast.ToastType.WARNING);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                NotificationToast.show(dialog, "Please enter a valid email address.", NotificationToast.ToastType.WARNING);
                return;
            }

            // Check if username exists
            boolean userFound = false;
            for (String[] user : userDatabase) {
                if (user[0].equals(username)) {
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                NotificationToast.show(dialog, "Username not found! Please check or contact admin.", NotificationToast.ToastType.WARNING);
                return;
            }

            try {
                String subject = "Attendance System - Password Reset Request";
                String body = "Hello,%0D%0A%0D%0A"
                    + "A password reset has been requested for your account.%0D%0A%0D%0A"
                    + "--- Account Details ---%0D%0A"
                    + "Username: " + username + "%0D%0A"
                    + "Registered Email: " + email + "%0D%0A%0D%0A"
                    + "To reset your password, please contact the administrator or click the link below:%0D%0A"
                    + "[Reset Link: http://attendance-system.com/reset-password]%0D%0A%0D%0A"
                    + "If you did not request this, please ignore this email.%0D%0A%0D%0A"
                    + "---%0D%0AAttendance Management System";

                String mailto = "mailto:" + email
                    + "?subject=" + subject.replace(" ", "%20")
                    + "&body=" + body;
                Desktop.getDesktop().mail(new java.net.URI(mailto));

                NotificationToast.show(dialog, "Reset link sent! Check your inbox.", NotificationToast.ToastType.SUCCESS);
                dialog.dispose();
            } catch (Exception ex) {
                NotificationToast.show(dialog, "Could not open email client. Please contact admin.", NotificationToast.ToastType.ERROR);
            }
        });

        // Close button
        JLabel closeLabel = new JLabel("\u2716", SwingConstants.CENTER);
        closeLabel.setBounds(410, 10, 30, 30);
        closeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        closeLabel.setForeground(TEXT_MUTED);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dialog.dispose();
            }
        });
        mainPanel.add(closeLabel);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private void openEmailRequestDialog() {
        JDialog dialog = new JDialog(frame, "Request Credentials", true);
        dialog.setUndecorated(true);
        dialog.setSize(450, 440);
        dialog.setLocationRelativeTo(frame);

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2d.setColor(new Color(226, 232, 240));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);

        JLabel title = new JLabel("\uD83D\uDC64  Request Login Credentials", SwingConstants.CENTER);
        title.setBounds(0, 20, 450, 35);
        title.setFont(UIStyles.FONT_BOLD_2XL);
        title.setForeground(TEXT_DARK);
        mainPanel.add(title);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(40, 70, 120, 25);
        lblName.setFont(UIStyles.FONT_BOLD_LG);
        lblName.setForeground(TEXT_DARK);
        mainPanel.add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(170, 70, 240, 36);
        txtName.setFont(UIStyles.FONT_PLAIN_LG);
        txtName.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 12, 5, 12)));
        mainPanel.add(txtName);

        JLabel lblEmail = new JLabel("Your Email:");
        lblEmail.setBounds(40, 120, 120, 25);
        lblEmail.setFont(UIStyles.FONT_BOLD_LG);
        lblEmail.setForeground(TEXT_DARK);
        mainPanel.add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(170, 120, 240, 36);
        txtEmail.setFont(UIStyles.FONT_PLAIN_LG);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 12, 5, 12)));
        mainPanel.add(txtEmail);

        JLabel lblRoll = new JLabel("Roll/ID No:");
        lblRoll.setBounds(40, 170, 120, 25);
        lblRoll.setFont(UIStyles.FONT_BOLD_LG);
        lblRoll.setForeground(TEXT_DARK);
        mainPanel.add(lblRoll);

        JTextField txtRoll = new JTextField();
        txtRoll.setBounds(170, 170, 240, 36);
        txtRoll.setFont(UIStyles.FONT_PLAIN_LG);
        txtRoll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            new EmptyBorder(5, 12, 5, 12)));
        mainPanel.add(txtRoll);

        JLabel lblCourse = new JLabel("Course/Program:");
        lblCourse.setBounds(40, 220, 120, 25);
        lblCourse.setFont(UIStyles.FONT_BOLD_LG);
        lblCourse.setForeground(TEXT_DARK);
        mainPanel.add(lblCourse);

        String[] courses = {"BCA", "MCA", "B.Tech CSE", "B.Tech IT", "B.Sc CS", "M.Sc CS", "Faculty", "Staff", "Other"};
        JComboBox<String> cmbCourse = new JComboBox<>(courses);
        cmbCourse.setBounds(170, 220, 240, 36);
        cmbCourse.setFont(UIStyles.FONT_PLAIN_LG);
        cmbCourse.setBackground(Color.WHITE);
        cmbCourse.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        mainPanel.add(cmbCourse);

        ModernButton btnSend = new ModernButton("  \uD83D\uDCE8  Send Request", ModernButton.ButtonVariant.PRIMARY);
        btnSend.setBounds(100, 280, 250, 44);
        mainPanel.add(btnSend);

        btnSend.addActionListener(ev -> {
            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String roll = txtRoll.getText().trim();
            String program = (String) cmbCourse.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || roll.isEmpty()) {
                NotificationToast.show(dialog, "Please fill in Name, Email, and Roll/ID No.", NotificationToast.ToastType.WARNING);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                NotificationToast.show(dialog, "Please enter a valid email address.", NotificationToast.ToastType.WARNING);
                return;
            }

            try {
                String subject = "Attendance System - New Credentials Request from " + name;
                String body = "Hello Admin,%0D%0A%0D%0A"
                    + "I would like to request login credentials for the Attendance Management System.%0D%0A%0D%0A"
                    + "--- Request Details ---%0D%0A"
                    + "Full Name: " + name + "%0D%0A"
                    + "Email: " + email + "%0D%0A"
                    + "Roll/ID No: " + roll + "%0D%0A"
                    + "Course/Program: " + program + "%0D%0A"
                    + "%0D%0A---%0D%0AThis request was sent from the Attendance Management System.";

                String mailto = "mailto:admin@attendance-system.com"
                    + "?subject=" + subject.replace(" ", "%20")
                    + "&body=" + body;
                Desktop.getDesktop().mail(new java.net.URI(mailto));

                NotificationToast.show(dialog, "Request submitted! Email composed to admin.", NotificationToast.ToastType.SUCCESS);
                dialog.dispose();
            } catch (Exception ex) {
                NotificationToast.show(dialog, "Could not open email client. Please manually email admin.", NotificationToast.ToastType.ERROR);
            }
        });

        // Close button
        JLabel closeLabel = new JLabel("\u2716", SwingConstants.CENTER);
        closeLabel.setBounds(410, 10, 30, 30);
        closeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        closeLabel.setForeground(TEXT_MUTED);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dialog.dispose();
            }
        });
        mainPanel.add(closeLabel);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
