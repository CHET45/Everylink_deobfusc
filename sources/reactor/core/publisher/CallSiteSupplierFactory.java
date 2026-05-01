package reactor.core.publisher;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import sun.misc.JavaLangAccess;
import sun.misc.SharedSecrets;

/* JADX INFO: loaded from: classes5.dex */
class CallSiteSupplierFactory implements Supplier<Supplier<String>> {
    static final Supplier<Supplier<String>> supplier = (Supplier) Stream.of((Object[]) new String[]{CallSiteSupplierFactory.class.getName() + "$SharedSecretsCallSiteSupplierFactory", CallSiteSupplierFactory.class.getName() + "$ExceptionCallSiteSupplierFactory"}).flatMap(new Function() { // from class: reactor.core.publisher.CallSiteSupplierFactory$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return CallSiteSupplierFactory.lambda$static$0((String) obj);
        }
    }).findFirst().orElseThrow(new Supplier() { // from class: reactor.core.publisher.CallSiteSupplierFactory$$ExternalSyntheticLambda1
        @Override // java.util.function.Supplier
        public final Object get() {
            return CallSiteSupplierFactory.lambda$static$1();
        }
    });

    CallSiteSupplierFactory() {
    }

    static /* synthetic */ Stream lambda$static$0(String str) {
        try {
            return Stream.of((Supplier) Class.forName(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        } catch (LinkageError unused) {
            return Stream.empty();
        } catch (Throwable unused2) {
            return Stream.empty();
        }
    }

    static /* synthetic */ IllegalStateException lambda$static$1() {
        return new IllegalStateException("Valid strategy not found");
    }

    @Override // java.util.function.Supplier
    public Supplier<String> get() {
        return supplier.get();
    }

    static class SharedSecretsCallSiteSupplierFactory implements Supplier<Supplier<String>> {
        SharedSecretsCallSiteSupplierFactory() {
        }

        static {
            SharedSecrets.getJavaLangAccess();
        }

        @Override // java.util.function.Supplier
        public Supplier<String> get() {
            return new TracingException();
        }

        static class TracingException extends Throwable implements Supplier<String> {
            static final JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();

            TracingException() {
            }

            @Override // java.util.function.Supplier
            public String get() {
                int stackTraceDepth = javaLangAccess.getStackTraceDepth(this);
                StackTraceElement stackTraceElement = null;
                for (int i = 4; i < stackTraceDepth; i++) {
                    StackTraceElement stackTraceElement2 = javaLangAccess.getStackTraceElement(this, i);
                    String className = stackTraceElement2.getClassName();
                    if (Traces.isUserCode(className)) {
                        StringBuilder sb = new StringBuilder();
                        if (stackTraceElement != null) {
                            sb.append("\t").append(stackTraceElement.toString()).append("\n");
                        }
                        sb.append("\t").append(stackTraceElement2.toString()).append("\n");
                        return sb.toString();
                    }
                    if (Traces.full || stackTraceElement2.getLineNumber() > 1) {
                        String str = className + "." + stackTraceElement2.getMethodName();
                        if (Traces.full || !Traces.shouldSanitize(str)) {
                            stackTraceElement = stackTraceElement2;
                        }
                    }
                }
                return "";
            }
        }
    }

    static class ExceptionCallSiteSupplierFactory implements Supplier<Supplier<String>> {
        ExceptionCallSiteSupplierFactory() {
        }

        @Override // java.util.function.Supplier
        public Supplier<String> get() {
            return new TracingException();
        }

        static class TracingException extends Throwable implements Supplier<String> {
            TracingException() {
            }

            @Override // java.util.function.Supplier
            public String get() {
                StackTraceElement[] stackTrace = getStackTrace();
                StackTraceElement stackTraceElement = null;
                for (int i = 4; i < stackTrace.length; i++) {
                    StackTraceElement stackTraceElement2 = stackTrace[i];
                    String className = stackTraceElement2.getClassName();
                    if (Traces.isUserCode(className)) {
                        StringBuilder sb = new StringBuilder();
                        if (stackTraceElement != null) {
                            sb.append("\t").append(stackTraceElement.toString()).append("\n");
                        }
                        sb.append("\t").append(stackTraceElement2.toString()).append("\n");
                        return sb.toString();
                    }
                    if (Traces.full || stackTraceElement2.getLineNumber() > 1) {
                        String str = className + "." + stackTraceElement2.getMethodName();
                        if (Traces.full || !Traces.shouldSanitize(str)) {
                            stackTraceElement = stackTraceElement2;
                        }
                    }
                }
                return "";
            }
        }
    }
}
