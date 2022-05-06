package minesweeper.logic;

import java.io.Serializable;

public record Difficulty(String name, int bombs, int x, int y) implements Serializable
{

    public static final Difficulty EASY = new Difficulty("Easy", 10, 10, 8);
    public static final Difficulty MEDIUM = new Difficulty("Medium", 40, 18, 14);
    public static final Difficulty HARD = new Difficulty("Hard", 99, 24, 20);

    public String toString()
    {
        return this.name;
    }

}
