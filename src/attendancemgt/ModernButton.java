package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A modern rounded button with shadow, hover effects, and ripple animation.
 * Supports multiple variants: PRIMARY, SUCCESS, DANGER, WARNING, OUTLINE, GHOST.
 */
public class ModernButton extends JButton {

    public enum ButtonVariant {
        PRIMARY, SUCCESS, DANGER, WARNING, OUTLINE, GHOST, SECONDARY
    }

    private ButtonVariant variant = ButtonVariant.PRIMARY;
    private int cornerRadius = UIStyles.BUTTON_BORDER_RADIUS;
    private boolean hovered = false;
    private boolean pressed = false;
    private float shadowOpacity = 0.3f;
    private Color rippleColor = new Color(255, 255, 255, 100);
    private float rippleProgress = 0f;
    private Point ripplePoint;
    private Timer rippleTimer;

    // Colors based on variant
    private Color bgColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private Color borderColor;

    public ModernButton(String text) {
        this(text, ButtonVariant.PRIMARY);
    }

    public ModernButton(String text, ButtonVariant variant) {
        super(text);
        this.variant = variant;
        setupButton();
        setupRippleEffect();
    }

    private void setupButton() {
        setFont(UIStyles.FONT_BOLD_MD);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        updateColors();

        // Add hover and press effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                shadowOpacity = 0.4f;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                shadowOpacity = 0.3f;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                ripplePoint = e.getPoint();
                startRipple();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
            }
        });
    }

    private void setupRippleEffect() {
        rippleTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rippleProgress += 0.08f;
                if (rippleProgress >= 1.0f) {
                    rippleProgress = 0f;
                    rippleTimer.stop();
                }
                repaint();
            }
        });
    }

    private void startRipple() {
        rippleProgress = 0f;
        rippleTimer.start();
    }

    private void updateColors() {
        switch (variant) {
            case PRIMARY:
                bgColor = UIStyles.PRIMARY;
                hoverColor = UIStyles.PRIMARY_DARK;
                pressedColor = UIStyles.darken(UIStyles.PRIMARY_DARK, 0.1);
                textColor = Color.WHITE;
                borderColor = bgColor;
                break;
            case SUCCESS:
                bgColor = UIStyles.SUCCESS;
                hoverColor = UIStyles.darken(UIStyles.SUCCESS, 0.1);
                pressedColor = UIStyles.darken(UIStyles.SUCCESS, 0.2);
                textColor = Color.WHITE;
                borderColor = bgColor;
                break;
            case DANGER:
                bgColor = UIStyles.DANGER;
                hoverColor = UIStyles.darken(UIStyles.DANGER, 0.1);
                pressedColor = UIStyles.darken(UIStyles.DANGER, 0.2);
                textColor = Color.WHITE;
                borderColor = bgColor;
                break;
            case WARNING:
                bgColor = UIStyles.WARNING;
                hoverColor = UIStyles.darken(UIStyles.WARNING, 0.1);
                pressedColor = UIStyles.darken(UIStyles.WARNING, 0.2);
                textColor = Color.WHITE;
                borderColor = bgColor;
                break;
            case OUTLINE:
                bgColor = new Color(0, 0, 0, 0);
                hoverColor = UIStyles.withAlpha(UIStyles.PRIMARY, 20);
                pressedColor = UIStyles.withAlpha(UIStyles.PRIMARY, 40);
                textColor = UIStyles.PRIMARY;
                borderColor = UIStyles.PRIMARY;
                setForeground(textColor);
                break;
            case GHOST:
                bgColor = new Color(0, 0, 0, 0);
                hoverColor = new Color(0, 0, 0, 20);
                pressedColor = new Color(0, 0, 0, 40);
                textColor = UIStyles.TEXT_DARK;
                borderColor = new Color(0, 0, 0, 0);
                break;
            case SECONDARY:
                bgColor = new Color(100, 116, 139);
                hoverColor = UIStyles.darken(new Color(100, 116, 139), 0.1);
                pressedColor = UIStyles.darken(new Color(100, 116, 139), 0.2);
                textColor = Color.WHITE;
                borderColor = bgColor;
                break;
        }
        setForeground(textColor);
    }

    public void setVariant(ButtonVariant variant) {
        this.variant = variant;
        updateColors();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();
        int arc = cornerRadius;

        // Shadow
        if (variant != ButtonVariant.GHOST && variant != ButtonVariant.OUTLINE) {
            g2d.setColor(new Color(0, 0, 0, (int) (shadowOpacity * 60)));
            g2d.fillRoundRect(2, 4, w - 2, h - 2, arc, arc);
        }

        // Background
        Color currentBg = bgColor;
        if (pressed) {
            currentBg = pressedColor;
        } else if (hovered) {
            currentBg = hoverColor;
        }
        g2d.setColor(currentBg);
        g2d.fillRoundRect(0, 0, w, h, arc, arc);

        // Border for outline variant
        if (variant == ButtonVariant.OUTLINE) {
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRoundRect(1, 1, w - 2, h - 2, arc, arc);
        }

        // Ripple effect
        if (rippleProgress > 0 && ripplePoint != null) {
            float radius = Math.max(w, h) * 1.2f * rippleProgress;
            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f - rippleProgress));
            g2d.setColor(rippleColor);
            Shape rippleShape = new java.awt.geom.Ellipse2D.Float(
                ripplePoint.x - radius, ripplePoint.y - radius,
                radius * 2, radius * 2
            );
            g2d.setClip(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
            g2d.fill(rippleShape);
            g2d.setClip(null);
        }

        g2d.dispose();

        // Draw text
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.width = Math.max(d.width + 32, 80);
        d.height = Math.max(d.height + 12, 36);
        return d;
    }
}
