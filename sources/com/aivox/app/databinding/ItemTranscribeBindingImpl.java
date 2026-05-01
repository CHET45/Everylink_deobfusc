package com.aivox.app.databinding;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.view.EditTextInList;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.RoundCornerContainer;
import com.aivox.common_ui.RoundedCornerBitmap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ItemTranscribeBindingImpl extends ItemTranscribeBinding {
    private static final ViewDataBinding.IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds;
    private long mDirtyFlags;
    private final Group mboundView7;

    @Override // androidx.databinding.ViewDataBinding
    protected boolean onFieldChange(int i, Object obj, int i2) {
        return false;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sViewsWithIds = sparseIntArray;
        sparseIntArray.put(C0726R.id.f113cl, 9);
        sparseIntArray.put(C0726R.id.ll_time_and_info, 10);
        sparseIntArray.put(C0726R.id.ll_speaker_info, 11);
        sparseIntArray.put(C0726R.id.iv_head, 12);
        sparseIntArray.put(C0726R.id.rc_speaker_avatar_text, 13);
        sparseIntArray.put(C0726R.id.tv_speaker_name_text, 14);
        sparseIntArray.put(C0726R.id.tv_speaker_name, 15);
        sparseIntArray.put(C0726R.id.iv_playing, 16);
        sparseIntArray.put(C0726R.id.cb_select, 17);
        sparseIntArray.put(C0726R.id.iv_trump, 18);
        sparseIntArray.put(C0726R.id.line_content, 19);
        sparseIntArray.put(C0726R.id.cl_mark, 20);
    }

    public ItemTranscribeBindingImpl(DataBindingComponent dataBindingComponent, View view2) {
        this(dataBindingComponent, view2, mapBindings(dataBindingComponent, view2, 21, sIncludes, sViewsWithIds));
    }

    private ItemTranscribeBindingImpl(DataBindingComponent dataBindingComponent, View view2, Object[] objArr) {
        super(dataBindingComponent, view2, 0, (CheckBox) objArr[17], (ConstraintLayout) objArr[9], (ConstraintLayout) objArr[20], (ConstraintLayout) objArr[0], (RoundedCornerBitmap) objArr[12], (ImageView) objArr[2], (ImageView) objArr[16], (ImageView) objArr[18], (View) objArr[19], (LinearLayout) objArr[11], (LinearLayout) objArr[10], (RoundCornerContainer) objArr[13], (RecyclerView) objArr[8], (EditTextInList) objArr[3], (TextView) objArr[6], (TextView) objArr[5], (TextView) objArr[15], (TextView) objArr[14], (TextView) objArr[1], (TextView) objArr[4]);
        this.mDirtyFlags = -1L;
        this.clTotal.setTag(null);
        this.ivImgTag.setTag(null);
        Group group = (Group) objArr[7];
        this.mboundView7 = group;
        group.setTag(null);
        this.rcvImgs.setTag(null);
        this.tvContent.setTag(null);
        this.tvNoteMarks.setTag(null);
        this.tvNoteMarksSimple.setTag(null);
        this.tvTime.setTag(null);
        this.tvTranslateResult.setTag(null);
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

    @Override // com.aivox.app.databinding.ItemTranscribeBinding
    public void setXmlmodel(Transcribe transcribe) {
        this.mXmlmodel = transcribe;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(6);
        super.requestRebind();
    }

    @Override // androidx.databinding.ViewDataBinding
    protected void executeBindings() {
        long j;
        int i;
        String strNumberToTime;
        boolean z;
        int i2;
        int i3;
        String str;
        String str2;
        String str3;
        String str4;
        String beginAt;
        AudioMarkBean audioMark;
        String var;
        boolean zIsHasBindImg;
        String translate;
        List<Transcribe.TagImgBean> imageList;
        synchronized (this) {
            j = this.mDirtyFlags;
            this.mDirtyFlags = 0L;
        }
        Transcribe transcribe = this.mXmlmodel;
        long j2 = j & 3;
        if (j2 != 0) {
            if (transcribe != null) {
                beginAt = transcribe.getBeginAt();
                audioMark = transcribe.getAudioMark();
                var = transcribe.getVar();
                zIsHasBindImg = transcribe.isHasBindImg();
                translate = transcribe.getTranslate();
                imageList = transcribe.getImageList();
            } else {
                beginAt = null;
                audioMark = null;
                var = null;
                zIsHasBindImg = false;
                translate = null;
                imageList = null;
            }
            if (j2 != 0) {
                j |= zIsHasBindImg ? 128L : 64L;
            }
            Long lValueOf = Long.valueOf(beginAt);
            boolean zIsEmpty = BaseStringUtil.isEmpty(var);
            int i4 = zIsHasBindImg ? 0 : 8;
            boolean zIsEmpty2 = BaseStringUtil.isEmpty(translate);
            if ((j & 3) != 0) {
                j = zIsEmpty ? j | 8 : j | 4;
            }
            if ((j & 3) != 0) {
                j |= zIsEmpty2 ? 512L : 256L;
            }
            String content = audioMark != null ? audioMark.getContent() : null;
            int size = imageList != null ? imageList.size() : 0;
            long jSafeUnbox = ViewDataBinding.safeUnbox(lValueOf);
            int i5 = zIsEmpty2 ? 8 : 0;
            boolean z2 = size > 0;
            if ((j & 3) != 0) {
                j |= z2 ? 32L : 16L;
            }
            long j3 = jSafeUnbox / 1000;
            int i6 = z2 ? 0 : 8;
            strNumberToTime = DateUtil.numberToTime((int) j3);
            z = zIsEmpty;
            str3 = translate;
            i2 = i5;
            i = i4;
            str2 = var;
            str = content;
            i3 = i6;
        } else {
            i = 0;
            strNumberToTime = null;
            z = false;
            i2 = 0;
            i3 = 0;
            str = null;
            str2 = null;
            str3 = null;
        }
        String onebest = ((8 & j) == 0 || transcribe == null) ? null : transcribe.getOnebest();
        long j4 = j & 3;
        if (j4 != 0) {
            if (z) {
                str2 = onebest;
            }
            str4 = str2;
        } else {
            str4 = null;
        }
        if (j4 != 0) {
            this.ivImgTag.setVisibility(i);
            this.mboundView7.setVisibility(i2);
            this.rcvImgs.setVisibility(i3);
            TextViewBindingAdapter.setText(this.tvContent, str4);
            TextViewBindingAdapter.setText(this.tvNoteMarks, str);
            TextViewBindingAdapter.setText(this.tvNoteMarksSimple, str);
            TextViewBindingAdapter.setText(this.tvTime, strNumberToTime);
            TextViewBindingAdapter.setText(this.tvTranslateResult, str3);
        }
    }
}
