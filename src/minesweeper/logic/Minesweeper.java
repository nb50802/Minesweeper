package minesweeper.logic;

import minesweeper.logic.square.Bomb;
import minesweeper.logic.square.Land;
import minesweeper.logic.square.Square;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Minesweeper implements Serializable
{

    private final Square[][] board;
    private final Difficulty difficulty;
    private int flags;
    private GameState gameState = GameState.CLEAN;

    public Minesweeper(Difficulty difficulty)
    {
        this.board = new Square[difficulty.y()][difficulty.x()];
        this.difficulty = difficulty;
        this.flags = difficulty.bombs();
    }

    public Square[][] getBoard()
    {
        return this.board;
    }

    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    public int getFlags()
    {
        return this.flags;
    }

    public GameState getGameState()
    {
        return this.gameState;
    }

    public void populateBoard(int xSafe, int ySafe)
    {
        IntStream.range(0, this.difficulty.y())
                .forEach(y -> IntStream.range(0, this.difficulty.x())
                        .forEach(x -> this.board[y][x] = new Land()));

        var random = new Random();

        for(int i = 0; i < this.difficulty.bombs();)
        {
            int x = random.nextInt(this.difficulty.x()), y = random.nextInt(this.difficulty.y());

            if(!this.board[y][x].isBomb() && x != xSafe && y != ySafe)
            {
                this.board[y][x] = new Bomb();
                this.getNeighbors(x, y).values().stream()
                        .filter(Predicate.not(Square::isBomb))
                        .map(Land.class::cast)
                        .forEach(Land::incrementNearbyBombs);
                i++;
            }
        }
    }

    public void flag(int x, int y)
    {
        Square square = this.board[y][x];

        if(this.flags > 0 || square.isFlagged())
        {
            this.flags += square.isFlagged() ? 1 : -1;
            square.toggleFlag();

            if(Arrays.stream(this.board).flatMap(Arrays::stream).filter(Square::isBomb).allMatch(Square::isFlagged))
            {
                this.gameState = GameState.WON;

                //...
            }
        }
    }

    public void dig(int x, int y)
    {
        if(this.gameState == GameState.CLEAN)
        {
            this.populateBoard(x, y);
            this.gameState = GameState.DIRTY;
        }

        Square square = this.board[y][x];
        square.dig();

        if(square.isBomb())
        {
            this.gameState = GameState.LOST;

            //...
        }
        else
        {
            Land land = (Land) square;

            if(land.getNearbyBombs() == 0)
            {
                this.uncoverSurroundings(x, y);
            }
        }
    }

    private void uncoverSurroundings(int x, int y)
    {
        this.getNeighbors(x, y).forEach((pos, square) ->
        {
            Land land = (Land) square;

            if(!land.isDug())
            {
                land.dig();

                if(land.isFlagged())
                {
                    land.toggleFlag();
                    this.flags++;
                }

                if(land.getNearbyBombs() == 0)
                {
                    this.uncoverSurroundings(pos[0], pos[1]);
                }
            }
        });
    }

    private HashMap<int[], Square> getNeighbors(int x, int y)
    {
        var neighbors = new HashMap<int[], Square>();

        for(int i = -1; i <= 1; i++)
        {
            for(int j = -1; j <= 1; j++)
            {
                if(!(i == 0 && j == 0))
                {
                    int offsetX = x + j, offsetY = y + i;

                    if(offsetX >= 0 && offsetX < this.difficulty.x() && offsetY >= 0 && offsetY < this.difficulty.y())
                    {
                        neighbors.put(new int[]{offsetX, offsetY}, this.board[offsetY][offsetX]);
                    }
                }
            }
        }

        return neighbors;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < this.board.length; i++)
        {
            for(int j = 0; j < this.board[i].length; j++)
            {
                builder.append(this.board[i][j]).append(" ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }

}
