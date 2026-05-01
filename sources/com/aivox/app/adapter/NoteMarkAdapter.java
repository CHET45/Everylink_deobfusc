package com.aivox.app.adapter;

import com.aivox.app.C0726R;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.AudioMarkBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class NoteMarkAdapter extends BaseQuickAdapter<AudioMarkBean, BaseViewHolder> {
    public NoteMarkAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AudioMarkBean audioMarkBean) {
        baseViewHolder.setText(C0726R.id.tv_mark_time, DateUtil.minToTimeWithoutMm(audioMarkBean.getMarkTime().longValue()));
        baseViewHolder.setText(C0726R.id.tv_mark_content, audioMarkBean.getContent());
        baseViewHolder.addOnClickListener(C0726R.id.iv_mark_delete);
        baseViewHolder.addOnClickListener(C0726R.id.tv_mark_content);
    }
}
