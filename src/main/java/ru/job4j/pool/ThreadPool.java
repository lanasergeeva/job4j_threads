package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        this.tasks = new SimpleBlockingQueue<>(size);
    }

    public void runs() {
        while (!tasks.isEmpty()) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            );
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool tp = new ThreadPool();
        Thread thread1 = new Thread(
                () -> {
                    try {
                        System.out.println("Start thread1 ... ");
                        Thread.sleep(3000);
                        System.out.println("End of thread1.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        System.out.println("Start thread2 ... ");
                        Thread.sleep(1000);
                        System.out.println("End of thread2.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        tp.work(thread1);
        tp.work(thread2);
        tp.runs();
        Thread.sleep(5000);
        tp.shutdown();
    }
}
