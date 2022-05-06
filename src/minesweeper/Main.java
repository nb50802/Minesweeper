package minesweeper;

import minesweeper.logic.Minesweeper;
import minesweeper.logic.Difficulty;

public class Main
{

    public static void main(String[] args)
    {
        Minesweeper b = new Minesweeper(Difficulty.MEDIUM);
        b.populateBoard(0, 0);
        System.out.println(b);
    }

}
