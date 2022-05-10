package minesweeper.logic.square;

import minesweeper.logic.Theme;

public class Land extends Square
{

    private int nearbyBombs;

    public boolean isBomb()
    {
        return false;
    }

    public void dig(int x, int y)
    {
        super.dig(x, y);

        if(this.nearbyBombs != 0)
        {
            this.setText(String.valueOf(this.nearbyBombs));
            this.setTextFill(this.nearbyBombs >= Theme.NEARBY.length ? Theme.NEARBY[Theme.NEARBY.length - 1] : Theme.NEARBY[this.nearbyBombs - 1]);
        }
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
