package reactor.core.publisher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelFluxName<T> extends ParallelFlux<T> implements Scannable {
    final String name;
    final ParallelFlux<T> source;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> parallelFlux, String str) {
        Objects.requireNonNull(str, "name");
        if (parallelFlux instanceof ParallelFluxName) {
            ParallelFluxName parallelFluxName = (ParallelFluxName) parallelFlux;
            return new ParallelFluxName(parallelFluxName.source, str, parallelFluxName.tagsWithDuplicates);
        }
        return new ParallelFluxName(parallelFlux, str, null);
    }

    static <T> ParallelFlux<T> createOrAppend(ParallelFlux<T> parallelFlux, String str, String str2) {
        List listSingletonList;
        Objects.requireNonNull(str, "tagName");
        Objects.requireNonNull(str2, "tagValue");
        Tuple2 tuple2M1988of = Tuples.m1988of(str, str2);
        if (parallelFlux instanceof ParallelFluxName) {
            ParallelFluxName parallelFluxName = (ParallelFluxName) parallelFlux;
            if (parallelFluxName.tagsWithDuplicates != null) {
                listSingletonList = new LinkedList(parallelFluxName.tagsWithDuplicates);
                listSingletonList.add(tuple2M1988of);
            } else {
                listSingletonList = Collections.singletonList(tuple2M1988of);
            }
            return new ParallelFluxName(parallelFluxName.source, parallelFluxName.name, listSingletonList);
        }
        return new ParallelFluxName(parallelFlux, null, Collections.singletonList(tuple2M1988of));
    }

    ParallelFluxName(ParallelFlux<T> parallelFlux, @Nullable String str, @Nullable List<Tuple2<String, String>> list) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.name = str;
        this.tagsWithDuplicates = list;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        List<Tuple2<String, String>> list;
        if (attr == Scannable.Attr.NAME) {
            return this.name;
        }
        if (attr == Scannable.Attr.TAGS && (list = this.tagsWithDuplicates) != null) {
            return list.stream();
        }
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.valueOf(getPrefetch());
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        this.source.subscribe(coreSubscriberArr);
    }
}
