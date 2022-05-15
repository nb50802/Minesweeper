package minesweeper.game.square;

import javafx.scene.control.Label;
import minesweeper.ui.Theme;

public class Land extends Square
{

    private int nearbyBombs;

    /**
     * Land constructor
     * @param x x coordinate
     * @param y y coordinate
     */
    public Land(int x, int y)
    {
        super(x, y);
    }

    /**
     * Implementation of abstract method of Square
     * Returns that Land object is not a bomb
     * @return false
     */
    public boolean isBomb()
    {
        return false;
    }

    /**
     * Returns the number of nearby bombs in diagonally adjacent cells
     * @return number of nearby bombs
     */
    public int getNearbyBombs()
    {
        return this.nearbyBombs;
    }

    /**
     * Method called to increment the integer value of nearby bombs when board is populated
     */
    public void incrementNearbyBombs()
    {
        this.nearbyBombs++;
    }

    /**
     * Updates label to display the number of nearby bombs
     * @param label label object representing Land
     */
    public void update(Label label)
    {
        super.update(label);

        if(this.isDug() && this.nearbyBombs != 0)
        {
            label.setText(String.valueOf(this.nearbyBombs));
            label.setTextFill(this.nearbyBombs >= Theme.NEARBY.length ? Theme.NEARBY[Theme.NEARBY.length - 1] : Theme.NEARBY[this.nearbyBombs - 1]);
        }
    }

    /**
     * String representation of Land
     * @return [${nearbyBombs}]
     */
    public String toString()
    {
        return (this.isDug() ? "\u001B[37m" : "\u001B[32m") + "[" + this.nearbyBombs + "]" + "\u001B[0m";
    }

}
