package com.example.project.flowfree;
import javafx.scene.paint.Color;

public class Dot extends ColoredGridItem {
    private boolean isConnected;

    public Dot(Color color) {
        super(color);
    }

    public Dot(Dot dotToCopy, int x, int y) {
        super(dotToCopy.getColor(), x,y);
    }

    @Override
    public String toString() {
        return String.format("Dot(%d, %d, %s)", this.getX(), this.getY(), this.getHexColor());
    }
}
