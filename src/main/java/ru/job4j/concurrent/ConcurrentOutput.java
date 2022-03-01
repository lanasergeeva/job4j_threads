package ru.job4j.concurrent;

public class ConcurrentOutput {
    /**
     * Создаем 2 потока и выводим имя каждого из них.
     * @param args
     */
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        System.out.println(Thread.currentThread().getName());
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
    }
}
