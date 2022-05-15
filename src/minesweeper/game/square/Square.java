package minesweeper.game.square;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import minesweeper.Main;
import minesweeper.ui.Theme;
import minesweeper.game.UIElement;
import minesweeper.Util;
import minesweeper.game.Board;

import java.io.Serializable;

public abstract class Square implements UIElement<Label>, Serializable
{

    private final int x, y;
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
     * Gets appropriate color for square in grid
     * @return Color object
     */
    public Color getColor()
    {
        return (this.x + this.y) % 2 == 0 ? (this.dug ? Theme.MINED_DARK : Theme.FIELD_DARK) : (this.dug ? Theme.MINED_LIGHT : Theme.FIELD_LIGHT);
    }

    /**
     * Initializes the Label object with needed properties
     * @param label label object representing Square
     */
    public void init(Label label)
    {
        Util.setColor(label, this.getColor());
        label.addEventFilter(MouseEvent.MOUSE_PRESSED, this::mousePressed);
        label.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> this.mouseEnter(label));
        label.addEventFilter(MouseEvent.MOUSE_EXITED, event -> this.mouseExit(label));
        label.setMinSize(Theme.SQUARE_SIZE, Theme.SQUARE_SIZE);
        label.setMaxSize(Theme.SQUARE_SIZE, Theme.SQUARE_SIZE);
        label.setFont(Theme.FONT.apply(30));
        label.setAlignment(Pos.CENTER);
        label.setCursor(Cursor.HAND);
    }

    /**
     * Updates the label when changes are made to Square
     * @param label label object representing Square
     */
    public void update(Label label)
    {
        Util.setColor(label, this.getColor());

        if(this.dug)
        {
            label.setCursor(Cursor.DEFAULT);
        }
        else
        {
            label.setTextFill(this.flag ? Theme.FLAG : Color.BLACK);
            label.setText(this.flag ? "⚑" : "");
        }
    }

    /**
     * Dig Square object
     */
    public void dig()
    {
        this.dug = true;
    }

    /**
     * Toggle whether the Square is flagged
     */
    public void toggleFlag()
    {
        this.flag = !this.flag;
    }

    /**
     * Mouse press event for handling mouse input
     * Left click digs, right click flags
     * @param event mouse event
     */
    public void mousePressed(MouseEvent event)
    {
        Board board = Main.MAIN_PANE.getBoard();

        if(!board.getGameState().isOver())
        {
            if(event.isPrimaryButtonDown())
            {
                board.dig(this.x, this.y);
                System.out.println("Dug (" + this.x + ", " + this.y + "):\n" + board);
            }
            else if(event.isSecondaryButtonDown())
            {
                board.flag(this.x, this.y);
            }
        }
    }

    /**
     * Mouse enter event for handling mouse movement
     * Adjusts color to make it appear selected
     * @param label label element representing Square
     */
    public void mouseEnter(Label label)
    {
        if(Main.MAIN_PANE.getBoard().getGameState().isOver())
        {
            label.setCursor(Cursor.DEFAULT);
        }
        else if(!this.dug)
        {
            Util.setColor(label, this.getColor().brighter());
        }
    }

    /**
     * Mouse exit event for handling mouse movement
     * Adjusts color to default color when mouse is moved away
     * @param label label element representing Square
     */
    public void mouseExit(Label label)
    {
        if(!this.dug)
        {
            Util.setColor(label, this.getColor());
        }
    }

}
