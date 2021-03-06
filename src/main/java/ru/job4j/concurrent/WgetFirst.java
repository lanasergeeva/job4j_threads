package ru.job4j.concurrent;

public class WgetFirst {
    /**
     * В методе main необходимо симулировать процесс загрузки.
     * Поток спит секунду и счетчик работает дальше.
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ... ");
                        for (int i = 0; i <= 100; i++) {
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                        }
                        System.out.println("\n Loaded.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
