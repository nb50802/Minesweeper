package minesweeper.panes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import minesweeper.Difficulty;
import minesweeper.GameState;
import minesweeper.Main;
import minesweeper.Util;
import minesweeper.dialogs.GameOverDialog;
import minesweeper.square.Bomb;
import minesweeper.square.Land;
import minesweeper.square.Square;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Board extends GridPane implements Serializable
{

    private final Square[][] board;
    private final Difficulty difficulty;
    private int flags;
    private GameState gameState = GameState.CLEAN;

    /**
     * Board constructor
     * @param difficulty game difficulty
     */
    public Board(Difficulty difficulty)
    {
        this.board = new Square[difficulty.y()][difficulty.x()];
        this.difficulty = difficulty;
        this.flags = difficulty.bombs();
        this.populateLand();
        System.out.println(this);
    }

    /**
     * Returns the array representing the board
     * @return double array of Square objects
     */
    public Square[][] getBoard()
    {
        return this.board;
    }

    /**
     * Returns the game difficulty
     * @return difficulty
     */
    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    /**
     * Returns the number of flags left
     * @return number of flags
     */
    public int getFlags()
    {
        return this.flags;
    }

    /**
     * Returns the game state
     * @return GameState object
     */
    public GameState getGameState()
    {
        return this.gameState;
    }

    /**
     * Loads Square objects in array to the pane if Board is read in from file
     */
    public void loadArray()
    {
        Util.doubleIntSupplier(0, this.difficulty.x(), 0, this.difficulty.y(),
                (x, y) -> this.add(this.board[y][x], x, y));
    }

    /**
     * Populates land on board
     */
    private void populateLand()
    {
        Util.doubleIntSupplier(0, this.difficulty.x(), 0, this.difficulty.y(),
                (x, y) -> this.add(this.board[y][x] = new Land(x, y), x, y));
    }

    /**
     * Populates bombs on board; assures first move will never be a bomb
     * @param xSafe first move x coordinate
     * @param ySafe first move y coordinate
     */
    private void populateBombs(int xSafe, int ySafe)
    {
        var random = new Random();

        for(int i = 0; i < this.difficulty.bombs();)
        {
            int x = random.nextInt(this.difficulty.x()), y = random.nextInt(this.difficulty.y());

            if(!this.board[y][x].isBomb() && Math.abs(xSafe - x) > 1 && Math.abs(ySafe - y) > 1)
            {
                this.add(this.board[y][x] = new Bomb(x, y), x, y);
                this.getNeighbors(this.board[y][x]).stream()
                        .filter(Predicate.not(Square::isBomb))
                        .map(Land.class::cast)
                        .forEach(Land::incrementNearbyBombs);

                i++;
            }
        }
    }

    /**
     * Flag element on board
     * @param x x coordinate of square to be flagged
     * @param y y coordinate of square to be flagged
     */
    public void flag(int x, int y)
    {
        if(this.gameState == GameState.CLEAN)
        {
            this.populateBombs(x, y);
            this.gameState = GameState.DIRTY;
        }

        Square square = this.board[y][x];

        if(!square.isDug() && (this.flags > 0 || square.isFlagged()))
        {
            this.flags += square.isFlagged() ? 1 : -1;
            Main.MAIN_PANE.getHeader().updateFlags(this.flags);
            square.toggleFlag();
        }
    }

    /**
     * Dig element on board
     * @param x x coordinate of square to be dug
     * @param y y coordinate of square to be dug
     */
    public void dig(int x, int y)
    {
        if(this.gameState == GameState.CLEAN)
        {
            this.populateBombs(x, y);
            this.gameState = GameState.DIRTY;
        }

        Square square = this.board[y][x];

        if(!square.isFlagged())
        {
            square.dig();

            if(square.isBomb())
            {
                this.lose();
            }
            else
            {
                Land land = (Land) square;

                if(land.getNearbyBombs() == 0)
                {
                    this.uncoverSurroundings(land);
                }

                if(Util.flatMap(this.board).filter(Predicate.not(Square::isBomb)).allMatch(Square::isDug))
                {
                    this.win();
                }
            }
        }
    }

    /**
     * Uncovers (digs) neighboring squares if they are not a bomb
     * @param landDug Land object with 0 surrounding bombs
     */
    private void uncoverSurroundings(Land landDug)
    {
        assert landDug.getNearbyBombs() == 0;

        this.getNeighbors(landDug).forEach(square ->
        {
            Land land = (Land) square;

            if(!land.isDug())
            {
                land.dig();

                if(land.isFlagged())
                {
                    land.toggleFlag();
                    Main.MAIN_PANE.getHeader().updateFlags(++this.flags);
                }

                if(land.getNearbyBombs() == 0)
                {
                    this.uncoverSurroundings(land);
                }
            }
        });
    }

    /**
     * Returns up to 8 surrounding Square elements; does not include self/center
     * @param square Square object of which to find neighboring elements
     * @return list of surrounding Square elements
     */
    private ArrayList<Square> getNeighbors(Square square)
    {
        var neighbors = new ArrayList<Square>();

        Util.doubleIntSupplier(-1, 2, -1, 2, (i, j) ->
        {
            if(!(i == 0 && j == 0))
            {
                int offsetX = square.getX() + j, offsetY = square.getY() + i;

                if(offsetX >= 0 && offsetX < this.difficulty.x() && offsetY >= 0 && offsetY < this.difficulty.y())
                {
                    neighbors.add(this.board[offsetY][offsetX]);
                }
            }
        });

        return neighbors;
    }

    /**
     * Initiates losing sequence
     */
    public void lose()
    {
        this.gameState = GameState.LOST;

        List<Bomb> bombs = Util.flatMap(this.board)
                .filter(s -> s.isBomb() && !s.isFlagged() && !s.isDug())
                .map(Bomb.class::cast)
                .collect(Collectors.toList());

        Collections.shuffle(bombs);
        final int delay = 2500;
        AtomicInteger rollingDelay = new AtomicInteger();
        bombs.forEach(bomb -> Util.onDelay(rollingDelay.getAndSet(delay / this.difficulty.bombs() + rollingDelay.get()), bomb::dig));
        Util.onDelay(delay + 300, () -> new GameOverDialog(false).show());
    }

    /**
     * Initiates winning sequence
     */
    public void win()
    {
        this.gameState = GameState.WON;
        Util.onDelay( 300, () -> new GameOverDialog(true).show());
    }

    /**
     * Converts board into a multi-line string composed of each element
     * @return string representation of board
     */
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for(Square[] squares : this.board)
        {
            for(Square square : squares)
            {
                builder.append(square).append(" ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }

}
