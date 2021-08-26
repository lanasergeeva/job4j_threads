package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000);
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
