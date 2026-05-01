package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import java.util.NoSuchElementException;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public final class Optional<T> {
    private static final Optional<?> EMPTY = new Optional<>();
    private final T value;

    private Optional() {
        this.value = null;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    private Optional(T t) {
        this.value = (T) Objects.requireNonNull(t);
    }

    /* JADX INFO: renamed from: of */
    public static <T> Optional<T> m536of(T t) {
        return new Optional<>(t);
    }

    public static <T> Optional<T> ofNullable(T t) {
        if (t == null) {
            return empty();
        }
        return m536of(t);
    }

    public T get() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public <R> R getCastOrNull(Class<R> cls) {
        if (ObjectUtil.isNotNull(this.value)) {
            return this.value;
        }
        return null;
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public boolean isNotPresent() {
        return this.value == null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Throwable */
    public <X extends Throwable> T orElseThrow(X x) throws Throwable {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw x;
    }

    public T orElseNull() {
        T t = this.value;
        if (t != null) {
            return t;
        }
        return null;
    }

    public T orDefault(T t) {
        T t2 = this.value;
        return t2 != null ? t2 : t;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Optional) {
            return Objects.equals(this.value, ((Optional) obj).value);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    public String toString() {
        T t = this.value;
        return t != null ? String.format("Optional[%s]", t) : "Optional.empty";
    }
}
