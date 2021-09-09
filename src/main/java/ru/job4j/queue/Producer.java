package ru.job4j.queue;

public class Producer implements Runnable {
    private final SimpleBlockingQueue<Integer> queue;
    private int index;

    public Producer(SimpleBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            index++;
            try {
                queue.offer(index);
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
