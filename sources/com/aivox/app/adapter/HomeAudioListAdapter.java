package com.aivox.app.adapter;

import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.Key;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common_ui.C1034R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HomeAudioListAdapter extends BaseQuickAdapter<AudioInfoBean, BaseViewHolder> {
    private static final int MODE_NORMAL = 0;
    private static final int MODE_SELECT_AUDIO = 1;
    private ImageView ivSyncStatus;
    private int mListMode;
    private OnSelectNumChangedListener mNumChangedListener;
    private final List<AudioInfoBean> mSelectAudio;

    public interface OnSelectNumChangedListener {
        void onAudioSelectedChanged(List<AudioInfoBean> list);
    }

    public HomeAudioListAdapter(int i) {
        super(i);
        this.mSelectAudio = new ArrayList();
        this.mListMode = 0;
    }

    public void setNumChangeListener(OnSelectNumChangedListener onSelectNumChangedListener) {
        this.mNumChangedListener = onSelectNumChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AudioInfoBean audioInfoBean) {
        String title;
        String strNumberToTime = DateUtil.numberToTime(audioInfoBean.getFileTime());
        if (BaseStringUtil.isEmpty(audioInfoBean.getTitle())) {
            title = DateUtil.getDateForHomeList(audioInfoBean.getStartTime(), true);
        } else {
            title = audioInfoBean.getTitle();
        }
        baseViewHolder.setText(C0726R.id.tv_item_name, title);
        baseViewHolder.setText(C0726R.id.tv_item_date, DateUtil.getDateForHomeList(audioInfoBean.getStartTime(), false));
        baseViewHolder.setText(C0726R.id.tv_item_duration, strNumberToTime);
        baseViewHolder.addOnClickListener(C0726R.id.cl_item_content);
        baseViewHolder.addOnLongClickListener(C0726R.id.cl_item_content);
        this.ivSyncStatus = (ImageView) baseViewHolder.itemView.findViewById(C0726R.id.iv_file_sync);
        if (this.mSelectAudio.contains(audioInfoBean)) {
            baseViewHolder.setBackgroundRes(C0726R.id.cl_item_content, C1034R.drawable.bg_home_enter_selected);
        } else {
            baseViewHolder.setBackgroundRes(C0726R.id.cl_item_content, C1034R.drawable.bg_home_enter);
        }
        refreshUploadAndTranscribeState(audioInfoBean);
    }

    private void refreshUploadAndTranscribeState(AudioInfoBean audioInfoBean) {
        int state = audioInfoBean.getState();
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivSyncStatus, Key.ROTATION, 360.0f, 0.0f);
        objectAnimatorOfFloat.setDuration(800L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        objectAnimatorOfFloat.setRepeatCount(-1);
        if (state == MyEnum.AUDIO_UPLOAD_STATE.NONE.type || state == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_FAIL.type) {
            this.ivSyncStatus.setVisibility(8);
            objectAnimatorOfFloat.cancel();
            return;
        }
        if (state == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_SUCCESS.type) {
            this.ivSyncStatus.setVisibility(8);
            objectAnimatorOfFloat.cancel();
            audioInfoBean.setState(MyEnum.AUDIO_UPLOAD_STATE.NONE.type);
        } else {
            if (state == MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type) {
                this.ivSyncStatus.setVisibility(0);
                if (objectAnimatorOfFloat.isRunning()) {
                    return;
                }
                objectAnimatorOfFloat.start();
                return;
            }
            this.ivSyncStatus.setVisibility(8);
        }
    }

    public void onItemSelected(int i) {
        AudioInfoBean audioInfoBean = (AudioInfoBean) this.mData.get(i);
        if (this.mListMode == 0) {
            this.mListMode = 1;
            this.mSelectAudio.add(audioInfoBean);
            notifyDataSetChanged();
        } else {
            if (this.mSelectAudio.contains(audioInfoBean)) {
                this.mSelectAudio.remove(audioInfoBean);
            } else {
                if (this.mSelectAudio.size() == 20) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.file_select_limit));
                    return;
                }
                this.mSelectAudio.add(audioInfoBean);
            }
            notifyItemChanged(i);
        }
        if (this.mSelectAudio.isEmpty()) {
            this.mListMode = 0;
            notifyDataSetChanged();
        }
        OnSelectNumChangedListener onSelectNumChangedListener = this.mNumChangedListener;
        if (onSelectNumChangedListener != null) {
            onSelectNumChangedListener.onAudioSelectedChanged(this.mSelectAudio);
        }
    }

    public boolean isAudioSelectMode() {
        return this.mListMode == 1;
    }

    public void quitAudioSelectMode() {
        this.mListMode = 0;
        this.mSelectAudio.clear();
        OnSelectNumChangedListener onSelectNumChangedListener = this.mNumChangedListener;
        if (onSelectNumChangedListener != null) {
            onSelectNumChangedListener.onAudioSelectedChanged(this.mSelectAudio);
        }
        notifyDataSetChanged();
    }
}
