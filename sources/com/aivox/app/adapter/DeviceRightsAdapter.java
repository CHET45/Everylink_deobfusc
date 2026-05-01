package com.aivox.app.adapter;

import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.DeviceRightsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class DeviceRightsAdapter extends BaseQuickAdapter<DeviceRightsBean, BaseViewHolder> {
    private boolean mIsExpand;

    public DeviceRightsAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, DeviceRightsBean deviceRightsBean) {
        baseViewHolder.setText(C0726R.id.tv_device_name, deviceRightsBean.getDeviceName());
        baseViewHolder.setText(C0726R.id.tv_device_left_exp_msg, DateUtil.getDateForHomeList(deviceRightsBean.getDevicePrivilegeExpireAt(), true));
        if (deviceRightsBean.getRecordTimeUnlimited().booleanValue()) {
            baseViewHolder.setText(C0726R.id.tv_device_left_time_msg, C0874R.string.device_unlimited_use);
            baseViewHolder.setGone(C0726R.id.iv_device_infinity, true);
        } else {
            baseViewHolder.setText(C0726R.id.tv_device_left_time_msg, BaseStringUtil.getHourStr(deviceRightsBean.getGeneralTime().intValue(), "h"));
            baseViewHolder.setGone(C0726R.id.iv_device_infinity, false);
        }
        baseViewHolder.setGone(C0726R.id.cl_bottom, baseViewHolder.getAbsoluteAdapterPosition() == this.mData.size() - 1);
        baseViewHolder.addOnClickListener(C0726R.id.cl_bottom);
        baseViewHolder.getView(C0726R.id.iv_bottom).setRotation(this.mIsExpand ? 0.0f : 180.0f);
        baseViewHolder.setText(C0726R.id.tv_bottom, this.mIsExpand ? C0874R.string.list_collapse : C0874R.string.list_expand);
    }

    public void switchExpandStatus() {
        this.mIsExpand = !this.mIsExpand;
    }

    public boolean isExpand() {
        return this.mIsExpand;
    }
}
