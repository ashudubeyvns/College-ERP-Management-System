import attendancemgt.HomeScreen;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class login_frame {

    JFrame frame;

    // Modern color palette
    private static final Color PRIMARY = new Color(44, 62, 80);
    private static final Color ACCENT = new Color(52, 152, 219);
    private static final Color BG_LIGHT = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    private static final Color FIELD_BG = new Color(255, 255, 255);

    public login_frame() throws IOException {
        frame = new JFrame("LOGIN - Attendance Management");
        frame.getContentPane().setBackground(BG_LIGHT);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 840, 220);
        panel.setBackground(WHITE);
        frame.add(panel);
        ImageIcon icon = new ImageIcon(getClass().getResource("/attendancemgt/resources/images.png"));
        panel.add(new JLabel(icon));

        // Styled Username Label
        JLabel lbl_username = new JLabel("Username");
        lbl_username.setBounds(300, 270, 250, 25);
        lbl_username.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl_username.setForeground(PRIMARY);
        frame.add(lbl_username);

        JTextField txt_username = new JTextField();
        txt_username.setBounds(300, 298, 250, 35);
        txt_username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt_username.setBackground(FIELD_BG);
        txt_username.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)));
        frame.add(txt_username);

        // Styled Password Label
        JLabel lbl_password = new JLabel("Password");
        lbl_password.setBounds(300, 345, 250, 25);
        lbl_password.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl_password.setForeground(PRIMARY);
        frame.add(lbl_password);

        JPasswordField value = new JPasswordField();
        value.setBounds(300, 373, 250, 35);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        value.setBackground(FIELD_BG);
        value.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(5, 10, 5, 10)));
        frame.add(value);

        // Styled Sign In Button
        JButton b = new JButton("Sign In");
        b.setBounds(345, 430, 160, 40);
        b.setBackground(ACCENT);
        b.setForeground(WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setOpaque(true);
        frame.add(b);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = "Ashu";
                String password = "Ashu0908";
                String enteredUsername = txt_username.getText();
                String enteredPassword = new String(value.getPassword());
                if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                    frame.dispose();
                    new HomeScreen();
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "<html><b style='color:#e74c3c'>Invalid username or password!</b><br>Please try again.</html>",
                        "Login Failed", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
