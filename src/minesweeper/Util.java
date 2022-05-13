package minesweeper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util
{

    /**
     * Utility function to iterate over two sets of numbers
     * @param x1 x start, inclusive
     * @param x2 x end, exclusive
     * @param y1 y start, inclusive
     * @param y2 y end, exclusive
     * @param consumer consumer of numbers
     */
    public static void doubleIntSupplier(int x1, int x2, int y1, int y2, BiConsumer<Integer, Integer> consumer)
    {
        IntStream.range(x1, x2).forEach(x -> IntStream.range(y1, y2).forEach(y -> consumer.accept(x, y)));
    }

    /**
     * Utility function to convert a double array into a single stream of elements of type T
     * @param arr double array
     * @param <T> array type
     * @return stream of T
     */
    public static <T> Stream<T> flatMap(T[][] arr)
    {
        return Arrays.stream(arr).flatMap(Arrays::stream);
    }

    /**
     * Utility function to run event on delay
     * @param millis delay in milliseconds
     * @param runnable event to run
     */
    public static void onDelay(int millis, Runnable runnable)
    {
        new Timeline(new KeyFrame(Duration.millis(millis), e -> runnable.run())).play();
    }

}
