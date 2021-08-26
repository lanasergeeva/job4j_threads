package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("download.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long time1 = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long time2 = System.currentTimeMillis();
                if ((time2 - time1) < speed) {
                    try {
                        Thread.sleep(speed - (time2 - time1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                time1 = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }


    public static void main(String[] args) {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}



   /* public static void main(String[] args) {
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
    }*/