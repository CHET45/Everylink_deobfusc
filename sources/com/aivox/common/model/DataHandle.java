package com.aivox.common.model;

import com.aivox.base.common.MyEnum;
import com.aivox.base.util.DateUtil;
import com.aivox.common.ble.service.CommonServiceUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataHandle {
    private static DataHandle ins;
    private int aiNamingAudioId;
    private AudioInfoBean audioInfoBeanPlaying;
    private int curTransType;
    private DeviceInfo deviceInfo;
    private int deviceOnlineOrOffline;
    private int fileId;
    private boolean hasConnectedBle;
    private boolean hasConnectedSpp;
    private boolean hasSetPwd;
    private String headsetVersion;
    private AiConfigBean mAiConfig;
    private FunctionRightsBean mFunctionBean;
    private TranslateInfoBean mInfoBean;
    private boolean mIsPhoneState;
    private boolean mLength401;
    private boolean mShouldHandleAiChat;
    private boolean mShouldHandleRecognition;
    private boolean recordFileDeleteSuccess;
    private int roomId;
    private boolean rtcDone;
    private ServiceUrl serviceUrl;
    private boolean transcribeWsCanClosed;
    private UserInfo userInfo;
    private String wxPayStatus = "";
    private List<AudioInfoBean> btRecordList = new ArrayList();
    private int breakAudioId = -1;
    private int deviceRecordState = 0;
    private List<String> delFailsFiles = new ArrayList();
    private List<UseHistory> useHistoryList = new ArrayList();
    private byte[] stopTime = new byte[4];
    private String fileName = "";

    public static DataHandle getIns() {
        if (ins == null) {
            ins = new DataHandle();
        }
        return ins;
    }

    public boolean hasConnectedBluetooth(boolean z) {
        return z ? (this.hasConnectedSpp || this.hasConnectedBle) && !MyEnum.DEVICE_MODEL.DIGI_GIFT_BOX.name.equals(CommonServiceUtils.getInstance().getConnectedDeviceName()) : this.hasConnectedSpp || this.hasConnectedBle;
    }

    public boolean isHasConnectedBle(boolean z) {
        if (z) {
            return this.hasConnectedBle && !MyEnum.DEVICE_MODEL.DIGI_GIFT_BOX.name.equals(CommonServiceUtils.getInstance().getConnectedDeviceName());
        }
        return this.hasConnectedBle;
    }

    public void setHasConnectedBle(boolean z) {
        this.hasConnectedBle = z;
    }

    public boolean isHasConnectedSpp() {
        return this.hasConnectedSpp;
    }

    public void setHasConnectedSpp(boolean z) {
        this.hasConnectedSpp = z;
    }

    public void setServiceUrl(ServiceUrl serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public ServiceUrl getServiceUrl() {
        if (this.serviceUrl == null) {
            this.serviceUrl = new ServiceUrl();
        }
        return this.serviceUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int i) {
        this.fileId = i;
    }

    public boolean isTranscribeWsCanClosed() {
        return this.transcribeWsCanClosed;
    }

    public void setTranscribeWsCanClosed(boolean z) {
        this.transcribeWsCanClosed = z;
    }

    public int getDeviceOnlineOrOffline() {
        return this.deviceOnlineOrOffline;
    }

    public void setDeviceOnlineOrOffline(int i) {
        this.deviceOnlineOrOffline = i;
    }

    public AudioInfoBean getAudioInfoPlaying() {
        return this.audioInfoBeanPlaying;
    }

    public void setAudioInfoPlaying(String str, String str2, boolean z) {
        if (this.audioInfoBeanPlaying == null) {
            this.audioInfoBeanPlaying = new AudioInfoBean();
        }
        this.audioInfoBeanPlaying.setTitle(str);
        this.audioInfoBeanPlaying.setStartTime(DateUtil.local2UTC(Long.parseLong(str2) * 1000, DateUtil.YYYY_MM_DD_HH_MM_SS));
    }

    public void setAudioInfoPlayingStartTime(int i) {
        if (this.audioInfoBeanPlaying == null) {
            this.audioInfoBeanPlaying = new AudioInfoBean();
        }
        this.audioInfoBeanPlaying.setStartTime(DateUtil.local2UTC(((long) i) * 1000, DateUtil.YYYY_MM_DD_HH_MM_SS));
    }

    public void setAudioInfoPlayingNull() {
        this.audioInfoBeanPlaying = null;
    }

    public boolean isRtcDone() {
        return this.rtcDone;
    }

    public void setRtcDone(boolean z) {
        this.rtcDone = z;
    }

    public byte[] getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(byte[] bArr) {
        this.stopTime = bArr;
    }

    public List<String> getDelFailsFiles() {
        return this.delFailsFiles;
    }

    public void setDelFailsFiles(List<String> list) {
        this.delFailsFiles = list;
    }

    public List<UseHistory> getUseHistoryList() {
        return this.useHistoryList;
    }

    public void setUseHistoryList(UseHistory useHistory) {
        this.useHistoryList.add(useHistory);
    }

    public boolean isRecordFileDeleteSuccess() {
        return this.recordFileDeleteSuccess;
    }

    public void setRecordFileDeleteSuccess(boolean z) {
        this.recordFileDeleteSuccess = z;
    }

    public void setRoomId(int i) {
        this.roomId = i;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public int getDeviceRecordState() {
        return this.deviceRecordState;
    }

    public void setDeviceRecordState(int i) {
        this.deviceRecordState = i;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setDeviceInfo2(DeviceInfo deviceInfo) {
        this.deviceInfo.setRecordStatus("1");
        this.deviceInfo.setTranscribeType(deviceInfo.getTranscribeType());
        this.deviceInfo.setMeetingType(deviceInfo.getMeetingType());
        this.deviceInfo.setFrom(deviceInfo.getFrom());
        this.deviceInfo.setTo(deviceInfo.getTo());
        this.deviceInfo.setFromF(deviceInfo.getFromF());
        this.deviceInfo.setToF(deviceInfo.getToF());
        this.deviceInfo.setFileName(deviceInfo.getFileName());
        this.deviceInfo.setFileId(deviceInfo.getFileId());
        this.deviceInfo.setIsRealTimeSave(deviceInfo.getIsRealTimeSave());
    }

    public List<AudioInfoBean> getBtRecordList() {
        return this.btRecordList;
    }

    public void setBtRecordList(AudioInfoBean audioInfoBean) {
        this.btRecordList.add(audioInfoBean);
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void clear() {
        List<AudioInfoBean> list = this.btRecordList;
        if (list != null) {
            list.clear();
        }
        List<UseHistory> list2 = this.useHistoryList;
        if (list2 != null) {
            list2.clear();
        }
        List<String> list3 = this.delFailsFiles;
        if (list3 != null) {
            list3.clear();
        }
        setRtcDone(false);
    }

    public String getWxPayStatus() {
        return this.wxPayStatus;
    }

    public void setWxPayStatus(String str) {
        this.wxPayStatus = str;
    }

    public boolean isVip() {
        UserInfo userInfo = this.userInfo;
        return userInfo != null && userInfo.isVip();
    }

    public boolean hasSetPwd() {
        return this.hasSetPwd;
    }

    public void setHasSetPwd(boolean z) {
        this.hasSetPwd = z;
    }

    public FunctionRightsBean getFunctionBean() {
        if (this.mFunctionBean == null) {
            FunctionRightsBean functionRightsBean = new FunctionRightsBean();
            this.mFunctionBean = functionRightsBean;
            functionRightsBean.setAiSummaryLimit(3);
            this.mFunctionBean.setImageInsert(true);
            this.mFunctionBean.setImageInsertLimit(Integer.valueOf(isVip() ? 10 : 3));
            this.mFunctionBean.setBilingualCountLimit(Integer.valueOf(isVip() ? 999 : 10));
            this.mFunctionBean.setAiChatLimit(3);
        }
        return this.mFunctionBean;
    }

    public void setFunctionBean(FunctionRightsBean functionRightsBean) {
        this.mFunctionBean = functionRightsBean;
    }

    public void setTranslateInfo(TranslateInfoBean translateInfoBean) {
        this.mInfoBean = translateInfoBean;
    }

    public TranslateInfoBean getTranslateInfo() {
        return this.mInfoBean;
    }

    public int getCurTransType() {
        return this.curTransType;
    }

    public void setCurTransType(int i) {
        this.curTransType = i;
    }

    public String getHeadsetVersion() {
        return this.headsetVersion;
    }

    public void setHeadsetVersion(String str) {
        this.headsetVersion = str;
    }

    public void setBreakAudioId(int i) {
        this.breakAudioId = i;
    }

    public int getBreakAudioId() {
        return this.breakAudioId;
    }

    public void setAiNamingAudioId(int i) {
        this.aiNamingAudioId = i;
    }

    public int getAiNamingAudioId() {
        return this.aiNamingAudioId;
    }

    public void setLength401(boolean z) {
        this.mLength401 = z;
    }

    public boolean isLength401() {
        return this.mLength401;
    }

    public boolean isPhoneState() {
        return this.mIsPhoneState;
    }

    public void setIsPhoneState(boolean z) {
        this.mIsPhoneState = z;
    }

    public boolean isShouldHandleAiChat() {
        return this.mShouldHandleAiChat;
    }

    public void setShouldHandleAiChat(boolean z) {
        this.mShouldHandleAiChat = z;
    }

    public AiConfigBean getAiConfig() {
        return this.mAiConfig;
    }

    public void setAiConfig(AiConfigBean aiConfigBean) {
        this.mAiConfig = aiConfigBean;
    }

    public boolean isShouldHandleRecognition() {
        return this.mShouldHandleRecognition;
    }

    public void setShouldHandleRecognition(boolean z) {
        this.mShouldHandleRecognition = z;
    }
}
