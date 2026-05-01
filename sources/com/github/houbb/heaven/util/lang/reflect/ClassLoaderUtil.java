package com.github.houbb.heaven.util.lang.reflect;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/* JADX INFO: loaded from: classes3.dex */
public class ClassLoaderUtil {
    public static Class<?> loadClass(String str, String str2) {
        try {
            return new URLClassLoader(new URL[]{new File(str).toURI().toURL()}, Thread.currentThread().getContextClassLoader()).loadClass(str2);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + str2, e);
        } catch (MalformedURLException e2) {
            throw new RuntimeException("Failed to convert file to URL", e2);
        }
    }

    public static Class<?> loadClassFromFile(String str) {
        if (str != null) {
            try {
                if (!str.isEmpty()) {
                    File file = new File(str);
                    try {
                        return new URLClassLoader(new URL[]{file.toURI().toURL()}).loadClass(file.getName().substring(0, file.getName().length() - 6));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Class not found: " + str, e);
                    }
                }
            } catch (MalformedURLException e2) {
                throw new RuntimeException(e2);
            }
        }
        throw new IllegalArgumentException("Class file path must not be null or empty.");
    }
}
