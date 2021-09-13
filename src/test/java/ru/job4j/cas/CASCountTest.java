package ru.job4j.cas;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CASCountTest {

    @Test
    public void when3AddThenGet() {
        CASCount cas = new CASCount();
        for (int i = 0; i < 3; i++) {
            cas.increment();
        }
        assertThat(cas.getCount(), is(3));
    }
}