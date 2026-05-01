package com.tencent.beacon.event.open;

/* JADX INFO: loaded from: classes4.dex */
public final class EventResult {
    public static final int ERROR_CODE_LOGID_NOT_INIT = 107;
    public static final int ERROR_CODE_NAME_NULL = 106;
    public static final int ERROR_CODE_NOT_ENABLE = 102;
    public static final int ERROR_CODE_OFFER_FAIL = 103;
    public static final int ERROR_CODE_OTHER = 199;
    public static final int ERROR_CODE_PREVENT = 100;
    public static final int ERROR_CODE_SAMPLE = 101;
    public static final int ERROR_CODE_SAMPLE_BY_USER = 108;
    public static final int ERROR_CODE_SUCCESS = 0;
    public static final int ERROR_PARAMS_LONG = 105;
    public String errMsg;
    public int errorCode;
    public long eventID;

    public EventResult(int i, long j, String str) {
        this.errorCode = i;
        this.eventID = j;
        this.errMsg = str;
    }

    public boolean isSuccess() {
        return this.errorCode == 0;
    }

    /* JADX INFO: renamed from: com.tencent.beacon.event.open.EventResult$a */
    public static final class C2742a {
        /* JADX INFO: renamed from: a */
        public static EventResult m1744a(int i) {
            switch (i) {
                case 100:
                    return new EventResult(i, -1L, "not allowed in strategy (false).");
                case 101:
                    return new EventResult(i, -1L, "sampled by svr rate (false).");
                case 102:
                    return new EventResult(i, -1L, "event report not enable!");
                case 103:
                    return new EventResult(i, -1L, "insert event to db error!");
                case 104:
                default:
                    return null;
                case 105:
                    return new EventResult(i, -1L, "event params length > 45K!");
                case 106:
                    return new EventResult(i, -1L, "event code isEmpty!");
                case 107:
                    return new EventResult(i, -1L, "not init LogIDGenerator");
                case 108:
                    return new EventResult(i, -1L, "sampled by user.");
            }
        }

        /* JADX INFO: renamed from: a */
        public static EventResult m1745a(long j) {
            return new EventResult(0, j, null);
        }
    }
}
