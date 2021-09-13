package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CacheTest {
    @Test
    public void addAndUpdate() {
        Cache cache = new Cache();
        Base one = new Base(1, 0);
        Base two = new Base(2, 0);
        one.setName("Anna");
        two.setName("Oleg");
        cache.add(one);
        cache.add(two);
        Base test = new Base(1, 0);
        Base test2 = new Base(2, 0);
        test.setName("Lana");
        test2.setName("Pavel");
        assertTrue(cache.update(test));
        assertTrue(cache.update(test2));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base one = new Base(1, 0);
        cache.add(one);
        assertThat(cache.sizeMemory(), is(1));
        cache.delete(one);
        assertThat(cache.sizeMemory(), is(0));
    }

    @Test
    public void whenAdd() {
        Cache cache = new Cache();
        Base one = new Base(1, 0);
        cache.add(one);
        assertThat(cache.sizeMemory(), is(1));
    }

    @Test(expected = OptimisticException.class)
    public void whenException() {
        Cache cache = new Cache();
        Base one = new Base(1, 0);
        cache.add(one);
        Base test = new Base(1, 1);
        cache.update(test);
    }

}