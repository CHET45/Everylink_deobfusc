package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.aivox.app.C0726R;
import com.aivox.app.generated.callback.OnClickListener;
import com.aivox.app.view.ConversationRecordIconView;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.common_ui.HeadTitleLinearView;

/* JADX INFO: loaded from: classes.dex */
public class ActivityConversationRecordingBindingImpl extends ActivityConversationRecordingBinding implements OnClickListener.Listener {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private final View.OnClickListener mCallback10;
    private final View.OnClickListener mCallback11;
    private final View.OnClickListener mCallback12;
    private final View.OnClickListener mCallback13;
    private final View.OnClickListener mCallback6;
    private final View.OnClickListener mCallback7;
    private final View.OnClickListener mCallback8;
    private final View.OnClickListener mCallback9;
    private long mDirtyFlags;
    private final ConstraintLayout mboundView0;
    private final ConstraintLayout mboundView7;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.title_view, 11);
        sparseIntArray.put(C0726R.id.ll_content, 12);
        sparseIntArray.put(C0726R.id.ll_noti, 13);
        sparseIntArray.put(C0726R.id.tv_noti, 14);
        sparseIntArray.put(C0726R.id.recyclerview, 15);
        sparseIntArray.put(C0726R.id.ll_wave, 16);
        sparseIntArray.put(C0726R.id.iv_wave, 17);
        sparseIntArray.put(C0726R.id.cl_bottom, 18);
        sparseIntArray.put(C0726R.id.ll_lang_selected, 19);
        sparseIntArray.put(C0726R.id.tv_language_from, 20);
        sparseIntArray.put(C0726R.id.tv_language_to, 21);
        sparseIntArray.put(C0726R.id.group_lang_set_s2s, 22);
        sparseIntArray.put(C0726R.id.cl_top, 23);
        sparseIntArray.put(C0726R.id.rl_top, 24);
        sparseIntArray.put(C0726R.id.iv_mic_top_start, 25);
        sparseIntArray.put(C0726R.id.iv_wave_top, 26);
        sparseIntArray.put(C0726R.id.rv_top, 27);
        sparseIntArray.put(C0726R.id.tv_notice_top, 28);
        sparseIntArray.put(C0726R.id.cl_btm, 29);
        sparseIntArray.put(C0726R.id.rl_bottom, 30);
        sparseIntArray.put(C0726R.id.iv_mic_bottom_start, 31);
        sparseIntArray.put(C0726R.id.iv_wave_bottom, 32);
        sparseIntArray.put(C0726R.id.rv_btm, 33);
        sparseIntArray.put(C0726R.id.tv_notice_btm, 34);
        sparseIntArray.put(C0726R.id.ll_lang_change_face, 35);
        sparseIntArray.put(C0726R.id.tv_language_from_face, 36);
        sparseIntArray.put(C0726R.id.tv_language_to_face, 37);
        sparseIntArray.put(C0726R.id.ll_lang_set_f2f, 38);
        sparseIntArray.put(C0726R.id.vp_guide, 39);
        sparseIntArray.put(C0726R.id.view_skip, 40);
        sparseIntArray.put(C0726R.id.view_prev, 41);
        sparseIntArray.put(C0726R.id.view_next, 42);
        sparseIntArray.put(C0726R.id.gp_guide, 43);
    }

    public ActivityConversationRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 44, sIncludes, sViewsWithIds));
    }

    private ActivityConversationRecordingBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (ConstraintLayout) objArr[18], (ConstraintLayout) objArr[29], (ConstraintLayout) objArr[1], (ConstraintLayout) objArr[23], (Group) objArr[43], (Group) objArr[22], (ImageView) objArr[31], (ConversationRecordIconView) objArr[2], (ConversationRecordIconView) objArr[3], (ImageView) objArr[25], (ImageView) objArr[9], (ImageView) objArr[5], (ImageView) objArr[17], (ImageView) objArr[32], (ImageView) objArr[26], (LinearLayout) objArr[12], (LinearLayout) objArr[35], (LinearLayout) objArr[19], (LinearLayout) objArr[38], (LinearLayout) objArr[13], (LinearLayout) objArr[16], (RecyclerView) objArr[15], (RelativeLayout) objArr[30], (RelativeLayout) objArr[24], (RecyclerView) objArr[33], (RecyclerView) objArr[27], (HeadTitleLinearView) objArr[11], (TextView) objArr[8], (TextView) objArr[4], (TextView) objArr[10], (TextView) objArr[6], (TextView) objArr[20], (TextView) objArr[36], (TextView) objArr[21], (TextView) objArr[37], (TextView) objArr[14], (TextView) objArr[34], (TextView) objArr[28], (View) objArr[42], (View) objArr[41], (View) objArr[40], (ViewPager) objArr[39]);
        this.mDirtyFlags = -1L;
        this.clConversation.setTag(null);
        this.ivMicLeft.setTag(null);
        this.ivMicRight.setTag(null);
        this.ivSwitchLangF2f.setTag(null);
        this.ivSwitchLangS2s.setTag(null);
        ConstraintLayout constraintLayout = (ConstraintLayout) objArr[0];
        this.mboundView0 = constraintLayout;
        constraintLayout.setTag(null);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) objArr[7];
        this.mboundView7 = constraintLayout2;
        constraintLayout2.setTag(null);
        this.tvLangMySideF2f.setTag(null);
        this.tvLangMySideS2s.setTag(null);
        this.tvLangOtherSideF2f.setTag(null);
        this.tvLangOtherSideS2s.setTag(null);
        setRootTag(view2);
        this.mCallback11 = new OnClickListener(this, 6);
        this.mCallback6 = new OnClickListener(this, 1);
        this.mCallback12 = new OnClickListener(this, 7);
        this.mCallback9 = new OnClickListener(this, 4);
        this.mCallback13 = new OnClickListener(this, 8);
        this.mCallback8 = new OnClickListener(this, 3);
        this.mCallback10 = new OnClickListener(this, 5);
        this.mCallback7 = new OnClickListener(this, 2);
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

    @Override // com.aivox.app.databinding.ActivityConversationRecordingBinding
    public void setIsFaceMode(Boolean bool) {
        this.mIsFaceMode = bool;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(2);
        super.requestRebind();
    }

    @Override // com.aivox.app.databinding.ActivityConversationRecordingBinding
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
        int i;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Boolean bool = this.mIsFaceMode;
        OnViewClickListener onViewClickListener = this.mClickListener;
        long j2 = j & 5;
        if (j2 != 0) {
            boolean zSafeUnbox = ViewDataBinding.safeUnbox(bool);
            if (j2 != 0) {
                j |= zSafeUnbox ? 80L : 40L;
            }
            int i2 = zSafeUnbox ? 8 : 0;
            i = zSafeUnbox ? 0 : 8;
            i = i2;
        } else {
            i = 0;
        }
        if ((5 & j) != 0) {
            this.clConversation.setVisibility(i);
            this.mboundView7.setVisibility(i);
        }
        if ((j & 4) != 0) {
            this.ivMicLeft.setOnClickListener(this.mCallback6);
            this.ivMicRight.setOnClickListener(this.mCallback7);
            this.ivSwitchLangF2f.setOnClickListener(this.mCallback12);
            this.ivSwitchLangS2s.setOnClickListener(this.mCallback9);
            this.tvLangMySideF2f.setOnClickListener(this.mCallback11);
            this.tvLangMySideS2s.setOnClickListener(this.mCallback8);
            this.tvLangOtherSideF2f.setOnClickListener(this.mCallback13);
            this.tvLangOtherSideS2s.setOnClickListener(this.mCallback10);
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
            case 8:
                OnViewClickListener onViewClickListener8 = this.mClickListener;
                if (onViewClickListener8 != null) {
                    onViewClickListener8.onViewClick(view2);
                }
                break;
        }
    }
}
