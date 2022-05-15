package minesweeper.ui.menus;

import javafx.scene.control.*;
import minesweeper.game.Difficulty;
import minesweeper.game.GameState;
import minesweeper.Main;
import minesweeper.game.Board;

import java.util.List;

public class DifficultyMenu extends Menu
{

    /**
     * Constructor for the difficulty menu
     */
    public DifficultyMenu()
    {
        super("Difficulty");

        List.of(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD).forEach(difficulty ->
        {
            MenuItem item = new MenuItem(difficulty.name());
            item.setOnAction(event -> this.changeDifficulty(difficulty));
            this.getItems().add(item);
        });
    }

    /**
     * Change board to new difficulty, asks user for confirmation if game is in progress
     * @param difficulty game difficulty
     */
    private void changeDifficulty(Difficulty difficulty)
    {
        GameState gameState = Main.MAIN_PANE.getBoard().getGameState();

        if(gameState == GameState.DIRTY)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to end this game?");
            alert.getButtonTypes().setAll(new ButtonType("Yes", ButtonBar.ButtonData.YES), new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
            alert.showAndWait().ifPresent(type ->
            {
                if(type.getButtonData() == ButtonBar.ButtonData.YES)
                {
                    Main.MAIN_PANE.setBoard(new Board(difficulty));
                    Main.MAIN_PANE.adjustWindow();
                }
            });
        }
        else
        {
            Main.MAIN_PANE.setBoard(new Board(difficulty));
            Main.MAIN_PANE.adjustWindow();
        }
    }

}
