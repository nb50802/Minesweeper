package minesweeper.logic.square;

import javafx.scene.control.Label;

public class Bomb extends Square
{

    public Bomb(int x, int y)
    {
        super(x, y);
    }

    public boolean isBomb()
    {
        return true;
    }

    public void dig()
    {
        super.dig();
        this.label.setText("*");
    }

    public String toString()
    {
        return (this.isDug() ? "\u001B[35m" : "\u001B[31m") + "[*]" + "\u001B[0m";
    }

}
