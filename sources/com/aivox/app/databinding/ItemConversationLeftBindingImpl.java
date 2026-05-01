package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.RoundedCornerBitmap;

/* JADX INFO: loaded from: classes.dex */
public class ItemConversationLeftBindingImpl extends ItemConversationLeftBinding {
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
        sparseIntArray.put(C0726R.id.f113cl, 1);
        sparseIntArray.put(C0726R.id.iv_avatar, 2);
        sparseIntArray.put(C0726R.id.tv_time, 3);
        sparseIntArray.put(C0726R.id.iv_to_audio, 4);
        sparseIntArray.put(C0726R.id.tv_content, 5);
        sparseIntArray.put(C0726R.id.line_content, 6);
        sparseIntArray.put(C0726R.id.tv_translate_result, 7);
    }

    public ItemConversationLeftBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 8, sIncludes, sViewsWithIds));
    }

    private ItemConversationLeftBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[1], (RoundedCornerBitmap) objArr[2], (ImageView) objArr[4], (ImageView) objArr[6], (TextView) objArr[5], (TextView) objArr[3], (TextView) objArr[7]);
        this.mDirtyFlags = -1L;
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
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
        if (6 != i) {
            return false;
        }
        setXmlmodel((Transcribe) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ItemConversationLeftBinding
    public void setXmlmodel(Transcribe transcribe) {
        this.mXmlmodel = transcribe;
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        synchronized (this) {
            this.mDirtyFlags = 0L;
        }
    }
}
