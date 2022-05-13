package minesweeper;

import java.io.Serializable;

/**
 * An enum of game states
 */
public enum GameState implements Serializable
{

    /**
     * CLEAN: player has not interacted with board; no bombs are populated
     * DIRTY: player has interacted with board; bombs are populated
     * LOST: player has dug a bomb and lost the game
     * WON: player has dug all squares that are not bombs
     */
    CLEAN, DIRTY, LOST, WON;

    /**
     * Returns whether the game is over
     * @return true if game is in progress, false if game is lost or won
     */
    public boolean isOver()
    {
        return this == LOST || this == WON;
    }

}
