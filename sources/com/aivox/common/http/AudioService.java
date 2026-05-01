package com.aivox.common.http;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.common.Constant;
import com.aivox.base.http.BaseResponse;
import com.aivox.base.http.ObjectLoader;
import com.aivox.base.http.PayLoad;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.JsonUtils;
import com.aivox.common.model.ActivityBean;
import com.aivox.common.model.AiChatBean;
import com.aivox.common.model.AiTitleBean;
import com.aivox.common.model.AudioContentBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.DouBaoConfigBean;
import com.aivox.common.model.ExtraAudioBean;
import com.aivox.common.model.FolderBean;
import com.aivox.common.model.GlassOtaUpdateBean;
import com.aivox.common.model.LocalTransBean;
import com.aivox.common.model.NoticeBean;
import com.aivox.common.model.OpenAiConfigBean;
import com.aivox.common.model.OtaUpdateBean;
import com.aivox.common.model.OtherUserInfo;
import com.aivox.common.model.PayOrderResultBean;
import com.aivox.common.model.PricePackageList;
import com.aivox.common.model.PurchaseHistoryBean;
import com.aivox.common.model.ShareBean;
import com.aivox.common.model.TransKeyBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.model.TranscribeListBean;
import com.aivox.common.model.UploadAudioBean;
import com.aivox.common_ui.update.UpdateBean;
import com.alibaba.fastjson.JSON;
import com.microsoft.azure.storage.table.TableConstants;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.List;
import okhttp3.MultipartBody;

/* JADX INFO: loaded from: classes.dex */
public class AudioService extends ObjectLoader {
    public IAudioService api;

    public AudioService(Context context) {
        super(context);
    }

    private IAudioService getApi() {
        if (this.api == null) {
            this.api = (IAudioService) new RetrofitServiceManager().create(IAudioService.class);
        }
        return this.api;
    }

    public Observable<String> glassApi(String str) {
        return observe(getApi().glassApi(str)).map(new PayLoad());
    }

    public Observable<String> glassDeleteApi(String str, String[] strArr) {
        this.map = new HashMap<>();
        this.map.put("files", strArr);
        return observe(getApi().glassDeleteApi(str, this.map)).map(new PayLoad());
    }

    public Observable<String> glassOtaApi(String str, MultipartBody.Part part) {
        return observe(getApi().glassOtaApi(str, part)).map(new PayLoad());
    }

    public Observable<Integer> createAudioInfo(int i) {
        this.map = new HashMap<>();
        this.map.put("source", Integer.valueOf(i));
        return observe(getApi().createLocalAudioInfo(getReqBody())).map(new PayLoad());
    }

    public Observable<AudioInfoBean> saveLocalRecordInfo(int i, long j, long j2, String str, String str2, String str3, String str4, String str5) {
        this.map = new HashMap<>();
        this.map.put("audioTimeDuration", Long.valueOf(j));
        this.map.put("audioLength", Long.valueOf(j2));
        this.map.put(Constant.KEY_TITLE, str);
        this.map.put("localPath", str2);
        this.map.put("province", str3);
        this.map.put("city", str4);
        this.map.put("site", str5);
        return observe(getApi().saveLocalRecordInfo(i, getReqBody())).map(new PayLoad());
    }

    public Observable<AudioInfoBean> saveRecordInfo(int i, String str, String str2, String str3, long j, long j2, String str4, int i2) {
        this.map = new HashMap<>();
        this.map.put("id", Integer.valueOf(i));
        this.map.put(Constant.KEY_TITLE, str);
        this.map.put("site", str2);
        this.map.put("participant", str3);
        this.map.put("audioTimeDuration", Long.valueOf(j));
        this.map.put("audioLength", Long.valueOf(j2));
        this.map.put("localPath", str4);
        this.map.put("from", Integer.valueOf(i2));
        return observe(getApi().saveRecordInfo(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> changeSpeakerAvatar(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("audioId", str);
        this.map.put("speakerAvatarStyle", Integer.valueOf(i));
        return observe(getApi().changeSpeakerAvatar(getReqBody())).map(new PayLoad());
    }

    public Observable<Integer> addRecordFromLocal(UploadAudioBean uploadAudioBean) {
        String title = uploadAudioBean.getTitle();
        if (BaseStringUtil.isNotEmpty(title)) {
            if (title.length() > 25) {
                title = title.substring(0, 25).trim();
            }
            uploadAudioBean.setTitle(title);
        } else {
            uploadAudioBean.setTitle(DateUtil.getCurDate(DateUtil.YYYY_MM_DD_HH_MM_SS));
        }
        return observe(getApi().addRecordFromLocal(stringToReqBody(JSON.parseObject(JsonUtils.getIns().obj2jsonStr(uploadAudioBean)).toString()))).map(new PayLoad());
    }

    public Observable<AudioInfoBean> editRecordInfo(int i, String str) {
        this.map = new HashMap<>();
        this.map.put("id", Integer.valueOf(i));
        this.map.put(Constant.KEY_TITLE, str);
        return observe(getApi().editRecordInfo(getReqBody())).map(new PayLoad());
    }

    public Observable<AudioInfoBean> recordDetails(int i) {
        return observe(getApi().recordDetails(i)).map(new PayLoad());
    }

    public Observable<List<AudioInfoBean>> getLocalRecordList(List<Integer> list) {
        return observe(getApi().getLocalRecordList(list)).map(new PayLoad());
    }

    public Observable<Object> deleteRecycleFile(int[] iArr) {
        return observe(getApi().delRecycleFile(iArr)).map(new PayLoad());
    }

    public Observable<Object> deleteFile(String str) {
        return observe(getApi().delCloudFile(str)).map(new PayLoad());
    }

    public Observable<Object> clearRecycleBin() {
        return observe(getApi().clearRecycleBin()).map(new PayLoad());
    }

    public Observable<Integer> addImgTag(int i, int i2, String str, int i3) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("audioTransId", Integer.valueOf(i2));
        this.map.put("imageUrl", str);
        this.map.put("imageSize", Integer.valueOf(i3));
        return observe(getApi().addImgTag(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> deleteImgTag(int i) {
        return observe(getApi().deleteImgTag(i).map(new PayLoad()));
    }

    public Observable<Integer> addNoteMark(int i, int i2, long j, String str) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("audioTransId", Integer.valueOf(i2));
        this.map.put("markTime", Long.valueOf(j));
        this.map.put("content", str);
        return observe(getApi().addNoteMark(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> editNoteMark(int i, int i2, String str) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("markId", Integer.valueOf(i2));
        this.map.put("content", str);
        return observe(getApi().editNoteMark(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> deleteNoteMark(List<Integer> list) {
        return observe(getApi().deleteNoteMark(list).map(new PayLoad()));
    }

    public Observable<List<AudioMarkBean>> getNoteMarkList(int i) {
        return observe(getApi().getNoteMarkList(i).map(new PayLoad()));
    }

    public Observable<TranscribeListBean> getTransResultByPage(int i, int i2) {
        return observe(getApi().getTransResultByPage(i, i2, 20)).map(new PayLoad());
    }

    public Observable<Object> saveAsrContent(int i, LocalTransBean... localTransBeanArr) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("transResult", localTransBeanArr);
        return observe(getApi().saveLocalAsrContent(getReqBody())).map(new PayLoad());
    }

    public Observable<List<Transcribe>> batchEditContent(AudioContentBean audioContentBean) {
        return observe(getApi().batchEditContent(stringToReqBody(BaseStringUtil.replaceBlock(audioContentBean.toString())))).map(new PayLoad());
    }

    public Observable<Object> batchDeleteContent(String str) {
        return observe(getApi().batchDeleteContent(str).map(new PayLoad()));
    }

    public Observable<Object> updateSpeakerName(String str, String str2, String str3) {
        this.map = new HashMap<>();
        this.map.put("speaker", str);
        this.map.put("audioId", str2);
        this.map.put("speakerName", str3);
        return observe(getApi().updateSpeakerName(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> speechToText(int i, String str, boolean z, int i2) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("language", str);
        this.map.put("sync", 1);
        this.map.put("supportSpeaker", Integer.valueOf(z ? 1 : 0));
        this.map.put("speakerNumber", Integer.valueOf(i2));
        return observe(getApi().speechToText(getReqBody())).map(new PayLoad());
    }

    public Observable<Integer> transcribeProgress(int i) {
        return observe(getApi().transcribeProgress(i)).map(new PayLoad());
    }

    public Observable<Integer> separateProgress(int i) {
        return observe(getApi().separateProgress(i)).map(new PayLoad());
    }

    public Observable<Object> translateAll(int i, int i2) {
        this.map = new HashMap<>();
        this.map.put("to", Integer.valueOf(i2));
        this.map.put("audioId", Integer.valueOf(i));
        return observe(getApi().translateAll(getReqBody())).map(new PayLoad());
    }

    public Observable<Integer> getTransStatus(int i) {
        return observe(getApi().getTransStatus(i)).map(new PayLoad());
    }

    public Observable<ExtraAudioBean> getAudioListById(int i, int i2, String[] strArr) {
        return observe(getApi().getAudioListById(i, i2, strArr)).map(new PayLoad());
    }

    public Observable<List<FolderBean>> getFolderList() {
        return observe(getApi().getFolderList()).map(new PayLoad());
    }

    public Observable<AudioNewAllListBean.DataBean> getAllAudioList(int i, int i2, String str, int i3) {
        return observe(getApi().getAllAudioList(i, i2, str, i3)).map(new PayLoad());
    }

    public Observable<AudioNewAllListBean.DataBean> getAsteriskAudioList(int i, int i2, int i3) {
        return observe(getApi().getAsteriskAudioList(i, i2, i3)).map(new PayLoad());
    }

    public Observable<AudioNewAllListBean.DataBean> getRecycleAudioList(int i, int i2, int i3) {
        return observe(getApi().getRecycleAudioList(i, i2, i3)).map(new PayLoad());
    }

    public Observable<AudioNewAllListBean.DataBean> getPersonalAudioList(int i, int i2, int i3, int i4) {
        return observe(getApi().getPersonalAudioList(i, i2, i3, i4)).map(new PayLoad());
    }

    public Observable<Object> addToFolder(int i, int[] iArr) {
        this.map = new HashMap<>();
        this.map.put("tagId", Integer.valueOf(i));
        this.map.put("audioIds", iArr);
        return observe(getApi().addToFolder(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> createFolder(String str) {
        this.map = new HashMap<>();
        this.map.put("name", str);
        return observe(getApi().createFolder(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> renameFolder(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("name", str);
        this.map.put("tagId", Integer.valueOf(i));
        return observe(getApi().renameFolder(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> deleteFolder(int i) {
        return observe(getApi().deleteFolder(i)).map(new PayLoad());
    }

    public Observable<Object> moveOutFromFolder(String str) {
        return observe(getApi().moveOutFromFolder(str)).map(new PayLoad());
    }

    public Observable<Object> moveOutFromFavorite(String str) {
        return observe(getApi().moveOutFromFavorite(str)).map(new PayLoad());
    }

    public Observable<OtaUpdateBean> checkOtaUpdate(String str, double d) {
        return observe(getApi().checkOtaUpdate(str, d)).map(new PayLoad());
    }

    public Observable<GlassOtaUpdateBean> checkGlassOtaUpdate(String str) {
        return observe(getApi().checkGlassOtaUpdate(str)).map(new PayLoad());
    }

    public Observable<Object> glassSync(int i, int i2, int i3, String str, String str2, String str3) {
        this.map = new HashMap<>();
        this.map.put("manufacturerId", Integer.valueOf(i));
        this.map.put("deviceId", Integer.valueOf(i2));
        this.map.put(TypedValues.Custom.S_COLOR, Integer.valueOf(i3));
        this.map.put("hardwareName", str);
        this.map.put("bleVersion", str2);
        this.map.put("wifiVersion", str3);
        return observe(getApi().glassSync(getReqBody())).map(new PayLoad());
    }

    public Observable<ActivityBean> getActivityInfo() {
        return observe(getApi().getActivityInfo()).map(new PayLoad());
    }

    public Observable<UpdateBean> checkUpdate() {
        return observeCheckNet(getApi().checkUpdate(1)).map(new PayLoad());
    }

    public Observable<TransKeyBean> getTencentKey(int i, int i2) {
        return observe(getApi().getTencentKey(i, i2)).map(new PayLoad());
    }

    public Observable<String> getTencentCosKey() {
        return observe(getApi().getTencentCosKey()).map(new PayLoad());
    }

    public Observable<TransKeyBean> getTransKeys(int i, int i2) {
        return observe(getApi().getTencentAndAiKey(i, i2)).map(new PayLoad<TransKeyBean>() { // from class: com.aivox.common.http.AudioService.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.aivox.base.http.PayLoad, io.reactivex.functions.Function
            public TransKeyBean apply(BaseResponse<TransKeyBean> baseResponse) {
                return (TransKeyBean) super.apply((BaseResponse) baseResponse);
            }
        });
    }

    public Observable<String> getTranslateInfo() {
        return observe(getApi().getTranslateInfo()).map(new PayLoad());
    }

    public Observable<String> getAzureInfo() {
        return observe(getApi().getAzureInfo()).map(new PayLoad());
    }

    public Observable<DouBaoConfigBean> getDouBaoConfig() {
        return observe(getApi().getDouBaoConfig()).map(new PayLoad());
    }

    public Observable<OpenAiConfigBean> getOpenAiConfig() {
        return observe(getApi().getOpenAiConfig()).map(new PayLoad());
    }

    public Observable<NoticeBean> getAllNewNotice() {
        return observeCheckNet(getApi().getAllNewNotice(1, 100, 0)).map(new PayLoad());
    }

    public Observable<Object> deleteNotify(int i) {
        this.map = new HashMap<>();
        this.map.put("id", Integer.valueOf(i));
        return observe(getApi().deleteNotify(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> syncFile(int i, String str, long j) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put("audioLength", Long.valueOf(j));
        this.map.put("audioUrl", str);
        return observe(getApi().syncFile(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> syncFileFromRecycleBin(List<Integer> list) {
        this.map = new HashMap<>();
        this.map.put("audioIds", list);
        return observe(getApi().syncFilesFromRecycle(getReqBody())).map(new PayLoad());
    }

    public Observable<ShareBean> getSignForShare(int i, int i2, int i3, int i4, int i5) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i4));
        this.map.put("dueDate", Integer.valueOf(i));
        this.map.put("frequency", Integer.valueOf(i2));
        this.map.put("isSave", Integer.valueOf(i5));
        this.map.put("shareType", 0);
        this.map.put("hasPsw", Integer.valueOf(i3));
        return observe(getApi().getSignForAllShare(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> getSignForAppShare(int i, int i2, String str) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i2));
        this.map.put("dueDate", Integer.valueOf(i));
        this.map.put("uuid", str);
        return observe(getApi().getSignForAppShare(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> saveAppShare(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("sign", str);
        this.map.put("messageNotifyId", Integer.valueOf(i));
        return observe(getApi().saveAppShare(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> generateAiTitleById(int i) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        return observe(getApi().generateAiTitleById(getReqBody())).map(new PayLoad());
    }

    public Observable<AiTitleBean> getAiTitleById(int i) {
        return observe(getApi().getAiTitleById(i)).map(new PayLoad());
    }

    public Observable<Object> editAiChat(int i, String str) {
        this.map = new HashMap<>();
        this.map.put("id", Integer.valueOf(i));
        this.map.put("answer", str);
        return observe(getApi().editAiChat(getReqBody())).map(new PayLoad());
    }

    public Observable<AiChatBean> getAiChatList(int i, int i2, int i3) {
        return observe(getApi().getAiChatList(i, i2, i3)).map(new PayLoad());
    }

    public Observable<AiChatBean.Records> getAiChatDetail(int i) {
        return observe(getApi().getAiChatDetail(i)).map(new PayLoad());
    }

    public Observable<Integer> generateByAi(int i, int i2, String str) {
        this.map = new HashMap<>();
        this.map.put("audioId", Integer.valueOf(i));
        this.map.put(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, Integer.valueOf(i2));
        this.map.put("question", str);
        return observe(getApi().generateByAi(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> deleteAiChatList(List<Integer> list) {
        return observe(getApi().deleteAiChatList(list)).map(new PayLoad());
    }

    public Observable<List<PricePackageList>> getPackageList(int i) {
        return observe(getApi().getPricePackageList(i, 2)).map(new PayLoad());
    }

    public Observable<PurchaseHistoryBean> getPurchaseHistoryList(int i, int i2) {
        return observe(getApi().getPurchaseHistoryList(i, i2)).map(new PayLoad());
    }

    public Observable<String> createOrder(String str) {
        this.map = new HashMap<>();
        this.map.put("priceUuid", str);
        this.map.put("platform", ExifInterface.GPS_MEASUREMENT_2D);
        return observe(getApi().createOrder(getReqBody())).map(new PayLoad());
    }

    public Observable<PayOrderResultBean> payOrder(String str, int i) {
        this.map = new HashMap<>();
        this.map.put("orderNo", str);
        this.map.put("payType", Integer.valueOf(i));
        return observe(getApi().payOrder(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> closePayment(String str, int i) {
        return observe(getApi().closePayment(str, i)).map(new PayLoad());
    }

    public Observable<Object> cancelSubscription(String str) {
        this.map = new HashMap<>();
        this.map.put("reason", str);
        return observe(getApi().cancelSubscription(getReqBody())).map(new PayLoad());
    }

    public Observable<Object> verifyGooglePay(String str, String str2) {
        this.map = new HashMap<>();
        this.map.put("orderNo", str);
        this.map.put("purchaseToken", str2);
        return observe(getApi().verifyGooglePay(getReqBody())).map(new PayLoad());
    }

    public Observable<OtherUserInfo> getOtherUserInfo(String str) {
        return observe(getApi().getOtherUserInfo(str)).map(new PayLoad());
    }

    public Observable<Boolean> checkDeviceActivated(String str) {
        return observe(getApi().checkDeviceActivated(str)).map(new PayLoad());
    }

    public Observable<Boolean> activateDevice(String str) {
        this.map = new HashMap<>();
        this.map.put("macAddress", str);
        return observe(getApi().activateDevice(getReqBody())).map(new PayLoad());
    }
}
