
package ru.job4j.async;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class SumDiagonalsTest {

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        SumDiagonals sm = new SumDiagonals();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] rsl = SumDiagonals.asyncSum(array);
        int[] expected = {15, 1, 6, 15, 14, 9};
        assertThat(rsl, is(expected));
    }
}


