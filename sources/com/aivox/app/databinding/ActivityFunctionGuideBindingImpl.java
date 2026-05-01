package com.aivox.app.databinding;

import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.databinding.adapters.TextViewBindingAdapter;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityFunctionGuideBindingImpl extends ActivityFunctionGuideBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.title_view, 3);
        sparseIntArray.put(C0726R.id.iv_step_1, 4);
        sparseIntArray.put(C0726R.id.tv_step_1, 5);
        sparseIntArray.put(C0726R.id.tv_step_1_msg, 6);
        sparseIntArray.put(C0726R.id.tv_step_2, 7);
    }

    public ActivityFunctionGuideBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 8, sIncludes, sViewsWithIds));
    }

    private ActivityFunctionGuideBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[4], (ImageView) objArr[1], (HeadTitleLinearView) objArr[3], (TextView) objArr[5], (TextView) objArr[6], (TextView) objArr[7], (TextView) objArr[2]);
        this.mDirtyFlags = -1L;
        this.ivStep2.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvStep2Msg.setTag(null);
        setRootTag(view2);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2L;
        }
        requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean hasPendingBindings() {
        synchronized (this) {
            return this.mDirtyFlags != 0;
        }
    }

    @Override // androidx.databinding.ViewDataBinding
    public boolean setVariable(int i, Object obj) {
        if (5 != i) {
            return false;
        }
        setType((Integer) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ActivityFunctionGuideBinding
    public void setType(Integer num) {
        this.mType = num;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(5);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        Drawable drawable;
        String string;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Integer num = this.mType;
        long j2 = j & 3;
        if (j2 != 0) {
            boolean z = ViewDataBinding.safeUnbox(num) == 1;
            if (j2 != 0) {
                j |= z ? 40L : 20L;
            }
            drawable = AppCompatResources.getDrawable(this.ivStep2.getContext(), z ? C1034R.drawable.pic_step_2_abridgement : C1034R.drawable.pic_step_2_notes);
            string = this.tvStep2Msg.getResources().getString(z ? C0874R.string.step_2_abridgement : C0874R.string.step_2_notes);
        } else {
            drawable = null;
            string = null;
        }
        if ((j & 3) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivStep2, drawable);
            TextViewBindingAdapter.setText(this.tvStep2Msg, string);
        }
    }
}
