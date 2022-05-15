package minesweeper.ui.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import minesweeper.game.Difficulty;
import minesweeper.ui.Theme;

public class HeaderPane extends HBox
{

    private Label flags = new Label();

    /**
     * HeaderPane constructor - pane that lies above grid
     * @param difficulty game difficulty
     */
    public HeaderPane(Difficulty difficulty)
    {
        this.setBackground(new Background(new BackgroundFill(Theme.HEADER, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        double inset = Theme.SQUARE_SIZE * difficulty.x() * 0.05;
        Label title = new Label("Minesweeper");
        title.setFont(Theme.FONT.apply(28));
        title.setTextFill(Color.WHITE);
        title.setPadding(new Insets(0, 0, 0, inset));
        this.getChildren().add(title);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        this.getChildren().add(spacer);
        Label flag = new Label("⚑ ");
        flag.setTextFill(Theme.FLAG);
        flag.setFont(Theme.FONT.apply(25));
        this.getChildren().add(flag);
        this.flags.setTextFill(Color.WHITE);
        this.flags.setFont(Theme.FONT.apply(25));
        this.flags.setPadding(new Insets(0, inset, 0, 0));
        this.getChildren().add(this.flags);
        this.updateFlags(difficulty.bombs());
        this.setStyle("-fx-border-color: black");
    }

    /**
     * Updates the flag counter in the header pane
     * @param flags number of flags left
     */
    public void updateFlags(int flags)
    {
        this.flags.setText(String.valueOf(flags));
    }

}
