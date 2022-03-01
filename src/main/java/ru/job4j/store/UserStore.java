package ru.job4j.store;

import net.jcip.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    /**
     * Добавляет пользователя в хранилище.
     * @param user которого нужно добавить в кеш
     * @return true, если пользователя нет в store, так как верентся null.
     * Если пользователь в store есть, то вернется этот пользоваель
     * и проверка на null вернет false.
     */
    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    /**
     * Обновляет пользователя в хранилище.
     * @param user которого нужно изменить.
     * @return true если данны были верны и false, если нет данных по ключу или
     * пользователь в параметрах не совпадает.
     */
    public synchronized boolean update(User user) {
        int key = user.getId();
        return store.replace(key, store.get(key), user);
    }

    /**
     * Удаляет пользователя из кеша.
     * @param user
     * @return
     */
    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    /**
     *
     * @param fromId айди пользователя, который переводит деньги
     * @param toId айди получателя
     * @param amount сумма перевода
     * @return true, если операция прошла успешно,
     * false - если дан неверный айди или у пользователя неджостаточно средств на счету.
     */
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

    public static void main(String[] args) {
        Map<Integer, User> store = new ConcurrentHashMap<>();
        User user = new User(1, 2500);
        User user2 = new User(1, 1500);
        User user3 = new User(4, 5500);
        User user4 = new User(4, 5500);
        System.out.println(store.putIfAbsent(user.getId(), user));
        System.out.println(store.putIfAbsent(user.getId(), user));
        System.out.println(store.replace(user.getId(), user, user2));
        System.out.println(store.replace(1, user4, user3));
        System.out.println(store.get(user.getId()));
    }
}
