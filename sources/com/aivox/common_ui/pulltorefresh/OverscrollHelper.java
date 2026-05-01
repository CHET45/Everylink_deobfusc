package com.aivox.common_ui.pulltorefresh;

import android.view.View;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;

/* JADX INFO: loaded from: classes.dex */
public final class OverscrollHelper {
    static final float DEFAULT_OVERSCROLL_SCALE = 1.0f;
    static final String LOG_TAG = "OverscrollHelper";

    public static void overScrollBy(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, boolean z) {
        overScrollBy(pullToRefreshBase, i, i2, i3, i4, 0, z);
    }

    public static void overScrollBy(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, int i5, boolean z) {
        overScrollBy(pullToRefreshBase, i, i2, i3, i4, i5, 0, 1.0f, z);
    }

    /* JADX INFO: renamed from: com.aivox.common_ui.pulltorefresh.OverscrollHelper$1 */
    static /* synthetic */ class C10531 {

        /* JADX INFO: renamed from: $SwitchMap$com$aivox$common_ui$pulltorefresh$PullToRefreshBase$Orientation */
        static final /* synthetic */ int[] f275x6b884803;

        static {
            int[] iArr = new int[PullToRefreshBase.Orientation.values().length];
            f275x6b884803 = iArr;
            try {
                iArr[PullToRefreshBase.Orientation.HORIZONTAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f275x6b884803[PullToRefreshBase.Orientation.VERTICAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static void overScrollBy(PullToRefreshBase<?> pullToRefreshBase, int i, int i2, int i3, int i4, int i5, int i6, float f, boolean z) {
        int scrollX;
        if (C10531.f275x6b884803[pullToRefreshBase.getPullToRefreshScrollDirection().ordinal()] == 1) {
            scrollX = pullToRefreshBase.getScrollX();
        } else {
            i2 = i4;
            scrollX = pullToRefreshBase.getScrollY();
            i = i3;
        }
        if (!pullToRefreshBase.isPullToRefreshOverScrollEnabled() || pullToRefreshBase.isRefreshing()) {
            return;
        }
        PullToRefreshBase.Mode mode = pullToRefreshBase.getMode();
        if (!mode.permitsPullToRefresh() || z || i == 0) {
            if (z && PullToRefreshBase.State.OVERSCROLLING == pullToRefreshBase.getState()) {
                pullToRefreshBase.setState(PullToRefreshBase.State.RESET, new boolean[0]);
                return;
            }
            return;
        }
        int i7 = i + i2;
        if (i7 < 0 - i6) {
            if (mode.showHeaderLoadingLayout()) {
                if (scrollX == 0) {
                    pullToRefreshBase.setState(PullToRefreshBase.State.OVERSCROLLING, new boolean[0]);
                }
                pullToRefreshBase.setHeaderScroll((int) (f * (scrollX + i7)));
                return;
            }
            return;
        }
        if (i7 > i5 + i6) {
            if (mode.showFooterLoadingLayout()) {
                if (scrollX == 0) {
                    pullToRefreshBase.setState(PullToRefreshBase.State.OVERSCROLLING, new boolean[0]);
                }
                pullToRefreshBase.setHeaderScroll((int) (f * ((scrollX + i7) - i5)));
                return;
            }
            return;
        }
        if (Math.abs(i7) <= i6 || Math.abs(i7 - i5) <= i6) {
            pullToRefreshBase.setState(PullToRefreshBase.State.RESET, new boolean[0]);
        }
    }

    static boolean isAndroidOverScrollEnabled(View view2) {
        return view2.getOverScrollMode() != 2;
    }
}
