package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        return memory.computeIfPresent(model.getId(), (base, bas) -> {
            if (model.getVersion() != stored.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.getId(), model.getVersion() + 1);
        }) != null;
    }


    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public int sizeMemory() {
        return memory.size();
    }
}
