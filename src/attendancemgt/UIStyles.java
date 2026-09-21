package attendancemgt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Centralized UI Style Constants for the College ERP System.
 * Defines color themes, fonts, and common UI settings.
 */
public class UIStyles {

    // ==================== COLOR THEME ====================
    // Primary Colors
    public static final Color PRIMARY = new Color(37, 99, 235);       // #2563EB - Blue
    public static final Color PRIMARY_DARK = new Color(30, 58, 138);  // #1E3A8A
    public static final Color PRIMARY_LIGHT = new Color(59, 130, 246); // #3B82F6
    public static final Color PRIMARY_GRADIENT_START = new Color(30, 58, 138);   // #1E3A8A
    public static final Color PRIMARY_GRADIENT_MID = new Color(37, 99, 235);     // #2563EB
    public static final Color PRIMARY_GRADIENT_END = new Color(59, 130, 246);    // #3B82F6

    // Background Colors
    public static final Color BG_LIGHT = new Color(248, 250, 252);    // #F8FAFC
    public static final Color BG_DARK = new Color(17, 24, 39);        // #111827
    public static final Color CARD_BG = Color.WHITE;                  // #FFFFFF
    public static final Color CARD_BG_DARK = new Color(31, 41, 55);   // #1F2937

    // Status Colors
    public static final Color SUCCESS = new Color(34, 197, 94);       // #22C55E - Green
    public static final Color DANGER = new Color(239, 68, 68);        // #EF4444 - Red
    public static final Color WARNING = new Color(245, 158, 11);      // #F59E0B - Amber
    public static final Color INFO = new Color(59, 130, 246);         // #3B82F6 - Blue

    // Sidebar Colors
    public static final Color SIDEBAR_BG = new Color(15, 23, 42);     // #0F172A
    public static final Color SIDEBAR_BG_DARK = new Color(8, 14, 26); // #080E1A
    public static final Color SIDEBAR_ACTIVE = new Color(37, 99, 235); // #2563EB
    public static final Color SIDEBAR_HOVER = new Color(30, 58, 138);  // #1E3A8A
    public static final Color SIDEBAR_TEXT = new Color(148, 163, 184); // #94A3B8
    public static final Color SIDEBAR_TEXT_ACTIVE = Color.WHITE;

    // Text Colors
    public static final Color TEXT_DARK = new Color(15, 23, 42);      // #0F172A
    public static final Color TEXT_MUTED = new Color(100, 116, 139);  // #64748B
    public static final Color TEXT_LIGHT = new Color(241, 245, 249);  // #F1F5F9
    public static final Color TEXT_WHITE = Color.WHITE;

    // Table Colors
    public static final Color TABLE_HEADER_BG = new Color(37, 99, 235);  // #2563EB
    public static final Color TABLE_HEADER_TEXT = Color.WHITE;
    public static final Color TABLE_ROW_ALT = new Color(241, 245, 249);  // #F1F5F9
    public static final Color TABLE_ROW = Color.WHITE;
    public static final Color TABLE_BORDER = new Color(226, 232, 240);   // #E2E8F0

    // Top Bar
    public static final Color TOPBAR_BG = Color.WHITE;
    public static final Color TOPBAR_BG_DARK = new Color(17, 24, 39);

    // ==================== FONTS ====================
    public static final String FONT_FAMILY = "Segoe UI";
    public static final String FONT_FAMILY_ALT = "Inter";
    public static final String FONT_FAMILY_MONO = "Consolas";

    // Font Sizes
    public static final int FONT_XS = 10;
    public static final int FONT_SM = 11;
    public static final int FONT_BASE = 12;
    public static final int FONT_MD = 13;
    public static final int FONT_LG = 14;
    public static final int FONT_XL = 16;
    public static final int FONT_2XL = 18;
    public static final int FONT_3XL = 22;
    public static final int FONT_4XL = 28;

    // Pre-defined Fonts
    public static final Font FONT_BOLD_XS = new Font(FONT_FAMILY, Font.BOLD, FONT_XS);
    public static final Font FONT_BOLD_SM = new Font(FONT_FAMILY, Font.BOLD, FONT_SM);
    public static final Font FONT_BOLD_BASE = new Font(FONT_FAMILY, Font.BOLD, FONT_BASE);
    public static final Font FONT_BOLD_MD = new Font(FONT_FAMILY, Font.BOLD, FONT_MD);
    public static final Font FONT_BOLD_LG = new Font(FONT_FAMILY, Font.BOLD, FONT_LG);
    public static final Font FONT_BOLD_XL = new Font(FONT_FAMILY, Font.BOLD, FONT_XL);
    public static final Font FONT_BOLD_2XL = new Font(FONT_FAMILY, Font.BOLD, FONT_2XL);
    public static final Font FONT_BOLD_3XL = new Font(FONT_FAMILY, Font.BOLD, FONT_3XL);
    public static final Font FONT_BOLD_4XL = new Font(FONT_FAMILY, Font.BOLD, FONT_4XL);

    public static final Font FONT_PLAIN_XS = new Font(FONT_FAMILY, Font.PLAIN, FONT_XS);
    public static final Font FONT_PLAIN_SM = new Font(FONT_FAMILY, Font.PLAIN, FONT_SM);
    public static final Font FONT_PLAIN_BASE = new Font(FONT_FAMILY, Font.PLAIN, FONT_BASE);
    public static final Font FONT_PLAIN_MD = new Font(FONT_FAMILY, Font.PLAIN, FONT_MD);
    public static final Font FONT_PLAIN_LG = new Font(FONT_FAMILY, Font.PLAIN, FONT_LG);
    public static final Font FONT_PLAIN_XL = new Font(FONT_FAMILY, Font.PLAIN, FONT_XL);
    public static final Font FONT_PLAIN_2XL = new Font(FONT_FAMILY, Font.PLAIN, FONT_2XL);

    // ==================== DIMENSIONS ====================
    public static final int SIDEBAR_WIDTH = 240;
    public static final int SIDEBAR_COLLAPSED_WIDTH = 60;
    public static final int TOPBAR_HEIGHT = 64;
    public static final int CARD_BORDER_RADIUS = 12;
    public static final int BUTTON_BORDER_RADIUS = 8;
    public static final int INPUT_BORDER_RADIUS = 6;
    public static final int GLASS_BLUR_ALPHA = 180;

    // ==================== BORDERS ====================
    public static final Border BORDER_NONE = BorderFactory.createEmptyBorder();
    public static final Border BORDER_CARD = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
        BorderFactory.createEmptyBorder(16, 20, 16, 20)
    );
    public static final Border BORDER_CARD_DARK = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(55, 65, 81), 1),
        BorderFactory.createEmptyBorder(16, 20, 16, 20)
    );

    // ==================== DARK MODE COLORS ====================
    public static final Color DARK_BG = new Color(17, 24, 39);        // #111827
    public static final Color DARK_SIDEBAR = new Color(8, 14, 26);    // #080E1A
    public static final Color DARK_CARD = new Color(31, 41, 55);      // #1F2937
    public static final Color DARK_TEXT = new Color(243, 244, 246);   // #F3F4F6
    public static final Color DARK_TEXT_MUTED = new Color(156, 163, 175); // #9CA3AF
    public static final Color DARK_TABLE_HEADER = new Color(37, 99, 235);
    public static final Color DARK_TABLE_ROW = new Color(31, 41, 55);
    public static final Color DARK_TABLE_ROW_ALT = new Color(55, 65, 81);
    public static final Color DARK_TABLE_BORDER = new Color(55, 65, 81);
    public static final Color DARK_TOPBAR = new Color(17, 24, 39);
    public static final Color DARK_NAV_TEXT = new Color(148, 163, 184);

    // ==================== SHADOWS ====================
    public static final int SHADOW_SMALL = 2;
    public static final int SHADOW_MEDIUM = 4;
    public static final int SHADOW_LARGE = 8;

    // ==================== HELPERS ====================
    public static Color hexToColor(String hex) {
        try {
            return Color.decode(hex);
        } catch (NumberFormatException e) {
            return PRIMARY;
        }
    }

    public static Color darken(Color color, double factor) {
        int r = (int) Math.max(0, color.getRed() * (1 - factor));
        int g = (int) Math.max(0, color.getGreen() * (1 - factor));
        int b = (int) Math.max(0, color.getBlue() * (1 - factor));
        return new Color(r, g, b, color.getAlpha());
    }

    public static Color lighten(Color color, double factor) {
        int r = (int) Math.min(255, color.getRed() + (255 - color.getRed()) * factor);
        int g = (int) Math.min(255, color.getGreen() + (255 - color.getGreen()) * factor);
        int b = (int) Math.min(255, color.getBlue() + (255 - color.getBlue()) * factor);
        return new Color(r, g, b, color.getAlpha());
    }

    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
