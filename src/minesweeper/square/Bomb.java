package minesweeper.square;

import minesweeper.Theme;

public class Bomb extends Square
{

    /**
     * Bomb constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Bomb(int x, int y)
    {
        super(x, y);
    }

    /**
     * Implementation of abstract method of Square
     * Returns that Bomb object is a bomb
     * @return true
     */
    public boolean isBomb()
    {
        return true;
    }

    /**
     * Dig Bomb object and set label to bomb
     */
    public void dig()
    {
        super.dig();
        this.setText("⚫");
        this.setTextFill(Theme.BOMB);
    }

    /**
     * String representation of Bomb
     * @return [*]
     */
    public String toString()
    {
        return (this.isDug() ? "\u001B[35m" : "\u001B[31m") + "[*]" + "\u001B[0m";
    }

}
