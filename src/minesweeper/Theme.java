package minesweeper;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.function.Function;

/**
 * A class to store color and number constants that are used repeatedly in the user interface
 */
public class Theme
{

    public static final Color HEADER = Color.rgb(74, 117, 44);
    public static final Color BACKGROUND = Color.rgb(192, 192, 192);

    public static final Color FIELD_LIGHT = Color.rgb(38, 194, 0);
    public static final Color FIELD_DARK = Color.rgb(30, 170, 30);

    public static final Color MINED_LIGHT = Color.rgb(229, 194, 159);
    public static final Color MINED_DARK = Color.rgb(215, 184, 153);

    public static final Color BOMB = Color.RED;
    public static final Color FLAG = Color.ORANGE;

    public static final Color[] NEARBY = new Color[]
            {
                    Color.rgb(25, 118, 210),
                    Color.rgb(56, 142, 60),
                    Color.rgb(165, 20, 20),
                    Color.rgb(123, 31, 162)
            };

    public static final int SQUARE_SIZE = 40;

    public static final Function<Integer, Font> FONT = size -> Font.font("Serif", FontWeight.BOLD, size);

}
