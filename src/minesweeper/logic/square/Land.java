package minesweeper.logic.square;

public class Land extends Square
{

    private int nearbyBombs;

    public boolean isBomb()
    {
        return false;
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
        return "[" + this.nearbyBombs + "]";
    }

}
