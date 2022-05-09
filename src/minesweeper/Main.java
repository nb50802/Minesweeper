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
        Minesweeper ms = new Minesweeper(Difficulty.MEDIUM);
        ms.populateBoard(0, 0);
        ms.dig(5, 10);

        System.out.println(ms);

        launch(args);
    }

    public void start(Stage stage)
    {
        // Create a Label control.
        Label myLabel = new Label("Hello world!");

        // Put the Label in a VBox.
        VBox vbox = new VBox(myLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Create a Scene and display it.
        Scene scene = new Scene(vbox, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

}
