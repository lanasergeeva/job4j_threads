package ru.job4j.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        long start = System.currentTimeMillis();
        Sums[] rsl = new Sums[matrix.length];
        for (int k = 0; k < matrix.length; k++) {
            rsl[k] = sumRowCol(matrix, k);
        }
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        int n = matrix.length;
        Sums[] rslSum = new Sums[n];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n; i++) {
            futures.put(i, getSum(matrix, i));
        }
        for (Integer key : futures.keySet()) {
            rslSum[key] = futures.get(key).get();
        }
        long finish = System.currentTimeMillis();
        System.out.println(finish - start);
        return rslSum;
    }

    private static Sums sumRowCol(int[][] matrix, int index) {
        int sumRow = 0;
        int sumCol = 0;
        for (int i = 0; i < matrix.length; i++) {
            sumRow += matrix[index][i];
            sumCol += matrix[i][index];
        }
        return new Sums(sumRow, sumCol);
    }


    public static CompletableFuture<Sums> getSum(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> sumRowCol(matrix, index));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] array = new int[3][3];
        int s = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = ++s;
            }
        }
        Sums[] in;
        in = sum(array);
        for (Sums ss : in) {
            System.out.println(ss);
        }
        Sums[] iny = asyncSum(array);
        for (Sums ss : iny) {
            System.out.println(ss);
        }
    }
}
