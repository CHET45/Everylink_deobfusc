package com.aivox.set.adapter;

import android.widget.ImageView;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.common.model.UploadFileBean;
import com.aivox.common_ui.C1034R;
import com.aivox.set.C1106R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class FileSelectAdapter extends BaseQuickAdapter<UploadFileBean, BaseViewHolder> {
    public FileSelectAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UploadFileBean uploadFileBean) {
        int type = uploadFileBean.getType();
        if (type == 0) {
            baseViewHolder.setImageResource(C1106R.id.iv_cover, C1034R.drawable.ic_file_add);
            baseViewHolder.setVisible(C1106R.id.iv_delete, false);
        } else if (type == 1) {
            ImageLoaderFactory.getLoader().displayImagePath((ImageView) baseViewHolder.getView(C1106R.id.iv_cover), uploadFileBean.getLocalPath(), null);
            baseViewHolder.setVisible(C1106R.id.iv_delete, true);
        } else if (type == 2) {
            baseViewHolder.setImageResource(C1106R.id.iv_cover, C1034R.drawable.ic_video_cover);
            baseViewHolder.setVisible(C1106R.id.iv_delete, true);
        }
        baseViewHolder.addOnClickListener(C1106R.id.iv_delete);
        baseViewHolder.addOnClickListener(C1106R.id.iv_cover);
    }
}
