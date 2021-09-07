package ru.job4j.store;

import net.jcip.annotations.*;

import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    List<User> list = new LinkedList<>();

    public synchronized boolean add(User user) {
        return list.add(user);
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        for (User us : list) {
            if (us.getId() == user.getId()) {
                us.setAmount(user.getAmount());
                rsl = true;
            }
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        boolean rsl = false;
        for (User us : list) {
            if (us.getId() == user.getId()) {
                list.remove(us);
                rsl = true;
            }
        }
        return rsl;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        int temp = 0;
        for (User us : list) {
            if (us.getId() == fromId && us.getAmount() >= amount) {
                us.setAmount(us.getAmount() - amount);
                temp++;
            } else if (us.getId() == toId) {
                us.setAmount(us.getAmount() + amount);
                temp++;
            }
        }
        return temp == 2;
    }
}
