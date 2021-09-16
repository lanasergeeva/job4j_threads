package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.job4j.pools.FindIndex.*;

public class FindIndexTest {

    @Test
    public void whenMax() {
        int[] arrayMain = getInitArray(30);
        int index = 22;
        int rsl = indexSearch(arrayMain, index);
        assertThat(rsl, is(21));
    }

    @Test
    public void whenLessThan10() {
        int[] arrayMain = getInitArray(9);
        int index = 6;
        int rsl = indexSearch(arrayMain, index);
        assertThat(rsl, is(5));
    }

    @Test
    public void whenLengthIsOne() {
        int[] arrayMain = getInitArray(1);
        int index = 1;
        int rsl = indexSearch(arrayMain, index);
        assertThat(rsl, is(0));
    }
}