package ru.job4j.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * Аргументы
 * https://proof.ovh.net/files/10Mb.dat  10  name.txt
 */
public class Wget implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());
    private final String url;
    private final int speed;
    private String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    /**
     * Программа должна скачивать файл из сети с ограничением по скорости скачки.
     * Чтобы ограничить скорость скачивания, нужно засечь время скачивания 1024 байт.
     * Если время меньше указанного, то нужно выставить паузу за счет Thread.sleep.
     * <p>
     * В данной реализации ставится ограничение на скачивание 1024 байт.
     * Это не самое гибкое решение, так как очень маленький размер памяти,
     * который проделывается очень быстро и подобрать оптимальное время сложно.
     * Поэтому даже если указать speed = 1, то он будет спать все время одну секунду.
     */
    @Override
    public void run() {
        long startDownloading = System.currentTimeMillis();
        int indexOfSleep = 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            /*
            Засекаем начало скичивания
             */
            long start = System.currentTimeMillis();
            LOG.info("Старт : {}", start);
            /*
            Читаем в цикле по 1024 байта и записываем их в массив dataBuffer
             */
            int all = 0;
            LOG.info("Скорость скачивания 1024 байт : {}", speed);
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                all += bytesRead;
                LOG.info("Все данные : {}", all);
                long finish = System.currentTimeMillis();
                LOG.info("Финиш : {}", all);
                /*
                если скорость скачивания меньше скорости заявленной в конструкторе,
                то выставляем паузу.
                 */

                long duration = finish - start;
                LOG.info("Разница : {}", duration);
                if (duration < speed) {
                    try {
                        /*
                        Высчитываем скорость - отнимаем из заявленной
                        и делаем паузу на остаток.
                        К примеру, speed ==200, (finish - start) = 125, ждем 75.
                         */
                        long sleep = speed - duration;
                        LOG.info("Sleep : {}", sleep);
                        Thread.sleep(sleep);
                        indexOfSleep++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*
                Присваиваем новое значение start
                 */
                start = System.currentTimeMillis();
                /*
                Записываем
                 */
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
        LOG.info("Работа программы заняла : {}, программа спала : {}",
                System.currentTimeMillis() - startDownloading, indexOfSleep);
    }


    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough parameters. "
                    + "You need write three parameters - url, speed and name of file for downloading");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String name = args[2];
        Thread wget = new Thread(new WgetNew(url, speed, name));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


/**
 * Мегабайт - 10485760байт.
 * https://proof.ovh.net/files/10Mb.dat  10485760  name.txt
 * Реализация этого класса основывается на том,
 * что в speed указывается объем файла, который должен скачиваться по времени
 * не меньше чем за  TIME, если меньше, то поток спит между TIME и прошедшим временем.
 * К примеру, если Time 5 сек, а объем скачался за 3, то спит - 2 сек.
 */
class WgetNew implements Runnable {
    public static final Logger LOG = LoggerFactory.getLogger(WgetNew.class.getName());
    /**
     * В TIME указывается время, за которое должен скачиваться объект данных,
     * указанный в speed.
     */
    public final static int TIME = 5000;
    private final String url;
    private final int speed;
    private String fileName;

    public WgetNew(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        long allSleep = 0;
        long startDownloading = System.currentTimeMillis();
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            long duration;
            int dataDownload = 0;
            int all = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                all += bytesRead;
                LOG.info("Все данные : {}", all);
                dataDownload += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                /**
                 * Если размер скаченных данных больше объема указанного в speed,
                 * то проверяем время, за которое этот объем скачался.
                 * Если он меньше отведенного в TIME времени, то потоку нужно поспать разницу.
                 */
                if (dataDownload >= speed) {
                    long finish = System.currentTimeMillis();
                    duration = finish - start;
                    if (duration < TIME) {
                        allSleep += duration;
                        long sleep = TIME - duration;
                        Thread.sleep(sleep);
                        LOG.info("Sleep : {}" + sleep);
                    }
                    /**
                     * После достижения speed счетчик сбрасывается
                     * (поспал или нет тут не важно).
                     * Время также засекается новое.
                     */
                    dataDownload = 0;
                    start = System.currentTimeMillis();
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        LOG.info("Работа программы заняла : {}б программа спала - {}",
                System.currentTimeMillis() - startDownloading, allSleep);
    }
}

class Wget22 implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    public Wget22(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long expectedTime = 1024 * 1000 / speed;
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long time = System.currentTimeMillis() - start;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (expectedTime > time) {
                    Thread.sleep(expectedTime - time);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new Wget22(url, speed, file));
        wget.start();
    }
}