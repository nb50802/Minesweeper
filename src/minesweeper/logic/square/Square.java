package minesweeper.logic.square;

import java.io.Serializable;

public abstract class Square implements Serializable
{

    private boolean dug, flag;

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
    }

}
