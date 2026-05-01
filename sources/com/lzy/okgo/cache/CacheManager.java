package com.lzy.okgo.cache;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes4.dex */
public enum CacheManager {
    INSTANCE;

    private Lock mLock = new ReentrantLock();
    private CacheDao<Object> cacheDao = new CacheDao<>();

    CacheManager() {
    }

    public CacheEntity<Object> get(String str) {
        this.mLock.lock();
        try {
            return this.cacheDao.get(str);
        } finally {
            this.mLock.unlock();
        }
    }

    public <T> CacheEntity<T> get(String str, Class<T> cls) {
        return (CacheEntity<T>) get(str);
    }

    public List<CacheEntity<Object>> getAll() {
        this.mLock.lock();
        try {
            return this.cacheDao.getAll();
        } finally {
            this.mLock.unlock();
        }
    }

    public <T> CacheEntity<T> replace(String str, CacheEntity<T> cacheEntity) {
        this.mLock.lock();
        try {
            cacheEntity.setKey(str);
            this.cacheDao.replace(cacheEntity);
            return cacheEntity;
        } finally {
            this.mLock.unlock();
        }
    }

    public boolean remove(String str) {
        if (str == null) {
            return true;
        }
        this.mLock.lock();
        try {
            return this.cacheDao.remove(str);
        } finally {
            this.mLock.unlock();
        }
    }

    public boolean clear() {
        this.mLock.lock();
        try {
            return this.cacheDao.deleteAll() > 0;
        } finally {
            this.mLock.unlock();
        }
    }
}
