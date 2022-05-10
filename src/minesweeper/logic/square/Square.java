package minesweeper.logic.square;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import minesweeper.logic.Theme;

import java.io.Serializable;

public abstract class Square extends Label implements Serializable
{

    private boolean dug, flag;

    public Square()
    {
        this.setMinSize(40, 40);
        this.setMaxSize(40, 40);
        this.setFont(Font.font("Serif", FontWeight.BOLD, 30));
        this.setAlignment(Pos.CENTER);
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

    public void dig(int x, int y)
    {
        this.dug = true;
        this.setColor((x + y) % 2 == 0 ? Theme.MINED_DARK : Theme.MINED_LIGHT);
    }

    public void toggleFlag()
    {
        this.flag = !this.flag;
        this.setTextFill(this.flag ? Theme.FLAG : Color.BLACK);
        this.setText(this.flag ? "⚑" : "");
    }

    public void setColor(Paint paint)
    {
        this.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
