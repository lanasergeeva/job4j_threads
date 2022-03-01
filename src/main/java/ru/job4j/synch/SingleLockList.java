package ru.job4j.synch;

import java.util.LinkedList;
import java.util.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    /**
     * В конструкторе также делаем копию поступающего списка
     *
     * @param list
     */
    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * @param list который будет копироваться
     * @return Возвращаем копи листа
     */
    public synchronized List<T> copy(List list) {
        return new LinkedList<>(list);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

}
