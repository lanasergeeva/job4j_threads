package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final Object monitor = this;
    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) {
        synchronized (monitor) {
            while (queue.size() == capacity) {
                try {
                    wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.offer(value);
            System.out.println("Producer add " + value);
            System.out.println("Queue size " + queue.size());
            monitor.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() < 1) {
                wait();
            }
            monitor.notify();
            T temp = queue.poll();
            System.out.println("Consumer add " + temp);
            return temp;
        }
    }
}
