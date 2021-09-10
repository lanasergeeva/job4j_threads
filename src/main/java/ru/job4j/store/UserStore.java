package ru.job4j.store;

import net.jcip.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        int key = user.getId();
        return store.replace(key, store.get(key), user);
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User from = store.get(fromId);
        User to = store.get(toId);
        if (from != null && to != null) {
            if (from.getAmount() < amount) {
                System.out.println("It`s not enough money for transfer");
            } else {
                from.setAmount(from.getAmount() - amount);
                to.setAmount(to.getAmount() + amount);
                rsl = true;
            }
        } else {
            System.out.println("Wrong id in parameters");
        }
        return rsl;
    }
}
