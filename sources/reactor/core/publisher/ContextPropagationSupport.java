package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.Scannable;
import reactor.util.Logger;
import reactor.util.Loggers;

/* JADX INFO: loaded from: classes5.dex */
final class ContextPropagationSupport {
    static final boolean isContextPropagation101OnClasspath;
    static final boolean isContextPropagation103OnClasspath;
    static final boolean isContextPropagationOnClasspath;
    static final Logger LOGGER = Loggers.getLogger((Class<?>) ContextPropagationSupport.class);
    static boolean propagateContextToThreadLocals = false;

    ContextPropagationSupport() {
    }

    static {
        boolean z;
        Throwable th;
        boolean z2;
        boolean z3 = false;
        try {
            Class.forName("io.micrometer.context.ContextRegistry");
            z = true;
            try {
                Class.forName("io.micrometer.context.ThreadLocalAccessor").getDeclaredMethod("restore", Object.class);
            } catch (ClassNotFoundException | LinkageError | NoSuchMethodException unused) {
            } catch (Throwable th2) {
                th = th2;
                z2 = false;
                LOGGER.error("Unexpected exception while detecting ContextPropagation feature. The feature is considered disabled due to this:", th);
                isContextPropagationOnClasspath = z;
                isContextPropagation101OnClasspath = z2;
                isContextPropagation103OnClasspath = z3;
                if (z) {
                    return;
                } else {
                    return;
                }
            }
            try {
                Class.forName("io.micrometer.context.ContextSnapshotFactory");
                z3 = true;
                z2 = z3;
            } catch (ClassNotFoundException | LinkageError | NoSuchMethodException unused2) {
                z2 = z;
            } catch (Throwable th3) {
                th = th3;
                th = th;
                z2 = z;
                LOGGER.error("Unexpected exception while detecting ContextPropagation feature. The feature is considered disabled due to this:", th);
            }
        } catch (ClassNotFoundException | LinkageError | NoSuchMethodException unused3) {
            z = false;
        } catch (Throwable th4) {
            th = th4;
            z = false;
        }
        isContextPropagationOnClasspath = z;
        isContextPropagation101OnClasspath = z2;
        isContextPropagation103OnClasspath = z3;
        if (z || z3) {
            return;
        }
        LOGGER.warn("context-propagation version below 1.0.3 can cause memory leaks when working with scope-based ThreadLocalAccessors, please upgrade!");
    }

    static boolean isContextPropagationAvailable() {
        return isContextPropagationOnClasspath;
    }

    static boolean isContextPropagation101Available() {
        return isContextPropagation101OnClasspath;
    }

    static boolean isContextPropagation103Available() {
        return isContextPropagation103OnClasspath;
    }

    static boolean shouldPropagateContextToThreadLocals() {
        return isContextPropagationOnClasspath && propagateContextToThreadLocals;
    }

    static boolean shouldWrapPublisher(Publisher<?> publisher) {
        return shouldPropagateContextToThreadLocals() && !((Boolean) Scannable.from(publisher).scanOrDefault(InternalProducerAttr.INSTANCE, false)).booleanValue();
    }

    static boolean shouldRestoreThreadLocalsInSomeOperators() {
        return isContextPropagationOnClasspath && !propagateContextToThreadLocals;
    }
}
