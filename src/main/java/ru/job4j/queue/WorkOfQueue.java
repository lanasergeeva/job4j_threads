package ru.job4j.queue;

public class WorkOfQueue {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        Thread consumer = new Thread(new Consumer(queue));
        Thread producer = new Thread(new Producer(queue));
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }
}
