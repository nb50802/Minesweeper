package minesweeper.square;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import minesweeper.Main;
import minesweeper.Theme;
import minesweeper.panes.Board;

import java.io.Serializable;

public abstract class Square extends Label implements Serializable
{

    private int x, y;
    private boolean dug, flag;

    /**
     * Square constructor
     * An abstract class for each square on grid
     * @param x x coordinate
     * @param y y coordinate
     */
    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.setColor((x + y) % 2 == 0 ? Theme.FIELD_DARK : Theme.FIELD_LIGHT);
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, this::mousePressed);
        this.addEventFilter(MouseEvent.MOUSE_ENTERED, this::mouseEnter);
        this.addEventFilter(MouseEvent.MOUSE_EXITED, this::mouseExit);
        this.setMinSize(Theme.SQUARE_SIZE, Theme.SQUARE_SIZE);
        this.setMaxSize(Theme.SQUARE_SIZE, Theme.SQUARE_SIZE);
        this.setFont(Theme.FONT.apply(30));
        this.setAlignment(Pos.CENTER);
        this.setCursor(Cursor.HAND);
    }

    /**
     * Returns whether the Square is a bomb
     * @return true if bomb, false if land
     */
    public abstract boolean isBomb();

    /**
     * Get x coordinate
     * @return x coordinate
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Get y coordinate
     * @return y coordinate
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * Get if Square was dug
     * @return true if dug, false if not
     */
    public boolean isDug()
    {
        return this.dug;
    }

    /**
     * Get if Square was flagged
     * @return true if flagged, false if not
     */
    public boolean isFlagged()
    {
        return this.flag;
    }

    /**
     * Dig Square object and adjust color
     */
    public void dig()
    {
        this.dug = true;
        this.setCursor(Cursor.DEFAULT);
        this.setColor((this.x + this.y) % 2 == 0 ? Theme.MINED_DARK : Theme.MINED_LIGHT);
    }

    /**
     * Toggle whether the Square is flagged
     */
    public void toggleFlag()
    {
        this.flag = !this.flag;
        this.setTextFill(this.flag ? Theme.FLAG : Color.BLACK);
        this.setText(this.flag ? "⚑" : "");
    }

    /**
     * Mouse press event for handling mouse input
     * Left click digs, right click flags
     * @param event mouse event
     */
    public void mousePressed(MouseEvent event)
    {
        Board board = Main.MAIN_PANE.getBoard();

        if(event.isPrimaryButtonDown())
        {
            board.dig(this.x, this.y);
        }
        else if(event.isSecondaryButtonDown())
        {
            board.flag(this.x, this.y);
        }

        System.out.println(board);
    }

    /**
     * Mouse enter event for handling mouse movement
     * Adjusts color of Square to make it appear selected
     * @param event mouse event
     */
    public void mouseEnter(MouseEvent event)
    {
        if(!this.dug)
        {
            this.setColor((this.x + this.y) % 2 == 0 ? Theme.FIELD_DARK.brighter() : Theme.FIELD_LIGHT.brighter());
        }
    }

    /**
     * Mouse exit event for handling mouse movement
     * Adjusts color of Square to default color when mouse is moved away
     * @param event mouse event
     */
    public void mouseExit(MouseEvent event)
    {
        if(!this.dug)
        {
            this.setColor((this.x + this.y) % 2 == 0 ? Theme.FIELD_DARK : Theme.FIELD_LIGHT);
        }
    }

    /**
     * A function to set the background color of the Square
     * @param paint color
     */
    public void setColor(Paint paint)
    {
        this.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
