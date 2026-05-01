package com.github.houbb.heaven.response.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/* JADX INFO: loaded from: classes3.dex */
public final class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static Throwable unwrapThrowable(Throwable th) {
        while (true) {
            if (th instanceof InvocationTargetException) {
                th = ((InvocationTargetException) th).getTargetException();
            } else {
                if (!(th instanceof UndeclaredThrowableException)) {
                    return th;
                }
                th = ((UndeclaredThrowableException) th).getUndeclaredThrowable();
            }
        }
    }

    public static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException();
    }

    public static RuntimeException unchecked(Throwable th) {
        if (th instanceof RuntimeException) {
            return (RuntimeException) th;
        }
        return new RuntimeException(th);
    }

    public static String getStackTraceAsString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String getErrorMessageWithNestedException(Throwable th) {
        Throwable cause = th.getCause();
        return th.getMessage() + " nested exception is " + cause.getClass().getName() + ":" + cause.getMessage();
    }

    public static Throwable getRootCause(Throwable th) {
        Throwable th2 = null;
        while (true) {
            Throwable cause = th.getCause();
            if (cause == null) {
                return th2;
            }
            th2 = cause;
        }
    }

    public static boolean isCausedBy(Exception exc, Class<? extends Exception>... clsArr) {
        Throwable cause = exc;
        while (true) {
            if (cause == null) {
                return false;
            }
            for (Class<? extends Exception> cls : clsArr) {
                if (cls.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
    }

    public static Throwable getActualThrowable(Throwable th) {
        return InvocationTargetException.class.equals(th.getClass()) ? ((InvocationTargetException) th).getTargetException() : th;
    }
}
