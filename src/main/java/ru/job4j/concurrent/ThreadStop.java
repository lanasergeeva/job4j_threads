package ru.job4j.concurrent;

public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        /*Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();*/

        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());
                        }
                    }
                }
        );
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
        progress.join();
    }
}
