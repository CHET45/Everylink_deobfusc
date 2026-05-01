package com.aivox.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.img.imageloader.GlideApp;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DensityUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.p003db.entity.HomeAiChatEntity;
import com.aivox.common_ui.antishake.AntiShake;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import io.noties.markwon.Markwon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class HomeAiChatAdapter extends BaseQuickAdapter<HomeAiChatEntity, BaseViewHolder> {
    private static final String PAYLOAD_TEXT_UPDATE = "PAYLOAD_TEXT_UPDATE";
    private final Context context;
    private int lastPosition;
    private RegenerateClickListener listener;
    private final Markwon markwon;

    public interface RegenerateClickListener {
        void regenerateClick(String str, View view2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
        onBindViewHolder((BaseViewHolder) viewHolder, i, (List<Object>) list);
    }

    public HomeAiChatAdapter(Context context, int i, Markwon markwon) {
        super(i);
        this.lastPosition = -1;
        this.context = context;
        this.markwon = markwon;
    }

    public void setListener(RegenerateClickListener regenerateClickListener) {
        this.listener = regenerateClickListener;
    }

    public void updateLastItemText(String str) {
        if (getData().isEmpty()) {
            return;
        }
        int size = getData().size() - 1;
        getData().get(size).setAnswer(str);
        notifyItemChanged(size, PAYLOAD_TEXT_UPDATE);
    }

    public void changeLastPosition() {
        int i = this.lastPosition;
        this.lastPosition = this.mData.size() - 1;
        if (i >= 0) {
            notifyItemChanged(i);
        }
        notifyItemChanged(this.lastPosition);
    }

    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i, List<Object> list) {
        if (list.isEmpty()) {
            super.onBindViewHolder(baseViewHolder, i, list);
            return;
        }
        Iterator<Object> it = list.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (PAYLOAD_TEXT_UPDATE.equals(it.next())) {
                int headerLayoutCount = i - getHeaderLayoutCount();
                if (headerLayoutCount >= 0 && headerLayoutCount < getData().size()) {
                    HomeAiChatEntity homeAiChatEntity = getData().get(headerLayoutCount);
                    TextView textView = (TextView) baseViewHolder.getView(C0726R.id.tv_answer);
                    ConstraintLayout constraintLayout = (ConstraintLayout) baseViewHolder.getView(C0726R.id.cl_answer);
                    if (!TextUtils.isEmpty(homeAiChatEntity.getAnswer())) {
                        this.markwon.setMarkdown(textView, homeAiChatEntity.getAnswer());
                        constraintLayout.setVisibility(0);
                    } else {
                        textView.setText("");
                        constraintLayout.setVisibility(8);
                    }
                }
                z = true;
            }
        }
        if (z) {
            return;
        }
        super.onBindViewHolder(baseViewHolder, i, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(final BaseViewHolder baseViewHolder, final HomeAiChatEntity homeAiChatEntity) {
        int i;
        TextView textView = (TextView) baseViewHolder.getView(C0726R.id.tv_question);
        ImageView imageView = (ImageView) baseViewHolder.getView(C0726R.id.iv_image);
        final TextView textView2 = (TextView) baseViewHolder.getView(C0726R.id.tv_answer);
        final ImageView imageView2 = (ImageView) baseViewHolder.getView(C0726R.id.iv_regenerate);
        ImageView imageView3 = (ImageView) baseViewHolder.getView(C0726R.id.iv_copy);
        ImageView imageView4 = (ImageView) baseViewHolder.getView(C0726R.id.iv_delete);
        ConstraintLayout constraintLayout = (ConstraintLayout) baseViewHolder.getView(C0726R.id.cl_answer);
        View view2 = baseViewHolder.getView(C0726R.id.v_line);
        textView.setText(homeAiChatEntity.getQuestion());
        if (TextUtils.isEmpty(homeAiChatEntity.getImageUrl())) {
            imageView.setVisibility(8);
        } else {
            GlideApp.with(this.context).load(homeAiChatEntity.getImageUrl()).transform((Transformation<Bitmap>) new RoundedCorners(DensityUtil.dp2px(this.context, 36.0f))).into(imageView);
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.HomeAiChatAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.m2256lambda$convert$0$comaivoxappadapterHomeAiChatAdapter(homeAiChatEntity, view3);
                }
            });
        }
        textView.setTextSize(((Float) SPUtil.get(SPUtil.AUDIO_FONT_SIZE, Float.valueOf(17.0f))).floatValue());
        textView2.setTextSize(((Float) SPUtil.get(SPUtil.AUDIO_FONT_SIZE, Float.valueOf(17.0f))).floatValue());
        if (!TextUtils.isEmpty(homeAiChatEntity.getAnswer())) {
            textView2.setText(this.markwon.toMarkdown(homeAiChatEntity.getAnswer()));
            constraintLayout.setVisibility(0);
            i = 8;
        } else {
            i = 8;
            constraintLayout.setVisibility(8);
        }
        if (BaseGlobalConfig.isMainland()) {
            imageView2.setVisibility(i);
        } else {
            imageView2.setVisibility(0);
            imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.HomeAiChatAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.m2257lambda$convert$1$comaivoxappadapterHomeAiChatAdapter(homeAiChatEntity, imageView2, view3);
                }
            });
        }
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.HomeAiChatAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.m2258lambda$convert$2$comaivoxappadapterHomeAiChatAdapter(textView2, view3);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.HomeAiChatAdapter$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.m2259lambda$convert$4$comaivoxappadapterHomeAiChatAdapter(baseViewHolder, view3);
            }
        });
        view2.setVisibility(baseViewHolder.getAbsoluteAdapterPosition() == this.lastPosition ? 0 : i);
    }

    /* JADX INFO: renamed from: lambda$convert$0$com-aivox-app-adapter-HomeAiChatAdapter, reason: not valid java name */
    /* synthetic */ void m2256lambda$convert$0$comaivoxappadapterHomeAiChatAdapter(HomeAiChatEntity homeAiChatEntity, View view2) {
        Transcribe transcribe = new Transcribe();
        Transcribe.TagImgBean tagImgBean = new Transcribe.TagImgBean(0, homeAiChatEntity.getImageUrl());
        ArrayList arrayList = new ArrayList();
        arrayList.add(tagImgBean);
        transcribe.setImageList(arrayList);
        Bundle bundle = new Bundle();
        bundle.putSerializable("transcribe", transcribe);
        bundle.putBoolean("showDelete", false);
        bundle.putInt("pos", 0);
        ARouterUtils.startWithContext(this.context, MainAction.PHOTO_BROWSE, bundle);
    }

    /* JADX INFO: renamed from: lambda$convert$1$com-aivox-app-adapter-HomeAiChatAdapter, reason: not valid java name */
    /* synthetic */ void m2257lambda$convert$1$comaivoxappadapterHomeAiChatAdapter(HomeAiChatEntity homeAiChatEntity, ImageView imageView, View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        this.listener.regenerateClick(homeAiChatEntity.getQuestion(), imageView);
    }

    /* JADX INFO: renamed from: lambda$convert$2$com-aivox-app-adapter-HomeAiChatAdapter, reason: not valid java name */
    /* synthetic */ void m2258lambda$convert$2$comaivoxappadapterHomeAiChatAdapter(TextView textView, View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        BaseStringUtil.putTextIntoClip(this.context, textView.getText().toString());
    }

    /* JADX INFO: renamed from: lambda$convert$4$com-aivox-app-adapter-HomeAiChatAdapter, reason: not valid java name */
    /* synthetic */ void m2259lambda$convert$4$comaivoxappadapterHomeAiChatAdapter(final BaseViewHolder baseViewHolder, View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.gentle_reminder), Integer.valueOf(C0874R.string.delete_cannot_recover_confirm), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.adapter.HomeAiChatAdapter$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_DELETE_CHAT, baseViewHolder.getAbsoluteAdapterPosition()));
            }
        }, null, true, false, C0874R.string.delete, C0874R.string.cancel, 0);
    }
}
