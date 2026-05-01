package com.aivox.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.databinding.ItemBindingViewHolder;
import com.aivox.base.databinding.MyBindingAdapter;
import com.aivox.base.databinding.NullViewHolder;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.ScreenUtil;
import com.aivox.common.model.Transcribe;
import com.blankj.utilcode.util.ConvertUtils;
import com.lcodecore.tkrefreshlayout.C2015R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ImgTagAdapter extends MyBindingAdapter {
    Context context;
    ImageView ivImg;
    View.OnLongClickListener onLongClickListener;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public ImgTagAdapter(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    public ImgTagAdapter(Context context, int i, List<Transcribe.TagImgBean> list, OnItemClickListener onItemClickListener, View.OnLongClickListener onLongClickListener) {
        super(context, i);
        this.context = context;
        this.mOnItemClickListener = onItemClickListener;
        this.onLongClickListener = onLongClickListener;
        isShowFooter(false);
        isShowNull(false);
        setmDate(list);
        setHasStableIds(true);
    }

    @Override // com.aivox.base.databinding.MyBindingAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof ItemBindingViewHolder) {
            if (this.mData == null || this.mData.size() <= 0 || i + 1 > this.mData.size()) {
                return;
            }
            Object obj = this.mData.get(i);
            ItemBindingViewHolder itemBindingViewHolder = (ItemBindingViewHolder) viewHolder;
            itemBindingViewHolder.getmBinding().setVariable(6, obj);
            itemBindingViewHolder.getmBinding().executePendingBindings();
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(C0726R.id.iv_img);
            this.ivImg = imageView;
            imageView.getLayoutParams().height = (int) ((ScreenUtil.getScreenWidth(this.context) - ConvertUtils.dp2px(40.0f)) / 3.0f);
            if (this.ivImg.getTag() == null || !this.ivImg.getTag().equals(((Transcribe.TagImgBean) obj).getImageUrl())) {
                Transcribe.TagImgBean tagImgBean = (Transcribe.TagImgBean) obj;
                ImageLoaderFactory.getLoader().displayImage(this.ivImg, tagImgBean.getImageUrl(), C2015R.drawable.anim_loading_view);
                this.ivImg.setTag(tagImgBean.getImageUrl());
            }
            ImageView imageView2 = this.ivImg;
            if (imageView2 != null) {
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.ImgTagAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        this.f$0.m2260lambda$onBindViewHolder$0$comaivoxappadapterImgTagAdapter(i, view2);
                    }
                });
                this.ivImg.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.aivox.app.adapter.ImgTagAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return this.f$0.m2261lambda$onBindViewHolder$1$comaivoxappadapterImgTagAdapter(view2);
                    }
                });
                return;
            }
            return;
        }
        boolean z = viewHolder instanceof NullViewHolder;
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-app-adapter-ImgTagAdapter, reason: not valid java name */
    /* synthetic */ void m2260lambda$onBindViewHolder$0$comaivoxappadapterImgTagAdapter(int i, View view2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view2, i);
        }
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$1$com-aivox-app-adapter-ImgTagAdapter, reason: not valid java name */
    /* synthetic */ boolean m2261lambda$onBindViewHolder$1$comaivoxappadapterImgTagAdapter(View view2) {
        View.OnLongClickListener onLongClickListener = this.onLongClickListener;
        if (onLongClickListener == null) {
            return true;
        }
        onLongClickListener.onLongClick(view2);
        return true;
    }
}
