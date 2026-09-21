package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Professional splash screen shown at application startup.
 * Displays college logo, title, version info, and loading progress.
 */
public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private JLabel progressLabel;
    private Timer progressTimer;
    private int progress = 0;

    // Colors
    private static final Color BG_COLOR = new Color(15, 23, 42);
    private static final Color ACCENT_COLOR = UIStyles.PRIMARY;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color MUTED_COLOR = new Color(148, 163, 184);
    private static final Color PROGRESS_BG = new Color(30, 41, 59);

    public SplashScreen() {
        setSize(500, 320);
        setLocationRelativeTo(null);

        // Main panel with custom painting
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Background
                g2d.setColor(BG_COLOR);
                g2d.fillRoundRect(0, 0, w, h, 20, 20);

                // Border
                g2d.setColor(new Color(37, 99, 235, 80));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, w - 2, h - 2, 20, 20);

                // Top gradient accent line
                GradientPaint gradient = new GradientPaint(0, 0, UIStyles.PRIMARY, w, 0, UIStyles.PRIMARY_LIGHT);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, w, 4, 2, 2);

                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);

        // Logo area (emoji icon as placeholder)
        JLabel logoLabel = new JLabel("\uD83C\uDF93", SwingConstants.CENTER);
        logoLabel.setBounds(0, 30, 500, 60);
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        logoLabel.setForeground(ACCENT_COLOR);
        mainPanel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("College ERP", SwingConstants.CENTER);
        titleLabel.setBounds(0, 95, 500, 35);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(TEXT_COLOR);
        mainPanel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Attendance Management System", SwingConstants.CENTER);
        subtitleLabel.setBounds(0, 130, 500, 22);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(MUTED_COLOR);
        mainPanel.add(subtitleLabel);

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setBounds(80, 165, 340, 1);
        separator.setForeground(new Color(51, 65, 85));
        separator.setBackground(new Color(51, 65, 85));
        mainPanel.add(separator);

        // Version
        JLabel versionLabel = new JLabel("Powered by Java Swing  |  Version 2.0", SwingConstants.CENTER);
        versionLabel.setBounds(0, 175, 500, 20);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        versionLabel.setForeground(MUTED_COLOR);
        mainPanel.add(versionLabel);

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(60, 220, 380, 8);
        progressBar.setStringPainted(false);
        progressBar.setBackground(PROGRESS_BG);
        progressBar.setForeground(ACCENT_COLOR);
        progressBar.setBorderPainted(false);
        progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Insets b = progressBar.getInsets();
                int barRectWidth = progressBar.getWidth() - (b.right + b.left);
                int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

                // Background
                g2d.setColor(PROGRESS_BG);
                g2d.fillRoundRect(b.left, b.top, barRectWidth, barRectHeight, 4, 4);

                // Progress
                if (progressBar.getValue() > 0) {
                    double percent = progressBar.getPercentComplete();
                    int amountFull = (int) (barRectWidth * percent);
                    if (amountFull > 0) {
                        g2d.setColor(ACCENT_COLOR);
                        g2d.fillRoundRect(b.left, b.top, amountFull, barRectHeight, 4, 4);
                    }
                }
                g2d.dispose();
            }
        });
        mainPanel.add(progressBar);

        // Progress text
        progressLabel = new JLabel("Initializing...", SwingConstants.CENTER);
        progressLabel.setBounds(0, 240, 500, 20);
        progressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressLabel.setForeground(MUTED_COLOR);
        mainPanel.add(progressLabel);

        // "Loading..." text
        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setBounds(0, 260, 500, 18);
        loadingLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loadingLabel.setForeground(ACCENT_COLOR);
        mainPanel.add(loadingLabel);

        setContentPane(mainPanel);
    }

    /**
     * Shows the splash screen with animated progress.
     * Calls the callback when loading is complete.
     */
    public void showSplash(Runnable onComplete) {
        setVisible(true);

        progressTimer = new Timer(30, e -> {
            progress += 2;
            progressBar.setValue(progress);

            // Update status messages
            if (progress < 20) {
                progressLabel.setText("Loading modules...");
            } else if (progress < 40) {
                progressLabel.setText("Initializing UI components...");
            } else if (progress < 60) {
                progressLabel.setText("Configuring database...");
            } else if (progress < 80) {
                progressLabel.setText("Loading user interface...");
            } else if (progress < 100) {
                progressLabel.setText("Almost ready...");
            } else {
                progressTimer.stop();
                progressLabel.setText("Ready!");

                // Close splash after a brief moment
                Timer closeTimer = new Timer(400, ev -> {
                    dispose();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                });
                closeTimer.setRepeats(false);
                closeTimer.start();
            }
        });
        progressTimer.start();
    }

    /**
     * Quick show method for standalone use.
     */
    public static void showSplashAndRun(Runnable onComplete) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplash(onComplete);
        });
    }
}
