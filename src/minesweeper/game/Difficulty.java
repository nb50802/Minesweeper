package minesweeper.game;

import java.io.Serializable;

/**
 * Difficulty constructor
 * A record to store constants pertaining to each difficulty
 * @param name name of difficulty
 * @param bombs number of bombs
 * @param x number of horizontal squares
 * @param y number of vertical squares
 */
public record Difficulty(String name, int bombs, int x, int y) implements Serializable
{

    public static final Difficulty EASY = new Difficulty("Easy", 10, 10, 8);
    public static final Difficulty MEDIUM = new Difficulty("Medium", 40, 18, 14);
    public static final Difficulty HARD = new Difficulty("Hard", 99, 24, 20);

    /**
     * Returns the difficulty name
     * @return difficulty name
     */
    public String toString()
    {
        return this.name;
    }

}
