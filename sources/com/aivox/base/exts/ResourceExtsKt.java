package com.aivox.base.exts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResourceExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\"\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u000b"}, m1901d2 = {"toColor", "", "context", "Landroid/content/Context;", "toDimen", "", "toDimenPx", "toDrawable", "Landroid/graphics/drawable/Drawable;", "toStr", "", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class ResourceExtsKt {
    public static final int toColor(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ContextCompat.getColor(context, i);
    }

    public static final String toStr(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getString(i);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public static final float toDimen(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDimension(i);
    }

    public static final int toDimenPx(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDimensionPixelSize(i);
    }

    public static final Drawable toDrawable(int i, Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ContextCompat.getDrawable(context, i);
    }
}
