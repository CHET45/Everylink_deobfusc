package com.github.houbb.heaven.support.concurrent.context;

import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface CrossThreadProcessor {
    void afterExecute(Map<String, Object> map);

    void beforeExecute(Map<String, Object> map);

    void initContext(Map<String, Object> map);
}
