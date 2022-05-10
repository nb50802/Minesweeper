package minesweeper.logic;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Theme
{

    public static final Paint FIELD_LIGHT = Color.rgb(38, 194, 0);
    public static final Paint FIELD_DARK = Color.rgb(30, 170, 30);

    public static final Paint MINED_LIGHT = Color.rgb(229, 194, 159);
    public static final Paint MINED_DARK = Color.rgb(215, 184, 153);

    public static final Paint BOMB = Color.RED;
    public static final Paint FLAG = Color.ORANGE;

    public static final Paint[] NEARBY = new Color[]
            {
                    Color.rgb(25, 118, 210),
                    Color.rgb(56, 142, 60),
                    Color.rgb(165, 20, 20),
                    Color.rgb(123, 31, 162)
            };

    //public static final Paint BACKGROUND = Color.LIGHT_GRAY;

}
