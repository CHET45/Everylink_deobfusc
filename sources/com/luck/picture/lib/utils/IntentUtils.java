package com.luck.picture.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.luck.picture.lib.basic.PictureFileProvider;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public class IntentUtils {
    public static void startSystemPlayerVideo(Context context, String str) {
        Uri uriFromFile;
        Intent intent = new Intent("android.intent.action.VIEW");
        boolean z = PictureMimeType.isContent(str) || PictureMimeType.isHasHttp(str);
        if (SdkVersionUtils.isQ()) {
            uriFromFile = z ? Uri.parse(str) : Uri.fromFile(new File(str));
        } else if (SdkVersionUtils.isMaxN()) {
            uriFromFile = z ? Uri.parse(str) : PictureFileProvider.getUriForFile(context, context.getPackageName() + ".luckProvider", new File(str));
        } else {
            uriFromFile = z ? Uri.parse(str) : Uri.fromFile(new File(str));
        }
        intent.addFlags(268468224);
        intent.addFlags(1);
        intent.setDataAndType(uriFromFile, "video/*");
        context.startActivity(intent);
    }
}
