package ru.job4j.async;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RolColSumTest {
    @Test
    public void whenSum() {
        RolColSum sm = new RolColSum();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = {new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)};
        assertThat(RolColSum.sum(array), is(rsl));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        RolColSum sm = new RolColSum();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rsl = {new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)};
        assertThat(RolColSum.asyncSum(array), is(rsl));
    }

    @Test (expected = ExecutionException.class)
    public void whenAsyncSumNotMatrix() throws ExecutionException, InterruptedException {
        RolColSum sm = new RolColSum();
        int[][] array = {{1, 2, 3}, {4, 5}, {6}};
        RolColSum.asyncSum(array);
    }
}