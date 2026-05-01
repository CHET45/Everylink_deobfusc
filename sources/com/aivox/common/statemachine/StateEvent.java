package com.aivox.common.statemachine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.http.socket.WebSocketCallBack;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceInfo;
import com.aivox.common.model.EventBean;
import com.aivox.common.parse.WifiSendManagerForJson;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.microsoft.azure.storage.table.TableConstants;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class StateEvent {
    public static StateEvent ins;

    public void parsePhoneRecordPause(boolean z) {
    }

    public void parsePhoneRecordResume(boolean z) {
    }

    public void parsePhoneRecordUploadProgress(boolean z, int i) {
    }

    public void parseTranscribeServiceArrears() {
    }

    public static StateEvent getIns() {
        if (ins == null) {
            ins = new StateEvent();
        }
        return ins;
    }

    private Activity getActivity() {
        return AppManager.getAppManager().currentActivity();
    }

    public void reconnect() {
        EventBus.getDefault().post(new EventBean(59));
    }

    public void backToVerifid() {
        if (((Integer) SPUtil.get(SPUtil.RECORD_STATE, 0)).intValue() != 0) {
            EventBus.getDefault().post(new EventBean(104));
        }
    }

    public void parsePhoneVerified(boolean z) {
        if (z) {
            DialogUtils.hideLoadingDialog();
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_VERIFIED));
        } else {
            final Activity activityCurrentActivity = AppManager.getAppManager().currentActivity();
            activityCurrentActivity.runOnUiThread(new Runnable() { // from class: com.aivox.common.statemachine.StateEvent$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StateEvent.lambda$parsePhoneVerified$1(activityCurrentActivity);
                }
            });
        }
    }

    static /* synthetic */ void lambda$parsePhoneVerified$1(Activity activity) {
        DialogUtils.hideLoadingDialog();
        LogUtil.m339i("websocket", "身份校验失败，请重新登录");
        DialogUtils.showDialogWithDefBtnAndSingleListener(activity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.socket_phone_verified_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.statemachine.StateEvent$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.logout(context);
            }
        }, false, false);
    }

    public void parsePhoneRecordStart(boolean z, JSONObject jSONObject) {
        if (z) {
            if (jSONObject != null) {
                DataHandle.getIns().setFileName(jSONObject.optString("fileName"));
                DataHandle.getIns().setFileId(jSONObject.optInt("fileId"));
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "parsePhoneRecordStart -> fileId:" + DataHandle.getIns().getFileId());
            }
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_START));
        }
    }

    public void parsePhoneRecordStop(boolean z) {
        if (z) {
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_STOP));
        }
    }

    public void parseTranscribe(JSONObject jSONObject, WebSocketCallBack webSocketCallBack) {
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("transcribe");
        if (webSocketCallBack != null) {
            webSocketCallBack.onMessage(DeviceProtocol.MSG_ID_WIFI_JSON.PULL_TRANSCRIBE_INFO, jSONObjectOptJSONObject == null ? "" : jSONObjectOptJSONObject.toString());
        }
    }

    public void parsePointLess(int i) {
        EventBus.getDefault().post(new EventBean(63, i));
    }

    public void parseStorageLess() {
        EventBus.getDefault().post(new EventBean(62));
    }

    public void parseCurrentAccountIsLoggedOut() {
        EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.PULL_CURRENT_ACCOUNT_IS_LOGGED_OUT));
    }

    public void parseDeviceConnectState(JSONObject jSONObject) {
        int iOptInt = jSONObject.optInt("online", 1);
        DataHandle.getIns().setDeviceOnlineOrOffline(iOptInt);
        EventBus.getDefault().post(new EventBean(51));
        if (iOptInt == 1) {
            WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsRequestDeviceInfo(), DeviceProtocol.MSG_ID_WIFI_JSON.ACK_DEVICE_INFO);
        }
    }

    public void parseThirdConnectState(JSONObject jSONObject) {
        int iOptInt = jSONObject.optInt("connectStatus");
        int iOptInt2 = jSONObject.optInt("roomId");
        DataHandle.getIns().setDeviceRecordState(iOptInt != 1 ? 0 : 1);
        DataHandle.getIns().setRoomId(iOptInt2);
        LogUtil.m338i("连接状态：" + iOptInt);
        EventBus.getDefault().post(new EventBean(52));
    }

    public void parseDeviceInfo(JSONObject jSONObject) {
        DeviceInfo deviceInfo = (DeviceInfo) JsonUtils.getIns().jsonStr2Obj(jSONObject.toString(), DeviceInfo.class);
        DataHandle.getIns().setDeviceOnlineOrOffline(deviceInfo.getOnline());
        DataHandle.getIns().setDeviceInfo(deviceInfo);
        LogUtil.m338i(deviceInfo.toString());
        EventBus.getDefault().post(new EventBean(27));
    }

    public void parseDeviceRecordList(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("files");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            arrayList.add((AudioInfoBean) JsonUtils.getIns().jsonStr2Obj(jSONArrayOptJSONArray.optJSONObject(i).toString(), AudioInfoBean.class));
        }
        EventBus.getDefault().post(new EventBean(28));
    }

    public void parseDeviceRecordDelete(JSONObject jSONObject) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("fileName");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            arrayList.add(jSONArrayOptJSONArray.optString(i));
        }
    }

    public void parseLimitAndKey(JSONObject jSONObject, int i) {
        try {
            if (i != 1) {
                if (i == -1) {
                    EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_CHECK_LIMIT, i));
                    return;
                }
                return;
            }
            String strDecrypt = SerAESUtil.decrypt(jSONObject.getJSONObject("fromData").getString("data"), Constant.DECRYPT_KEY);
            if (BaseStringUtil.isNotEmpty(strDecrypt)) {
                JSONObject jSONObject2 = new JSONObject(strDecrypt);
                if (jSONObject2.has("appKey") && BaseStringUtil.isNotEmpty(jSONObject2.getString("appKey"))) {
                    String string = jSONObject.getJSONObject("fromData").getString(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
                    SPUtil.put(SPUtil.ALI_TRANS_KEY + string, jSONObject2.getString("appKey"));
                    SPUtil.put(SPUtil.ALI_TRANS_TOKEN + string, jSONObject2.getString(SPUtil.TOKEN));
                } else {
                    SPUtil.put(SPUtil.TENCENT_TRANS_SK, jSONObject2.getString("secretKey"));
                    SPUtil.put(SPUtil.TENCENT_TRANS_SI, jSONObject2.getString("secretId"));
                    SPUtil.put(SPUtil.TENCENT_TRANS_ID, jSONObject2.getString("appId"));
                }
            }
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_CHECK_LIMIT, i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
