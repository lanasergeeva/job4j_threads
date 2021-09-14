package ru.job4j.threadsafesingl.lazy.singlecheckedlocking;

import ru.job4j.threadsafesingl.Item;

/**
 * Инициализация и проверка экземпляра происходит внутри критической секции.
 * Этот шаблон деградирует производительность.
 */
public class TrackerSingle {
    private static TrackerSingle instance;

    private TrackerSingle() {
    }

    public static synchronized TrackerSingle getInstance() {
        if (instance == null) {
            instance = new TrackerSingle();
        }
        return instance;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingle tracker = TrackerSingle.getInstance();
    }
}
