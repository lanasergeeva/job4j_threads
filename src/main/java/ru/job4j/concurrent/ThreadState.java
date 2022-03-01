package ru.job4j.concurrent;

public class ThreadState {
    /**
     * Создаем 2 нити и выводим на консоль имя и состояние.
     * Нить main должна дождаться завершения этих нитей
     * и вывести на консоль сообщение, что работа завершена.
     * В while делаем условие, что пока статус двух потоков не Terminated
     * выводим состояние потоков.
     * @param args
     */
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                }
        );
        Thread second = new Thread(
                () -> {
                }
        );
        first.start();
        second.start();
        System.out.println(first.getName());
        System.out.println(second.getName());
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println(Thread.currentThread().getName() + " Работа нитей first и second заверешена");
    }
}
