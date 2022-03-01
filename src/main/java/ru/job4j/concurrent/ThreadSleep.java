package ru.job4j.concurrent;

public class ThreadSleep {
    /**
     * Thread.sleep(millisecond) переводит нить в состояние TIMED_WAITING.
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        Thread.sleep(3000);
                        System.out.println("Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
