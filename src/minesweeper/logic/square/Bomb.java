package minesweeper.logic.square;

import minesweeper.logic.Theme;

public class Bomb extends Square
{

    public boolean isBomb()
    {
        return true;
    }

    public void dig(int x, int y)
    {
        super.dig(x, y);
        this.setText("⚫");
        this.setTextFill(Theme.BOMB);
    }

    public String toString()
    {
        return (this.isDug() ? "\u001B[35m" : "\u001B[31m") + "[*]" + "\u001B[0m";
    }

}
