package minesweeper.ui.menus;

import javafx.scene.control.MenuItem;
import minesweeper.Main;

public class RestartMenuItem extends MenuItem
{

    /**
     * Constructor for restart menu item
     * Resets the board to a new game
     */
    public RestartMenuItem()
    {
        super("Restart");
        this.setOnAction(event -> Main.MAIN_PANE.newBoard());
    }

}
