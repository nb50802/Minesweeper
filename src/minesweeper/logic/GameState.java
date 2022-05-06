package minesweeper.logic;

import java.io.Serializable;

public enum GameState implements Serializable
{

    CLEAN, DIRTY, LOST, WON;

    public boolean isOver()
    {
        return this == LOST || this == WON;
    }

}
