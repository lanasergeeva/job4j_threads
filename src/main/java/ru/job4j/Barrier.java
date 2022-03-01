package ru.job4j;

/**
 * Метод on и off меняют флаг с true на false.
 * После каждого изменения программа будит нити,
 * которые ждут изменений.
 * Переменная flag - это общий ресурс,
 * поэтому мы с ней работаем только в критической секции.
 * Когда нить заходит в метод check, то она проверяет flag.
 * Если флаг = false, то нить засыпает.
 * Когда другая нить выполнит метод on или off,
 * то у объекта монитора выполняется метод notifyAll.
 * Метод notifyAll переводит все нити из состояния wait в runnable.
 */
public class Barrier {
    private boolean flag = false;

    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
