package reactor.util;

import io.micrometer.core.instrument.MeterRegistry;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
public class Metrics {
    static final boolean isMicrometerAvailable;

    static {
        boolean z;
        try {
            io.micrometer.core.instrument.Metrics.globalRegistry.getRegistries();
            z = true;
        } catch (Throwable unused) {
            z = false;
        }
        isMicrometerAvailable = z;
    }

    @Deprecated
    public static final boolean isInstrumentationAvailable() {
        return isMicrometerAvailable;
    }

    @Deprecated
    public static class MicrometerConfiguration {
        private static MeterRegistry registry = io.micrometer.core.instrument.Metrics.globalRegistry;

        @Deprecated
        public static MeterRegistry useRegistry(MeterRegistry meterRegistry) {
            MeterRegistry meterRegistry2 = registry;
            registry = meterRegistry;
            return meterRegistry2;
        }

        @Deprecated
        public static MeterRegistry getRegistry() {
            return registry;
        }
    }
}
