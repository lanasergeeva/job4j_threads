package ru.job4j.queue.without;


public class ParalelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            queue.poll();
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();

                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 5; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

        );
        producer.start();
        producer.join();
        consumer.interrupt();
        producer.interrupt();
    }
}
