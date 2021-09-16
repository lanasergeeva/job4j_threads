package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindIndex extends RecursiveTask<Integer> {
    private final int[] array;
    private final int from;
    private final int to;
    private final int index;

    public FindIndex(int[] array, int from, int to, int index) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.index = index;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (from == to) {
            return 0;
        }
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i] == index) {
                    rsl = i;
                    return rsl;
                }
            }
            return rsl;
        }
        int mid = (from + to) / 2;
        FindIndex leftSort = new FindIndex(array, from, mid, index);
        FindIndex rightSort = new FindIndex(array, mid + 1, to, index);
        leftSort.fork();
        rightSort.fork();
        return Math.max(leftSort.compute(), rightSort.join());

    }

    public static Integer indexSearch(int[] array, int index) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new FindIndex(array, 0, array.length - 1, index));
    }

    public static int[] getInitArray(int capacity) {
        int[] array = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    @Override
    public String toString() {
        return "FindIndex{"
                + "array=" + Arrays.toString(array)
                + ", from=" + from
                + ", to=" + to
                + ", index=" + index
                + '}';
    }
}
