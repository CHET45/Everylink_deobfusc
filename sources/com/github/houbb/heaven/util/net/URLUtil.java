package com.github.houbb.heaven.util.net;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class URLUtil {
    private URLUtil() {
    }

    public static List<String> readAllLines(URL url) {
        return readAllLines(url, "UTF-8");
    }

    public static List<String> readAllLines(URL url, String str) {
        ArgUtil.notNull(url, "url");
        ArgUtil.notEmpty(str, "charset");
        ArrayList arrayList = new ArrayList();
        try {
            InputStream inputStreamOpenStream = url.openStream();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenStream, Charset.forName(str)));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        arrayList.add(line);
                    } finally {
                    }
                }
                bufferedReader.close();
                if (inputStreamOpenStream != null) {
                    inputStreamOpenStream.close();
                }
                return arrayList;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
