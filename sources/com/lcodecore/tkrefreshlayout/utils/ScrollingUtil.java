package com.lcodecore.tkrefreshlayout.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes3.dex */
public class ScrollingUtil {
    private ScrollingUtil() {
    }

    public static boolean canChildScrollUp(View view2) {
        if (view2 == null) {
            return false;
        }
        return ViewCompat.canScrollVertically(view2, -1);
    }

    public static boolean canChildScrollDown(View view2) {
        return ViewCompat.canScrollVertically(view2, 1);
    }

    public static boolean isScrollViewOrWebViewToTop(View view2) {
        return view2 != null && view2.getScrollY() == 0;
    }

    public static boolean isViewToTop(View view2, int i) {
        return view2 instanceof AbsListView ? isAbsListViewToTop((AbsListView) view2) : view2 instanceof RecyclerView ? isRecyclerViewToTop((RecyclerView) view2) : view2 != null && Math.abs(view2.getScrollY()) <= i * 2;
    }

    public static boolean isViewToBottom(View view2, int i) {
        if (view2 instanceof AbsListView) {
            return isAbsListViewToBottom((AbsListView) view2);
        }
        if (view2 instanceof RecyclerView) {
            return isRecyclerViewToBottom((RecyclerView) view2);
        }
        if (view2 instanceof WebView) {
            return isWebViewToBottom((WebView) view2, i);
        }
        if (view2 instanceof ViewGroup) {
            return isViewGroupToBottom((ViewGroup) view2);
        }
        return false;
    }

    public static boolean isAbsListViewToTop(AbsListView absListView) {
        if (absListView != null) {
            int top2 = absListView.getChildCount() > 0 ? absListView.getChildAt(0).getTop() - absListView.getPaddingTop() : 0;
            if (absListView.getFirstVisiblePosition() == 0 && top2 == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRecyclerViewToTop(RecyclerView recyclerView) {
        int top2;
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null || layoutManager.getItemCount() == 0) {
                return true;
            }
            if (recyclerView.getChildCount() > 0) {
                View childAt = recyclerView.getChildAt(0);
                if (childAt != null && childAt.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                    return !ViewCompat.canScrollVertically(recyclerView, -1);
                }
                View childAt2 = recyclerView.getChildAt(0);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt2.getLayoutParams();
                top2 = ((childAt2.getTop() - layoutParams.topMargin) - getRecyclerViewItemTopInset(layoutParams)) - recyclerView.getPaddingTop();
            } else {
                top2 = 0;
            }
            if (layoutManager instanceof LinearLayoutManager) {
                if (((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() < 1 && top2 == 0) {
                    return true;
                }
            } else if ((layoutManager instanceof StaggeredGridLayoutManager) && ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(null)[0] < 1 && top2 == 0) {
                return true;
            }
        }
        return false;
    }

    private static int getRecyclerViewItemTopInset(RecyclerView.LayoutParams layoutParams) {
        try {
            Field declaredField = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
            declaredField.setAccessible(true);
            return ((Rect) declaredField.get(layoutParams)).top;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isWebViewToBottom(WebView webView, int i) {
        return webView != null && (((float) webView.getContentHeight()) * webView.getScale()) - ((float) (webView.getHeight() + webView.getScrollY())) <= ((float) (i * 2));
    }

    public static boolean isViewGroupToBottom(ViewGroup viewGroup) {
        View childAt = viewGroup.getChildAt(0);
        return childAt != null && childAt.getMeasuredHeight() <= viewGroup.getScrollY() + viewGroup.getHeight();
    }

    public static boolean isScrollViewToBottom(ScrollView scrollView) {
        return scrollView != null && ((scrollView.getScrollY() + scrollView.getMeasuredHeight()) - scrollView.getPaddingTop()) - scrollView.getPaddingBottom() == scrollView.getChildAt(0).getMeasuredHeight();
    }

    public static boolean isAbsListViewToBottom(AbsListView absListView) {
        return absListView != null && absListView.getAdapter() != null && absListView.getChildCount() > 0 && absListView.getLastVisiblePosition() == ((ListAdapter) absListView.getAdapter()).getCount() - 1 && absListView.getChildAt(absListView.getChildCount() - 1).getBottom() <= absListView.getMeasuredHeight();
    }

    public static boolean isRecyclerViewToBottom(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager;
        if (recyclerView != null && (layoutManager = recyclerView.getLayoutManager()) != null && layoutManager.getItemCount() != 0) {
            if (layoutManager instanceof LinearLayoutManager) {
                View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                if (childAt != null && childAt.getMeasuredHeight() >= recyclerView.getMeasuredHeight()) {
                    return !ViewCompat.canScrollVertically(recyclerView, 1);
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getItemCount() - 1) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] iArrFindLastCompletelyVisibleItemPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                int itemCount = staggeredGridLayoutManager.getItemCount() - 1;
                for (int i : iArrFindLastCompletelyVisibleItemPositions) {
                    if (i == itemCount) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void scrollAViewBy(View view2, int i) {
        if (view2 instanceof RecyclerView) {
            ((RecyclerView) view2).scrollBy(0, i);
            return;
        }
        if (view2 instanceof ScrollView) {
            ((ScrollView) view2).smoothScrollBy(0, i);
        } else {
            if (view2 instanceof AbsListView) {
                ((AbsListView) view2).smoothScrollBy(i, 0);
                return;
            }
            try {
                view2.getClass().getDeclaredMethod("smoothScrollBy", Integer.class, Integer.class).invoke(view2, 0, Integer.valueOf(i));
            } catch (Exception unused) {
                view2.scrollBy(0, i);
            }
        }
    }

    public static void scrollToBottom(final ScrollView scrollView) {
        if (scrollView != null) {
            scrollView.post(new Runnable() { // from class: com.lcodecore.tkrefreshlayout.utils.ScrollingUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    scrollView.fullScroll(130);
                }
            });
        }
    }

    public static void scrollToBottom(final AbsListView absListView) {
        if (absListView == null || absListView.getAdapter() == null || ((ListAdapter) absListView.getAdapter()).getCount() <= 0) {
            return;
        }
        absListView.post(new Runnable() { // from class: com.lcodecore.tkrefreshlayout.utils.ScrollingUtil.2
            @Override // java.lang.Runnable
            public void run() {
                absListView.setSelection(((ListAdapter) r0.getAdapter()).getCount() - 1);
            }
        });
    }

    public static void scrollToBottom(final RecyclerView recyclerView) {
        if (recyclerView == null || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() <= 0) {
            return;
        }
        recyclerView.post(new Runnable() { // from class: com.lcodecore.tkrefreshlayout.utils.ScrollingUtil.3
            @Override // java.lang.Runnable
            public void run() {
                recyclerView.smoothScrollToPosition(r0.getAdapter().getItemCount() - 1);
            }
        });
    }

    public static void scrollToBottom(View view2) {
        if (view2 instanceof RecyclerView) {
            scrollToBottom((RecyclerView) view2);
        }
        if (view2 instanceof AbsListView) {
            scrollToBottom((AbsListView) view2);
        }
        if (view2 instanceof ScrollView) {
            scrollToBottom((ScrollView) view2);
        }
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
