package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.logic.Board;
import minesweeper.logic.Difficulty;

public class Main extends Application
{

    public static void main(String[] args)
    {
//        Board ms = new Board(Difficulty.MEDIUM);
//        ms.addBombs(0, 0);
//        ms.dig(5, 10);

//      System.out.println(ms);

        launch(args);
    }

    public void start(Stage stage)
    {
        // Create a Label control.
        Label myLabel = new Label("Hello world!");

        Board b = new Board(Difficulty.MEDIUM);
        System.out.println(b);

        b.setAlignment(Pos.CENTER);
        b.setPadding(new Insets(10));

        // Create a Scene and display it.
        Scene scene = new Scene(b, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

}
