package minesweeper.logic.square;

public class Bomb extends Square
{

    public boolean isBomb()
    {
        return true;
    }

    public String toString()
    {
        return (this.isDug() ? "\u001B[35m" : "\u001B[31m") + "[*]" + "\u001B[0m";
    }

}
