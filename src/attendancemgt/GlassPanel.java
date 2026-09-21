package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A glassmorphism panel with translucent background, blur effect, and rounded corners.
 * Creates a modern frosted-glass aesthetic.
 */
public class GlassPanel extends JPanel {

    private int cornerRadius = UIStyles.CARD_BORDER_RADIUS;
    private float alpha = 0.15f;
    private Color glassColor = Color.WHITE;
    private Color borderColor = new Color(255, 255, 255, 60);
    private boolean showBorder = true;
    private boolean hasShadow = true;

    public GlassPanel() {
        setOpaque(false);
    }

    public GlassPanel(int radius, float alpha, Color glassColor) {
        this.cornerRadius = radius;
        this.alpha = alpha;
        this.glassColor = glassColor;
        setOpaque(false);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public void setGlassAlpha(float alpha) {
        this.alpha = alpha;
        repaint();
    }

    public void setGlassColor(Color color) {
        this.glassColor = color;
        repaint();
    }

    public void setShowBorder(boolean show) {
        this.showBorder = show;
        repaint();
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
        int height = getHeight();

        // Shadow
        if (hasShadow) {
            g2d.setColor(new Color(0, 0, 0, 20));
            g2d.fillRoundRect(3, 5, width - 3, height - 3, cornerRadius, cornerRadius);
        }

        // Glass background
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2d.setColor(glassColor);
        g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

        // Border
        if (showBorder) {
            g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
            g2d.setColor(borderColor);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(0, 0, width - 1, height - 1, cornerRadius, cornerRadius);
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 200);
    }

    /**
     * Creates a glass panel with a specific background blur effect.
     */
    public static JPanel createGlassCard(int width, int height) {
        GlassPanel panel = new GlassPanel();
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
}
