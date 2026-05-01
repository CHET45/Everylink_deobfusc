package com.aivox.app.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.motion.widget.Key;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.SpanUtils;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.MultipleRecordItem;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class AudioListAdapter extends BaseMultiItemQuickAdapter<MultipleRecordItem, BaseViewHolder> implements SwipedSelectItemTouchCallBack.OnItemSwipeListener {
    private static final int MODE_NORMAL = 0;
    private static final int MODE_SELECT_AUDIO = 1;
    private ImageView ivTransStatus;
    private LinearLayout llTransStatus;
    private final Context mContext;
    private final List<MultipleRecordItem> mDataBean;
    private boolean mIsHomeList;
    private final boolean mIsLocalList;
    private int mListMode;
    private OnSelectNumChangedListener mNumChangedListener;
    private final List<AudioInfoBean> mSelectAudio;

    public interface OnSelectNumChangedListener {
        void onAudioSelectedChanged(List<AudioInfoBean> list, boolean z);
    }

    public AudioListAdapter(Context context, List<MultipleRecordItem> list) {
        this(context, list, false);
        this.mIsHomeList = true;
    }

    public AudioListAdapter(Context context, List<MultipleRecordItem> list, boolean z) {
        super(list);
        this.mListMode = 0;
        this.mSelectAudio = new ArrayList();
        this.mContext = context;
        this.mDataBean = list;
        this.mIsLocalList = z;
        addItemType(1, C0726R.layout.item_audio_list_layout);
    }

    public void setNumChangeListener(OnSelectNumChangedListener onSelectNumChangedListener) {
        this.mNumChangedListener = onSelectNumChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MultipleRecordItem multipleRecordItem) {
        String colourText;
        CharSequence charSequenceCreate;
        if (baseViewHolder.getItemViewType() != 1) {
            return;
        }
        AudioInfoBean audioInfo = multipleRecordItem.getAudioInfo();
        boolean zAnyMatch = this.mIsLocalList ? false : audioInfo.getTagGroupList().stream().anyMatch(new Predicate() { // from class: com.aivox.app.adapter.AudioListAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AudioListAdapter.lambda$convert$0((AudioInfoBean.TagGroupList) obj);
            }
        });
        this.ivTransStatus = (ImageView) baseViewHolder.itemView.findViewById(C0726R.id.iv_trans_status);
        this.llTransStatus = (LinearLayout) baseViewHolder.itemView.findViewById(C0726R.id.ll_trans_status);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -2);
        if (multipleRecordItem.getAudioInfo().isTheLast()) {
            layoutParams.setMargins(SizeUtils.dp2px(20.0f), SizeUtils.dp2px(8.0f), SizeUtils.dp2px(20.0f), SizeUtils.dp2px(100.0f));
            baseViewHolder.getView(C0726R.id.f113cl).setLayoutParams(layoutParams);
        } else {
            layoutParams.setMargins(SizeUtils.dp2px(20.0f), SizeUtils.dp2px(8.0f), SizeUtils.dp2px(20.0f), SizeUtils.dp2px(8.0f));
            baseViewHolder.getView(C0726R.id.f113cl).setLayoutParams(layoutParams);
        }
        baseViewHolder.addOnClickListener(C0726R.id.iv_favorite);
        baseViewHolder.addOnClickListener(C0726R.id.cl_content);
        baseViewHolder.addOnLongClickListener(C0726R.id.cl_content);
        baseViewHolder.setImageResource(C0726R.id.iv_favorite, zAnyMatch ? C1034R.drawable.ic_star_highlight : C1034R.drawable.ic_star_normal);
        baseViewHolder.setGone(C0726R.id.iv_favorite, false);
        baseViewHolder.setGone(C0726R.id.iv_ai_summary, audioInfo.isExistAiSummary());
        baseViewHolder.setText(C0726R.id.item_info_duration, DateUtil.numberToTime(audioInfo.getFileTime()));
        baseViewHolder.setText(C0726R.id.item_info_tv, DateUtil.getDateForList(audioInfo.getStartTime(), this.mIsHomeList) + " • ");
        baseViewHolder.setBackgroundRes(C1034R.id.cl_content, this.mSelectAudio.contains(audioInfo) ? C1034R.drawable.bg_record_item_select : C1034R.drawable.bg_record_item_normal);
        List<AudioInfoBean.TagGroupList> tagGroupList = audioInfo.getTagGroupList();
        Iterator<AudioInfoBean.TagGroupList> it = tagGroupList.iterator();
        while (true) {
            if (!it.hasNext()) {
                colourText = "";
                break;
            }
            AudioInfoBean.TagGroupList next = it.next();
            if (BaseStringUtil.isNotEmpty(next.getColourText())) {
                colourText = next.getColourText();
                break;
            }
        }
        if (CollectionUtils.isNotEmpty(tagGroupList) && BaseStringUtil.isNotEmpty(colourText)) {
            baseViewHolder.setBackgroundColor(C0726R.id.v_folder, Color.parseColor(colourText));
        } else {
            baseViewHolder.setBackgroundColor(C0726R.id.v_folder, Color.parseColor("#00000000"));
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(baseViewHolder.itemView.findViewById(C1034R.id.iv_loading), Key.ROTATION, 0.0f, 360.0f);
        objectAnimatorOfFloat.setDuration(800L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        objectAnimatorOfFloat.setRepeatCount(-1);
        if ((audioInfo.getTitleStatus() == 1 && audioInfo.getTitle().isEmpty() && DataHandle.getIns().getAiNamingAudioId() == audioInfo.getId()) || audioInfo.getTitleStatus() == 2) {
            baseViewHolder.setText(C0726R.id.item_name, C0874R.string.ai_easy_namer);
            baseViewHolder.setTextColor(C0726R.id.item_name, ColorUtils.getColor(C0874R.color.txt_color_highlight));
            baseViewHolder.setGone(C0726R.id.iv_loading, true);
            objectAnimatorOfFloat.start();
        } else {
            int i = C0726R.id.item_name;
            if (audioInfo.getTitle().isEmpty()) {
                charSequenceCreate = audioInfo.getStartTime().replace(ExifInterface.GPS_DIRECTION_TRUE, " ");
            } else {
                charSequenceCreate = getSpanUtils(audioInfo.getTitleStatus() == 3 ? C0874R.color.txt_color_highlight : C0874R.color.txt_color_primary, audioInfo).create();
            }
            baseViewHolder.setText(i, charSequenceCreate);
            baseViewHolder.setGone(C1034R.id.iv_loading, false);
            objectAnimatorOfFloat.pause();
        }
        refreshUploadAndTranscribeState(audioInfo);
    }

    static /* synthetic */ boolean lambda$convert$0(AudioInfoBean.TagGroupList tagGroupList) {
        return tagGroupList.getType().intValue() == Constant.FOLDER_TYPE_FAVORITE;
    }

    private void refreshUploadAndTranscribeState(AudioInfoBean audioInfoBean) {
        this.llTransStatus.setVisibility(0);
        int state = audioInfoBean.getState();
        this.ivTransStatus.setBackgroundResource(C1034R.drawable.transcribe_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.ivTransStatus.getBackground();
        if (state == MyEnum.AUDIO_UPLOAD_STATE.NONE.type || state == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_FAIL.type) {
            animationDrawable.stop();
            refreshTranscribeBtn(audioInfoBean);
        } else {
            if (state == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_SUCCESS.type) {
                animationDrawable.stop();
                refreshTranscribeBtn(audioInfoBean);
                audioInfoBean.setState(MyEnum.AUDIO_UPLOAD_STATE.NONE.type);
                return;
            }
            animationDrawable.start();
        }
    }

    private void refreshTranscribeBtn(AudioInfoBean audioInfoBean) {
        int isTrans = audioInfoBean.getIsTrans();
        this.ivTransStatus.setBackgroundResource(C1034R.drawable.transcribe_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.ivTransStatus.getBackground();
        if (isTrans == MyEnum.TRANS_STATE.ON_TRANS.type) {
            animationDrawable.start();
            return;
        }
        if (isTrans == MyEnum.TRANS_STATE.NOT_TRANS.type || isTrans == MyEnum.TRANS_STATE.TRANS_FAIL.type) {
            animationDrawable.stop();
            this.ivTransStatus.setBackgroundResource(C1034R.drawable.ic_transcribe_frame_0);
        } else {
            animationDrawable.stop();
            this.ivTransStatus.setBackgroundResource(0);
            this.llTransStatus.setVisibility(8);
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
            onSelectNumChangedListener.onAudioSelectedChanged(this.mSelectAudio, this.mIsLocalList);
        }
        notifyDataSetChanged();
    }

    private SpanUtils getSpanUtils(int i, AudioInfoBean audioInfoBean) {
        SpanUtils spanUtils = new SpanUtils();
        String lowerCase = BaseStringUtil.isEmpty(audioInfoBean.getTitle()) ? "" : audioInfoBean.getTitle().toLowerCase();
        String lowerCase2 = BaseStringUtil.isEmpty(audioInfoBean.getKeywords()) ? "" : audioInfoBean.getKeywords().toLowerCase();
        if (BaseStringUtil.isNotEmpty(lowerCase2) && lowerCase.contains(lowerCase2)) {
            while (BaseStringUtil.isNotEmpty(lowerCase2) && lowerCase.contains(lowerCase2)) {
                spanUtils.append(lowerCase.substring(0, lowerCase.indexOf(lowerCase2))).setForegroundColor(this.mContext.getResources().getColor(i));
                spanUtils.append(lowerCase.substring(lowerCase.indexOf(lowerCase2), lowerCase.indexOf(lowerCase2) + lowerCase2.length())).setForegroundColor(this.mContext.getResources().getColor(C0874R.color.txt_color_highlight));
                lowerCase = lowerCase.substring(lowerCase.indexOf(lowerCase2) + lowerCase2.length());
            }
            spanUtils.append(lowerCase).setForegroundColor(this.mContext.getResources().getColor(i));
        } else {
            spanUtils.append(audioInfoBean.getTitle()).setForegroundColor(this.mContext.getResources().getColor(i));
        }
        return spanUtils;
    }

    @Override // com.aivox.app.util.SwipedSelectItemTouchCallBack.OnItemSwipeListener
    public void onSwipeSelected(int i) {
        if (getItemViewType(i) != 1) {
            return;
        }
        AudioInfoBean audioInfo = this.mDataBean.get(i).getAudioInfo();
        if (this.mListMode == 0) {
            this.mListMode = 1;
            this.mSelectAudio.add(audioInfo);
            notifyDataSetChanged();
        } else {
            if (this.mSelectAudio.contains(audioInfo)) {
                this.mSelectAudio.remove(audioInfo);
            } else {
                if (this.mSelectAudio.size() == 20) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.file_select_limit));
                    return;
                }
                this.mSelectAudio.add(audioInfo);
            }
            notifyItemChanged(i);
        }
        if (this.mSelectAudio.isEmpty()) {
            this.mListMode = 0;
            notifyDataSetChanged();
        }
        OnSelectNumChangedListener onSelectNumChangedListener = this.mNumChangedListener;
        if (onSelectNumChangedListener != null) {
            onSelectNumChangedListener.onAudioSelectedChanged(this.mSelectAudio, this.mIsLocalList);
        }
    }
}
