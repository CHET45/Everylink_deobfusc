package org.videolan.libvlc.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;
import java.io.File;

/* JADX INFO: loaded from: classes5.dex */
public class AndroidUtil {
    public static final boolean isJellyBeanMR2OrLater;
    public static final boolean isKitKatOrLater;
    public static final boolean isLolliPopOrLater;
    public static final boolean isMarshMallowOrLater;
    public static final boolean isNougatMR1OrLater;
    public static final boolean isNougatOrLater;
    public static final boolean isOOrLater;
    public static final boolean isPOrLater;
    public static final boolean isROrLater;

    static {
        isROrLater = Build.VERSION.SDK_INT >= 30;
        isPOrLater = Build.VERSION.SDK_INT >= 28;
        isOOrLater = true;
        isNougatMR1OrLater = true;
        isNougatOrLater = true;
        isMarshMallowOrLater = true;
        isLolliPopOrLater = true;
        isKitKatOrLater = true;
        isJellyBeanMR2OrLater = true;
    }

    public static File UriToFile(Uri uri) {
        return new File(uri.getPath().replaceFirst("file://", ""));
    }

    public static Uri PathToUri(String str) {
        return Uri.fromFile(new File(str));
    }

    public static Uri LocationToUri(String str) {
        Uri uri = Uri.parse(str);
        if (uri.getScheme() != null) {
            return uri;
        }
        throw new IllegalArgumentException("location has no scheme");
    }

    public static Uri FileToUri(File file) {
        return Uri.fromFile(file);
    }

    public static Activity resolveActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return resolveActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
}
