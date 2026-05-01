package com.lxj.easyadapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WrapperUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(m1899bv = {1, 0, 3}, m1900d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J[\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062K\u0010\u0007\u001aG\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u000f0\bJ\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013¨\u0006\u0014"}, m1901d2 = {"Lcom/lxj/easyadapter/WrapperUtils;", "", "()V", "onAttachedToRecyclerView", "", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "fn", "Lkotlin/Function3;", "Landroidx/recyclerview/widget/GridLayoutManager;", "Lkotlin/ParameterName;", "name", "layoutManager", "Landroidx/recyclerview/widget/GridLayoutManager$SpanSizeLookup;", "oldLookup", "", "position", "setFullSpan", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "easy-adapter_release"}, m1902k = 1, m1903mv = {1, 1, 15})
public final class WrapperUtils {
    public static final WrapperUtils INSTANCE = new WrapperUtils();

    private WrapperUtils() {
    }

    public final void onAttachedToRecyclerView(RecyclerView recyclerView, final Function3<? super GridLayoutManager, ? super GridLayoutManager.SpanSizeLookup, ? super Integer, Integer> fn) {
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        Intrinsics.checkParameterIsNotNull(fn, "fn");
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.lxj.easyadapter.WrapperUtils.onAttachedToRecyclerView.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    Function3 function3 = fn;
                    RecyclerView.LayoutManager layoutManager2 = layoutManager;
                    GridLayoutManager.SpanSizeLookup spanSizeLookup2 = spanSizeLookup;
                    Intrinsics.checkExpressionValueIsNotNull(spanSizeLookup2, "spanSizeLookup");
                    return ((Number) function3.invoke(layoutManager2, spanSizeLookup2, Integer.valueOf(position))).intValue();
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    public final void setFullSpan(RecyclerView.ViewHolder holder) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        View view2 = holder.itemView;
        Intrinsics.checkExpressionValueIsNotNull(view2, "holder.itemView");
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        if (layoutParams == null || !(layoutParams instanceof StaggeredGridLayoutManager.LayoutParams)) {
            return;
        }
        ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
    }
}
