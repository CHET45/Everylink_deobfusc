package com.aivox.app.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class TranscribeConversationTextAdapter extends BaseQuickAdapter<Transcribe, BaseViewHolder> implements IConversationAdapter {
    private int mCurSpeakPosition;
    private boolean mIsAutoSpeakMode;
    private final boolean mMySide;

    public TranscribeConversationTextAdapter(int i, boolean z) {
        super(i);
        this.mCurSpeakPosition = -1;
        this.mMySide = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Transcribe transcribe) {
        int i;
        boolean z = this.mMySide == transcribe.isConversationLeft();
        baseViewHolder.setVisible(C0726R.id.iv_to_audio, !z);
        baseViewHolder.setText(C0726R.id.tv_content, z ? transcribe.getOnebest() + transcribe.getVar() : transcribe.getTranslate());
        int i2 = C0726R.id.tv_content;
        if (transcribe.isConversationLeft()) {
            if (this.mMySide) {
                i = C0874R.color.txt_color_primary;
            } else {
                i = C0874R.color.txt_color_primary;
            }
        } else {
            i = C0874R.color.txt_color_highlight;
        }
        baseViewHolder.setTextColor(i2, ColorUtils.getColor(i));
        baseViewHolder.setText(C0726R.id.tv_time, DateUtil.numberToTime((int) ((Long.parseLong(transcribe.getEndAt()) - Long.parseLong(transcribe.getBeginAt())) / 1000)));
        if (!z) {
            baseViewHolder.addOnClickListener(C0726R.id.f113cl);
        }
        int i3 = C0726R.id.tv_time;
        boolean z2 = this.mMySide;
        baseViewHolder.setTextColor(i3, ColorUtils.getColor(C0874R.color.txt_color_primary));
        String str = (String) SPUtil.get(SPUtil.USER_AVATAR, "");
        if (transcribe.isConversationLeft()) {
            if (BaseStringUtil.isNotEmpty(str)) {
                ImageLoaderFactory.getLoader().displayImage((ImageView) baseViewHolder.getView(C0726R.id.iv_avatar), str);
            } else {
                baseViewHolder.setImageResource(C0726R.id.iv_avatar, C1034R.drawable.ic_avatar_me);
            }
        } else {
            baseViewHolder.setImageResource(C0726R.id.iv_avatar, C1034R.drawable.ic_avatar_other);
        }
        if (this.mCurSpeakPosition == baseViewHolder.getBindingAdapterPosition()) {
            if (!z) {
                baseViewHolder.setVisible(C0726R.id.iv_to_audio, true);
            }
            baseViewHolder.setBackgroundRes(C0726R.id.iv_to_audio, this.mMySide ? C1034R.drawable.trans_play_black_anim : C1034R.drawable.trans_play_anim);
            ((AnimationDrawable) baseViewHolder.getView(C0726R.id.iv_to_audio).getBackground()).start();
            return;
        }
        if (!z && this.mIsAutoSpeakMode) {
            baseViewHolder.setVisible(C0726R.id.iv_to_audio, false);
        }
        Drawable background = baseViewHolder.getView(C0726R.id.iv_to_audio).getBackground();
        if (background instanceof AnimationDrawable) {
            ((AnimationDrawable) background).stop();
        }
        baseViewHolder.setBackgroundRes(C0726R.id.iv_to_audio, this.mMySide ? C1034R.drawable.ic_trans_play_frame_black_3 : C1034R.drawable.ic_trans_play_frame_3);
    }

    @Override // com.aivox.app.adapter.IConversationAdapter
    public void setAutoSpeakMode(boolean z) {
        this.mIsAutoSpeakMode = z;
        notifyDataSetChanged();
    }

    @Override // com.aivox.app.adapter.IConversationAdapter
    public void startTtsAnim(int i) {
        this.mCurSpeakPosition = i;
        notifyDataSetChanged();
    }

    @Override // com.aivox.app.adapter.IConversationAdapter
    public void stopTtsAnim() {
        this.mCurSpeakPosition = -1;
        notifyDataSetChanged();
    }
}
