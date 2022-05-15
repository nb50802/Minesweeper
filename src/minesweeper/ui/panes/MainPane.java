package minesweeper.ui.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Window;
import minesweeper.game.Difficulty;
import minesweeper.ui.Theme;
import minesweeper.game.Board;
import minesweeper.ui.menus.GameMenu;

public class MainPane extends VBox
{

    private HeaderPane headerPane;
    public GridPane pane = new GridPane();
    private Board board;

    /**
     * MainPane constructor - VBox containing the menu bar, header pane, and board
     * @param board Board object
     */
    public MainPane(Board board)
    {
        this.setAlignment(Pos.CENTER);
        MenuBar menuBar = new MenuBar(new GameMenu());
        this.getChildren().add(menuBar);
        this.headerPane = new HeaderPane(board.getDifficulty());
        this.getChildren().add(this.headerPane);
        this.setBoard(board);
        this.setBackground(new Background(new BackgroundFill(Theme.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Get the header pane
     * @return HeaderPane object
     */
    public HeaderPane getHeader()
    {
        return this.headerPane;
    }

    /**
     * Get the board
     * @return Board object
     */
    public Board getBoard()
    {
        return this.board;
    }

    /**
     * Creates a new board with the same difficulty
     */
    public void newBoard()
    {
        this.setBoard(new Board(this.board.getDifficulty()));
    }

    /**
     * Set a new board
     * @param board Board to be set
     */
    public void setBoard(Board board)
    {
        this.getChildren().remove(this.pane);
        this.pane = new GridPane();
        this.board = board;
        this.board.init(this.pane);
        this.board.update(this.pane);
        this.getChildren().add(this.pane);
        Difficulty difficulty = board.getDifficulty();
        this.headerPane.updateFlags(this.board.getFlags());
        this.headerPane.setMinSize(Theme.SQUARE_SIZE * difficulty.x(), Theme.SQUARE_SIZE);
        this.headerPane.setMaxSize(Theme.SQUARE_SIZE * difficulty.x(), Theme.SQUARE_SIZE);
        this.pane.setMinSize(Theme.SQUARE_SIZE * difficulty.x(), Theme.SQUARE_SIZE * difficulty.y());
        this.pane.setMaxSize(Theme.SQUARE_SIZE * difficulty.x(), Theme.SQUARE_SIZE * difficulty.y());
    }

    /**
     * Adjusts the window size for when the difficulty is changed
     */
    public void adjustWindow()
    {
        Window window = this.getScene().getWindow();
        window.setWidth(Theme.SQUARE_SIZE * this.board.getDifficulty().x() + 16);
        window.setHeight(this.headerPane.getMinHeight() + this.pane.getMinHeight() + 64);
        window.centerOnScreen();
    }

}
