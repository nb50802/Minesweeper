package minesweeper.logic.square;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import minesweeper.logic.Theme;

import java.io.Serializable;

public abstract class Square extends StackPane implements Serializable
{

    private boolean dug, flag;
    protected Label label = new Label();

    public Square(int x, int y)
    {
        this.setMinSize(40, 40);
        this.getChildren().add(this.label);

        if((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0))
        {
            this.setStyle("-fx-background-color: " + Theme.LIGHT_GREEN);
        }
        else
        {
            this.setStyle("-fx-background-color: " + Theme.DARK_GREEN);
        }
    }

    public abstract boolean isBomb();

    public boolean isDug()
    {
        return this.dug;
    }

    public boolean isFlagged()
    {
        return this.flag;
    }

    public void dig()
    {
        this.dug = true;
    }

    public void toggleFlag()
    {
        this.flag = !this.flag;
        this.label.setText(this.flag ? "\uD83D\uDEA9" : "");
    }

}
