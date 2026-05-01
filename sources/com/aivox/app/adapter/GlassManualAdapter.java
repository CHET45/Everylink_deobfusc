package com.aivox.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GlassManualAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0010B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\u0005H\u0016J\u001c\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0005H\u0016J\u001c\u0010\f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0005H\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, m1901d2 = {"Lcom/aivox/app/adapter/GlassManualAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/aivox/app/adapter/GlassManualAdapter$ImageViewHolder;", "imageList", "", "", "(Ljava/util/List;)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ImageViewHolder", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class GlassManualAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private final List<Integer> imageList;

    public GlassManualAdapter(List<Integer> imageList) {
        Intrinsics.checkNotNullParameter(imageList, "imageList");
        this.imageList = imageList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new ImageViewHolder(this, imageView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.imageList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        holder.bind(this.imageList.get(position).intValue());
    }

    /* JADX INFO: compiled from: GlassManualAdapter.kt */
    @Metadata(m1900d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, m1901d2 = {"Lcom/aivox/app/adapter/GlassManualAdapter$ImageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/aivox/app/adapter/GlassManualAdapter;Landroid/view/View;)V", "imageView", "Landroid/widget/ImageView;", "bind", "", "imageResource", "", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public final class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        final /* synthetic */ GlassManualAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ImageViewHolder(GlassManualAdapter glassManualAdapter, View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.this$0 = glassManualAdapter;
            this.imageView = (ImageView) itemView;
        }

        public final void bind(int imageResource) {
            this.imageView.setImageResource(imageResource);
        }
    }
}
