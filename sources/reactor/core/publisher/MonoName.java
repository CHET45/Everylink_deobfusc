package reactor.core.publisher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/* JADX INFO: loaded from: classes5.dex */
final class MonoName<T> extends InternalMonoOperator<T, T> {
    final String name;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return coreSubscriber;
    }

    static <T> Mono<T> createOrAppend(Mono<T> mono, String str) {
        Objects.requireNonNull(str, "name");
        if (mono instanceof MonoName) {
            MonoName monoName = (MonoName) mono;
            return new MonoName(monoName.source, str, monoName.tagsWithDuplicates);
        }
        if (mono instanceof MonoNameFuseable) {
            MonoNameFuseable monoNameFuseable = (MonoNameFuseable) mono;
            return new MonoNameFuseable(monoNameFuseable.source, str, monoNameFuseable.tagsWithDuplicates);
        }
        if (mono instanceof Fuseable) {
            return new MonoNameFuseable(mono, str, null);
        }
        return new MonoName(mono, str, null);
    }

    static <T> Mono<T> createOrAppend(Mono<T> mono, String str, String str2) {
        List listSingletonList;
        List listSingletonList2;
        Objects.requireNonNull(str, "tagName");
        Objects.requireNonNull(str2, "tagValue");
        Tuple2 tuple2M1988of = Tuples.m1988of(str, str2);
        if (mono instanceof MonoName) {
            MonoName monoName = (MonoName) mono;
            if (monoName.tagsWithDuplicates != null) {
                listSingletonList2 = new LinkedList(monoName.tagsWithDuplicates);
                listSingletonList2.add(tuple2M1988of);
            } else {
                listSingletonList2 = Collections.singletonList(tuple2M1988of);
            }
            return new MonoName(monoName.source, monoName.name, listSingletonList2);
        }
        if (mono instanceof MonoNameFuseable) {
            MonoNameFuseable monoNameFuseable = (MonoNameFuseable) mono;
            if (monoNameFuseable.tagsWithDuplicates != null) {
                listSingletonList = new LinkedList(monoNameFuseable.tagsWithDuplicates);
                listSingletonList.add(tuple2M1988of);
            } else {
                listSingletonList = Collections.singletonList(tuple2M1988of);
            }
            return new MonoNameFuseable(monoNameFuseable.source, monoNameFuseable.name, listSingletonList);
        }
        if (mono instanceof Fuseable) {
            return new MonoNameFuseable(mono, null, Collections.singletonList(tuple2M1988of));
        }
        return new MonoName(mono, null, Collections.singletonList(tuple2M1988of));
    }

    MonoName(Mono<? extends T> mono, @Nullable String str, @Nullable List<Tuple2<String, String>> list) {
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
