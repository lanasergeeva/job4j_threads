package ru.job4j.concurrent;

public class ThreadStop {
    /**
     *  interrupt(). Этот метод выставляет флаг прерывания,
     *  но никаких дополнительных действий не совершает.
     *  Программисту необходимо проверить, что флаг выставлен
     *  и завершить выполнение метода run().
     *  Thread.currentThread().isInterrupted() проверить состояние флага.
     * @param args
     * @throws InterruptedException
     */
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

        /**
         * Метод Thread.interrupt() не выставляет флаг прерывания,
         * если нить находится в режиме WAIT, JOIN.
         * В этом случае методы sleep(), join(), wait() выкинут исключение.
         * Поэтому нужно дополнительно проставить флаг прерывания.
         *  Если используются методы sleep(), join() или wait(),
         *  то нужно в блоке catch вызвать прерывание.
         * Это пример вечного цикла.
         *
         */
        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                           /* System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());*/
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
