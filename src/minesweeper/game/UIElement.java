package minesweeper.game;

import javafx.scene.Node;

/**
 * An interface providing for UI changes when game objects are loaded and modified
 * @param <T> A JavaFX element
 */
public interface UIElement<T extends Node>
{

    /**
     * Called to initialize UI element
     * @param node UI element T extending javafx.scene.Node
     */
    void init(T node);

    /**
     * Called to update UI element
     * @param node UI element T extending javafx.scene.Node
     */
    void update(T node);

}
