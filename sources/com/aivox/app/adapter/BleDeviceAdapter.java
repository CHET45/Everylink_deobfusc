package com.aivox.app.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.ItemBindingViewHolder;
import com.aivox.base.databinding.MyBindingAdapterJustItem;
import com.aivox.common.model.LocalDeviceBean;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceAdapter extends MyBindingAdapterJustItem {
    Context context;

    public BleDeviceAdapter(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    @Override // com.aivox.base.databinding.MyBindingAdapterJustItem, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (!(viewHolder instanceof ItemBindingViewHolder) || this.mData == null || this.mData.isEmpty() || i + 1 > this.mData.size()) {
            return;
        }
        LocalDeviceBean localDeviceBean = (LocalDeviceBean) this.mData.get(i);
        TextView textView = (TextView) viewHolder.itemView.findViewById(C0726R.id.tv_device_name);
        TextView textView2 = (TextView) viewHolder.itemView.findViewById(C0726R.id.tv_device_desc);
        ImageView imageView = (ImageView) viewHolder.itemView.findViewById(C0726R.id.iv_device_icon);
        ImageView imageView2 = (ImageView) viewHolder.itemView.findViewById(C0726R.id.iv_device_signal);
        if (localDeviceBean.getFromNew().booleanValue()) {
            imageView.setImageResource(localDeviceBean.getDevice().picRes);
            textView.setText(localDeviceBean.getDevice().name);
            textView2.setText(localDeviceBean.getDevice().name);
        } else if (localDeviceBean.getIsBleDevice().booleanValue()) {
            textView.setText(localDeviceBean.getBluetoothDevice().getName() + "\n" + localDeviceBean.getBluetoothDevice().getAddress().substring(12));
            imageView.setImageResource(MyEnum.DEVICE_MODEL.getEarphone(localDeviceBean.getBleDevice().name).picRes);
            textView2.setText(MyEnum.DEVICE_MODEL.getEarphone(localDeviceBean.getBleDevice().name).name);
        } else {
            imageView.setImageResource(MyEnum.DEVICE_MODEL.getEarphone(localDeviceBean.getBluetoothDevice().getName()).picRes);
            textView2.setText(MyEnum.DEVICE_MODEL.getEarphone(localDeviceBean.getBluetoothDevice().getName()).name);
            textView.setText(localDeviceBean.getBluetoothDevice().getName());
        }
        setSignal(imageView2, localDeviceBean.getRssi());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.adapter.BleDeviceAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2255lambda$onBindViewHolder$0$comaivoxappadapterBleDeviceAdapter(i, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-app-adapter-BleDeviceAdapter, reason: not valid java name */
    /* synthetic */ void m2255lambda$onBindViewHolder$0$comaivoxappadapterBleDeviceAdapter(int i, View view2) {
        if (this.mOnItemClickListener != null) {
            this.mOnItemClickListener.onItemClick(view2, i);
        }
    }

    private void setSignal(ImageView imageView, int i) {
        imageView.setVisibility(i == 0 ? 8 : 0);
        if (i > -40) {
            imageView.setImageResource(C1034R.drawable.ic_bt_signal_4);
            return;
        }
        if (i > -60) {
            imageView.setImageResource(C1034R.drawable.ic_bt_signal_3);
        } else if (i > -80) {
            imageView.setImageResource(C1034R.drawable.ic_bt_signal_2);
        } else {
            imageView.setImageResource(C1034R.drawable.ic_bt_signal_1);
        }
    }
}
