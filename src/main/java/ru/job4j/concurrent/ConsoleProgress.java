package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    /**
     * Запускаем  main run. Затем main спит 5 секунду.
     * В это время поток с загрузкой работой.
     * Затем идет прерывание потока и программа завершаети свою работу.
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] array = {"-", "\\", "|", "/"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String s : array) {
                    System.out.print("\rLoading : " + s);
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}