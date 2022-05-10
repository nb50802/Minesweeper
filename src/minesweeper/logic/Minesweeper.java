package minesweeper.logic;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import minesweeper.logic.square.Bomb;
import minesweeper.logic.square.Land;
import minesweeper.logic.square.Square;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Minesweeper extends GridPane implements Serializable
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
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, this::mousePressed);
        this.populateLand();
        System.out.println(this);
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

    private void populateLand()
    {
        IntStream.range(0, this.difficulty.y())
                .forEach(y -> IntStream.range(0, this.difficulty.x())
                        .forEach(x ->
                        {
                            this.board[y][x] = new Land();
                            this.add(this.board[y][x], x, y);
                            this.board[y][x].setColor((x + y) % 2 == 0 ? Theme.FIELD_DARK : Theme.FIELD_LIGHT);
                        }));
    }

    private void populateBoard(int xSafe, int ySafe)
    {
        var random = new Random();

        for(int i = 0; i < this.difficulty.bombs();)
        {
            int x = random.nextInt(this.difficulty.x()), y = random.nextInt(this.difficulty.y());

            if(!this.board[y][x].isBomb() && Math.abs(xSafe - x) > 1 && Math.abs(ySafe - y) > 1)
            {
                this.board[y][x] = new Bomb();
                this.add(this.board[y][x], x, y);
                this.board[y][x].setColor((x + y) % 2 == 0 ? Theme.FIELD_DARK : Theme.FIELD_LIGHT);
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
        if(this.gameState == GameState.CLEAN)
        {
            this.populateBoard(x, y);
            this.gameState = GameState.DIRTY;
        }

        Square square = this.board[y][x];

        if(!square.isDug() && (this.flags > 0 || square.isFlagged()))
        {
            this.flags += square.isFlagged() ? 1 : -1;
            square.toggleFlag();

            if(Arrays.stream(this.board).flatMap(Arrays::stream).filter(Square::isBomb).allMatch(Square::isFlagged))
            {
                this.gameState = GameState.WON;
                System.out.println("won");
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

        if(!square.isFlagged())
        {
            square.dig(x, y);

            if(square.isBomb())
            {
                this.gameState = GameState.LOST;
                System.out.println("lost");
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
    }

    private void uncoverSurroundings(int x, int y)
    {
        this.getNeighbors(x, y).forEach((pos, square) ->
        {
            Land land = (Land) square;

            if(!land.isDug())
            {
                land.dig(pos[0], pos[1]);

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

    public void mousePressed(MouseEvent event)
    {
        Node clickedNode = event.getPickResult().getIntersectedNode();

        if(clickedNode instanceof Text)
        {
            clickedNode = clickedNode.getParent();
        }

        if(clickedNode != this)
        {
            int x = GridPane.getColumnIndex(clickedNode);
            int y = GridPane.getRowIndex(clickedNode);

            if(event.isPrimaryButtonDown())
            {
                this.dig(x, y);
            }
            else if(event.isSecondaryButtonDown())
            {
                this.flag(x, y);
            }
        }

        System.out.println(this);
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
