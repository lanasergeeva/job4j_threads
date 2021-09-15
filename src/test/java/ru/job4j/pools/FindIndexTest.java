package ru.job4j.pools;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.job4j.pools.FindIndex.*;

public class FindIndexTest {

    @Test
    public void whenMax() {
        int[] arrayMain = getInitArray(30);
        int value = -1;
        int index = 22;
        for (int i = 0; i < arrayMain.length; i++) {
            if(arrayMain[i] == index) {
                value = i;
            }
        }
        int rsl = indexSearch(arrayMain, index);
        assertThat(rsl, is(value));
    }
}