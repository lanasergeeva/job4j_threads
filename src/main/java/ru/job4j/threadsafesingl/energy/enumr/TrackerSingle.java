package ru.job4j.threadsafesingl.energy.enumr;

import ru.job4j.threadsafesingl.Item;

/**
 * Объект enum создается при загрузке класса и
 * безопасно публикуется всем клиентам.
 */
public enum TrackerSingle {
    INSTANCE;

    public Item add(Item model) {
        return model;
    }

    public static void main(String[] args) {
        TrackerSingle tracker = TrackerSingle.INSTANCE;
    }
}
