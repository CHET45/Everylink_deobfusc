package com.aivox.common.util;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.luck.picture.lib.basic.PictureSelectorSupporterActivity;

/* JADX INFO: loaded from: classes.dex */
public class IsForeBackGroundActivityCallback implements Application.ActivityLifecycleCallbacks {
    public static final String TAG = "LIFECYCLE_CALLBACK";
    private int foregroundActivities = 0;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        LogUtil.m339i(TAG, "onCreated: " + activity.toString());
        if (activity instanceof PictureSelectorSupporterActivity) {
            LayoutUtil.fitSystemInsets(activity.getWindow().getDecorView().findViewById(R.id.content), false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        LogUtil.m339i(TAG, "onStarted: " + activity.getLocalClassName() + " ---> 栈顶 Activity");
        int i = this.foregroundActivities + 1;
        this.foregroundActivities = i;
        if (i == 1) {
            if (RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.RECORD_ING) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "APP IN FOREGROUND");
            }
            LogUtil.m339i(TAG, "APP应用切换到前台");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        LogUtil.m339i(TAG, "onResumed: " + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        LogUtil.m339i(TAG, "onPaused: " + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        LogUtil.m339i(TAG, "onStopped: " + activity.getLocalClassName());
        int i = this.foregroundActivities - 1;
        this.foregroundActivities = i;
        if (i == 0) {
            if (RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.RECORD_ING) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "APP IN BACKGROUND");
            }
            LogUtil.m339i(TAG, "APP应用切换到后台");
            LogUtil.m339i(TAG, "recordState: " + ((Integer) SPUtil.get(SPUtil.RECORD_STATE, 0)).intValue());
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        LogUtil.m339i(TAG, "onSaveInstanceState: " + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        LogUtil.m339i(TAG, "onDestroyed: " + activity.getLocalClassName());
    }
}
