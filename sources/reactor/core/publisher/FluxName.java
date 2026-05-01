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
final class FluxName<T> extends InternalFluxOperator<T, T> {
    final String name;
    final List<Tuple2<String, String>> tagsWithDuplicates;

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return coreSubscriber;
    }

    static <T> Flux<T> createOrAppend(Flux<T> flux, String str) {
        Objects.requireNonNull(str, "name");
        if (flux instanceof FluxName) {
            FluxName fluxName = (FluxName) flux;
            return new FluxName(fluxName.source, str, fluxName.tagsWithDuplicates);
        }
        if (flux instanceof FluxNameFuseable) {
            FluxNameFuseable fluxNameFuseable = (FluxNameFuseable) flux;
            return new FluxNameFuseable(fluxNameFuseable.source, str, fluxNameFuseable.tagsWithDuplicates);
        }
        if (flux instanceof Fuseable) {
            return new FluxNameFuseable(flux, str, null);
        }
        return new FluxName(flux, str, null);
    }

    static <T> Flux<T> createOrAppend(Flux<T> flux, String str, String str2) {
        List listSingletonList;
        List listSingletonList2;
        Objects.requireNonNull(str, "tagName");
        Objects.requireNonNull(str2, "tagValue");
        Tuple2 tuple2M1988of = Tuples.m1988of(str, str2);
        if (flux instanceof FluxName) {
            FluxName fluxName = (FluxName) flux;
            if (fluxName.tagsWithDuplicates != null) {
                listSingletonList2 = new LinkedList(fluxName.tagsWithDuplicates);
                listSingletonList2.add(tuple2M1988of);
            } else {
                listSingletonList2 = Collections.singletonList(tuple2M1988of);
            }
            return new FluxName(fluxName.source, fluxName.name, listSingletonList2);
        }
        if (flux instanceof FluxNameFuseable) {
            FluxNameFuseable fluxNameFuseable = (FluxNameFuseable) flux;
            if (fluxNameFuseable.tagsWithDuplicates != null) {
                listSingletonList = new LinkedList(fluxNameFuseable.tagsWithDuplicates);
                listSingletonList.add(tuple2M1988of);
            } else {
                listSingletonList = Collections.singletonList(tuple2M1988of);
            }
            return new FluxNameFuseable(fluxNameFuseable.source, fluxNameFuseable.name, listSingletonList);
        }
        if (flux instanceof Fuseable) {
            return new FluxNameFuseable(flux, null, Collections.singletonList(tuple2M1988of));
        }
        return new FluxName(flux, null, Collections.singletonList(tuple2M1988of));
    }

    FluxName(Flux<? extends T> flux, @Nullable String str, @Nullable List<Tuple2<String, String>> list) {
        super(flux);
        this.name = str;
        this.tagsWithDuplicates = list;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
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
