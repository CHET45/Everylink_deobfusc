package com.aivox.common.translate;

import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BreakUpSentence;
import com.aivox.base.util.LogUtil;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.TranslateInfoBean;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class SeqTransModel {
    private static final String TAG = "SeqTransModel";
    public static int TRANS_STATE_DONE = 2;
    public static int TRANS_STATE_EMPTY = 0;
    public static int TRANS_STATE_TRANSLATING = 1;
    private final int TRANS_RETRY_TIME;
    private HashMap<Integer, SeqTransBean> mTransMap;
    private Map<Integer, Integer> tranRetryMap;

    public interface ISeqTranCallback {
        void onComplete(boolean z, int i, String str, int i2, String str2);
    }

    public interface TransCallback {
        void transResult(int i, String str, int i2, int i3, String str2);
    }

    /* synthetic */ SeqTransModel(C09921 c09921) {
        this();
    }

    private SeqTransModel() {
        this.TRANS_RETRY_TIME = 5;
        this.tranRetryMap = new HashMap();
    }

    private static final class MInstanceHolder {
        static final SeqTransModel mInstance = new SeqTransModel(null);

        private MInstanceHolder() {
        }
    }

    public static SeqTransModel getInstance() {
        return MInstanceHolder.mInstance;
    }

    public void destroy() {
        HashMap<Integer, SeqTransBean> map = this.mTransMap;
        if (map != null) {
            map.clear();
        }
        Map<Integer, Integer> map2 = this.tranRetryMap;
        if (map2 != null) {
            map2.clear();
        }
    }

    public void start(int i, int i2, int i3, boolean z, int i4, String str, String str2, ISeqTranCallback iSeqTranCallback) {
        String[] strArrSplitSentence = BreakUpSentence.splitSentence(str);
        if (strArrSplitSentence.length >= 3 || !z) {
            if (this.mTransMap == null) {
                this.mTransMap = new HashMap<>(5);
            }
            SeqTransBean seqTransBean = this.mTransMap.get(Integer.valueOf(i));
            if (seqTransBean == null) {
                seqTransBean = new SeqTransBean(i, new ArrayList());
                this.mTransMap.put(Integer.valueOf(i), seqTransBean);
            }
            SeqTransBean seqTransBean2 = seqTransBean;
            LogUtil.m335d(TAG, i + "List:" + seqTransBean2.getSepreatItemList().size() + ",len:" + strArrSplitSentence.length + str + "isSeq:" + z);
            if (z) {
                if (strArrSplitSentence.length <= seqTransBean2.getSepreatItemList().size()) {
                    return;
                }
                int size = seqTransBean2.getSepreatItemList().size();
                LogUtil.m335d(TAG, "长于三句的中间态 start" + size + ",strings.length :" + strArrSplitSentence.length + strArrSplitSentence[strArrSplitSentence.length - 1]);
                while (size < strArrSplitSentence.length - 2) {
                    LogUtil.m335d(TAG, "长于三句的中间态add:" + strArrSplitSentence[size]);
                    seqTransBean2.getSepreatItemList().add(new SeqTransBean.SeparateItem(size, strArrSplitSentence[size], TRANS_STATE_EMPTY));
                    size++;
                }
            } else {
                LogUtil.m335d(TAG, " 完成态 start" + seqTransBean2.getSepreatItemList().size() + " ,strings.length :" + strArrSplitSentence.length + " && isSeq :" + z);
                seqTransBean2.getSepreatItemList().clear();
                seqTransBean2.getSepreatItemList().add(new SeqTransBean.SeparateItem(strArrSplitSentence.length, str, TRANS_STATE_EMPTY));
            }
            startTranslate(seqTransBean2, i2, i3, i4, z, str2, iSeqTranCallback);
            LogUtil.m335d(TAG, "start strings.length:" + strArrSplitSentence.length + ",isSeq:" + z + ",SepreatItemList:" + seqTransBean2.getSepreatItemList().size() + ",lastEd:" + i);
        }
    }

    private void startTranslate(SeqTransBean seqTransBean, int i, int i2, int i3, final boolean z, String str, final ISeqTranCallback iSeqTranCallback) {
        for (int i4 = 0; i4 < seqTransBean.getSepreatItemList().size(); i4++) {
            if (seqTransBean.getSepreatItemList().get(i4).state == TRANS_STATE_EMPTY) {
                LogUtil.m335d(TAG, "startTranslate：" + seqTransBean.getSepreatItemList().get(i4).src + seqTransBean.getSepreatItemList().get(i4).index);
                seqTransBean.getSepreatItemList().get(i4).state = TRANS_STATE_TRANSLATING;
                reqTransNiu(seqTransBean.getEndTime(), seqTransBean.getSepreatItemList().get(i4).src, i, i2, i3, seqTransBean.getSepreatItemList().get(i4).index, str, new TransCallback() { // from class: com.aivox.common.translate.SeqTransModel$$ExternalSyntheticLambda0
                    @Override // com.aivox.common.translate.SeqTransModel.TransCallback
                    public final void transResult(int i5, String str2, int i6, int i7, String str3) {
                        this.f$0.m2468lambda$startTranslate$0$comaivoxcommontranslateSeqTransModel(z, iSeqTranCallback, i5, str2, i6, i7, str3);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$startTranslate$0$com-aivox-common-translate-SeqTransModel, reason: not valid java name */
    /* synthetic */ void m2468lambda$startTranslate$0$comaivoxcommontranslateSeqTransModel(boolean z, ISeqTranCallback iSeqTranCallback, int i, String str, int i2, int i3, String str2) {
        LogUtil.m335d(TAG, "TranslateDone:" + i + "s:" + str + "，sentenceIndex" + i3);
        StringBuilder sb = new StringBuilder();
        SeqTransBean seqTransBean = this.mTransMap.get(Integer.valueOf(i));
        if (seqTransBean != null) {
            if (seqTransBean.getSepreatItemList() != null) {
                for (int i4 = 0; i4 < seqTransBean.getSepreatItemList().size(); i4++) {
                    if (seqTransBean.getSepreatItemList().get(i4).index == i3) {
                        seqTransBean.getSepreatItemList().get(i4).transResult = str;
                        seqTransBean.getSepreatItemList().get(i4).state = TRANS_STATE_DONE;
                    }
                }
                boolean z2 = true;
                for (int i5 = 0; i5 < seqTransBean.getSepreatItemList().size(); i5++) {
                    if (seqTransBean.getSepreatItemList().get(i5).state == TRANS_STATE_DONE) {
                        sb.append(seqTransBean.getSepreatItemList().get(i5).transResult);
                    } else {
                        z2 = false;
                    }
                }
                if (!z && z2) {
                    this.mTransMap.remove(Integer.valueOf(i));
                }
            }
            iSeqTranCallback.onComplete(z, i, sb.toString(), i2, str2);
        }
    }

    static class SeqTransBean {
        int endTime;
        List<SeparateItem> sepreatItemList;

        public SeqTransBean(int i, List<SeparateItem> list) {
            this.endTime = i;
            this.sepreatItemList = list;
        }

        public int getEndTime() {
            return this.endTime;
        }

        public void setEndTime(int i) {
            this.endTime = i;
        }

        public List<SeparateItem> getSepreatItemList() {
            return this.sepreatItemList;
        }

        public void setSepreatItemList(List<SeparateItem> list) {
            this.sepreatItemList = list;
        }

        static class SeparateItem {
            int index;
            String src;
            int state;
            String transResult;

            public SeparateItem(int i, String str, int i2) {
                this.index = i;
                this.src = str;
                this.state = i2;
            }
        }
    }

    public void reqTransNiu(int i, String str, int i2, int i3, int i4, int i5, String str2, TransCallback transCallback) {
        TranslateInfoBean translateInfo = DataHandle.getIns().getTranslateInfo();
        if (translateInfo == null) {
            transCallback.transResult(i, "", i4, i5, str2);
            return;
        }
        try {
            String str3 = translateInfo.getXiaoNiuApi() + "?from=" + MyEnum.TRANSLATE_LANGUAGE.getLanguage(i2).name + "&to=" + MyEnum.TRANSLATE_LANGUAGE.getLanguage(i3).name + "&apikey=" + translateInfo.getXiaoNiuApiKey() + "&src_text=" + URLEncoder.encode(str, "utf-8");
            LogUtil.m335d(TAG, "reqTransUrl" + str3);
            new OkHttpClient.Builder().connectTimeout(20L, TimeUnit.SECONDS).build().newCall(new Request.Builder().url(str3).get().build()).enqueue(new C09921(i, transCallback, i4, i5, str2, str, i2, i3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: com.aivox.common.translate.SeqTransModel$1 */
    class C09921 implements Callback {
        final /* synthetic */ TransCallback val$callback;
        final /* synthetic */ int val$from;
        final /* synthetic */ int val$index;
        final /* synthetic */ int val$lastED;
        final /* synthetic */ String val$realIndex;
        final /* synthetic */ int val$sentenceIndex;
        final /* synthetic */ String val$text;
        final /* synthetic */ int val$to;

        C09921(int i, TransCallback transCallback, int i2, int i3, String str, String str2, int i4, int i5) {
            this.val$lastED = i;
            this.val$callback = transCallback;
            this.val$index = i2;
            this.val$sentenceIndex = i3;
            this.val$realIndex = str;
            this.val$text = str2;
            this.val$from = i4;
            this.val$to = i5;
        }

        @Override // okhttp3.Callback
        public void onFailure(final Call call, final IOException iOException) {
            final int i = this.val$lastED;
            final TransCallback transCallback = this.val$callback;
            final int i2 = this.val$index;
            final int i3 = this.val$sentenceIndex;
            final String str = this.val$realIndex;
            final String str2 = this.val$text;
            final int i4 = this.val$from;
            final int i5 = this.val$to;
            new Thread(new Runnable() { // from class: com.aivox.common.translate.SeqTransModel$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2469lambda$onFailure$0$comaivoxcommontranslateSeqTransModel$1(i, transCallback, i2, i3, str, iOException, str2, i4, i5, call);
                }
            }).start();
        }

        /* JADX INFO: renamed from: lambda$onFailure$0$com-aivox-common-translate-SeqTransModel$1, reason: not valid java name */
        /* synthetic */ void m2469lambda$onFailure$0$comaivoxcommontranslateSeqTransModel$1(int i, TransCallback transCallback, int i2, int i3, String str, IOException iOException, String str2, int i4, int i5, Call call) {
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                LogUtil.m338i("e：" + e.getLocalizedMessage());
            }
            if (!SeqTransModel.this.tranRetryMap.containsKey(Integer.valueOf(i))) {
                SeqTransModel.this.tranRetryMap.put(Integer.valueOf(i), 1);
                SeqTransModel.this.reqTransNiu(i, str2, i4, i5, i2, i3, str, transCallback);
            } else if (((Integer) SeqTransModel.this.tranRetryMap.get(Integer.valueOf(i))).intValue() >= 5) {
                transCallback.transResult(i, "", i2, i3, str);
                BaseAppUtils.printErrorMsg(iOException, "niuTransError after try 5 times");
            } else {
                LogUtil.m334d("SeqTransModelniuTransErrortranRetryMap" + SeqTransModel.this.tranRetryMap);
                SeqTransModel.this.tranRetryMap.put(Integer.valueOf(i), Integer.valueOf(((Integer) SeqTransModel.this.tranRetryMap.get(Integer.valueOf(i))).intValue() + 1));
                SeqTransModel.this.reqTransNiu(i, str2, i4, i5, i2, i3, str, transCallback);
            }
            call.cancel();
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) {
            try {
                String strString = response.body().string();
                this.val$callback.transResult(this.val$lastED, JSONObject.parseObject(strString).getString("tgt_text"), this.val$index, this.val$sentenceIndex, this.val$realIndex);
                LogUtil.m334d("SeqTransModelonResponse" + strString);
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e, "niuTransError_response");
                this.val$callback.transResult(this.val$lastED, "", this.val$index, this.val$sentenceIndex, this.val$realIndex);
            }
            call.cancel();
        }
    }
}
