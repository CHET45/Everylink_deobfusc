package com.aivox.base.exts;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ObserverExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0007\u001a2\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0007¨\u0006\t"}, m1901d2 = {"observeNonNull", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/lifecycle/LiveData;", "owner", "Landroidx/lifecycle/LifecycleOwner;", "block", "Lkotlin/Function1;", "observeNullable", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ObserverExtsKt {
    public static final <T> void observeNullable(LiveData<T> liveData, LifecycleOwner owner, final Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        liveData.observe(owner, new Observer() { // from class: com.aivox.base.exts.ObserverExtsKt$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ObserverExtsKt.observeNullable$lambda$0(block, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeNullable$lambda$0(Function1 block, Object obj) {
        Intrinsics.checkNotNullParameter(block, "$block");
        block.invoke(obj);
    }

    public static final <T> void observeNonNull(LiveData<T> liveData, LifecycleOwner owner, final Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        liveData.observe(owner, new Observer() { // from class: com.aivox.base.exts.ObserverExtsKt$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ObserverExtsKt.observeNonNull$lambda$1(block, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeNonNull$lambda$1(Function1 block, Object obj) {
        Intrinsics.checkNotNullParameter(block, "$block");
        if (obj != null) {
            block.invoke(obj);
        }
    }
}
