package minesweeper.dialogs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import minesweeper.Main;
import minesweeper.Theme;

public class GameOverDialog extends Dialog<String>
{

    /**
     * Dialog (popup) that appears when the game is over
     * @param won boolean whether the game was won
     */
    public GameOverDialog(boolean won)
    {
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        ButtonType b1 = new ButtonType("Exit Game", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType b2 = new ButtonType((won ? "Play" : "Try") + " Again", ButtonBar.ButtonData.OK_DONE);
        this.setHeaderText(won ? "You won!" : "You lost :(");
        Label graphic = new Label(won ? "🏆" : "😔");
        graphic.setTextFill(Color.ORANGE);
        graphic.setFont(Theme.FONT.apply(40));
        this.setGraphic(graphic);
        this.getDialogPane().getButtonTypes().addAll(b1, b2);
        this.getDialogPane().lookupButton(b1).addEventFilter(ActionEvent.ACTION, event -> Platform.exit());
        this.getDialogPane().lookupButton(b2).addEventFilter(ActionEvent.ACTION, event -> Main.MAIN_PANE.newBoard());
    }

}
