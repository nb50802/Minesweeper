package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.logic.Minesweeper;
import minesweeper.logic.Difficulty;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage)
    {
        Minesweeper ms = new Minesweeper(Difficulty.MEDIUM);
        ms.setAlignment(Pos.CENTER);

        Scene scene = new Scene(new VBox(ms), 800, 800);
        stage.setScene(scene);
        stage.show();
    }

}
