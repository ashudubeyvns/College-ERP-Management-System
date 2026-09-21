package attendancemgt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A modern chart panel that supports bar charts and pie charts.
 * Uses pure Java2D - no external libraries.
 */
public class ChartPanel extends JPanel {

    public enum ChartType {
        BAR_VERTICAL, BAR_HORIZONTAL, PIE, LINE
    }

    private ChartType chartType = ChartType.BAR_VERTICAL;
    private String title = "";
    private List<String> labels = new ArrayList<>();
    private List<Double> values = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();
    private boolean showValues = true;
    private boolean animated = true;
    private double animationProgress = 1.0;
    private Timer animationTimer;

    // Color palette
    private static final Color[] DEFAULT_COLORS = {
        UIStyles.PRIMARY,
        UIStyles.SUCCESS,
        UIStyles.WARNING,
        UIStyles.DANGER,
        new Color(139, 92, 246),  // Purple
        new Color(236, 72, 153),  // Pink
        new Color(20, 184, 166),  // Teal
        new Color(249, 115, 22),  // Orange
    };

    public ChartPanel(String title, ChartType type) {
        this.title = title;
        this.chartType = type;
        setOpaque(false);
        setPreferredSize(new Dimension(400, 280));
    }

    public ChartPanel(String title, ChartType type, String[] labels, double[] values) {
        this(title, type);
        setData(labels, values);
    }

    /**
     * Set chart data.
     */
    public void setData(String[] labels, double[] values) {
        this.labels.clear();
        this.values.clear();
        this.colors.clear();

        for (int i = 0; i < labels.length && i < values.length; i++) {
            this.labels.add(labels[i]);
            this.values.add(values[i]);
            this.colors.add(DEFAULT_COLORS[i % DEFAULT_COLORS.length]);
        }

        if (animated) {
            startAnimation();
        } else {
            animationProgress = 1.0;
        }
        repaint();
    }

    /**
     * Add a single data point.
     */
    public void addData(String label, double value, Color color) {
        labels.add(label);
        values.add(value);
        colors.add(color != null ? color : DEFAULT_COLORS[(labels.size() - 1) % DEFAULT_COLORS.length]);
        repaint();
    }

    /**
     * Start animation.
     */
    public void startAnimation() {
        animationProgress = 0.0;
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        animationTimer = new Timer(16, e -> {
            animationProgress += 0.05;
            if (animationProgress >= 1.0) {
                animationProgress = 1.0;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }

    /**
     * Enable/disable animation.
     */
    public void setAnimated(boolean animated) {
        this.animated = animated;
        if (!animated) {
            animationProgress = 1.0;
            repaint();
        }
    }

    /**
     * Set show values on bars.
     */
    public void setShowValues(boolean show) {
        this.showValues = show;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
        int height = getHeight();
        int padding = 40;
        int titleHeight = 30;

        // Draw title
        g2d.setFont(UIStyles.FONT_BOLD_LG);
        g2d.setColor(UIStyles.TEXT_DARK);
        FontMetrics fm = g2d.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        g2d.drawString(title, titleX, 22);

        int chartY = padding + titleHeight;
        int chartH = height - chartY - padding;
        int chartW = width - 2 * padding;

        if (labels.isEmpty() || values.isEmpty()) {
            g2d.setFont(UIStyles.FONT_PLAIN_LG);
            g2d.setColor(UIStyles.TEXT_MUTED);
            String msg = "No data available";
            fm = g2d.getFontMetrics();
            g2d.drawString(msg, (width - fm.stringWidth(msg)) / 2, height / 2);
            g2d.dispose();
            return;
        }

        switch (chartType) {
            case BAR_VERTICAL:
                drawVerticalBarChart(g2d, padding, chartY, chartW, chartH);
                break;
            case BAR_HORIZONTAL:
                drawHorizontalBarChart(g2d, padding, chartY, chartW, chartH);
                break;
            case PIE:
                drawPieChart(g2d, padding, chartY, chartW, chartH);
                break;
            case LINE:
                drawLineChart(g2d, padding, chartY, chartW, chartH);
                break;
        }

        g2d.dispose();
    }

    private void drawVerticalBarChart(Graphics2D g2d, int x, int y, int w, int h) {
        int n = labels.size();
        if (n == 0) return;

        double maxVal = 0;
        for (double v : values) maxVal = Math.max(maxVal, v);
        if (maxVal == 0) maxVal = 1;

        int barGap = 15;
        int barAreaW = w - 20;
        int barW = (barAreaW - (n - 1) * barGap) / n;
        if (barW < 15) barW = 15;

        int totalBarsW = n * barW + (n - 1) * barGap;
        int startX = x + (w - totalBarsW) / 2;

        // Y-axis line
        g2d.setColor(new Color(203, 213, 225));
        g2d.drawLine(startX - 5, y, startX - 5, y + h);

        for (int i = 0; i < n; i++) {
            double val = values.get(i);
            double barH = (val / maxVal) * (h - 30) * animationProgress;
            int bx = startX + i * (barW + barGap);
            int by = y + h - 10 - (int) barH;

            // Bar with rounded top
            g2d.setColor(colors.get(i % colors.size()));
            g2d.fillRoundRect(bx, by, barW, (int) barH, 6, 6);

            // Value on top
            if (showValues) {
                g2d.setFont(UIStyles.FONT_BOLD_SM);
                g2d.setColor(colors.get(i % colors.size()));
                String valStr = formatValue(val);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(valStr, bx + (barW - fm.stringWidth(valStr)) / 2, by - 5);
            }

            // Label below
            g2d.setFont(UIStyles.FONT_PLAIN_XS);
            g2d.setColor(UIStyles.TEXT_MUTED);
            String label = labels.get(i);
            FontMetrics fm2 = g2d.getFontMetrics();
            int labelW = fm2.stringWidth(label);
            int labelX = bx + (barW - labelW) / 2;
            g2d.drawString(label, labelX, y + h + 15);
        }
    }

    private void drawHorizontalBarChart(Graphics2D g2d, int x, int y, int w, int h) {
        int n = labels.size();
        if (n == 0) return;

        double maxVal = 0;
        for (double v : values) maxVal = Math.max(maxVal, v);
        if (maxVal == 0) maxVal = 1;

        int barGap = 8;
        int barAreaH = h - 20;
        int barH = (barAreaH - (n - 1) * barGap) / n;
        if (barH < 18) barH = 18;

        // Find max label width
        g2d.setFont(UIStyles.FONT_PLAIN_SM);
        FontMetrics fm = g2d.getFontMetrics();
        int maxLabelW = 0;
        for (String lbl : labels) {
            maxLabelW = Math.max(maxLabelW, fm.stringWidth(lbl));
        }
        int labelX = x + 5;
        int barStartX = labelX + maxLabelW + 15;

        int totalBarsH = n * barH + (n - 1) * barGap;
        int startY = y + (h - totalBarsH) / 2;

        for (int i = 0; i < n; i++) {
            int by = startY + i * (barH + barGap);
            double val = values.get(i);
            int barW = (int) ((val / maxVal) * (w - barStartX + x - 20) * animationProgress);

            // Label
            g2d.setFont(UIStyles.FONT_PLAIN_SM);
            g2d.setColor(UIStyles.TEXT_DARK);
            g2d.drawString(labels.get(i), labelX, by + barH - 5);

            // Bar
            g2d.setColor(colors.get(i % colors.size()));
            g2d.fillRoundRect(barStartX, by, Math.max(barW, 2), barH, 4, 4);

            // Value
            if (showValues) {
                g2d.setFont(UIStyles.FONT_BOLD_SM);
                g2d.setColor(colors.get(i % colors.size()));
                String valStr = formatValue(val);
                g2d.drawString(valStr, barStartX + barW + 5, by + barH - 5);
            }
        }
    }

    private void drawPieChart(Graphics2D g2d, int x, int y, int w, int h) {
        int n = labels.size();
        if (n == 0) return;

        double total = 0;
        for (double v : values) total += v;
        if (total == 0) total = 1;

        int diameter = Math.min(w, h) - 20;
        int pieX = x + 20;
        int pieY = y + (h - diameter) / 2;

        double startAngle = 90;
        int legendX = x + w - 120;
        int legendY = y + 10;

        // Draw legend
        g2d.setFont(UIStyles.FONT_PLAIN_SM);
        FontMetrics legendFm = g2d.getFontMetrics();
        int ly = legendY;
        for (int i = 0; i < n; i++) {
            g2d.setColor(colors.get(i % colors.size()));
            g2d.fillRect(legendX, ly, 12, 12);
            g2d.setColor(UIStyles.TEXT_DARK);
            String legendText = labels.get(i) + " (" + formatValue(values.get(i)) + ")";
            g2d.drawString(legendText, legendX + 18, ly + 10);
            ly += 22;
        }

        // Draw pie slices
        for (int i = 0; i < n; i++) {
            double angle = (values.get(i) / total) * 360 * animationProgress;
            if (angle > 0) {
                g2d.setColor(colors.get(i % colors.size()));
                g2d.fillArc(pieX, pieY, diameter, diameter, (int) startAngle, (int) Math.max(angle, 1));
                startAngle += angle;
            }
        }

        // Center circle for donut effect
        int centerDiameter = diameter * 40 / 100;
        g2d.setColor(getParent() != null ? getParent().getBackground() : Color.WHITE);
        g2d.fillOval(pieX + (diameter - centerDiameter) / 2,
                     pieY + (diameter - centerDiameter) / 2,
                     centerDiameter, centerDiameter);

        // Center text
        g2d.setFont(UIStyles.FONT_BOLD_2XL);
        g2d.setColor(UIStyles.TEXT_DARK);
        FontMetrics totalFm = g2d.getFontMetrics();
        String centerText = formatValue(total);
        g2d.drawString(centerText,
            pieX + (diameter - totalFm.stringWidth(centerText)) / 2,
            pieY + diameter / 2 + totalFm.getHeight() / 3);
    }

    private void drawLineChart(Graphics2D g2d, int x, int y, int w, int h) {
        int n = labels.size();
        if (n < 2) {
            drawVerticalBarChart(g2d, x, y, w, h);
            return;
        }

        double maxVal = 0;
        for (double v : values) maxVal = Math.max(maxVal, v);
        if (maxVal == 0) maxVal = 1;

        int graphH = h - 40;
        int graphW = w - 30;

        // Draw axes
        g2d.setColor(new Color(203, 213, 225));
        g2d.drawLine(x + 5, y, x + 5, y + graphH);
        g2d.drawLine(x + 5, y + graphH, x + graphW, y + graphH);

        // Calculate points
        int[] px = new int[n];
        int[] py = new int[n];
        for (int i = 0; i < n; i++) {
            px[i] = x + 5 + (i * (graphW - 10)) / (n - 1);
            py[i] = y + graphH - (int) ((values.get(i) / maxVal) * (graphH - 10) * animationProgress);
        }

        // Fill area under line
        int[] fillX = new int[n + 2];
        int[] fillY = new int[n + 2];
        fillX[0] = px[0];
        fillY[0] = y + graphH;
        for (int i = 0; i < n; i++) {
            fillX[i + 1] = px[i];
            fillY[i + 1] = py[i];
        }
        fillX[n + 1] = px[n - 1];
        fillY[n + 1] = y + graphH;

        g2d.setColor(UIStyles.withAlpha(UIStyles.PRIMARY, 30));
        g2d.fillPolygon(fillX, fillY, n + 2);

        // Draw line
        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(UIStyles.PRIMARY);
        for (int i = 0; i < n - 1; i++) {
            g2d.drawLine(px[i], py[i], px[i + 1], py[i + 1]);
        }

        // Draw points
        for (int i = 0; i < n; i++) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(px[i] - 4, py[i] - 4, 8, 8);
            g2d.setColor(UIStyles.PRIMARY);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawOval(px[i] - 4, py[i] - 4, 8, 8);
        }

        // Labels
        g2d.setFont(UIStyles.FONT_PLAIN_XS);
        g2d.setColor(UIStyles.TEXT_MUTED);
        FontMetrics fm = g2d.getFontMetrics();
        for (int i = 0; i < n; i++) {
            String label = labels.get(i);
            int lx = px[i] - fm.stringWidth(label) / 2;
            g2d.drawString(label, lx, y + graphH + 18);
        }

        // Values
        if (showValues) {
            g2d.setFont(UIStyles.FONT_BOLD_SM);
            g2d.setColor(UIStyles.PRIMARY);
            for (int i = 0; i < n; i++) {
                String valStr = formatValue(values.get(i));
                fm = g2d.getFontMetrics();
                g2d.drawString(valStr, px[i] - fm.stringWidth(valStr) / 2, py[i] - 10);
            }
        }
    }

    private String formatValue(double value) {
        if (value == (long) value) {
            return String.valueOf((long) value);
        }
        return String.format("%.1f", value);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450, 300);
    }
}
