package com.aivox.base.util;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.base.view.FastBlur;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ScreenUtil {
    private ScreenUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static void getDisplayMetrics(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        SPUtil.put(SPUtil.SCREEN_W, Integer.valueOf(displayMetrics.widthPixels));
        SPUtil.put(SPUtil.SCREEN_H, Integer.valueOf(displayMetrics.heightPixels));
        LogUtil.m338i("宽度：" + displayMetrics.widthPixels + ";高度：" + displayMetrics.heightPixels);
    }

    public static int getStatusHeight(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            return context.getResources().getDimensionPixelSize(Integer.parseInt(cls.getField("status_bar_height").get(cls.newInstance()).toString()));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return -1;
        }
    }

    public static void getStatusHeight(Activity activity) {
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.top;
        Log.i("=bar=", i + "/" + (window.findViewById(R.id.content).getTop() - i));
    }

    public static Bitmap activityScreenShotWithStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(decorView.getDrawingCache(), 0, 0, getScreenWidth(activity), getScreenHeight(activity));
        decorView.destroyDrawingCache();
        return bitmapCreateBitmap;
    }

    public static Bitmap activityScreenShotWithoutStatusBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap drawingCache = decorView.getDrawingCache();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.top;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawingCache, 0, i, getScreenWidth(activity), getScreenHeight(activity) - i);
        decorView.destroyDrawingCache();
        return bitmapCreateBitmap;
    }

    public static Bitmap scrollViewScreenShot(ScrollView scrollView) {
        int height = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            height += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Bitmap.Config.RGB_565);
        scrollView.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static Bitmap listViewScreenShot(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int count = adapter.getCount();
        ArrayList arrayList = new ArrayList();
        int measuredHeight = 0;
        for (int i = 0; i < count; i++) {
            View view2 = adapter.getView(i, null, listView);
            view2.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
            view2.layout(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight());
            view2.setDrawingCacheEnabled(true);
            view2.buildDrawingCache();
            arrayList.add(view2.getDrawingCache());
            measuredHeight += view2.getMeasuredHeight();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(listView.getMeasuredWidth(), measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        int height = 0;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Bitmap bitmap = (Bitmap) arrayList.get(i2);
            canvas.drawBitmap(bitmap, 0.0f, height, paint);
            height += bitmap.getHeight();
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap recyclerViewScreenShot(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return null;
        }
        int itemCount = adapter.getItemCount();
        Paint paint = new Paint();
        LruCache lruCache = new LruCache(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
        int measuredHeight = 0;
        for (int i = 0; i < itemCount; i++) {
            RecyclerView.ViewHolder viewHolderCreateViewHolder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
            adapter.onBindViewHolder(viewHolderCreateViewHolder, i);
            viewHolderCreateViewHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
            viewHolderCreateViewHolder.itemView.layout(0, 0, viewHolderCreateViewHolder.itemView.getMeasuredWidth(), viewHolderCreateViewHolder.itemView.getMeasuredHeight());
            viewHolderCreateViewHolder.itemView.setDrawingCacheEnabled(true);
            viewHolderCreateViewHolder.itemView.buildDrawingCache();
            Bitmap drawingCache = viewHolderCreateViewHolder.itemView.getDrawingCache();
            if (drawingCache != null) {
                lruCache.put(String.valueOf(i), drawingCache);
            }
            measuredHeight += viewHolderCreateViewHolder.itemView.getMeasuredHeight();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Drawable background = recyclerView.getBackground();
        if (background instanceof ColorDrawable) {
            canvas.drawColor(((ColorDrawable) background).getColor());
        }
        int height = 0;
        for (int i2 = 0; i2 < itemCount; i2++) {
            Bitmap bitmap = (Bitmap) lruCache.get(String.valueOf(i2));
            canvas.drawBitmap(bitmap, 0.0f, height, paint);
            height += bitmap.getHeight();
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static void blur(Bitmap bitmap, View view2, Context context) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (view2.getMeasuredWidth() / 8.0f), (int) (view2.getMeasuredHeight() / 8.0f), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.translate((-view2.getLeft()) / 8.0f, (-view2.getTop()) / 8.0f);
        canvas.scale(0.125f, 0.125f);
        Paint paint = new Paint();
        paint.setFlags(2);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        view2.setBackground(new BitmapDrawable(context.getResources(), FastBlur.doBlur(bitmapCreateBitmap, (int) 2.0f, true)));
        LogUtil.m338i("cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
    }
}
