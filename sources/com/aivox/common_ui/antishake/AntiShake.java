package com.aivox.common_ui.antishake;

import com.aivox.base.util.MyAnimationUtil;

/* JADX INFO: loaded from: classes.dex */
public class AntiShake {
    public static final int CLICK_DELAY_TIME = 500;
    public static final int CLICK_DELAY_TIME_2 = 2000;
    private static long lastClickTime;
    private static LimitQueue<OneClick> queue = new LimitQueue<>(20);

    public static boolean check(Object obj, int i) {
        String string;
        if (obj == null) {
            string = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            string = obj.toString();
        }
        for (OneClick oneClick : queue.getArrayList()) {
            if (oneClick.getMethodName().equals(string)) {
                return oneClick.check(i);
            }
        }
        OneClick oneClick2 = new OneClick(string);
        queue.offer(oneClick2);
        return oneClick2.check(i);
    }

    public static boolean check(Object obj) {
        return check(obj, 500);
    }

    public static boolean isFastDoubleClick() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastClickTime < MyAnimationUtil.ANI_TIME_2000) {
            return true;
        }
        lastClickTime = jCurrentTimeMillis;
        return false;
    }
}
