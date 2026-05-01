package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.util.JsonRecyclerPools;
import com.fasterxml.jackson.core.util.RecyclerPool;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
public final class JsonRecyclerPools {
    public static RecyclerPool<BufferRecycler> defaultPool() {
        return threadLocalPool();
    }

    public static RecyclerPool<BufferRecycler> threadLocalPool() {
        return ThreadLocalPool.GLOBAL;
    }

    public static RecyclerPool<BufferRecycler> nonRecyclingPool() {
        return NonRecyclingPool.GLOBAL;
    }

    public static RecyclerPool<BufferRecycler> sharedConcurrentDequePool() {
        return ConcurrentDequePool.GLOBAL;
    }

    public static RecyclerPool<BufferRecycler> newConcurrentDequePool() {
        return ConcurrentDequePool.construct();
    }

    @Deprecated
    public static RecyclerPool<BufferRecycler> sharedLockFreePool() {
        return LockFreePool.GLOBAL;
    }

    @Deprecated
    public static RecyclerPool<BufferRecycler> newLockFreePool() {
        return LockFreePool.construct();
    }

    public static RecyclerPool<BufferRecycler> sharedBoundedPool() {
        return BoundedPool.GLOBAL;
    }

    public static RecyclerPool<BufferRecycler> newBoundedPool(int i) {
        return BoundedPool.construct(i);
    }

    public static class ThreadLocalPool extends RecyclerPool.ThreadLocalPoolBase<BufferRecycler> {
        protected static final ThreadLocalPool GLOBAL = new ThreadLocalPool();
        private static final long serialVersionUID = 1;

        private ThreadLocalPool() {
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool.ThreadLocalPoolBase, com.fasterxml.jackson.core.util.RecyclerPool
        public BufferRecycler acquirePooled() {
            return BufferRecyclers.getBufferRecycler();
        }

        protected Object readResolve() {
            return GLOBAL;
        }
    }

    public static class NonRecyclingPool extends RecyclerPool.NonRecyclingPoolBase<BufferRecycler> {
        protected static final NonRecyclingPool GLOBAL = new NonRecyclingPool();
        private static final long serialVersionUID = 1;

        protected NonRecyclingPool() {
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool.NonRecyclingPoolBase, com.fasterxml.jackson.core.util.RecyclerPool
        public BufferRecycler acquirePooled() {
            return new BufferRecycler();
        }

        protected Object readResolve() {
            return GLOBAL;
        }
    }

    public static class ConcurrentDequePool extends RecyclerPool.ConcurrentDequePoolBase<BufferRecycler> {
        protected static final ConcurrentDequePool GLOBAL = new ConcurrentDequePool(-1);
        private static final long serialVersionUID = 1;

        protected ConcurrentDequePool(int i) {
            super(i);
        }

        public static ConcurrentDequePool construct() {
            return new ConcurrentDequePool(1);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool.StatefulImplBase
        public BufferRecycler createPooled() {
            return new BufferRecycler();
        }

        protected Object readResolve() {
            return _resolveToShared(GLOBAL).orElseGet(new Supplier() { // from class: com.fasterxml.jackson.core.util.JsonRecyclerPools$ConcurrentDequePool$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return JsonRecyclerPools.ConcurrentDequePool.construct();
                }
            });
        }
    }

    @Deprecated
    public static class LockFreePool extends RecyclerPool.LockFreePoolBase<BufferRecycler> {
        protected static final LockFreePool GLOBAL = new LockFreePool(-1);
        private static final long serialVersionUID = 1;

        protected LockFreePool(int i) {
            super(i);
        }

        public static LockFreePool construct() {
            return new LockFreePool(1);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool.StatefulImplBase
        public BufferRecycler createPooled() {
            return new BufferRecycler();
        }

        protected Object readResolve() {
            return _resolveToShared(GLOBAL).orElseGet(new Supplier() { // from class: com.fasterxml.jackson.core.util.JsonRecyclerPools$LockFreePool$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return JsonRecyclerPools.LockFreePool.construct();
                }
            });
        }
    }

    public static class BoundedPool extends RecyclerPool.BoundedPoolBase<BufferRecycler> {
        protected static final BoundedPool GLOBAL = new BoundedPool(-1);
        private static final long serialVersionUID = 1;

        protected BoundedPool(int i) {
            super(i);
        }

        public static BoundedPool construct(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("capacity must be > 0, was: " + i);
            }
            return new BoundedPool(i);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool.StatefulImplBase
        public BufferRecycler createPooled() {
            return new BufferRecycler();
        }

        /* JADX INFO: renamed from: lambda$readResolve$0$com-fasterxml-jackson-core-util-JsonRecyclerPools$BoundedPool */
        /* synthetic */ RecyclerPool.StatefulImplBase m517x641fe684() {
            return construct(this._serialization);
        }

        protected Object readResolve() {
            return _resolveToShared(GLOBAL).orElseGet(new Supplier() { // from class: com.fasterxml.jackson.core.util.JsonRecyclerPools$BoundedPool$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m517x641fe684();
                }
            });
        }
    }
}
