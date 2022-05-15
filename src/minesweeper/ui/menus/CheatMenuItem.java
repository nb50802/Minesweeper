package minesweeper.ui.menus;

import javafx.scene.control.MenuItem;
import minesweeper.game.Difficulty;
import minesweeper.Main;
import minesweeper.Util;
import minesweeper.game.Board;
import minesweeper.game.square.Square;

public class CheatMenuItem extends MenuItem
{

    /**
     * Constructor for the cheat menu item
     * Digs all land and flags all bombs
     * Meant for demo purposes
     */
    public CheatMenuItem()
    {
        super("Cheat");
        this.setOnAction(event ->
        {
            Board board = Main.MAIN_PANE.getBoard();
            Difficulty difficulty = board.getDifficulty();
            Util.doubleIntSupplier(0, difficulty.x(), 0, difficulty.y(), (x, y) ->
            {
                Square square = board.getBoard()[y][x];

                if(square.isFlagged())
                {
                    board.flag(x, y);
                }

                if(square.isBomb())
                {
                    board.flag(x, y);
                }
                else
                {
                    board.dig(x, y);
                }
            });
        });
    }

}
