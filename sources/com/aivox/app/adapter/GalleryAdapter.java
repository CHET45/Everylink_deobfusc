package com.aivox.app.adapter;

import android.widget.ImageView;
import com.aivox.app.C0726R;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.common.model.DemoMultiEntity;
import com.aivox.common_ui.C1034R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class GalleryAdapter extends BaseMultiItemQuickAdapter<DemoMultiEntity, BaseViewHolder> {
    public static final String SUFFIX_JPEG = ".jpg";
    public static final String SUFFIX_MP4 = ".mp4";
    private boolean deleteMode;

    public boolean isDeleteMode() {
        return this.deleteMode;
    }

    public void reverseDeleteMode() {
        this.deleteMode = !this.deleteMode;
        notifyDataSetChanged();
    }

    public GalleryAdapter(List<DemoMultiEntity> list) {
        super(list);
        this.deleteMode = false;
        addItemType(0, C0726R.layout.item_gallery_date);
        addItemType(1, C0726R.layout.item_gallery_pic);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, DemoMultiEntity demoMultiEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        if (itemViewType == 0) {
            baseViewHolder.setText(C0726R.id.tv_item_date, demoMultiEntity.getDate());
            return;
        }
        if (itemViewType != 1) {
            return;
        }
        ImageLoaderFactory.getLoader().displayImage((ImageView) baseViewHolder.getView(C0726R.id.iv_item_pic), demoMultiEntity.getDemoPicBean().getUrl().endsWith(".mp4") ? demoMultiEntity.getDemoPicBean().getUrl().replace(".mp4", ".jpg") : demoMultiEntity.getDemoPicBean().getUrl());
        baseViewHolder.setVisible(C0726R.id.iv_play, demoMultiEntity.getDemoPicBean().getUrl().endsWith(".mp4"));
        if (this.deleteMode) {
            baseViewHolder.getView(C0726R.id.tv_duration).setVisibility(8);
        } else {
            baseViewHolder.getView(C0726R.id.tv_duration).setVisibility(demoMultiEntity.getDemoPicBean().getUrl().endsWith(".mp4") ? 0 : 8);
        }
        baseViewHolder.setText(C0726R.id.tv_duration, msToHMS(demoMultiEntity.getDemoPicBean().getDuration()));
        baseViewHolder.addOnLongClickListener(C0726R.id.iv_item_pic);
        baseViewHolder.addOnClickListener(C0726R.id.iv_item_pic);
        baseViewHolder.setVisible(C0726R.id.iv_item_selected, this.deleteMode);
        baseViewHolder.setImageResource(C0726R.id.iv_item_selected, demoMultiEntity.getDemoPicBean().isSelected() ? C1034R.drawable.ic_gallery_selected : C1034R.drawable.ic_gallery_unselected);
    }

    private String msToHMS(long j) {
        if (j < 0) {
            j = 0;
        }
        long hours = TimeUnit.MILLISECONDS.toHours(j);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(j) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(j) % 60;
        if (hours > 0) {
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds));
        }
        return String.format(Locale.getDefault(), "%02d:%02d", Long.valueOf(minutes), Long.valueOf(seconds));
    }

    public void selectItem(int i) {
        if (i < 0 || i >= this.mData.size()) {
            return;
        }
        DemoMultiEntity demoMultiEntity = (DemoMultiEntity) this.mData.get(i);
        if (demoMultiEntity.getShowTypeId() == 1) {
            demoMultiEntity.getDemoPicBean().setSelected(!demoMultiEntity.getDemoPicBean().isSelected());
            notifyItemChanged(i);
        }
    }

    public void selectAllItem(boolean z) {
        for (T t : this.mData) {
            if (t.getShowTypeId() == 1) {
                t.getDemoPicBean().setSelected(z);
            }
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        int i = 0;
        for (T t : this.mData) {
            if (t.getShowTypeId() == 1 && t.getDemoPicBean().isSelected()) {
                i++;
            }
        }
        return i;
    }

    public boolean isSelectedAll() {
        for (T t : this.mData) {
            if (t.getShowTypeId() == 1 && !t.getDemoPicBean().isSelected()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFavoriteAll() {
        for (T t : this.mData) {
            if (t.getShowTypeId() == 1 && t.getDemoPicBean().isSelected() && !t.getDemoPicBean().isFavorite()) {
                return false;
            }
        }
        return true;
    }
}
