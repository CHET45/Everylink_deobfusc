package com.github.houbb.heaven.support.concurrent.context;

import com.github.houbb.heaven.util.lang.SpiUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes3.dex */
public class CrossThreadWrapper<T> implements Runnable, Callable<T> {
    private static List<CrossThreadProcessor> processorList;
    private Callable<T> callable;
    private final Map<String, Object> context = new HashMap();
    private Runnable runnable;

    static {
        processorList = new ArrayList();
        processorList = SpiUtil.getClassImplList(CrossThreadProcessor.class);
    }

    public CrossThreadWrapper(Runnable runnable) {
        initContext();
        this.runnable = runnable;
    }

    public CrossThreadWrapper(Callable<T> callable) {
        initContext();
        this.callable = callable;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            beforeExecute();
            this.runnable.run();
        } finally {
            afterExecute();
        }
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        try {
            beforeExecute();
            return this.callable.call();
        } finally {
            afterExecute();
        }
    }

    protected void initContext() {
        Iterator<CrossThreadProcessor> it = processorList.iterator();
        while (it.hasNext()) {
            it.next().initContext(this.context);
        }
    }

    protected void beforeExecute() {
        Iterator<CrossThreadProcessor> it = processorList.iterator();
        while (it.hasNext()) {
            it.next().beforeExecute(this.context);
        }
    }

    protected void afterExecute() {
        Iterator<CrossThreadProcessor> it = processorList.iterator();
        while (it.hasNext()) {
            it.next().afterExecute(this.context);
        }
    }
}
