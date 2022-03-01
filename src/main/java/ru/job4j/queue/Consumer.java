package ru.job4j.queue;

/**
 * Потребитель 8 раз берет из очереди значение.
 * Берет 1 элемент, спит секунду и продолжает работу.
 */
public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;

    public Consumer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < queue.getCapacity(); i++) {
            try {
                queue.poll();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
