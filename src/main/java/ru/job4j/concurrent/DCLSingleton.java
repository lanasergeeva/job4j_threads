package ru.job4j.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class DCLSingleton {

    private static DCLSingleton inst;
    private static volatile boolean flag = true;

    /**
     * Без volatile.
     * В этом случае получается, что когда два потока войдут в метод,
     * первый поток проверит на null и засинхронизирует метод и объявит класс,
     * но второй поток продолжит затем свою работу,
     * так как объявленный класс может быть в кэше процессора.
     * и класс объявится снова
     * @return DCLSingleton.
     */
    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    System.out.println("tut");
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
        System.out.println("New DCLSingleton()");
    }
}
