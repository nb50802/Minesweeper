package minesweeper.logic.square;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;

public class Land extends Square
{

    private int nearbyBombs;

    public Land(int x, int y)
    {
        super(x, y);
    }

    public boolean isBomb()
    {
        return false;
    }

    public void dig()
    {
        super.dig();
        this.label.setText(String.valueOf(this.nearbyBombs));
    }

    public int getNearbyBombs()
    {
        return this.nearbyBombs;
    }

    public void incrementNearbyBombs()
    {
        this.nearbyBombs++;
    }

    public String toString()
    {
        return (this.isDug() ? "\u001B[37m" : "\u001B[32m") + "[" + this.nearbyBombs + "]" + "\u001B[0m";
    }

}
