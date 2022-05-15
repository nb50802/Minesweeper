package minesweeper.ui.menus;

import javafx.scene.control.Menu;

public class GameMenu extends Menu
{

    /**
     * Constructor for game menu
     */
    public GameMenu()
    {
        super("Game");
        this.getItems().addAll(new RestartMenuItem(), new DifficultyMenu(), new CheatMenuItem());
    }

}
