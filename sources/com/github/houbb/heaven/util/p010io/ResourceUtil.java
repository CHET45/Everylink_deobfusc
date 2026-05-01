package com.github.houbb.heaven.util.p010io;

/* JADX INFO: loaded from: classes3.dex */
public class ResourceUtil {
    public static String getCurrentThreadContextClassLoaderResource() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    public static String getSystemClassLoaderResource() {
        return ClassLoader.getSystemResource("").getPath();
    }

    public static String getClassLoaderResource(Class<?> cls) {
        return cls.getClassLoader().getResource("").getPath();
    }

    public static String getClassRootResource(Class<?> cls) {
        return cls.getResource("/").getPath();
    }

    public static String getClassResource(Class<?> cls) {
        return cls.getResource("").getPath();
    }
}
