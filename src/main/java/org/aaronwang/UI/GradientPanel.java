package org.aaronwang.UI;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    public static final int DIAGONAL_FILL = 0;
    public static final int HORIZONTAL_FILL = 1;
    public static final int VERTICAL_FILL = 2;
    private final Color color1, color2;
    private final int orientation;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int height = this.getHeight();
        int width = this.getWidth();

        switch(orientation) {
            case 0:
                g2d.setPaint(new GradientPaint(0, 0, color1, width, height, color2));
                break;
            case 1:
                g2d.setPaint(new GradientPaint(0, height/2f, color1, width, height/2f, color2));
                break;
            case 2:
                g2d.setPaint(new GradientPaint(width/2f, 0, color1, width/2f, height, color2));
                break;
            default:
                throw new IllegalArgumentException("Illegal orientation: must be 0, 1, or 2.");
        }
        g2d.fillRect(0, 0, width, height);
    }
    public GradientPanel(Color c1, Color c2, int orientation) {
        this.color1 = c1;
        this.color2 = c2;
        this.orientation = orientation;
    }
}
