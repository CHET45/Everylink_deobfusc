package com.fasterxml.jackson.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes3.dex */
class ThreadLocalBufferManager {
    private final ReentrantLock RELEASE_LOCK = new ReentrantLock();
    private final Map<SoftReference<BufferRecycler>, Boolean> _trackedRecyclers = new ConcurrentHashMap();
    private final ReferenceQueue<BufferRecycler> _refQueue = new ReferenceQueue<>();

    ThreadLocalBufferManager() {
    }

    public static ThreadLocalBufferManager instance() {
        return ThreadLocalBufferManagerHolder.manager;
    }

    public int releaseBuffers() {
        this.RELEASE_LOCK.lock();
        try {
            removeSoftRefsClearedByGc();
            Iterator<SoftReference<BufferRecycler>> it = this._trackedRecyclers.keySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                it.next().clear();
                i++;
            }
            this._trackedRecyclers.clear();
            return i;
        } finally {
            this.RELEASE_LOCK.unlock();
        }
    }

    public SoftReference<BufferRecycler> wrapAndTrack(BufferRecycler bufferRecycler) {
        SoftReference<BufferRecycler> softReference = new SoftReference<>(bufferRecycler, this._refQueue);
        this._trackedRecyclers.put(softReference, true);
        removeSoftRefsClearedByGc();
        return softReference;
    }

    private void removeSoftRefsClearedByGc() {
        while (true) {
            SoftReference softReference = (SoftReference) this._refQueue.poll();
            if (softReference == null) {
                return;
            } else {
                this._trackedRecyclers.remove(softReference);
            }
        }
    }

    private static final class ThreadLocalBufferManagerHolder {
        static final ThreadLocalBufferManager manager = new ThreadLocalBufferManager();

        private ThreadLocalBufferManagerHolder() {
        }
    }
}
