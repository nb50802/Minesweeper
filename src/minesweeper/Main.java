package minesweeper;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import minesweeper.panes.Board;
import minesweeper.panes.MainPane;

import java.io.*;

public class Main extends Application
{

    private static final File DATA_FILE = new File("minesweeper.dat");
    public static MainPane MAIN_PANE;

    public static void main(String[] args)
    {
        readFromFile();
        launch(args);
    }

    public void start(Stage stage)
    {
        stage.setTitle("Minesweeper");
        Scene scene = new Scene(MAIN_PANE);
        stage.setScene(scene);
        //stage.setOnCloseRequest(event -> writeToFile());
        stage.show();
    }

    public static void readFromFile()
    {
        if(DATA_FILE.exists())
        {
            try
            {
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(DATA_FILE));

                if(stream.readBoolean())
                {
                    Board board = (Board) stream.readObject();
                    System.out.println("read in\n" + board);
                    board.loadArray();
                    MAIN_PANE = new MainPane(board);
                }
                else
                {
                    Difficulty difficulty = (Difficulty) stream.readObject();
                    System.out.println("read in difficulty " + difficulty);
                    MAIN_PANE = new MainPane(new Board(difficulty));
                }

                stream.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            MAIN_PANE = new MainPane(new Board(Difficulty.EASY));
        }
    }

    public static void writeToFile()
    {
        try
        {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
            Board board = MAIN_PANE.getBoard();

            if(board.getGameState() == GameState.DIRTY)
            {
                stream.writeBoolean(true);
                stream.writeObject(board);
            }
            else
            {
                stream.writeBoolean(false);
                stream.writeObject(board.getDifficulty());
            }

            stream.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
