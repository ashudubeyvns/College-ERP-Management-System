package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A modern loading overlay screen that can be shown during data operations.
 * Displays a spinner animation with status message and optional progress bar.
 */
public class LoadingScreen {

    private JDialog dialog;
    private JLabel messageLabel;
    private JProgressBar progressBar;
    private JPanel spinnerPanel;

    /**
     * Creates a loading dialog attached to the parent frame.
     */
    public LoadingScreen(JFrame parent, String message) {
        dialog = new JDialog(parent, "Loading", true);
        dialog.setUndecorated(true);
        dialog.setSize(320, 180);
        dialog.setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Background with rounded corners
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, w, h, 16, 16);

                // Border
                g2d.setColor(new Color(226, 232, 240));
                g2d.setStroke(new BasicStroke(1f));
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 16, 16);

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(3, 5, w - 3, h - 3, 16, 16);

                g2d.dispose();
            }
        };
        mainPanel.setOpaque(false);

        // Spinner (animated dots)
        spinnerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int dotCount = 3;
                int dotSize = 10;
                int gap = 12;
                int totalW = dotCount * dotSize + (dotCount - 1) * gap;
                int startX = (getWidth() - totalW) / 2;
                int y = (getHeight() - dotSize) / 2;

                long time = System.currentTimeMillis();
                for (int i = 0; i < dotCount; i++) {
                    float alpha = (float) (0.3 + 0.7 * Math.sin((time / 400.0) + i * 1.5));
                    g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
                    g2d.setColor(UIStyles.PRIMARY);
                    g2d.fillOval(startX + i * (dotSize + gap), y, dotSize, dotSize);
                }
                g2d.dispose();
            }
        };
        spinnerPanel.setOpaque(false);
        spinnerPanel.setBounds(0, 20, 320, 40);

        // Start animation timer for spinner
        Timer animTimer = new Timer(50, e -> spinnerPanel.repaint());
        animTimer.start();

        // Message label
        messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setBounds(0, 65, 320, 25);
        messageLabel.setFont(UIStyles.FONT_BOLD_MD);
        messageLabel.setForeground(UIStyles.TEXT_DARK);

        // Progress bar (optional, hidden by default)
        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(40, 100, 240, 6);
        progressBar.setStringPainted(false);
        progressBar.setBackground(new Color(226, 232, 240));
        progressBar.setForeground(UIStyles.PRIMARY);
        progressBar.setBorderPainted(false);
        progressBar.setVisible(false);

        mainPanel.add(spinnerPanel);
        mainPanel.add(messageLabel);
        mainPanel.add(progressBar);

        // Close on dispose
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                animTimer.stop();
            }
        });

        dialog.setContentPane(mainPanel);
    }

    /**
     * Show the loading dialog (blocks caller thread).
     */
    public void show() {
        dialog.setVisible(true);
    }

    /**
     * Hide and close the loading dialog.
     */
    public void hide() {
        dialog.dispose();
    }

    /**
     * Update the status message.
     */
    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Show progress bar and set progress value.
     */
    public void setProgress(int value) {
        progressBar.setVisible(true);
        progressBar.setValue(value);
    }

    /**
     * Show loading with a callback pattern (non-blocking on EDT).
     */
    public static void showLoading(JFrame parent, String message, Runnable task, Runnable onComplete) {
        LoadingScreen loading = new LoadingScreen(parent, message);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                task.run();
                return null;
            }

            @Override
            protected void done() {
                loading.hide();
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        };
        worker.execute();

        // Show loading in a separate thread
        SwingUtilities.invokeLater(() -> loading.show());
    }

    /**
     * Create and show a simple loading overlay with auto-close after task.
     */
    public static void runWithLoading(JFrame parent, String message, Runnable task) {
        LoadingScreen loading = new LoadingScreen(parent, message);

        new Thread(() -> {
            try {
                task.run();
            } finally {
                SwingUtilities.invokeLater(() -> loading.hide());
            }
        }).start();

        loading.show();
    }
}
