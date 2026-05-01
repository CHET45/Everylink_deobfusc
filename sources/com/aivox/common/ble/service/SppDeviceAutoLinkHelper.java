package com.aivox.common.ble.service;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import com.aivox.base.common.Constant;
import com.aivox.base.util.SPUtil;
import com.aivox.common.ble.listener.SppBtConnectListener;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class SppDeviceAutoLinkHelper {
    private WeakReference<Context> mContextWeakReference;
    private IConnectStatusChangListener mListener;

    public interface IConnectStatusChangListener {
        void onFailed(String str);

        void onSuccess();
    }

    private SppDeviceAutoLinkHelper() {
    }

    private static final class MInstanceHolder {
        static final SppDeviceAutoLinkHelper mInstance = new SppDeviceAutoLinkHelper();

        private MInstanceHolder() {
        }
    }

    public static SppDeviceAutoLinkHelper getInstance() {
        return MInstanceHolder.mInstance;
    }

    public void destroy() {
        WeakReference<Context> weakReference = this.mContextWeakReference;
        if (weakReference != null) {
            weakReference.clear();
            this.mContextWeakReference = null;
        }
    }

    public void setConnectStatusChangeListener(IConnectStatusChangListener iConnectStatusChangListener) {
        this.mListener = iConnectStatusChangListener;
    }

    public void startAutoLinkSpp(Context context) {
        this.mContextWeakReference = new WeakReference<>(context);
        CommonServiceUtils.getInstance().startSppService(context);
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.common.ble.service.SppDeviceAutoLinkHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m345xa0a1cd09();
            }
        }, 1000L);
    }

    /* JADX INFO: renamed from: lambda$startAutoLinkSpp$0$com-aivox-common-ble-service-SppDeviceAutoLinkHelper */
    /* synthetic */ void m345xa0a1cd09() {
        CommonServiceUtils.getInstance().setSppListening(new SppBtConnectListener() { // from class: com.aivox.common.ble.service.SppDeviceAutoLinkHelper.1
            @Override // com.aivox.common.ble.listener.SppBtConnectListener
            public void onConnectSuccess(BluetoothDevice bluetoothDevice) {
                DataHandle.getIns().setHasConnectedBle(false);
                CommonServiceUtils.getInstance().sendData(Constant.CmdUpEnd);
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_CONNECTED));
                if (SppDeviceAutoLinkHelper.this.mListener != null) {
                    SppDeviceAutoLinkHelper.this.mListener.onSuccess();
                }
                SppDeviceAutoLinkHelper.this.destroy();
            }

            @Override // com.aivox.common.ble.listener.SppBtConnectListener
            public void onConnectFailed(String str) {
                if (SppDeviceAutoLinkHelper.this.mListener != null) {
                    SppDeviceAutoLinkHelper.this.mListener.onFailed(str);
                }
                SppDeviceAutoLinkHelper.this.destroy();
            }
        });
        getBondedDeviceAndBound();
    }

    private void getBondedDeviceAndBound() {
        for (BluetoothDevice bluetoothDevice : CommonServiceUtils.getInstance().getBondedDevices()) {
            boolean zBooleanValue = false;
            try {
                zBooleanValue = ((Boolean) bluetoothDevice.getClass().getMethod("isConnected", new Class[0]).invoke(bluetoothDevice, new Object[0])).booleanValue();
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            if (zBooleanValue && (CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID1) || CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID2))) {
                if (bluetoothDevice.getAddress().equals(SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, ""))) {
                    CommonServiceUtils.getInstance().connect(bluetoothDevice.getAddress());
                }
            }
        }
    }
}
