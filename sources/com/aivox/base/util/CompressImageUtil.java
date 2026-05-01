package com.aivox.base.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import org.videolan.libvlc.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
public class CompressImageUtil {
    public static void saveBitmap(Bitmap bitmap) {
    }

    public static Bitmap compressImg1(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int i = 50;
        while (byteArrayOutputStream.toByteArray().length / 1024 > 100 && i > 10) {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
            i -= 10;
            LogUtil.m338i("=compress=" + i + "--" + (byteArrayOutputStream.toByteArray().length / 1024));
        }
        return BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap compressImg2(java.lang.String r6) {
        /*
            android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
            r0.<init>()
            r1 = 1
            r0.inJustDecodeBounds = r1
            android.graphics.BitmapFactory.decodeFile(r6, r0)
            r2 = 0
            r0.inJustDecodeBounds = r2
            int r2 = r0.outWidth
            int r3 = r0.outHeight
            if (r2 <= r3) goto L21
            float r4 = (float) r2
            r5 = 1139802112(0x43f00000, float:480.0)
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L21
            int r2 = r0.outWidth
            float r2 = (float) r2
            float r2 = r2 / r5
        L1f:
            int r2 = (int) r2
            goto L30
        L21:
            if (r2 >= r3) goto L2f
            float r2 = (float) r3
            r3 = 1145569280(0x44480000, float:800.0)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L2f
            int r2 = r0.outHeight
            float r2 = (float) r2
            float r2 = r2 / r3
            goto L1f
        L2f:
            r2 = r1
        L30:
            if (r2 > 0) goto L33
            goto L34
        L33:
            r1 = r2
        L34:
            r0.inSampleSize = r1
            android.graphics.Bitmap r6 = android.graphics.BitmapFactory.decodeFile(r6, r0)
            android.graphics.Bitmap r6 = compressImg1(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.CompressImageUtil.compressImg2(java.lang.String):android.graphics.Bitmap");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap compressImg3(android.graphics.Bitmap r10) {
        /*
            Method dump skipped, instruction units count: 222
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.CompressImageUtil.compressImg3(android.graphics.Bitmap):android.graphics.Bitmap");
    }

    public static Bitmap getSmallBitmap(Bitmap bitmap, int i, Activity activity) {
        ScreenUtil.getScreenWidth(activity);
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(i);
        if (800 > bitmap.getHeight() && 480 > bitmap.getWidth()) {
            matrix.postScale(1.0f, 1.0f);
        } else {
            float width = 480 / bitmap.getWidth();
            float height = MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING / bitmap.getHeight();
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                width = height;
            }
            matrix.postScale(width, width);
        }
        return compressImg1(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }

    public static int readPictureDegree(String str) {
        int i;
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                i = 180;
            } else if (attributeInt == 6) {
                i = 90;
            } else {
                if (attributeInt != 8) {
                    return 0;
                }
                i = 270;
            }
            return i;
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
            return 0;
        }
    }

    public static void clearSdcardCache() {
        File[] fileArrListFiles = new File("/mnt/sdcard/").listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            if ("favour.jpg".equals(file.getName())) {
                file.delete();
                return;
            }
        }
    }
}
