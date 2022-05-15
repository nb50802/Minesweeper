package minesweeper.game.square;

import javafx.scene.control.Label;
import minesweeper.ui.Theme;

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
     * Updates label to display bomb
     * @param label label object representing Bomb
     */
    public void update(Label label)
    {
        super.update(label);

        if(this.isDug())
        {
            label.setText("⚫");
            label.setTextFill(Theme.BOMB);
        }
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
