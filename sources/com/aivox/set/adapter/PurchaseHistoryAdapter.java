package com.aivox.set.adapter;

import com.aivox.base.util.DateUtil;
import com.aivox.common.model.PurchaseHistoryBean;
import com.aivox.set.C1106R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class PurchaseHistoryAdapter extends BaseQuickAdapter<PurchaseHistoryBean.OrderDetail, BaseViewHolder> {
    public PurchaseHistoryAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, PurchaseHistoryBean.OrderDetail orderDetail) {
        baseViewHolder.setText(C1106R.id.tv_title, orderDetail.getProductName());
        baseViewHolder.setText(C1106R.id.tv_date, DateUtil.utc2Local(orderDetail.getCreatedAt(), DateUtil.DATE_IN_TIME_DETAIL));
        baseViewHolder.setText(C1106R.id.tv_price, orderDetail.getAmount() + "");
    }
}
