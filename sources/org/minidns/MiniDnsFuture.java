package org.minidns;

import java.io.IOException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.minidns.util.CallbackRecipient;
import org.minidns.util.ExceptionCallback;
import org.minidns.util.MultipleIoException;
import org.minidns.util.SuccessCallback;

/* JADX INFO: loaded from: classes4.dex */
public abstract class MiniDnsFuture<V, E extends Exception> implements Future<V>, CallbackRecipient<V, E> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ExecutorService EXECUTOR_SERVICE;
    private boolean cancelled;
    protected E exception;
    private ExceptionCallback<E> exceptionCallback;
    protected V result;
    private SuccessCallback<V> successCallback;

    public interface ExceptionsWrapper<EI extends Exception, EO extends Exception> {
        EO wrap(List<EI> list);
    }

    @Override // java.util.concurrent.Future
    public synchronized boolean cancel(boolean z) {
        if (isDone()) {
            return false;
        }
        this.cancelled = true;
        if (z) {
            notifyAll();
        }
        return true;
    }

    @Override // java.util.concurrent.Future
    public final synchronized boolean isCancelled() {
        return this.cancelled;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0010  */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean isDone() {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.hasResult()     // Catch: java.lang.Throwable -> L13
            if (r0 != 0) goto L10
            boolean r0 = r1.hasException()     // Catch: java.lang.Throwable -> L13
            if (r0 == 0) goto Le
            goto L10
        Le:
            r0 = 0
            goto L11
        L10:
            r0 = 1
        L11:
            monitor-exit(r1)
            return r0
        L13:
            r0 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L13
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.minidns.MiniDnsFuture.isDone():boolean");
    }

    public final synchronized boolean hasResult() {
        return this.result != null;
    }

    public final synchronized boolean hasException() {
        return this.exception != null;
    }

    @Override // org.minidns.util.CallbackRecipient
    public CallbackRecipient<V, E> onSuccess(SuccessCallback<V> successCallback) {
        this.successCallback = successCallback;
        maybeInvokeCallbacks();
        return this;
    }

    @Override // org.minidns.util.CallbackRecipient
    public CallbackRecipient<V, E> onError(ExceptionCallback<E> exceptionCallback) {
        this.exceptionCallback = exceptionCallback;
        maybeInvokeCallbacks();
        return this;
    }

    private V getOrThrowExecutionException() throws ExecutionException {
        V v = this.result;
        if (v != null) {
            return v;
        }
        if (this.exception != null) {
            throw new ExecutionException(this.exception);
        }
        throw new CancellationException();
    }

    @Override // java.util.concurrent.Future
    public final synchronized V get() throws ExecutionException, InterruptedException {
        while (this.result == null && this.exception == null && !this.cancelled) {
            wait();
        }
        return getOrThrowExecutionException();
    }

    public final synchronized V getOrThrow() throws Exception {
        V v;
        while (true) {
            v = this.result;
            if (v != null || this.exception != null || this.cancelled) {
                break;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        E e2 = this.exception;
        if (e2 != null) {
            throw e2;
        }
        if (this.cancelled) {
            throw new CancellationException();
        }
        return v;
    }

    @Override // java.util.concurrent.Future
    public final synchronized V get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        V v;
        long jCurrentTimeMillis = System.currentTimeMillis() + timeUnit.toMillis(j);
        while (true) {
            v = this.result;
            if (v == null || this.exception == null || this.cancelled) {
                break;
            }
            long jCurrentTimeMillis2 = jCurrentTimeMillis - System.currentTimeMillis();
            if (jCurrentTimeMillis2 > 0) {
                wait(jCurrentTimeMillis2);
            }
        }
        if (this.cancelled) {
            throw new CancellationException();
        }
        if (v == null || this.exception == null) {
            throw new TimeoutException();
        }
        return getOrThrowExecutionException();
    }

    static {
        ThreadFactory threadFactory = new ThreadFactory() { // from class: org.minidns.MiniDnsFuture.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                thread.setName("MiniDnsFuture Thread");
                return thread;
            }
        };
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(128);
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() { // from class: org.minidns.MiniDnsFuture.2
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                runnable.run();
            }
        };
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        if (iAvailableProcessors <= 4) {
            iAvailableProcessors = 2;
        }
        EXECUTOR_SERVICE = new ThreadPoolExecutor(0, iAvailableProcessors, 60L, TimeUnit.SECONDS, arrayBlockingQueue, threadFactory, rejectedExecutionHandler);
    }

    protected final synchronized void maybeInvokeCallbacks() {
        if (this.cancelled) {
            return;
        }
        if (this.result != null && this.successCallback != null) {
            EXECUTOR_SERVICE.submit(new Runnable() { // from class: org.minidns.MiniDnsFuture.3
                @Override // java.lang.Runnable
                public void run() {
                    MiniDnsFuture.this.successCallback.onSuccess(MiniDnsFuture.this.result);
                }
            });
        } else if (this.exception != null && this.exceptionCallback != null) {
            EXECUTOR_SERVICE.submit(new Runnable() { // from class: org.minidns.MiniDnsFuture.4
                @Override // java.lang.Runnable
                public void run() {
                    MiniDnsFuture.this.exceptionCallback.processException(MiniDnsFuture.this.exception);
                }
            });
        }
    }

    public static class InternalMiniDnsFuture<V, E extends Exception> extends MiniDnsFuture<V, E> {
        public final synchronized void setResult(V v) {
            if (isDone()) {
                return;
            }
            this.result = v;
            notifyAll();
            maybeInvokeCallbacks();
        }

        public final synchronized void setException(E e) {
            if (isDone()) {
                return;
            }
            this.exception = e;
            notifyAll();
            maybeInvokeCallbacks();
        }
    }

    public static <V, E extends Exception> MiniDnsFuture<V, E> from(V v) {
        InternalMiniDnsFuture internalMiniDnsFuture = new InternalMiniDnsFuture();
        internalMiniDnsFuture.setResult(v);
        return internalMiniDnsFuture;
    }

    public static <V> MiniDnsFuture<V, IOException> anySuccessfulOf(Collection<MiniDnsFuture<V, IOException>> collection) {
        return anySuccessfulOf(collection, new ExceptionsWrapper() { // from class: org.minidns.MiniDnsFuture$$ExternalSyntheticLambda0
            @Override // org.minidns.MiniDnsFuture.ExceptionsWrapper
            public final Exception wrap(List list) {
                return MultipleIoException.toIOException(list);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <V, EI extends Exception, EO extends Exception> MiniDnsFuture<V, EO> anySuccessfulOf(final Collection<MiniDnsFuture<V, EI>> collection, final ExceptionsWrapper<EI, EO> exceptionsWrapper) {
        final InternalMiniDnsFuture internalMiniDnsFuture = new InternalMiniDnsFuture();
        final List listSynchronizedList = Collections.synchronizedList(new ArrayList(collection.size()));
        for (MiniDnsFuture<V, EI> miniDnsFuture : collection) {
            miniDnsFuture.onSuccess(new SuccessCallback<V>() { // from class: org.minidns.MiniDnsFuture.5
                @Override // org.minidns.util.SuccessCallback
                public void onSuccess(V v) {
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        ((MiniDnsFuture) it.next()).cancel(true);
                    }
                    internalMiniDnsFuture.setResult(v);
                }
            });
            miniDnsFuture.onError(new ExceptionCallback<EI>() { // from class: org.minidns.MiniDnsFuture.6
                /* JADX WARN: Incorrect types in method signature: (TEI;)V */
                /* JADX WARN: Multi-variable type inference failed */
                @Override // org.minidns.util.ExceptionCallback
                public void processException(Exception exc) {
                    listSynchronizedList.add(exc);
                    if (listSynchronizedList.size() == collection.size()) {
                        internalMiniDnsFuture.setException(exceptionsWrapper.wrap(listSynchronizedList));
                    }
                }
            });
        }
        return internalMiniDnsFuture;
    }
}
