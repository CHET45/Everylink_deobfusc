package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.util.XPopupUtils;

/* JADX INFO: loaded from: classes4.dex */
public class VerticalRecyclerView extends RecyclerView {
    public VerticalRecyclerView(Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupDivider(Boolean bool) {
        SmartDivider smartDivider = new SmartDivider(getContext(), 1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(getResources().getColor(bool.booleanValue() ? C2213R.color._xpopup_list_dark_divider : C2213R.color._xpopup_list_divider));
        gradientDrawable.setSize(10, XPopupUtils.dp2px(getContext(), 0.5f));
        smartDivider.setDrawable(gradientDrawable);
        addItemDecoration(smartDivider);
    }
}
