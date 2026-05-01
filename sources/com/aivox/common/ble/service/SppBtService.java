package com.aivox.common.ble.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.NotificationCompat;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.ble.BleDataManager;
import com.aivox.common.ble.listener.SppBtConnectListener;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.blankj.utilcode.util.Utils;
import com.microsoft.azure.storage.table.TableConstants;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public class SppBtService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ALLOW_RETRY_TIMES = 10;
    private static final int CONNECTED_START = 2002;
    private static final String TAG = "SppBtService";
    private static int curRetryTimes;
    private static SppBtConnectListener mSppBtConnectListener;
    private static SppBtService service;
    private ConnectTask mConnectTask;
    NotificationManager manager;
    Notification notification;
    NotificationCompat.Builder notificationBuilder;
    PendingIntent pendingIntent;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private String channelId = TAG;

    private static class ConnectTask extends AsyncTask<String, Byte[], Void> {
        private BluetoothAdapter bluetoothAdapter;
        BluetoothSocket bluetoothSocket;
        boolean isRunning;
        BluetoothDevice romoteDevice;
        SppBtConnectListener sppBtConnectListener;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(String... strArr) {
            this.isRunning = true;
            try {
                LogUtil.m339i(SppBtService.TAG, "--doInBackground--");
                this.romoteDevice = this.bluetoothAdapter.getRemoteDevice(strArr[0]);
                LogUtil.m339i(SppBtService.TAG, "romoteDevice.address:" + this.romoteDevice.getAddress());
                LogUtil.m339i(SppBtService.TAG, "socket_isconnect:" + (this.bluetoothSocket != null ? "" + this.bluetoothSocket.isConnected() : "=null"));
                if (CommonServiceUtils.getInstance().isContainUUid(this.romoteDevice.getUuids(), CommonServiceUtils.UUID1)) {
                    this.bluetoothSocket = this.romoteDevice.createRfcommSocketToServiceRecord(CommonServiceUtils.UUID1);
                } else {
                    this.bluetoothSocket = this.romoteDevice.createRfcommSocketToServiceRecord(CommonServiceUtils.UUID2);
                }
                BluetoothSocket bluetoothSocket = this.bluetoothSocket;
                if (bluetoothSocket == null) {
                    this.sppBtConnectListener.onConnectFailed("连接失败:获取Socket失败");
                    DataHandle.getIns().setHasConnectedSpp(false);
                    this.isRunning = false;
                    return null;
                }
                try {
                    bluetoothSocket.connect();
                    LogUtil.m335d(SppBtService.TAG, "连接成功1" + this.bluetoothSocket.hashCode());
                    int unused = SppBtService.curRetryTimes = 0;
                    EventBus.getDefault().post(new EventBean(2002));
                    SPUtil.put(SPUtil.CONNECTED_DEVICE_ADDRESS, this.romoteDevice.getAddress());
                    SPUtil.put(SPUtil.CONNECTED_DEVICE_NAME, this.romoteDevice.getName());
                    DataHandle.getIns().setHasConnectedSpp(true);
                    this.sppBtConnectListener.onConnectSuccess(this.romoteDevice);
                } catch (Exception e) {
                    SppBtService.curRetryTimes++;
                    LogUtil.m335d(SppBtService.TAG, "连接失败:" + e.getLocalizedMessage());
                    try {
                        Log.e(SppBtService.TAG, "trying fallback...");
                        if (SppBtService.curRetryTimes < 10) {
                            doInBackground(this.romoteDevice.getAddress());
                        } else {
                            cancel(true);
                        }
                        Log.e(SppBtService.TAG, "Connected");
                    } catch (Exception e2) {
                        Log.e(SppBtService.TAG, "Couldn't establish Bluetooth connection!" + e2.getLocalizedMessage());
                        try {
                            this.bluetoothSocket.close();
                            this.bluetoothSocket = null;
                        } catch (IOException e3) {
                            LogUtil.m339i(SppBtService.TAG, "to_close:" + e3.getLocalizedMessage());
                        }
                        DataHandle.getIns().setHasConnectedSpp(false);
                        this.sppBtConnectListener.onConnectFailed(Utils.getApp().getString(C0874R.string.connect_failed) + ":" + e.getMessage());
                        return null;
                    }
                }
                try {
                    InputStream inputStream = this.bluetoothSocket.getInputStream();
                    while (this.isRunning) {
                        byte[] bArr = new byte[DataHandle.getIns().isLength401() ? TypedValues.CycleType.TYPE_CURVE_FIT : Constant.EVENT.BLE_GLASS_ASK_AI];
                        while (inputStream.available() == 0 && this.isRunning && System.currentTimeMillis() >= 0) {
                        }
                        while (this.isRunning) {
                            try {
                                int i = inputStream.read(bArr);
                                String str = new String(bArr, StandardCharsets.UTF_8);
                                if (str.startsWith(Constant.CmdDownHead)) {
                                    if (str.startsWith(Constant.CmdDownBatteryLevel)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(302, str));
                                        String[] strArrSplit = str.split("-");
                                        if (strArrSplit.length >= 5) {
                                            LogUtil.m336e(strArrSplit[4]);
                                            DataHandle.getIns().setLength401(strArrSplit[4].startsWith("401"));
                                        } else {
                                            DataHandle.getIns().setLength401(false);
                                        }
                                    } else if (str.startsWith(Constant.CmdDownEndCall)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(304));
                                        EventBus.getDefault().post(new EventBean(315));
                                    } else if (str.startsWith(Constant.CmdDownEarphoneVersion)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(305, str));
                                        parseVersion(str.trim());
                                    } else if (str.startsWith(Constant.CmdDownLeftStart)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(309));
                                    } else if (str.startsWith(Constant.CmdDownLeftEnd)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(310));
                                    } else if (str.startsWith(Constant.CmdDownRightStart)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(311));
                                    } else if (str.startsWith(Constant.CmdDownRightEnd)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(312));
                                    } else if (str.startsWith(Constant.CmdDownRequestMode)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(303, str));
                                    } else if (str.startsWith(Constant.CmdDownActiveEnd)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(313));
                                    } else if (str.startsWith(Constant.CmdDownCalling) || str.startsWith(Constant.CmdDownCallingE) || str.startsWith(Constant.CmdDownCallingF) || str.startsWith(Constant.CmdDownStartCall) || str.startsWith(Constant.CmdDownStartCallE) || str.startsWith(Constant.CmdDownStartCallF)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(314));
                                    } else if (str.startsWith(Constant.CmdDownNoCall)) {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                        EventBus.getDefault().post(new EventBean(315));
                                    } else {
                                        LogUtil.m335d(SppBtService.TAG, str);
                                    }
                                } else if (DataHandle.getIns().isLength401()) {
                                    int i2 = i - 1;
                                    byte[] bArr2 = new byte[i2];
                                    System.arraycopy(bArr, 1, bArr2, 0, i2);
                                    BleDataManager.getInstance().writeData(bArr2);
                                } else {
                                    BleDataManager.getInstance().writeData(bArr);
                                }
                                if (inputStream.available() == 0) {
                                    break;
                                }
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                this.sppBtConnectListener.onConnectFailed(Utils.getApp().getString(C0874R.string.connect_failed_data_single) + ":" + e4.getMessage());
                            }
                        }
                    }
                } catch (Exception e5) {
                    this.sppBtConnectListener.onConnectFailed(Utils.getApp().getString(C0874R.string.connect_failed_data) + ":" + e5.getMessage());
                }
                return null;
            } catch (Exception e6) {
                LogUtil.m335d(SppBtService.TAG, "获取Socket失败_e:" + e6.getLocalizedMessage());
                this.isRunning = false;
                return null;
            }
        }

        private void parseVersion(String str) {
            if (str.startsWith(Constant.CmdDownEarphoneVersion)) {
                String[] strArrSplit = str.split("-");
                if (strArrSplit.length >= 3) {
                    DataHandle.getIns().setHeadsetVersion(strArrSplit[1]);
                }
            }
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            try {
                LogUtil.m335d(SppBtService.TAG, "AsyncTask 开始释放资源");
                this.isRunning = false;
                BluetoothSocket bluetoothSocket = this.bluetoothSocket;
                if (bluetoothSocket != null) {
                    bluetoothSocket.close();
                    this.bluetoothSocket = null;
                }
            } catch (IOException e) {
                LogUtil.m339i(SppBtService.TAG, "ConnectTask_onCanceled_e:" + e.getLocalizedMessage());
            }
        }

        private ConnectTask() {
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            this.isRunning = false;
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtil.m339i(SppBtService.TAG, "--onPreExecute--");
            this.romoteDevice = null;
            try {
                this.isRunning = false;
                BluetoothSocket bluetoothSocket = this.bluetoothSocket;
                if (bluetoothSocket != null) {
                    bluetoothSocket.close();
                    this.bluetoothSocket = null;
                }
            } catch (IOException e) {
                LogUtil.m339i(SppBtService.TAG, "ConnectTask_onPreExecute_e:" + e.getLocalizedMessage());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r2) {
            super.onPostExecute(r2);
            LogUtil.m339i(SppBtService.TAG, "--onPostExecute--");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Byte[]... bArr) {
            super.onProgressUpdate((Object[]) bArr);
            LogUtil.m339i(SppBtService.TAG, "--onProgressUpdate--");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(Void r2) {
            super.onCancelled(r2);
            LogUtil.m339i(SppBtService.TAG, "---onCancelled-");
        }

        void send(byte[] bArr) {
            try {
                if (this.bluetoothSocket.isConnected()) {
                    this.bluetoothSocket.getOutputStream().write(bArr);
                }
            } catch (Exception e) {
                boolean z = e instanceof IOException;
                LogUtil.m339i(SppBtService.TAG, "spp---send" + z);
                if (z) {
                    DataHandle.getIns().setHasConnectedSpp(false);
                    EventBus.getDefault().post(new EventBean(306));
                }
                e.printStackTrace();
            }
        }
    }

    public void setListening(SppBtConnectListener sppBtConnectListener) {
        mSppBtConnectListener = sppBtConnectListener;
    }

    public void removeListening() {
        mSppBtConnectListener = null;
    }

    public static SppBtService getInstance() {
        if (service == null) {
            service = new SppBtService();
        }
        return service;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtil.m337e(TAG, "--onStartCommand--");
        return 1;
    }

    public void setServiceForeground() {
        NotificationChannel notificationChannel = new NotificationChannel(this.channelId, getString(C0874R.string.app_name), 3);
        Intent intent = new Intent(this, (Class<?>) SppBtService.class);
        intent.putExtra(TableConstants.ErrorConstants.ERROR_CODE, 1234);
        this.pendingIntent = PendingIntent.getService(this, 1002, intent, 67108864);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        this.manager = notificationManager;
        notificationManager.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, this.channelId);
        this.notificationBuilder = builder;
        Notification notificationBuild = builder.setOngoing(true).setSmallIcon(C0874R.mipmap.icon_logo).setContentTitle(getString(C0874R.string.notification_bluetooth_connected)).setPriority(4).setCategory(NotificationCompat.CATEGORY_SERVICE).setChannelId(this.channelId).setContentIntent(this.pendingIntent).build();
        this.notification = notificationBuild;
        startForeground(1, notificationBuild);
        this.manager.notify(1, this.notification);
    }

    private void update(boolean z) {
        if (this.manager == null) {
            return;
        }
        if (this.notificationBuilder != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, this.channelId);
            this.notificationBuilder = builder;
            this.notification = builder.setChannelId(this.channelId).setSmallIcon(C0874R.mipmap.icon_logo).setContentIntent(this.pendingIntent).setContentTitle(getResources().getString(z ? C0874R.string.notification_bluetooth_connected : C0874R.string.notification_bluetooth_disconnected)).build();
        }
        startForeground(1, this.notification);
        this.manager.notify(1, this.notification);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        service = this;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        EventBus.getDefault().register(this);
        if (this.mConnectTask == null) {
            this.mConnectTask = new ConnectTask();
        }
    }

    public String getConnectedDeviceName() {
        ConnectTask connectTask = this.mConnectTask;
        if (connectTask != null && connectTask.romoteDevice != null) {
            return this.mConnectTask.romoteDevice.getName();
        }
        return "";
    }

    public String getConnectedDeviceAddress() {
        ConnectTask connectTask = this.mConnectTask;
        if (connectTask != null && connectTask.romoteDevice != null) {
            return this.mConnectTask.romoteDevice.getAddress();
        }
        return "";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBean eventBean) {
        if (eventBean.getFrom() != 2002) {
            return;
        }
        setServiceForeground();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogUtil.m335d(TAG, "ON_BIND: " + service.hashCode());
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogUtil.m335d(TAG, "SppBleService_onDestroy");
        try {
            LogUtil.m335d(TAG, "onDestroy，开始释放资源" + this.mConnectTask.hashCode());
            disconnect(true);
            EventBus.getDefault().unregister(this);
            this.mConnectTask.isRunning = false;
            this.mConnectTask.cancel(true);
        } catch (Exception e) {
            LogUtil.m339i(TAG, "destroy_e::" + e.getLocalizedMessage());
        }
        service = null;
    }

    public void connect(String str) {
        if (this.mConnectTask == null) {
            this.mConnectTask = new ConnectTask();
        }
        connect_(str);
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.mBluetoothAdapter.getBondedDevices();
    }

    public void disconnect(boolean z) {
        if (!z) {
            try {
                update(false);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                return;
            }
        }
        ConnectTask connectTask = this.mConnectTask;
        if (connectTask != null) {
            if (connectTask.bluetoothSocket != null) {
                this.mConnectTask.bluetoothSocket.close();
                this.mConnectTask.bluetoothSocket = null;
                DataHandle.getIns().setHasConnectedSpp(false);
            }
            this.mConnectTask.isRunning = false;
            this.mConnectTask.cancel(true);
            this.mConnectTask = null;
        }
    }

    private void connect_(String str) {
        LogUtil.m339i(TAG, "ConnectTask.status:" + this.mConnectTask.getStatus() + ";isRunning:" + this.mConnectTask.isRunning + this.mConnectTask.hashCode());
        if (this.mConnectTask.getStatus() == AsyncTask.Status.RUNNING && this.mConnectTask.isRunning) {
            SppBtConnectListener sppBtConnectListener = mSppBtConnectListener;
            if (sppBtConnectListener != null) {
                sppBtConnectListener.onConnectFailed(getString(C0874R.string.connect_failed_in_progress));
            }
            disconnect(false);
            LogUtil.m339i(TAG, "Connect 失败");
            return;
        }
        this.mConnectTask.sppBtConnectListener = mSppBtConnectListener;
        try {
            this.mConnectTask.executeOnExecutor(Executors.newCachedThreadPool(), str);
        } catch (Exception e) {
            LogUtil.m339i(TAG, "connect_fail:" + e.getLocalizedMessage());
        }
    }

    public void send(byte[] bArr) {
        if (this.mConnectTask != null && DataHandle.getIns().isHasConnectedSpp()) {
            this.mConnectTask.send(bArr);
        } else {
            LogUtil.m339i(TAG, "ConnectTask==null");
        }
    }

    public void send(String str) {
        ConnectTask connectTask = this.mConnectTask;
        if (connectTask != null) {
            connectTask.send(str.getBytes());
        } else {
            LogUtil.m339i(TAG, "ConnectTask==null");
        }
    }
}
