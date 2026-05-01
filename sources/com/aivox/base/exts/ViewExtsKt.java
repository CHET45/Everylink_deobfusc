package com.aivox.base.exts;

import android.view.View;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a-\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006\u001a-\u0010\u0007\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\u0006\u001a9\u0010\t\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00022\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0014\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005¢\u0006\u0002\u0010\f¨\u0006\r"}, m1901d2 = {"click", "", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/view/View;", "action", "Lkotlin/Function1;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "longClick", "", "singleClick", "interval", "", "(Landroid/view/View;JLkotlin/jvm/functions/Function1;)V", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ViewExtsKt {
    public static final <T extends View> void click(final T t, final Function1<? super T, Unit> action) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        t.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.base.exts.ViewExtsKt$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewExtsKt.click$lambda$0(action, t, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void click$lambda$0(Function1 action, View this_click, View view2) {
        Intrinsics.checkNotNullParameter(action, "$action");
        Intrinsics.checkNotNullParameter(this_click, "$this_click");
        action.invoke(this_click);
    }

    public static final <T extends View> void longClick(final T t, final Function1<? super T, Boolean> action) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        t.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.aivox.base.exts.ViewExtsKt$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return ViewExtsKt.longClick$lambda$1(action, t, view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean longClick$lambda$1(Function1 action, View this_longClick, View view2) {
        Intrinsics.checkNotNullParameter(action, "$action");
        Intrinsics.checkNotNullParameter(this_longClick, "$this_longClick");
        return ((Boolean) action.invoke(this_longClick)).booleanValue();
    }

    public static /* synthetic */ void singleClick$default(View view2, long j, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 500;
        }
        singleClick(view2, j, function1);
    }

    public static final <T extends View> void singleClick(T t, long j, Function1<? super T, Unit> function1) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        t.setOnClickListener(new SingleClickListener(j, function1));
    }
}
