package ru.job4j.concurrent;

/**
 * Синхронизация общих ресурсов.
 */
public final class Cache {

    private static Cache cache;

    /**
     * Чтобы исправить ошибку атомарности, данный метод нужно сделать synchronized
     * @return
     */
    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}

/**
 * Что такое атомарность?
 * Этот код не является атомарным, так как здесь есть статический класс,
 * который нужно будет сначала прочитать,
 * затем инициализировать и потом еще вернуть,
 * что может вызвать не состыковку в потоках.
 */
final class CacheNoAtom {
    private static Cache cache;

    public static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}

