package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    /**
     * В кеш нужно добавлять копию объекта и возвращать копию.
     *
     * @param user
     */
    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    /**
     * @param id
     * @return копия объекта User с id = id
     */
    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    /**
     * Метод из задания.
     *
     * @return Возвращаем список с копиями.
     */
    public List<User> findAll() {
        List<User> rsl = new ArrayList<>();
        for (User user : users.values()) {
            rsl.add(User.of(user.getName()));
        }
        return rsl;
    }
}
