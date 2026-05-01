package com.aivox.common_ui.cityselect.util;

import android.content.Context;
import com.aivox.base.util.BaseAppUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class ReadAssetsFileUtil {
    public static String readAssetsTxt(Context context, String str) {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str + ".txt");
            byte[] bArr = new byte[inputStreamOpen.available()];
            inputStreamOpen.read(bArr);
            inputStreamOpen.close();
            return new String(bArr, "utf-8");
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String getJson(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (IOException e) {
            BaseAppUtils.printErrorMsg(e);
        }
        return sb.toString();
    }
}
