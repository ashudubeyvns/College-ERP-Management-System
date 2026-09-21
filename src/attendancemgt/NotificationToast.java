package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern toast notification system that replaces JOptionPane for success/error/warning/info messages.
 * Displays sliding notifications that auto-dismiss.
 */
public class NotificationToast {

    public enum ToastType {
        SUCCESS("\u2705", UIStyles.SUCCESS),
        ERROR("\u274C", UIStyles.DANGER),
        WARNING("\u26A0\uFE0F", UIStyles.WARNING),
        INFO("\u2139\uFE0F", UIStyles.INFO);

        private final String icon;
        private final Color color;

        ToastType(String icon, Color color) {
            this.icon = icon;
            this.color = color;
        }

        public String getIcon() { return icon; }
        public Color getColor() { return color; }
    }

    /**
     * Shows a toast notification on the given parent frame.
     */
    public static void show(JFrame parent, String message, ToastType type) {
        show(parent, message, type, 3000);
    }

    /**
     * Shows a toast notification with custom duration.
     */
    public static void show(JFrame parent, String message, ToastType type, int durationMs) {
        if (parent == null) {
            // Fallback to JOptionPane if no parent
            int msgType = JOptionPane.INFORMATION_MESSAGE;
            switch (type) {
                case ERROR: msgType = JOptionPane.ERROR_MESSAGE; break;
                case WARNING: msgType = JOptionPane.WARNING_MESSAGE; break;
            }
            JOptionPane.showMessageDialog(null, message, type.name(), msgType);
            return;
        }

        // Create a JDialog for the toast
        JDialog toast = new JDialog(parent);
        toast.setUndecorated(true);
        toast.setAlwaysOnTop(true);

        // Build the toast panel
        JPanel panel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(type.getColor());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(new Color(0, 0, 0, 40));
                g2d.fillRoundRect(2, 4, getWidth() - 2, getHeight() - 2, 12, 12);
                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        // Icon
        JLabel iconLabel = new JLabel(type.getIcon());
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        iconLabel.setForeground(Color.WHITE);
        panel.add(iconLabel, BorderLayout.WEST);

        // Message
        JLabel msgLabel = new JLabel("<html><b>" + message + "</b></html>");
        msgLabel.setFont(UIStyles.FONT_BOLD_MD);
        msgLabel.setForeground(Color.WHITE);
        panel.add(msgLabel, BorderLayout.CENTER);

        toast.add(panel);
        toast.pack();

        // Position at top-right of parent
        Dimension parentSize = parent.getSize();
        Point parentLocation = parent.getLocationOnScreen();
        int toastWidth = toast.getWidth();
        int toastHeight = toast.getHeight();
        int x = parentLocation.x + parentSize.width - toastWidth - 20;
        int y = parentLocation.y + 80;
        toast.setLocation(x, y);

        // Show with fade-in animation
        toast.setOpacity(0.0f);
        toast.setVisible(true);

        // Fade in
        Timer fadeIn = new Timer(20, null);
        fadeIn.addActionListener(e -> {
            float opacity = toast.getOpacity() + 0.1f;
            if (opacity >= 1.0f) {
                opacity = 1.0f;
                fadeIn.stop();
            }
            toast.setOpacity(opacity);
        });
        fadeIn.start();

        // Auto dismiss after duration
        Timer dismiss = new Timer(durationMs, e -> {
            // Fade out
            Timer fadeOut = new Timer(20, null);
            fadeOut.addActionListener(ev -> {
                float opacity = toast.getOpacity() - 0.1f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    fadeOut.stop();
                    toast.dispose();
                }
                toast.setOpacity(opacity);
            });
            fadeOut.start();
        });
        dismiss.setRepeats(false);
        dismiss.start();
    }

    /**
     * Shows a toast notification using a component reference (finds parent frame).
     */
    public static void show(Component parentComponent, String message, ToastType type) {
        JFrame parent = findParentFrame(parentComponent);
        show(parent, message, type);
    }

    /**
     * Shows a toast notification with custom duration.
     */
    public static void show(Component parentComponent, String message, ToastType type, int durationMs) {
        JFrame parent = findParentFrame(parentComponent);
        show(parent, message, type, durationMs);
    }

    private static JFrame findParentFrame(Component comp) {
        if (comp == null) return null;
        Container parent = comp.getParent();
        while (parent != null) {
            if (parent instanceof JFrame) {
                return (JFrame) parent;
            }
            parent = parent.getParent();
        }
        return (JFrame) SwingUtilities.getWindowAncestor(comp);
    }

    /**
     * Convenience methods matching JOptionPane style but with toast notifications.
     */
    public static void showSuccess(JFrame parent, String message) {
        show(parent, message, ToastType.SUCCESS);
    }

    public static void showError(JFrame parent, String message) {
        show(parent, message, ToastType.ERROR);
    }

    public static void showWarning(JFrame parent, String message) {
        show(parent, message, ToastType.WARNING);
    }

    public static void showInfo(JFrame parent, String message) {
        show(parent, message, ToastType.INFO);
    }

    public static void showSuccess(Component comp, String message) {
        JFrame parent = findParentFrame(comp);
        show(parent, message, ToastType.SUCCESS);
    }

    public static void showError(Component comp, String message) {
        JFrame parent = findParentFrame(comp);
        show(parent, message, ToastType.ERROR);
    }

    public static void showWarning(Component comp, String message) {
        JFrame parent = findParentFrame(comp);
        show(parent, message, ToastType.WARNING);
    }

    public static void showInfo(Component comp, String message) {
        JFrame parent = findParentFrame(comp);
        show(parent, message, ToastType.INFO);
    }
}
