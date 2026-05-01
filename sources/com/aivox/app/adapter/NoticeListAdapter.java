package com.aivox.app.adapter;

import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.CommonNoticeBean;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class NoticeListAdapter extends BaseQuickAdapter<CommonNoticeBean, BaseViewHolder> {
    public NoticeListAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, CommonNoticeBean commonNoticeBean) {
        int i;
        baseViewHolder.setText(C0726R.id.tv_notice_title, commonNoticeBean.getTitle());
        baseViewHolder.setText(C0726R.id.tv_notice_time, DateUtil.getDateForList(commonNoticeBean.getCreatedAt(), false));
        int i2 = C0726R.id.iv_notice_type;
        if (commonNoticeBean.getType().intValue() == 0) {
            i = C1034R.drawable.ic_notice_system;
        } else if (commonNoticeBean.getType().intValue() == 2) {
            i = C1034R.drawable.ic_notice_email;
        } else {
            i = C1034R.drawable.ic_notice_activity;
        }
        baseViewHolder.setImageResource(i2, i);
        baseViewHolder.setVisible(C0726R.id.iv_arrow, commonNoticeBean.getType().intValue() != 2);
        baseViewHolder.setVisible(C0726R.id.iv_save, commonNoticeBean.getType().intValue() == 2 && commonNoticeBean.isEffective());
        baseViewHolder.setGone(C0726R.id.iv_delete, commonNoticeBean.getType().intValue() == 2);
        if (commonNoticeBean.getType().intValue() == 2 && !commonNoticeBean.isEffective()) {
            baseViewHolder.setTextColor(C0726R.id.tv_notice_content, ColorUtils.getColor(C0874R.color.txt_color_warning));
            baseViewHolder.setText(C0726R.id.tv_notice_content, C0874R.string.share_access_expired);
        } else {
            baseViewHolder.setTextColor(C0726R.id.tv_notice_content, ColorUtils.getColor(C0874R.color.txt_color_primary));
            baseViewHolder.setText(C0726R.id.tv_notice_content, commonNoticeBean.getContent());
        }
        baseViewHolder.addOnClickListener(C0726R.id.iv_save);
        baseViewHolder.addOnClickListener(C0726R.id.iv_delete);
    }
}
