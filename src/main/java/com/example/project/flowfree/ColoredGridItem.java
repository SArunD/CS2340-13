package com.example.project.flowfree;

import javafx.scene.paint.Color;

public class ColoredGridItem extends GridItem {
    private Color color;

    public ColoredGridItem(Color color) {
        this.color = color;
    }
    public ColoredGridItem(Color color, int x, int y) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() { return this.color; }
    public void setColor(Color color) { this.color = color; }

    public String getHexColor() {
        // Source: https://stackoverflow.com/a/18803814/4375502
        return color == null ? "" : String.format( "#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    @Override
    public String toString() {
        return String.format("ColoredGridItem(%d, %d, %s)", this.getX(), this.getY(), this.getHexColor());
    }
}
