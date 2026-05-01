package top.zibin.luban.p042io;

import java.util.ArrayDeque;
import java.util.Queue;
import top.zibin.luban.p042io.PoolAble;

/* JADX INFO: loaded from: classes5.dex */
abstract class BaseKeyPool<T extends PoolAble> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> keyPool = createQueue(20);

    abstract T create();

    BaseKeyPool() {
    }

    T get() {
        T tPoll = this.keyPool.poll();
        return tPoll == null ? (T) create() : tPoll;
    }

    public void offer(T t) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(t);
        }
    }

    public static <T> Queue<T> createQueue(int i) {
        return new ArrayDeque(i);
    }
}
