package com.lxj.xpopup.util;

import android.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BubbleAttachPopupView;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class XPopupUtils {
    private static int correctKeyboardHeight;
    private static int sDecorViewDelta;
    private static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void setCursorDrawableColor(EditText editText, int i) {
    }

    public static int getWindowWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getAppHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getStatusBarHeight() {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static int getNavBarHeight() {
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier != 0) {
            return system.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void setWidthHeight(View view2, int i, int i2) {
        if (i > 0 || i2 > 0) {
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            if (i > 0) {
                layoutParams.width = i;
            }
            if (i2 > 0) {
                layoutParams.height = i2;
            }
            view2.setLayoutParams(layoutParams);
        }
    }

    public static void applyPopupSize(final ViewGroup viewGroup, final int i, final int i2, final int i3, final int i4, final Runnable runnable) {
        viewGroup.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.1
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                View childAt = viewGroup.getChildAt(0);
                ViewGroup.LayoutParams layoutParams2 = childAt.getLayoutParams();
                int measuredWidth = viewGroup.getMeasuredWidth();
                int i5 = i;
                if (i5 > 0) {
                    layoutParams.width = Math.min(measuredWidth, i5);
                    int i6 = i3;
                    if (i6 > 0) {
                        layoutParams.width = Math.min(i6, i);
                        layoutParams2.width = Math.min(i3, i);
                    }
                } else {
                    int i7 = i3;
                    if (i7 > 0) {
                        layoutParams.width = i7;
                        layoutParams2.width = i3;
                    }
                }
                int measuredHeight = viewGroup.getMeasuredHeight();
                int i8 = i2;
                if (i8 > 0) {
                    layoutParams.height = Math.min(measuredHeight, i8);
                    int i9 = i4;
                    if (i9 > 0) {
                        layoutParams.height = Math.min(i9, i2);
                        layoutParams2.height = Math.min(i4, i2);
                    }
                } else {
                    int i10 = i4;
                    if (i10 > 0) {
                        layoutParams.height = i10;
                        layoutParams2.height = i4;
                    }
                }
                childAt.setLayoutParams(layoutParams2);
                viewGroup.setLayoutParams(layoutParams);
                viewGroup.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                });
            }
        });
    }

    public static BitmapDrawable createBitmapDrawable(Resources resources, int i, int i2) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, 20, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setColor(i2);
        canvas.drawRect(0.0f, 0.0f, bitmapCreateBitmap.getWidth(), 4.0f, paint);
        paint.setColor(0);
        canvas.drawRect(0.0f, 4.0f, bitmapCreateBitmap.getWidth(), 20.0f, paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmapCreateBitmap);
        bitmapDrawable.setGravity(80);
        return bitmapDrawable;
    }

    public static StateListDrawable createSelector(Drawable drawable, Drawable drawable2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_focused}, drawable2);
        stateListDrawable.addState(new int[0], drawable);
        return stateListDrawable;
    }

    public static boolean isInRect(float f, float f2, Rect rect) {
        return f >= ((float) rect.left) && f <= ((float) rect.right) && f2 >= ((float) rect.top) && f2 <= ((float) rect.bottom);
    }

    public static int getDecorViewInvisibleHeight(Window window) {
        View decorView = window.getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int iAbs = Math.abs(decorView.getBottom() - rect.bottom);
        if (iAbs <= getNavBarHeight()) {
            sDecorViewDelta = iAbs;
            return 0;
        }
        return iAbs - sDecorViewDelta;
    }

    public static void moveUpToKeyboard(int i, final BasePopupView basePopupView) {
        correctKeyboardHeight = i;
        basePopupView.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.2
            @Override // java.lang.Runnable
            public void run() {
                XPopupUtils.moveUpToKeyboardInternal(XPopupUtils.correctKeyboardHeight, basePopupView);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a4 A[PHI: r7
  0x00a4: PHI (r7v6 int) = (r7v3 int), (r7v8 int) binds: [B:73:0x010e, B:40:0x00a2] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void moveUpToKeyboardInternal(int r11, com.lxj.xpopup.core.BasePopupView r12) {
        /*
            Method dump skipped, instruction units count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.util.XPopupUtils.moveUpToKeyboardInternal(int, com.lxj.xpopup.core.BasePopupView):void");
    }

    private static boolean isBottomPartShadow(BasePopupView basePopupView) {
        return (basePopupView instanceof PartShadowPopupView) && ((PartShadowPopupView) basePopupView).isShowUp;
    }

    private static boolean isTopPartShadow(BasePopupView basePopupView) {
        return (basePopupView instanceof PartShadowPopupView) && !((PartShadowPopupView) basePopupView).isShowUp;
    }

    public static void moveDown(BasePopupView basePopupView) {
        if ((basePopupView instanceof PositionPopupView) || (basePopupView instanceof AttachPopupView) || (basePopupView instanceof BubbleAttachPopupView)) {
            return;
        }
        if ((basePopupView instanceof PartShadowPopupView) && !isBottomPartShadow(basePopupView)) {
            basePopupView.getPopupImplView().animate().translationY(0.0f).setDuration(100L).start();
        } else {
            basePopupView.getPopupContentView().animate().translationY(0.0f).setDuration(100L).start();
        }
    }

    public static boolean isNavBarVisible(Window window) {
        boolean z;
        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
        int childCount = viewGroup.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                z = false;
                break;
            }
            View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(window.getContext().getResources().getResourceEntryName(id)) && childAt.getVisibility() == 0) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            return z;
        }
        if (FuckRomUtils.isSamsung() && Build.VERSION.SDK_INT < 29) {
            try {
                return Settings.Global.getInt(window.getContext().getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
            } catch (Exception unused) {
            }
        }
        return (viewGroup.getSystemUiVisibility() & 2) == 0;
    }

    public static void findAllEditText(ArrayList<EditText> arrayList, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof EditText) && childAt.getVisibility() == 0) {
                arrayList.add((EditText) childAt);
            } else if (childAt instanceof ViewGroup) {
                findAllEditText(arrayList, (ViewGroup) childAt);
            }
        }
    }

    public static void saveBmpToAlbum(final Context context, final XPopupImageLoader xPopupImageLoader, final Object obj) {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.3
            @Override // java.lang.Runnable
            public void run() {
                File imageFile = xPopupImageLoader.getImageFile(context, obj);
                if (imageFile == null) {
                    Context context2 = context;
                    XPopupUtils.showToast(context2, context2.getString(C2213R.string.xpopup_image_not_exist));
                    return;
                }
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), context.getPackageName());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, System.currentTimeMillis() + "." + XPopupUtils.getImageType(imageFile));
                    if (Build.VERSION.SDK_INT < 29) {
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            XPopupUtils.writeFileFromIS(fileOutputStream, new FileInputStream(imageFile));
                            fileOutputStream.close();
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.parse("file://" + file2.getAbsolutePath()));
                            context.sendBroadcast(intent);
                        } finally {
                        }
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_display_name", file2.getName());
                        contentValues.put("mime_type", "image/*");
                        Uri uri = Environment.getExternalStorageState().equals("mounted") ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                        contentValues.put("relative_path", Environment.DIRECTORY_DCIM + "/" + context.getPackageName());
                        contentValues.put("is_pending", (Integer) 1);
                        Uri uriInsert = context.getContentResolver().insert(uri, contentValues);
                        if (uriInsert == null) {
                            Context context3 = context;
                            XPopupUtils.showToast(context3, context3.getString(C2213R.string.xpopup_saved_fail));
                            return;
                        }
                        ContentResolver contentResolver = context.getContentResolver();
                        OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                        try {
                            XPopupUtils.writeFileFromIS(outputStreamOpenOutputStream, new FileInputStream(imageFile));
                            if (outputStreamOpenOutputStream != null) {
                                outputStreamOpenOutputStream.close();
                            }
                            contentValues.clear();
                            contentValues.put("is_pending", (Integer) 0);
                            contentResolver.update(uriInsert, contentValues, null, null);
                        } finally {
                        }
                    }
                    Context context4 = context;
                    XPopupUtils.showToast(context4, context4.getString(C2213R.string.xpopup_saved_to_gallery));
                } catch (Exception e) {
                    e.printStackTrace();
                    Context context5 = context;
                    XPopupUtils.showToast(context5, context5.getString(C2213R.string.xpopup_saved_fail));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showToast(final Context context, final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.4
            @Override // java.lang.Runnable
            public void run() {
                Context context2 = context;
                if (context2 != null) {
                    Toast.makeText(context2, str, 0).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean writeFileFromIS(OutputStream outputStream, InputStream inputStream) throws Throwable {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(outputStream);
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i = inputStream.read(bArr, 0, 8192);
                        if (i != -1) {
                            bufferedOutputStream2.write(bArr, 0, i);
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    inputStream.close();
                    try {
                        bufferedOutputStream2.close();
                        return true;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return true;
                    }
                } catch (IOException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    try {
                        inputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e8) {
            e = e8;
        }
    }

    public static Bitmap renderScriptBlur(Context context, Bitmap bitmap, float f, boolean z) throws Throwable {
        RenderScript renderScriptCreate;
        if (!z) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        try {
            renderScriptCreate = RenderScript.create(context);
        } catch (Throwable th) {
            th = th;
            renderScriptCreate = null;
        }
        try {
            renderScriptCreate.setMessageHandler(new RenderScript.RSMessageHandler());
            Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
            Allocation allocationCreateTyped = Allocation.createTyped(renderScriptCreate, allocationCreateFromBitmap.getType());
            ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
            scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
            scriptIntrinsicBlurCreate.setRadius(f);
            scriptIntrinsicBlurCreate.forEach(allocationCreateTyped);
            allocationCreateTyped.copyTo(bitmap);
            if (renderScriptCreate != null) {
                renderScriptCreate.destroy();
            }
            return bitmap;
        } catch (Throwable th2) {
            th = th2;
            if (renderScriptCreate != null) {
                renderScriptCreate.destroy();
            }
            throw th;
        }
    }

    public static Bitmap view2Bitmap(View view2) {
        Bitmap bitmapCreateBitmap;
        if (view2 == null) {
            return null;
        }
        boolean zIsDrawingCacheEnabled = view2.isDrawingCacheEnabled();
        boolean zWillNotCacheDrawing = view2.willNotCacheDrawing();
        view2.setDrawingCacheEnabled(true);
        view2.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view2.getDrawingCache();
        if (drawingCache == null) {
            view2.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view2.layout(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight());
            view2.buildDrawingCache();
            Bitmap drawingCache2 = view2.getDrawingCache();
            if (drawingCache2 != null) {
                bitmapCreateBitmap = Bitmap.createBitmap(drawingCache2);
            } else {
                bitmapCreateBitmap = Bitmap.createBitmap(view2.getMeasuredWidth(), view2.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                view2.draw(new Canvas(bitmapCreateBitmap));
            }
        } else {
            bitmapCreateBitmap = Bitmap.createBitmap(drawingCache);
        }
        view2.destroyDrawingCache();
        view2.setWillNotCacheDrawing(zWillNotCacheDrawing);
        view2.setDrawingCacheEnabled(zIsDrawingCacheEnabled);
        return bitmapCreateBitmap;
    }

    public static boolean isLayoutRtl(Context context) {
        return TextUtils.getLayoutDirectionFromLocale(context.getResources().getConfiguration().getLocales().get(0)) == 1;
    }

    public static Activity context2Activity(View view2) {
        for (Context context = view2.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    public static Drawable createDrawable(int i, float f) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(f);
        return gradientDrawable;
    }

    public static Drawable createDrawable(int i, float f, float f2, float f3, float f4) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
        return gradientDrawable;
    }

    public static boolean hasSetKeyListener(View view2) {
        try {
            Method declaredMethod = Class.forName("android.view.View").getDeclaredMethod("getListenerInfo", new Class[0]);
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
            Object objInvoke = declaredMethod.invoke(view2, new Object[0]);
            Field declaredField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnKeyListener");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            return declaredField.get(objInvoke) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        while (true) {
            if (i3 <= i2 && i4 <= i) {
                return i5;
            }
            i3 >>= 1;
            i4 >>= 1;
            i5 <<= 1;
        }
    }

    public static Bitmap getBitmap(File file, int i, int i2) {
        if (file == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static int[] getImageSize(File file) {
        if (file == null) {
            return new int[]{0, 0};
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a5, code lost:
    
        if (r4.contains("00000200") != false) goto L61;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v32 */
    /* JADX WARN: Type inference failed for: r1v33 */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r1v36 */
    /* JADX WARN: Type inference failed for: r1v37 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:87:0x00d4 -> B:99:0x00d7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getImageType(java.io.File r4) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.util.XPopupUtils.getImageType(java.io.File):java.lang.String");
    }

    public static String bytes2HexString(byte[] bArr, boolean z) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = z ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int length = bArr.length;
        if (length <= 0) {
            return "";
        }
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr2[i] = cArr[(b >> 4) & 15];
            i += 2;
            cArr2[i2] = cArr[b & 15];
        }
        return new String(cArr2);
    }

    public static int getRotateDegree(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int i, float f, float f2) {
        if (i == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i, f, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
