package ui;

import javax.swing.*;

import model.AnimeType;

import java.awt.*;
import java.util.Map;

//displays a bar chart of how many times each AnimeType appears.
public class ChartPanel extends JPanel {
    private static final int LEFT = 50;
    private static final int BOTTOM = 50;
    private static final int TOP = 20;
    private static final int RIGHT = 20;
    private static final int BAR_W = 30;
    private static final int GAP = 20;
    private Map<AnimeType, Integer> counts;
    private int maxCount;

    // REQUIRES: c != null
    // MODIFIES:this
    // EFFECTS:Constructs a chart panel given a map of AnimeType -> count.
    public ChartPanel(Map<AnimeType, Integer> c) {
        counts = c;
        maxCount = 0;
        for (int v : counts.values()) {
            if (v > maxCount) {
                maxCount = v;
            }
        }
        setPreferredSize(new Dimension(400, 300));
    }

    // MODIFIES: g
    // EFFECTS:Paints the bar chart, including axes and bars for each AnimeType
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth() - LEFT - RIGHT;
        int h = getHeight() - TOP - BOTTOM;
        int yyy = getHeight() - BOTTOM;
        g.setColor(Color.BLACK);
        g.drawLine(LEFT, yyy, LEFT + w, yyy); // x-axis
        g.drawLine(LEFT, yyy, LEFT, TOP); // y-axis
        drawYAxisTicks(g, w, h, yyy);
        drawBars(g, w, h, yyy);
    }

    // MODIFIES:g
    // EFFECTS:Draws tick marks and numeric labels along the y-axis
    private void drawYAxisTicks(Graphics g, int w, int h, int yy) {
        for (int j = 0; j <= maxCount; j++) {
            int tickY = yy - (maxCount == 0 ? 0 : j * h / maxCount);
            g.drawLine(LEFT - 5, tickY, LEFT, tickY);
            g.drawString("" + j, LEFT - 25, tickY + 5);
        }
    }

    // MODIFIES:g
    // EFFECTS:Draws each bar for the corresponding AnimeType and label the count on
    // the top of bar
    private void drawBars(Graphics g, int w, int h, int base) {
        int i = 0;
        for (AnimeType t : counts.keySet()) {
            int val = counts.get(t);
            int barH = maxCount == 0 ? 0 : val * h / maxCount;
            int x = LEFT + i * (BAR_W + GAP);
            int y = base - barH;
            g.setColor(Color.BLUE);
            g.fillRect(x, y, BAR_W, barH);
            g.setColor(Color.BLACK);
            g.drawString("" + val, x + (BAR_W / 2), y - 5);
            g.drawString(t.name(), x, base + 15);
            i++;
        }
    }
}
