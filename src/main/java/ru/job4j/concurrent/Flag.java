package ru.job4j.concurrent;

public class Flag {
    /**
     * Без volatile.
     * Может возникнуть ситуация,
     * что главная нить запишет новое значение переменной в кеш процессора,
     * а дополнительная нить будет продолжать читать переменную flag из регистра.
     */
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        flag = false;
        thread.join();
    }
}
