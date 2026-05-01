package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityEarPhoneConversationRecordingBindingImpl extends ActivityEarPhoneConversationRecordingBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback20;
    private final View.OnClickListener mCallback21;
    private final View.OnClickListener mCallback22;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.title_view, 5);
        sparseIntArray.put(C0726R.id.recyclerview, 6);
        sparseIntArray.put(C0726R.id.ll_wave, 7);
        sparseIntArray.put(C0726R.id.iv_wave, 8);
        sparseIntArray.put(C0726R.id.tv_speak_hint, 9);
        sparseIntArray.put(C0726R.id.ll_bottom, 10);
        sparseIntArray.put(C0726R.id.tv_left_recording, 11);
        sparseIntArray.put(C0726R.id.tv_right_recording, 12);
    }

    public ActivityEarPhoneConversationRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 13, sIncludes, sViewsWithIds));
    }

    private ActivityEarPhoneConversationRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[1], (ImageView) objArr[3], (ImageView) objArr[8], (ConstraintLayout) objArr[10], (LinearLayout) objArr[7], (RecyclerView) objArr[6], (HeadTitleLinearView) objArr[5], (TextView) objArr[2], (TextView) objArr[4], (TextView) objArr[11], (TextView) objArr[12], (TextView) objArr[9]);
        this.mDirtyFlags = -1L;
        this.clConversation.setTag(null);
        this.ivSwitchLang.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        this.tvLangMySide.setTag(null);
        this.tvLangOtherSide.setTag(null);
        setRootTag(view2);
        this.mCallback21 = new OnClickListener(this, 2);
        this.mCallback22 = new OnClickListener(this, 3);
        this.mCallback20 = new OnClickListener(this, 1);
        invalidateAll();
    }

    @Override // androidx.databinding.ViewDataBinding
    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4L;
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
        if (2 == i) {
            setIsFaceMode((Boolean) obj);
            return true;
        }
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ActivityEarPhoneConversationRecordingBinding
    public void setIsFaceMode(Boolean bool) {
        this.mIsFaceMode = bool;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // com.aivox.app.databinding.ActivityEarPhoneConversationRecordingBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(1);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Boolean bool = this.mIsFaceMode;
        OnViewClickListener onViewClickListener = this.mClickListener;
        long j2 = j & 5;
        int i = 0;
        if (j2 != 0) {
            boolean zSafeUnbox = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= zSafeUnbox ? 16L : 8L;
            }
            if (zSafeUnbox) {
                i = 8;
            }
        }
        if ((j & 5) != 0) {
            this.clConversation.setVisibility(i);
        }
        if ((j & 4) != 0) {
            this.ivSwitchLang.setOnClickListener(this.mCallback21);
            this.tvLangMySide.setOnClickListener(this.mCallback20);
            this.tvLangOtherSide.setOnClickListener(this.mCallback22);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        OnViewClickListener onViewClickListener;
        if (i == 1) {
            OnViewClickListener onViewClickListener2 = this.mClickListener;
            if (onViewClickListener2 != null) {
                onViewClickListener2.onViewClick(view2);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3 && (onViewClickListener = this.mClickListener) != null) {
                onViewClickListener.onViewClick(view2);
                return;
            }
            return;
        }
        OnViewClickListener onViewClickListener3 = this.mClickListener;
        if (onViewClickListener3 != null) {
            onViewClickListener3.onViewClick(view2);
        }
    }
}
