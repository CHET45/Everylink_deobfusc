package reactor.core.publisher;

import com.microsoft.azure.storage.Constants;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class Traces {
    static final String CALL_SITE_GLUE = " ⇢ ";
    private static final String PUBLISHER_PACKAGE_PREFIX = "reactor.core.publisher.";
    static final boolean full = Boolean.parseBoolean(System.getProperty("reactor.trace.assembly.fullstacktrace", Constants.FALSE));
    static final Supplier<Supplier<String>> callSiteSupplierFactory = new CallSiteSupplierFactory();

    Traces() {
    }

    static boolean shouldSanitize(String str) {
        return str.startsWith("java.util.function") || str.startsWith("reactor.core.publisher.Mono.onAssembly") || str.startsWith("reactor.core.publisher.Flux.onAssembly") || str.startsWith("reactor.core.publisher.ParallelFlux.onAssembly") || str.startsWith("reactor.core.publisher.SignalLogger") || str.startsWith("reactor.core.publisher.FluxOnAssembly") || str.startsWith("reactor.core.publisher.MonoOnAssembly.") || str.startsWith("reactor.core.publisher.MonoCallableOnAssembly.") || str.startsWith("reactor.core.publisher.FluxCallableOnAssembly.") || str.startsWith("reactor.core.publisher.Hooks") || str.startsWith("sun.reflect") || str.startsWith("java.util.concurrent.ThreadPoolExecutor") || str.startsWith("java.lang.reflect");
    }

    static String extractOperatorAssemblyInformation(String str) {
        String[] strArrExtractOperatorAssemblyInformationParts = extractOperatorAssemblyInformationParts(str);
        int length = strArrExtractOperatorAssemblyInformationParts.length;
        if (length == 0) {
            return "[no operator assembly information]";
        }
        if (length == 1) {
            return strArrExtractOperatorAssemblyInformationParts[0];
        }
        if (length == 2) {
            return strArrExtractOperatorAssemblyInformationParts[0] + CALL_SITE_GLUE + strArrExtractOperatorAssemblyInformationParts[1];
        }
        throw new IllegalStateException("Unexpected number of assembly info parts: " + strArrExtractOperatorAssemblyInformationParts.length);
    }

    static boolean isUserCode(String str) {
        return !str.startsWith(PUBLISHER_PACKAGE_PREFIX) || str.contains("Test");
    }

    static String[] extractOperatorAssemblyInformationParts(String str) {
        Iterator<StackLineView> itTrimmedNonemptyLines = trimmedNonemptyLines(str);
        if (!itTrimmedNonemptyLines.hasNext()) {
            return new String[0];
        }
        StackLineView next = itTrimmedNonemptyLines.next();
        if (next.isUserCode()) {
            return new String[]{next.toString()};
        }
        while (itTrimmedNonemptyLines.hasNext()) {
            StackLineView next2 = itTrimmedNonemptyLines.next();
            if (next2.isUserCode()) {
                return new String[]{next.withoutPublisherPackagePrefix().withoutLocationSuffix().toString(), "at " + next2};
            }
            next = next2;
        }
        return new String[]{next.withoutPublisherPackagePrefix().toString()};
    }

    private static Iterator<StackLineView> trimmedNonemptyLines(final String str) {
        return new Iterator<StackLineView>() { // from class: reactor.core.publisher.Traces.1
            private int index = 0;

            @Nullable
            private StackLineView next = getNextLine();

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.next != null;
            }

            @Override // java.util.Iterator
            public StackLineView next() {
                StackLineView stackLineView = this.next;
                if (stackLineView == null) {
                    throw new NoSuchElementException();
                }
                this.next = getNextLine();
                return stackLineView;
            }

            @Nullable
            private StackLineView getNextLine() {
                while (this.index < str.length()) {
                    int iIndexOf = str.indexOf(10, this.index);
                    if (iIndexOf == -1) {
                        iIndexOf = str.length();
                    }
                    StackLineView stackLineViewTrim = new StackLineView(str, this.index, iIndexOf).trim();
                    this.index = iIndexOf + 1;
                    if (!stackLineViewTrim.isEmpty()) {
                        return stackLineViewTrim;
                    }
                }
                return null;
            }
        };
    }

    static final class StackLineView {
        private final int end;
        private final int start;
        private final String underlying;

        StackLineView(String str, int i, int i2) {
            this.underlying = str;
            this.start = i;
            this.end = i2;
        }

        StackLineView trim() {
            int i = this.start;
            while (i < this.end && this.underlying.charAt(i) <= ' ') {
                i++;
            }
            int i2 = this.end;
            while (i2 > i && this.underlying.charAt(i2 - 1) <= ' ') {
                i2--;
            }
            return (i == this.start && i2 == this.end) ? this : new StackLineView(this.underlying, i, i2);
        }

        boolean isEmpty() {
            return this.start == this.end;
        }

        boolean startsWith(String str) {
            return this.end - this.start >= str.length() && this.underlying.startsWith(str, this.start);
        }

        boolean contains(String str) {
            int iIndexOf = this.underlying.indexOf(str, this.start);
            return iIndexOf >= this.start && iIndexOf + (str.length() - 1) < this.end;
        }

        boolean isUserCode() {
            return !startsWith(Traces.PUBLISHER_PACKAGE_PREFIX) || contains("Test");
        }

        StackLineView withoutLocationSuffix() {
            int iIndexOf = this.underlying.indexOf(40, this.start);
            return (iIndexOf <= 0 || iIndexOf >= this.end) ? this : new StackLineView(this.underlying, this.start, iIndexOf);
        }

        StackLineView withoutPublisherPackagePrefix() {
            return startsWith(Traces.PUBLISHER_PACKAGE_PREFIX) ? new StackLineView(this.underlying, this.start + Traces.PUBLISHER_PACKAGE_PREFIX.length(), this.end) : this;
        }

        public String toString() {
            return this.underlying.substring(this.start, this.end);
        }
    }
}
