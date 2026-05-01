package com.aivox.base.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import com.aivox.base.util.BaseAppUtils;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class AppManager {
    private static LinkedList<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        return activityStack.getLast();
    }

    public void finishActivity() {
        finishActivity(activityStack.getLast());
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            LinkedList<Activity> linkedList = activityStack;
            if (linkedList != null) {
                linkedList.remove(activity);
            }
            activity.finish();
        }
    }

    public void finishActivity(Class<?>... clsArr) {
        LinkedList<Activity> linkedList = new LinkedList();
        linkedList.addAll(activityStack);
        for (Activity activity : linkedList) {
            if (activity.getClass().equals(clsArr)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void finishAllActivityExcept(Class<?> cls) {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null && !activityStack.get(i).getClass().equals(cls)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public LinkedList<Activity> getAllActivity() {
        return activityStack;
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ((ActivityManager) context.getSystemService("activity")).restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public int ActivityStackSize() {
        LinkedList<Activity> linkedList = activityStack;
        if (linkedList == null) {
            return 0;
        }
        return linkedList.size();
    }
}
