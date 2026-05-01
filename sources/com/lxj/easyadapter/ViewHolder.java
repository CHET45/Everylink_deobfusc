package com.lxj.easyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewHolder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(m1899bv = {1, 0, 3}, m1900d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\r\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001d\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\u0004\u0018\u0001H\n\"\b\b\u0000\u0010\n*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\fJ\u0016\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, m1901d2 = {"Lcom/lxj/easyadapter/ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "convertView", "Landroid/view/View;", "(Landroid/view/View;)V", "getConvertView", "()Landroid/view/View;", "mViews", "Landroid/util/SparseArray;", "getView", ExifInterface.GPS_DIRECTION_TRUE, "viewId", "", "(I)Landroid/view/View;", "getViewOrNull", "setImageResource", "resId", "setText", "text", "", "Companion", "easy-adapter_release"}, m1902k = 1, m1903mv = {1, 1, 15})
public final class ViewHolder extends RecyclerView.ViewHolder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final View convertView;
    private final SparseArray<View> mViews;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewHolder(View convertView) {
        super(convertView);
        Intrinsics.checkParameterIsNotNull(convertView, "convertView");
        this.convertView = convertView;
        this.mViews = new SparseArray<>();
    }

    public final View getConvertView() {
        return this.convertView;
    }

    public final <T extends View> T getView(int viewId) {
        T t = (T) this.mViews.get(viewId);
        if (t == null) {
            t = (T) this.convertView.findViewById(viewId);
            this.mViews.put(viewId, t);
        }
        if (t != null) {
            return t;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }

    public final <T extends View> T getViewOrNull(int viewId) {
        T t = (T) this.mViews.get(viewId);
        if (t == null) {
            t = (T) this.convertView.findViewById(viewId);
            this.mViews.put(viewId, t);
        }
        if (t instanceof View) {
            return t;
        }
        return null;
    }

    public final ViewHolder setText(int viewId, CharSequence text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        TextView textView = (TextView) getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public final ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = (ImageView) getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
        return this;
    }

    /* JADX INFO: compiled from: ViewHolder.kt */
    @Metadata(m1899bv = {1, 0, 3}, m1900d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, m1901d2 = {"Lcom/lxj/easyadapter/ViewHolder$Companion;", "", "()V", "createViewHolder", "Lcom/lxj/easyadapter/ViewHolder;", "context", "Landroid/content/Context;", "parent", "Landroid/view/ViewGroup;", "layoutId", "", "itemView", "Landroid/view/View;", "easy-adapter_release"}, m1902k = 1, m1903mv = {1, 1, 15})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ViewHolder createViewHolder(View itemView) {
            Intrinsics.checkParameterIsNotNull(itemView, "itemView");
            return new ViewHolder(itemView);
        }

        public final ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(parent, "parent");
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            Intrinsics.checkExpressionValueIsNotNull(itemView, "itemView");
            return new ViewHolder(itemView);
        }
    }
}
