package com.aivox.app.test.trans;

import android.content.Context;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.http.HttpException;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.EventBean;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class AudioTransModel {
    private final int PROGRESS_UPDATE_SEC;
    private final int TIME_OUT_SEC;
    private int mAudioId;
    private WeakReference<Context> mContext;
    private Disposable mDis;
    private EventBean<Object> mEventBean;
    private boolean mOnProgressing;
    private int mTotalTransTime;

    private AudioTransModel() {
        this.TIME_OUT_SEC = 10800;
        this.PROGRESS_UPDATE_SEC = 5;
    }

    private static final class InstanceHolder {
        static final AudioTransModel mInstance = new AudioTransModel();

        private InstanceHolder() {
        }
    }

    public static AudioTransModel getInstance() {
        return InstanceHolder.mInstance;
    }

    public void startTrans(Context context, int i, int i2) {
        if (this.mOnProgressing) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.another_trans_on_progressing));
            return;
        }
        this.mOnProgressing = true;
        this.mContext = new WeakReference<>(context);
        this.mAudioId = i;
        EventBean<Object> eventBean = new EventBean<>(Constant.EVENT.AUDIO_TRANS_START);
        this.mEventBean = eventBean;
        eventBean.setA(this.mAudioId);
        EventBus.getDefault().post(this.mEventBean);
        new AudioService(this.mContext.get()).speechToText(this.mAudioId, MyEnum.TRANSLATE_LANGUAGE.getLanguage(i2).alias, false, 0).subscribe(new Consumer() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2373lambda$startTrans$0$comaivoxapptesttransAudioTransModel(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2374lambda$startTrans$1$comaivoxapptesttransAudioTransModel((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startTrans$0$com-aivox-app-test-trans-AudioTransModel, reason: not valid java name */
    /* synthetic */ void m2373lambda$startTrans$0$comaivoxapptesttransAudioTransModel(Object obj) throws Exception {
        getTransProgress();
    }

    /* JADX INFO: renamed from: lambda$startTrans$1$com-aivox-app-test-trans-AudioTransModel, reason: not valid java name */
    /* synthetic */ void m2374lambda$startTrans$1$comaivoxapptesttransAudioTransModel(Throwable th) throws Exception {
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.ASR_COUNT_TOO_MUCH.code) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.server_error_code_3305));
            this.mTotalTransTime = 0;
            this.mOnProgressing = false;
            this.mEventBean.setFrom(212);
            EventBus.getDefault().post(this.mEventBean);
            return;
        }
        transFailed(th.getLocalizedMessage());
    }

    public void continueTrans(Context context, int i) {
        this.mContext = new WeakReference<>(context);
        this.mOnProgressing = true;
        this.mAudioId = i;
        EventBean<Object> eventBean = new EventBean<>(Constant.EVENT.AUDIO_TRANS_PROGRESS);
        this.mEventBean = eventBean;
        eventBean.setB(0);
        this.mEventBean.setA(this.mAudioId);
        EventBus.getDefault().post(this.mEventBean);
        getTransProgress();
    }

    private void getTransProgress() {
        this.mDis = Observable.interval(5L, TimeUnit.SECONDS).flatMap(new Function() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m323x56ff3fbf((Long) obj);
            }
        }).takeUntil((Predicate<? super R>) new Predicate() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return AudioTransModel.lambda$getTransProgress$3((Integer) obj);
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m324x65c9a9fd((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.test.trans.AudioTransModel$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m325x6d2edf1c((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getTransProgress$2$com-aivox-app-test-trans-AudioTransModel */
    /* synthetic */ ObservableSource m323x56ff3fbf(Long l) throws Exception {
        return new AudioService(this.mContext.get()).transcribeProgress(this.mAudioId);
    }

    static /* synthetic */ boolean lambda$getTransProgress$3(Integer num) throws Exception {
        return num.intValue() == 100;
    }

    /* JADX INFO: renamed from: lambda$getTransProgress$4$com-aivox-app-test-trans-AudioTransModel */
    /* synthetic */ void m324x65c9a9fd(Integer num) throws Exception {
        if (num.intValue() == 100) {
            this.mTotalTransTime = 0;
            this.mOnProgressing = false;
            this.mEventBean.setFrom(212);
            EventBus.getDefault().post(this.mEventBean);
            return;
        }
        int i = this.mTotalTransTime + 5;
        this.mTotalTransTime = i;
        if (i >= 10800) {
            transFailed("transcribe timeout");
            return;
        }
        this.mEventBean.setFrom(Constant.EVENT.AUDIO_TRANS_PROGRESS);
        this.mEventBean.setB(num.intValue());
        EventBus.getDefault().post(this.mEventBean);
    }

    /* JADX INFO: renamed from: lambda$getTransProgress$5$com-aivox-app-test-trans-AudioTransModel */
    /* synthetic */ void m325x6d2edf1c(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            transFailed(Constant.SeverErrorCode.getCodeMsg(BaseDataHandle.getIns().getCode()));
        } else {
            transFailed(th.getLocalizedMessage());
        }
    }

    public void destroy() {
        this.mOnProgressing = false;
        this.mTotalTransTime = 0;
        Disposable disposable = this.mDis;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mDis.dispose();
    }

    private void transFailed(String str) {
        this.mTotalTransTime = 0;
        this.mOnProgressing = false;
        this.mEventBean.setFrom(Constant.EVENT.AUDIO_TRANS_FAILED);
        this.mEventBean.setS1(str);
        EventBus.getDefault().post(this.mEventBean);
    }
}
