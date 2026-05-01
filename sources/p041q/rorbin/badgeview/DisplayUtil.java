package p041q.rorbin.badgeview;

import android.content.Context;

/* JADX INFO: loaded from: classes5.dex */
public class DisplayUtil {
    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dp(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
