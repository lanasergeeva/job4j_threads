package ru.job4j.queue.without;

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

    public int getCapacity() {
        return capacity;
    }

    /**
     * Ждет, если размер очереди равен capacity(вмесимости).
     * Если есть место, куда добавить значение, добавляет.
     *
     * @param value поступающее значение в очередь
     * @throws InterruptedException не обрабатываеми исключение
     *                              После того, как добавили, разбудили другой поток методо notify()
     */
    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            while (queue.size() == capacity) {
                System.out.println("Producer ждет");
                wait();
            }
            queue.offer(value);
            System.out.println("Producer add " + value);
            System.out.println("Queue size " + queue.size());
            monitor.notify();
        }
    }

    /**
     * Берет значени из очереди. Если в очереди нет элементов(queue.size() < 1),
     * ждет, пока в offer добавится элемент и вызовется notify().
     * Затем берем значение и вызываем notify()
     *
     * @return значение, которое получил.
     * @throws InterruptedException не обрабатываем исключение.
     */
    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (queue.isEmpty()) {
                System.out.println("Consumer ждет");
                wait();
            }
            monitor.notify();
            T temp = queue.poll();
            System.out.println("Consumer add " + temp);
            return temp;
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
