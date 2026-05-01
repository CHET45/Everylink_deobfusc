package com.aivox.base.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import com.aivox.base.C0874R;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class BitmapUtil {
    private BitmapUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Bitmap viewConversionBitmap(View view2) {
        int width = view2.getWidth();
        int height = view2.getHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawColor(-1);
        view2.layout(0, 0, width, height);
        view2.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static Bitmap getBitmapByView(View view2) {
        view2.setBackgroundColor(Color.parseColor("#ffffff"));
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.RGB_565);
        view2.draw(new Canvas(bitmapCreateBitmap));
        return bitmapCreateBitmap;
    }

    public static long getBitmapLen(Bitmap bitmap) {
        if (bitmap == null) {
            return 0L;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        return r0.toByteArray().length;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public static Bitmap readBitMap(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(i), null, options);
    }

    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        LogUtil.m338i("size=" + byteArray.length);
        return Base64.encodeToString(byteArray, 0);
    }

    public static Bitmap convertStringToIcon(String str) {
        try {
            byte[] bArrDecode = Base64.decode(str.split(PunctuationConst.COMMA)[1], 0);
            return BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static Bitmap Resource2Bitmap(int i) {
        return BitmapFactory.decodeResource(BaseAppUtils.getContext().getResources(), i);
    }

    public static Drawable Bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] bArr) {
        if (bArr.length != 0) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public static void setBitmap(ImageView imageView, String str) {
        LogUtil.m338i("URL——" + str);
    }

    public static Bitmap decodeFile(String str, int i) throws IOException {
        File file = new File(str);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        FileInputStream fileInputStream = new FileInputStream(file);
        BitmapFactory.decodeStream(fileInputStream, null, options);
        fileInputStream.close();
        int iPow = (options.outHeight > i || options.outWidth > i) ? (int) Math.pow(2.0d, (int) Math.round(Math.log(((double) i) / ((double) Math.max(options.outHeight, options.outWidth))) / Math.log(0.5d))) : 1;
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = iPow;
        FileInputStream fileInputStream2 = new FileInputStream(file);
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(fileInputStream2, null, options2);
        fileInputStream2.close();
        return bitmapDecodeStream;
    }

    public static void saveImageToGallery(Context context, Bitmap bitmap, String str) {
        File file = new File(Environment.getExternalStorageDirectory(), "Download");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e = e;
            ((Activity) context).runOnUiThread(new Runnable() { // from class: com.aivox.base.util.BitmapUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.save_failed));
                }
            });
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e = e3;
            ((Activity) context).runOnUiThread(new Runnable() { // from class: com.aivox.base.util.BitmapUtil.1
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.save_failed));
                }
            });
            e.printStackTrace();
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file2.getAbsolutePath(), str, (String) null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", file2.getAbsolutePath());
            contentValues.put("mime_type", "image/jpeg");
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } catch (FileNotFoundException | NullPointerException e4) {
            ((Activity) context).runOnUiThread(new Runnable() { // from class: com.aivox.base.util.BitmapUtil.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.save_failed));
                }
            });
            e4.printStackTrace();
        }
        ((Activity) context).runOnUiThread(new Runnable() { // from class: com.aivox.base.util.BitmapUtil.3
            @Override // java.lang.Runnable
            public void run() {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.image_saved_to_local));
            }
        });
        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(file2.getAbsolutePath())));
        bitmap.recycle();
    }
}
