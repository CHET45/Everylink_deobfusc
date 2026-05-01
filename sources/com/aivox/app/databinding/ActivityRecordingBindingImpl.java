package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.LangSwitchView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityRecordingBindingImpl extends ActivityRecordingBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback109;
    private final View.OnClickListener mCallback110;
    private final View.OnClickListener mCallback111;
    private final View.OnClickListener mCallback112;
    private final View.OnClickListener mCallback113;
    private final View.OnClickListener mCallback114;
    private final View.OnClickListener mCallback115;
    private long mDirtyFlags;
    private final LinearLayout mboundView0;
    private final ImageView mboundView1;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.common_toolbar, 8);
        sparseIntArray.put(C0726R.id.iv_leading, 9);
        sparseIntArray.put(C0726R.id.tv_record_time, 10);
        sparseIntArray.put(C0726R.id.rl_btn, 11);
        sparseIntArray.put(C0726R.id.right_icon, 12);
        sparseIntArray.put(C0726R.id.right_two_icon, 13);
        sparseIntArray.put(C0726R.id.iv_tts_switch, 14);
        sparseIntArray.put(C0726R.id.ll_notify, 15);
        sparseIntArray.put(C0726R.id.tv_notify, 16);
        sparseIntArray.put(C0726R.id.fl_container, 17);
        sparseIntArray.put(C0726R.id.tv_only_recording, 18);
        sparseIntArray.put(C0726R.id.iv_recording, 19);
        sparseIntArray.put(C0726R.id.tv_on_pause, 20);
        sparseIntArray.put(C0726R.id.cl_bottom, 21);
    }

    public ActivityRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 22, sIncludes, sViewsWithIds));
    }

    private ActivityRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ImageView) objArr[7], (ImageView) objArr[5], (ImageView) objArr[6], (LinearLayout) objArr[21], (Toolbar) objArr[8], (FrameLayout) objArr[17], (ImageView) objArr[4], (ImageView) objArr[3], (ImageView) objArr[9], (ImageView) objArr[19], (ImageView) objArr[14], (LangSwitchView) objArr[2], (LinearLayout) objArr[15], (ImageView) objArr[12], (ImageView) objArr[13], (LinearLayout) objArr[11], (TextView) objArr[16], (TextView) objArr[20], (TextView) objArr[18], (TextView) objArr[10]);
        this.mDirtyFlags = -1L;
        this.audioOverIv.setTag(null);
        this.audioStartIv.setTag(null);
        this.audioStopIv.setTag(null);
        this.ivAddMark.setTag(null);
        this.ivInsertImg.setTag(null);
        this.langSwitchView.setTag(null);
        LinearLayout linearLayout = (LinearLayout) objArr[0];
        this.mboundView0 = linearLayout;
        linearLayout.setTag(null);
        ImageView imageView = (ImageView) objArr[1];
        this.mboundView1 = imageView;
        imageView.setTag(null);
        setRootTag(view2);
        this.mCallback114 = new OnClickListener(this, 6);
        this.mCallback112 = new OnClickListener(this, 4);
        this.mCallback110 = new OnClickListener(this, 2);
        this.mCallback115 = new OnClickListener(this, 7);
        this.mCallback113 = new OnClickListener(this, 5);
        this.mCallback111 = new OnClickListener(this, 3);
        this.mCallback109 = new OnClickListener(this, 1);
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
        if (1 != i) {
            return false;
        }
        setClickListener((OnViewClickListener) obj);
        return true;
    }

    @Override // com.aivox.app.databinding.ActivityRecordingBinding
    public void setClickListener(OnViewClickListener onViewClickListener) {
        this.mClickListener = onViewClickListener;
        synchronized (this) {
            this.mDirtyFlags |= 1;
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
        OnViewClickListener onViewClickListener = this.mClickListener;
        if ((j & 2) != 0) {
            this.audioOverIv.setOnClickListener(this.mCallback115);
            this.audioStartIv.setOnClickListener(this.mCallback113);
            this.audioStopIv.setOnClickListener(this.mCallback114);
            this.ivAddMark.setOnClickListener(this.mCallback112);
            this.ivInsertImg.setOnClickListener(this.mCallback111);
            this.langSwitchView.setOnClickListener(this.mCallback110);
            this.mboundView1.setOnClickListener(this.mCallback109);
        }
    }

    @Override // com.aivox.app.generated.callback.OnClickListener.Listener
    public final void _internalCallbackOnClick(int i, View view2) {
        switch (i) {
            case 1:
                OnViewClickListener onViewClickListener = this.mClickListener;
                if (onViewClickListener != null) {
                    onViewClickListener.onViewClick(view2);
                }
                break;
            case 2:
                OnViewClickListener onViewClickListener2 = this.mClickListener;
                if (onViewClickListener2 != null) {
                    onViewClickListener2.onViewClick(view2);
                }
                break;
            case 3:
                OnViewClickListener onViewClickListener3 = this.mClickListener;
                if (onViewClickListener3 != null) {
                    onViewClickListener3.onViewClick(view2);
                }
                break;
            case 4:
                OnViewClickListener onViewClickListener4 = this.mClickListener;
                if (onViewClickListener4 != null) {
                    onViewClickListener4.onViewClick(view2);
                }
                break;
            case 5:
                OnViewClickListener onViewClickListener5 = this.mClickListener;
                if (onViewClickListener5 != null) {
                    onViewClickListener5.onViewClick(view2);
                }
                break;
            case 6:
                OnViewClickListener onViewClickListener6 = this.mClickListener;
                if (onViewClickListener6 != null) {
                    onViewClickListener6.onViewClick(view2);
                }
                break;
            case 7:
                OnViewClickListener onViewClickListener7 = this.mClickListener;
                if (onViewClickListener7 != null) {
                    onViewClickListener7.onViewClick(view2);
                }
                break;
        }
    }
}
