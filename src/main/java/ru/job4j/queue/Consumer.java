package ru.job4j.queue;

public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;

    public Consumer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                queue.poll();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
