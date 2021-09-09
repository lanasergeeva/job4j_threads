package ru.job4j.queue;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        Thread consumer = new Thread(new Consumer(queue));
        Thread producer = new Thread(new Producer(queue));
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
    }
}