package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GestureInfo implements Serializable {
    public static final int ACTION_NONE = 0;
    public static final int ALL = 255;
    public static final int AMBIENT_AWARE = 4;
    public static final int ANC = 3;
    public static final int ANC_AMBIENT_AWARE_OFF = 9;
    public static final int CANCEL_AMAZON_ALEXA = 165;
    public static final int CANCEL_DEFAULT_ASSISTANT = 160;
    public static final int CANCEL_GOOGLE_ASSISTANT = 162;
    public static final int CANCEL_TENCENT_XIAOWEI = 167;
    public static final int DOUBLE_TAP_HOLD_LEFT = 51;
    public static final int DOUBLE_TAP_HOLD_RIGHT = 52;
    public static final int DOUBLE_TAP_LEFT = 3;
    public static final int DOUBLE_TAP_RIGHT = 4;
    public static final int GOOGLE_ASSISTANT_NOTIFICATION_CALL_OUT = 163;
    public static final int LEFT_ALL = 253;
    public static final int LONG_PRESS_LEFT = 5;
    public static final int LONG_PRESS_RIGHT = 6;
    public static final int NEXT_TRACK = 6;
    public static final int PLAY_PAUSE = 8;
    public static final int PREVIOUS_TRACK = 7;
    public static final int RELEASE_TAP_HOLD_LEFT = 53;
    public static final int RELEASE_TAP_HOLD_RIGHT = 54;
    public static final int RIGHT_ALL = 254;
    public static final int SINGLE_TAP_HOLD_LEFT = 49;
    public static final int SINGLE_TAP_HOLD_RIGHT = 50;
    public static final int SINGLE_TAP_LEFT = 1;
    public static final int SINGLE_TAP_RIGHT = 2;
    public static final int SWIPE_BACKWARD_LEFT = 19;
    public static final int SWIPE_BACKWARD_RIGHT = 20;
    public static final int SWIPE_DOWN_LEFT = 23;
    public static final int SWIPE_DOWN_RIGHT = 24;
    public static final int SWIPE_FORWARD_LEFT = 17;
    public static final int SWIPE_FORWARD_RIGHT = 18;
    public static final int SWIPE_UP_LEFT = 21;
    public static final int SWIPE_UP_RIGHT = 22;
    public static final int TALK_THROUGH = 5;
    public static final int TALK_TO_AMAZON_ALEXA = 166;
    public static final int TALK_TO_DEFAULT_ASSISTANT = 161;
    public static final int TALK_TO_GOOGLE_ASSISTANT = 164;
    public static final int TALK_TO_TENCENT_XIAOWEI = 168;
    public static final int TRIPLE_TAP_LEFT = 7;
    public static final int TRIPLE_TAP_RIGHT = 8;
    public static final int VOLUME_DOWN = 2;
    public static final int VOLUME_UP = 1;
    private int actionId;
    private int gestureId;

    public GestureInfo() {
    }

    public GestureInfo(int i, int i2) {
        this.gestureId = i;
        this.actionId = i2;
    }

    public int getGestureId() {
        return this.gestureId;
    }

    public void setGestureId(int i) {
        this.gestureId = i;
    }

    public int getActionId() {
        return this.actionId;
    }

    public void setActionId(int i) {
        this.actionId = i;
    }

    public String toString() {
        return "GestureInfo{gestureId=" + this.gestureId + ", actionId=" + this.actionId + '}';
    }
}
