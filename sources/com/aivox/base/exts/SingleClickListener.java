package com.aivox.base.exts;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B%\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0002H\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, m1901d2 = {"Lcom/aivox/base/exts/SingleClickListener;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/view/View;", "Landroid/view/View$OnClickListener;", "interval", "", "clickFunc", "Lkotlin/Function1;", "", "(JLkotlin/jvm/functions/Function1;)V", "lastClickTime", "onClick", "v", "base_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class SingleClickListener<T extends View> implements View.OnClickListener {
    private Function1<? super T, Unit> clickFunc;
    private final long interval;
    private long lastClickTime;

    public SingleClickListener(long j, Function1<? super T, Unit> function1) {
        this.interval = j;
        this.clickFunc = function1;
    }

    public /* synthetic */ SingleClickListener(long j, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 500L : j, function1);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastClickTime > this.interval) {
            Function1<? super T, Unit> function1 = this.clickFunc;
            if (function1 != null) {
                function1.invoke(v);
            }
            this.lastClickTime = jCurrentTimeMillis;
        }
    }
}
