package com.aivox.app.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.ItemBindingViewHolder;
import com.aivox.base.databinding.MyBindingAdapterJustItem;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.Transcribe;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.ConvertUtils;

/* JADX INFO: loaded from: classes.dex */
public class TranscribeConversationAdapter extends MyBindingAdapterJustItem<Transcribe> implements IConversationAdapter {
    private static final int ITEM_VIEW_TYPE_LEFT = 0;
    private static final int ITEM_VIEW_TYPE_RIGHT = 1;
    private boolean mAutoSpeakMode;
    private final Context mContext;
    private int mCurSpeakPosition;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public TranscribeConversationAdapter(Context context) {
        super(context, 0);
        this.mAutoSpeakMode = false;
        this.mCurSpeakPosition = -1;
        this.mContext = context;
    }

    @Override // com.aivox.base.databinding.MyBindingAdapterJustItem, androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding viewDataBindingInflate;
        if (i == 0) {
            viewDataBindingInflate = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), C0726R.layout.item_conversation_left, viewGroup, false);
        } else {
            viewDataBindingInflate = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), C0726R.layout.item_conversation_right, viewGroup, false);
        }
        return new ItemBindingViewHolder(viewDataBindingInflate);
    }

    @Override // com.aivox.base.databinding.MyBindingAdapterJustItem, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ItemBindingViewHolder) {
            Transcribe transcribe = (Transcribe) this.mData.get(i);
            String str = transcribe.getOnebest() + transcribe.getVar();
            TextView textView = (TextView) viewHolder.itemView.findViewById(C0726R.id.tv_content);
            TextView textView2 = (TextView) viewHolder.itemView.findViewById(C0726R.id.tv_translate_result);
            TextView textView3 = (TextView) viewHolder.itemView.findViewById(C0726R.id.tv_time);
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(C0726R.id.iv_to_audio);
            textView3.setText(DateUtil.numberToTime((int) ((Long.parseLong(transcribe.getEndAt()) - Long.parseLong(transcribe.getBeginAt())) / 1000)));
            textView.setText(str);
            textView2.setText(transcribe.getTranslate());
            if (i == this.mCurSpeakPosition) {
                imageView.setVisibility(0);
                imageView.setBackgroundResource(C1034R.drawable.trans_play_anim);
                ((AnimationDrawable) imageView.getBackground()).start();
            } else {
                imageView.setVisibility(this.mAutoSpeakMode ? 4 : 0);
                Drawable background = imageView.getBackground();
                if (background instanceof AnimationDrawable) {
                    ((AnimationDrawable) background).stop();
                }
                imageView.setBackgroundResource(C1034R.drawable.ic_trans_play_frame_3);
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.TranscribeConversationAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m247xb5ba599d(i, view2);
                }
            });
            if (i == this.mData.size() - 1) {
                LayoutUtil.setMargins(viewHolder.itemView, 0, 0, 0, ConvertUtils.dp2px(70.0f));
            } else {
                LayoutUtil.setMargins(viewHolder.itemView, 0, 0, 0, 0);
            }
            if (viewHolder.getItemViewType() == 0) {
                ImageView imageView2 = (ImageView) viewHolder.itemView.findViewById(C0726R.id.iv_avatar);
                String str2 = (String) SPUtil.get(SPUtil.USER_AVATAR, "");
                if (BaseStringUtil.isNotEmpty(str2)) {
                    ImageLoaderFactory.getLoader().displayImage(imageView2, str2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-app-adapter-TranscribeConversationAdapter */
    /* synthetic */ void m247xb5ba599d(int i, View view2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view2, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.mData == null) {
            return 0;
        }
        return !((Transcribe) this.mData.get(i)).isConversationLeft() ? 1 : 0;
    }

    @Override // com.aivox.app.adapter.IConversationAdapter
    public void setAutoSpeakMode(boolean z) {
        this.mAutoSpeakMode = z;
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

    public void changePlayStatus(int i) {
        if (i != -1) {
            startTtsAnim(i);
        } else {
            stopTtsAnim();
        }
    }
}
