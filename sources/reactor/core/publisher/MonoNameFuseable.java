package reactor.core.publisher;

import java.util.List;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

/* JADX INFO: loaded from: classes5.dex */
final class MonoNameFuseable<T> extends InternalMonoOperator<T, T> implements Fuseable {
    final String name;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return coreSubscriber;
    }

    MonoNameFuseable(Mono<? extends T> mono, @Nullable String str, @Nullable List<Tuple2<String, String>> list) {
        super(mono);
        this.name = str;
        this.tagsWithDuplicates = list;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        List<Tuple2<String, String>> list;
        if (attr == Scannable.Attr.NAME) {
            return this.name;
        }
        if (attr == Scannable.Attr.TAGS && (list = this.tagsWithDuplicates) != null) {
            return list.stream();
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(attr);
    }
}
