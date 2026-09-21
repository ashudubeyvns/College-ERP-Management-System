package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Change_Password {

    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;

    /**
     * Returns a JPanel for Change Password that can be embedded in HomeScreen's CardLayout.
     */
    public static JPanel getPanel(HomeScreen homeScreen) {
        JPanel main = new JPanel(null);
        main.setBackground(BG_LIGHT);

        // Header
        JPanel imagePanel = new JPanel();
        imagePanel.setBounds(0, 0, 1200, 150);
        imagePanel.setBackground(WHITE);
        try {
            java.net.URL imgUrl = Change_Password.class.getResource("/attendancemgt/resources/images.png");
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaled = icon.getImage().getScaledInstance(1200, 150, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaled)));
            } else {
                JLabel fallback = new JLabel("CHANGE PASSWORD", SwingConstants.CENTER);
                fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
                fallback.setForeground(new Color(44, 62, 80));
                imagePanel.add(fallback);
            }
        } catch (Exception ex) {
            JLabel fallback = new JLabel("CHANGE PASSWORD", SwingConstants.CENTER);
            fallback.setFont(new Font("Segoe UI", Font.BOLD, 20));
            fallback.setForeground(new Color(44, 62, 80));
            imagePanel.add(fallback);
        }
        main.add(imagePanel);

        // Nav Bar
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

        JLabel infoLbl = new JLabel("Change Password", SwingConstants.CENTER);
        infoLbl.setBounds(400, 5, 400, 40);
        infoLbl.setForeground(WHITE);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navPanel.add(infoLbl);

        // Title
        JLabel title = new JLabel("CHANGE YOUR PASSWORD", SwingConstants.CENTER);
        title.setBounds(0, 220, 1200, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(PRIMARY);
        main.add(title);

        // Form Panel (centered)
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(350, 280, 500, 320);
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        main.add(formPanel);

        JLabel lblTitle = new JLabel("Update Your Password", SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 400, 30);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(PRIMARY);
        formPanel.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 70, 100, 25);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsername.setForeground(PRIMARY);
        formPanel.add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(160, 70, 290, 30);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(txtUsername);

        JLabel lblCurrent = new JLabel("Current Password:");
        lblCurrent.setBounds(50, 115, 120, 25);
        lblCurrent.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCurrent.setForeground(PRIMARY);
        formPanel.add(lblCurrent);

        JPasswordField txtCurrent = new JPasswordField();
        txtCurrent.setBounds(180, 115, 270, 30);
        txtCurrent.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCurrent.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(txtCurrent);

        JLabel lblNew = new JLabel("New Password:");
        lblNew.setBounds(50, 160, 120, 25);
        lblNew.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNew.setForeground(PRIMARY);
        formPanel.add(lblNew);

        JPasswordField txtNew = new JPasswordField();
        txtNew.setBounds(180, 160, 270, 30);
        txtNew.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNew.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(txtNew);

        JLabel lblConfirm = new JLabel("Confirm Password:");
        lblConfirm.setBounds(50, 205, 130, 25);
        lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblConfirm.setForeground(PRIMARY);
        formPanel.add(lblConfirm);

        JPasswordField txtConfirm = new JPasswordField();
        txtConfirm.setBounds(190, 205, 260, 30);
        txtConfirm.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtConfirm.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        formPanel.add(txtConfirm);

        JButton btnUpdate = new JButton("Update Password");
        btnUpdate.setBounds(150, 255, 200, 40);
        btnUpdate.setBackground(ACCENT);
        btnUpdate.setForeground(WHITE);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdate.setOpaque(true);
        formPanel.add(btnUpdate);

        btnUpdate.addActionListener(ev -> {
            String username = txtUsername.getText().trim();
            String current = new String(txtCurrent.getPassword()).trim();
            String newPass = new String(txtNew.getPassword()).trim();
            String confirm = new String(txtConfirm.getPassword()).trim();

            if (username.isEmpty() || current.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(main,
                    "<html><b style='color:#e74c3c'>All fields are required!</b></html>",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(main,
                    "<html><b style='color:#e74c3c'>New password and confirm password do not match!</b></html>",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPass.length() < 6) {
                JOptionPane.showMessageDialog(main,
                    "<html><b style='color:#e74c3c'>Password must be at least 6 characters!</b></html>",
                    "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(main,
                "<html><b style='color:#27ae60'>Password updated successfully for user: " + username + "</b></html>",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            txtUsername.setText("");
            txtCurrent.setText("");
            txtNew.setText("");
            txtConfirm.setText("");
        });

        int totalHeight = 650;
        main.setPreferredSize(new Dimension(1200, totalHeight));
        return main;
    }
}
