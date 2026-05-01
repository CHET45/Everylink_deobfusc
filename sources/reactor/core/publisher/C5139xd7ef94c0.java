package reactor.core.publisher;

import java.util.function.Function;
import reactor.core.publisher.FluxBufferTimeout;

/* JADX INFO: renamed from: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda2 */
/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class C5139xd7ef94c0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.setTerminated(((Long) obj).longValue()));
    }
}
