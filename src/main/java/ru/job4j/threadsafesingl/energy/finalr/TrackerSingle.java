package ru.job4j.threadsafesingl.energy.finalr;

import ru.job4j.threadsafesingl.Item;

/**
 *
 */
public class TrackerSingle {
    private static final TrackerSingle INSTANCE = new TrackerSingle();

    private TrackerSingle() {
    }

    public static TrackerSingle getInstance() {
        return INSTANCE;
    }

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingle tracker = TrackerSingle.getInstance();
    }
}
