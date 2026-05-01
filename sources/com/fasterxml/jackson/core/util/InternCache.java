package com.fasterxml.jackson.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes3.dex */
public final class InternCache extends ConcurrentHashMap<String, String> {
    private static final int DEFAULT_MAX_ENTRIES = 280;
    public static final InternCache instance = new InternCache();
    private static final long serialVersionUID = 1;
    private final ReentrantLock lock;

    public InternCache() {
        this(DEFAULT_MAX_ENTRIES, 0.8f, 4);
    }

    public InternCache(int i, float f, int i2) {
        super(i, f, i2);
        this.lock = new ReentrantLock();
    }

    public String intern(String str) {
        String str2 = get(str);
        if (str2 != null) {
            return str2;
        }
        if (size() >= DEFAULT_MAX_ENTRIES && this.lock.tryLock()) {
            try {
                if (size() >= DEFAULT_MAX_ENTRIES) {
                    clear();
                }
            } finally {
                this.lock.unlock();
            }
        }
        String strIntern = str.intern();
        put(strIntern, strIntern);
        return strIntern;
    }
}
