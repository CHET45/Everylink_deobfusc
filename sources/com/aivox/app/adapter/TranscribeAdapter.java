package com.aivox.app.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.databinding.ItemBindingViewHolder;
import com.aivox.base.databinding.MyBindingAdapterJustItem;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.model.AudioContentBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.ColorUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TranscribeAdapter extends MyBindingAdapterJustItem<Transcribe> implements SwipedSelectItemTouchCallBack.OnItemSwipeListener {
    public static final String TAG = "TranscribeAdapter";
    private int avatarStyle;
    private final Context context;
    private int curSpeechIndex;
    private int editId;
    private int editPos;
    private final Map<Integer, String> editted;
    private final boolean isRecording;
    private final int itemLayoutId;
    private int mode;
    private OnCheckChangeListener onCheckChangeListener;
    private RecyclerView rcvImgs;
    private boolean showImage;
    private TextView tvContent;
    private TextView tvTranslate;

    public interface OnCheckChangeListener {
        void checkChange(Transcribe transcribe, boolean z, int i);
    }

    static /* synthetic */ boolean lambda$onBindViewHolder$6(View view2) {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    private enum SPEAKER_AVATAR {
        SPEAKER_A(0, "00", C1034R.drawable.ic_avatar_illustration_0, C1034R.drawable.ic_avatar_animal_0),
        SPEAKER_B(1, "01", C1034R.drawable.ic_avatar_illustration_1, C1034R.drawable.ic_avatar_animal_1),
        SPEAKER_C(2, "02", C1034R.drawable.ic_avatar_illustration_2, C1034R.drawable.ic_avatar_animal_2),
        SPEAKER_D(3, "03", C1034R.drawable.ic_avatar_illustration_3, C1034R.drawable.ic_avatar_animal_3),
        SPEAKER_E(4, "04", C1034R.drawable.ic_avatar_illustration_4, C1034R.drawable.ic_avatar_animal_4),
        SPEAKER_F(5, "05", C1034R.drawable.ic_avatar_illustration_5, C1034R.drawable.ic_avatar_animal_5),
        SPEAKER_G(6, "06", C1034R.drawable.ic_avatar_illustration_6, C1034R.drawable.ic_avatar_animal_6),
        SPEAKER_H(7, "07", C1034R.drawable.ic_avatar_illustration_7, C1034R.drawable.ic_avatar_animal_7),
        SPEAKER_I(8, "08", C1034R.drawable.ic_avatar_illustration_8, C1034R.drawable.ic_avatar_animal_8),
        SPEAKER_J(9, "09", C1034R.drawable.ic_avatar_illustration_9, C1034R.drawable.ic_avatar_animal_9),
        SPEAKER_K(10, "10", C1034R.drawable.ic_avatar_illustration_0, C1034R.drawable.ic_avatar_animal_0),
        SPEAKER_L(11, "11", C1034R.drawable.ic_avatar_illustration_1, C1034R.drawable.ic_avatar_animal_1),
        SPEAKER_M(12, "12", C1034R.drawable.ic_avatar_illustration_2, C1034R.drawable.ic_avatar_animal_2),
        SPEAKER_N(13, "13", C1034R.drawable.ic_avatar_illustration_3, C1034R.drawable.ic_avatar_animal_3),
        SPEAKER_O(14, "14", C1034R.drawable.ic_avatar_illustration_4, C1034R.drawable.ic_avatar_animal_4),
        SPEAKER_P(15, "15", C1034R.drawable.ic_avatar_illustration_5, C1034R.drawable.ic_avatar_animal_5),
        SPEAKER_Q(16, "16", C1034R.drawable.ic_avatar_illustration_6, C1034R.drawable.ic_avatar_animal_6),
        SPEAKER_R(17, "17", C1034R.drawable.ic_avatar_illustration_7, C1034R.drawable.ic_avatar_animal_7),
        SPEAKER_S(18, "18", C1034R.drawable.ic_avatar_illustration_8, C1034R.drawable.ic_avatar_animal_8),
        SPEAKER_T(19, "19", C1034R.drawable.ic_avatar_illustration_9, C1034R.drawable.ic_avatar_animal_9);

        public final int iconResTypeAnimal;
        public final int iconResTypeIllustration;
        public final int index;
        public final String speaker;

        SPEAKER_AVATAR(int i, String str, int i2, int i3) {
            this.index = i;
            this.speaker = str;
            this.iconResTypeIllustration = i2;
            this.iconResTypeAnimal = i3;
        }

        public static int getSpeakerAvatarRes(String str, int i) {
            for (SPEAKER_AVATAR speaker_avatar : values()) {
                if (speaker_avatar.speaker.equals(str)) {
                    if (i == 2) {
                        return speaker_avatar.iconResTypeIllustration;
                    }
                    if (i == 3) {
                        return speaker_avatar.iconResTypeAnimal;
                    }
                }
            }
            return SPEAKER_A.iconResTypeIllustration;
        }
    }

    public TranscribeAdapter(Context context, int i) {
        this(context, i, false);
        this.showImage = false;
    }

    public TranscribeAdapter(Context context, int i, boolean z) {
        super(context, i);
        this.editted = new HashMap();
        this.showImage = true;
        this.editPos = -1;
        this.curSpeechIndex = -1;
        this.avatarStyle = 1;
        this.context = context;
        this.isRecording = z;
        this.itemLayoutId = i;
    }

    @Override // com.aivox.base.databinding.MyBindingAdapterJustItem, androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ItemBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(this.context), this.itemLayoutId, viewGroup, false));
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0194  */
    @Override // com.aivox.base.databinding.MyBindingAdapterJustItem, androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r19, final int r20) {
        /*
            Method dump skipped, instruction units count: 819
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.adapter.TranscribeAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ void m241x9c645920(int i, Transcribe transcribe, View view2) {
        int i2 = this.mode;
        if (i2 == 1) {
            this.editPos = i;
            transcribe.setPick(!transcribe.isPick());
            notifyItemChanged(i);
            OnCheckChangeListener onCheckChangeListener = this.onCheckChangeListener;
            if (onCheckChangeListener != null) {
                onCheckChangeListener.checkChange(transcribe, transcribe.isPick(), i);
                return;
            }
            return;
        }
        if (i2 == 2) {
            this.editPos = i;
            int id = transcribe.getId();
            this.editId = id;
            this.editted.put(Integer.valueOf(id), transcribe.getOnebest());
            notifyDataSetChanged();
            return;
        }
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view2, i);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$1$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ boolean m242x29048421(int i, Transcribe transcribe, View view2) {
        if (this.onItemLongClickListener == null || this.mode != 0) {
            return false;
        }
        this.mode = 1;
        this.editPos = i;
        transcribe.setPick(true);
        notifyDataSetChanged();
        OnCheckChangeListener onCheckChangeListener = this.onCheckChangeListener;
        if (onCheckChangeListener != null) {
            onCheckChangeListener.checkChange(transcribe, transcribe.isPick(), i);
        }
        this.onItemLongClickListener.onItemLongClick(view2, i);
        return false;
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$2$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ void m243xb5a4af22(Transcribe transcribe, int i, View view2) {
        if (this.isRecording) {
            return;
        }
        transcribe.setShowNoteMark(!transcribe.isShowNoteMark());
        notifyItemChanged(i);
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$3$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ void m244x4244da23(Transcribe transcribe, int i, View view2) {
        if (this.isRecording) {
            return;
        }
        transcribe.setShowNoteMark(!transcribe.isShowNoteMark());
        notifyItemChanged(i);
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$4$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ void m245xcee50524(int i, Transcribe transcribe, View view2, boolean z) {
        LogUtil.m337e(TAG, "INDEX : " + i + " " + z + " " + view2.hashCode());
        String string = ((EditText) view2).getText().toString();
        if (BaseStringUtil.isEmpty(string.trim())) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.can_not_edit_to_empty));
            string = transcribe.getOnebest();
        } else {
            this.editted.put(Integer.valueOf(transcribe.getId()), string);
        }
        if (z) {
            TextView textView = this.tvContent;
            ((EditText) textView).setSelection(textView.getText().length());
        } else {
            transcribe.setOnebest(string);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$5$com-aivox-app-adapter-TranscribeAdapter */
    /* synthetic */ void m246x5b853025(int i, Transcribe transcribe, View view2, int i2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(this.rcvImgs, i);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("transcribe", transcribe);
        bundle.putInt("pos", i2);
        ARouterUtils.startWithContext(this.context, MainAction.PHOTO_BROWSE, bundle);
    }

    private void highlightKeyword(TextView textView, Transcribe transcribe, int i, boolean z) {
        String onebest;
        String keywords = transcribe.getKeywords();
        if (z) {
            onebest = transcribe.getTranslate();
        } else {
            onebest = BaseStringUtil.isEmpty(transcribe.getVar()) ? transcribe.getOnebest() : transcribe.getVar();
        }
        int length = 0;
        if (transcribe.getCurSpeakIndex() == i && !z) {
            textView.setTypeface(null, 1);
        } else {
            textView.setTypeface(null, 0);
        }
        if (BaseStringUtil.isEmpty(keywords)) {
            textView.setTextColor(getColorIdsForContent(transcribe, i));
            textView.setText(onebest);
            return;
        }
        if (BaseStringUtil.isNotEmpty(onebest)) {
            String lowerCase = keywords.toLowerCase();
            String lowerCase2 = onebest.toLowerCase();
            SpannableString spannableString = new SpannableString(onebest);
            int iIndexOf = lowerCase2.indexOf(lowerCase);
            while (iIndexOf >= 0) {
                if (length < iIndexOf) {
                    spannableString.setSpan(new ForegroundColorSpan(getColorIdsForContent(transcribe, i)), length, iIndexOf, 33);
                }
                spannableString.setSpan(new ForegroundColorSpan(ColorUtils.getColor(C0874R.color.txt_color_highlight)), iIndexOf, keywords.length() + iIndexOf, 33);
                length = keywords.length() + iIndexOf;
                iIndexOf = lowerCase2.indexOf(lowerCase, length);
            }
            if (length < onebest.length()) {
                spannableString.setSpan(new ForegroundColorSpan(getColorIdsForContent(transcribe, i)), length, onebest.length(), 33);
            }
            textView.setText(spannableString);
        }
    }

    private int getColorIdsForContent(Transcribe transcribe, int i) {
        int i2 = this.isRecording ? C0874R.color.txt_color_tertiary : C0874R.color.txt_color_primary;
        int i3 = C0874R.color.txt_color_primary;
        Resources resources = this.context.getResources();
        if ((transcribe.getCurSpeakIndex() != i || this.mode == 2) && (BaseStringUtil.isEmpty(transcribe.getType()) || transcribe.getType().equals("0"))) {
            i2 = i3;
        }
        return resources.getColor(i2);
    }

    public void setFontsize(float f) {
        notifyDataSetChanged();
    }

    public void changeMode(int i) {
        this.mode = i;
        if (i == 0) {
            for (int i2 = 0; i2 < this.mData.size(); i2++) {
                ((Transcribe) this.mData.get(i2)).setPick(false);
            }
            this.editPos = -1;
            this.editId = -1;
        } else if (i == 2) {
            for (int i3 = 0; i3 < this.mData.size(); i3++) {
                ((Transcribe) this.mData.get(i3)).setPick(false);
            }
            Iterator it = this.mData.iterator();
            while (it.hasNext()) {
                ((Transcribe) it.next()).setPick(false);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    @Override // com.aivox.app.util.SwipedSelectItemTouchCallBack.OnItemSwipeListener
    public void onSwipeSelected(int i) {
        if (i == -1 || this.mode == 2) {
            return;
        }
        if (this.onItemLongClickListener != null && this.mode == 0) {
            this.mode = 1;
            this.editPos = i;
            ((Transcribe) this.mData.get(i)).setPick(true);
            notifyDataSetChanged();
            OnCheckChangeListener onCheckChangeListener = this.onCheckChangeListener;
            if (onCheckChangeListener != null) {
                onCheckChangeListener.checkChange((Transcribe) this.mData.get(i), ((Transcribe) this.mData.get(i)).isPick(), i);
            }
            this.onItemLongClickListener.onItemLongClick(null, i);
            return;
        }
        if (this.mode == 1) {
            this.editPos = i;
            ((Transcribe) this.mData.get(i)).setPick(true ^ ((Transcribe) this.mData.get(i)).isPick());
            notifyItemChanged(i);
            OnCheckChangeListener onCheckChangeListener2 = this.onCheckChangeListener;
            if (onCheckChangeListener2 != null) {
                onCheckChangeListener2.checkChange((Transcribe) this.mData.get(i), ((Transcribe) this.mData.get(i)).isPick(), i);
            }
        }
    }

    public String getPickIds(int i) {
        StringBuilder sb = new StringBuilder();
        for (T t : this.mData) {
            if (t.isPick() && t.getId() != 0) {
                if (i == 0) {
                    sb.append(t.getId()).append(PunctuationConst.COMMA);
                } else if (i == 1) {
                    if (t.getImageList().size() > 0) {
                        sb.append(t.getId()).append(PunctuationConst.COMMA);
                    }
                } else if (t.getImageList().size() == 0) {
                    sb.append(t.getId()).append(PunctuationConst.COMMA);
                }
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    public List<AudioContentBean.DataBean> getEditData() {
        ArrayList arrayList = new ArrayList();
        for (Integer num : this.editted.keySet()) {
            for (int i = 0; i < this.mData.size(); i++) {
                if (num.intValue() == ((Transcribe) this.mData.get(i)).getId()) {
                    arrayList.add(new AudioContentBean.DataBean(num.intValue(), ((Transcribe) this.mData.get(i)).getOnebest(), i));
                }
            }
        }
        this.editted.clear();
        return arrayList;
    }

    public String getPickTexts() {
        StringBuilder sb = new StringBuilder();
        for (T t : this.mData) {
            if (t.isPick() && t.getOnebest() != null) {
                sb.append(t.getOnebest()).append("\n\n");
                if (BaseStringUtil.isNotEmpty(t.getTranslate())) {
                    sb.append(t.getTranslate()).append("\n\n");
                }
            }
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

    public void setCurSpeechIndex(int i) {
        this.curSpeechIndex = i;
        if (i == -1) {
            notifyDataSetChanged();
        }
    }

    public void setAvatarStyle(int i) {
        this.avatarStyle = i;
    }
}
