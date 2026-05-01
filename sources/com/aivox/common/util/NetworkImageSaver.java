package com.aivox.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.aivox.base.C0874R;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.ToastUtil;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class NetworkImageSaver {
    public static void saveImageFromNetwork(final String str, final boolean z) {
        ToastUtil.showLong(Integer.valueOf(C0874R.string.downloading));
        new Thread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NetworkImageSaver.lambda$saveImageFromNetwork$3(str, z);
            }
        }).start();
    }

    static /* synthetic */ void lambda$saveImageFromNetwork$3(String str, boolean z) {
        try {
            Bitmap bitmapDownloadImage = downloadImage(str);
            if (bitmapDownloadImage != null) {
                if (Build.VERSION.SDK_INT < 29) {
                    PermissionUtils.getIns(ActivityUtils.getTopActivity(), new C09981(bitmapDownloadImage, z)).request("android.permission.WRITE_EXTERNAL_STORAGE");
                } else {
                    File fileSave2Album = ImageUtils.save2Album(bitmapDownloadImage, "everyLink", Bitmap.CompressFormat.PNG);
                    if (fileSave2Album != null) {
                        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ToastUtil.showShort(Integer.valueOf(C0874R.string.download_success));
                            }
                        });
                        if (z) {
                            FileUtils.shareImage(ActivityUtils.getTopActivity(), fileSave2Album.getAbsolutePath());
                        }
                    }
                }
            } else {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ToastUtil.showShort(Integer.valueOf(C0874R.string.download_failed));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.image_saving_failed));
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.aivox.common.util.NetworkImageSaver$1 */
    class C09981 implements PermissionCallback {
        final /* synthetic */ Bitmap val$bitmap;
        final /* synthetic */ boolean val$doShare;

        C09981(Bitmap bitmap, boolean z) {
            this.val$bitmap = bitmap;
            this.val$doShare = z;
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            File fileSave2Album = ImageUtils.save2Album(this.val$bitmap, "everyLink", Bitmap.CompressFormat.PNG);
            if (fileSave2Album != null) {
                ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ToastUtil.showShort(Integer.valueOf(C0874R.string.download_success));
                    }
                });
                if (this.val$doShare) {
                    FileUtils.shareImage(ActivityUtils.getTopActivity(), fileSave2Album.getAbsolutePath());
                }
            }
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.common.util.NetworkImageSaver$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_acquisition_failed));
                }
            });
        }
    }

    private static Bitmap downloadImage(String str) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            if (httpURLConnection.getResponseCode() == 200) {
                return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
