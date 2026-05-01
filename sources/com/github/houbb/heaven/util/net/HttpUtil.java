package com.github.houbb.heaven.util.net;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.p010io.FileUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class HttpUtil {
    public static final String GET = "GET";
    public static final String POST = "POST";

    private HttpUtil() {
    }

    public static String getRequest(String str) {
        return request(str, "GET");
    }

    public static String postRequest(String str) {
        return request(str, "POST");
    }

    public static String request(String str, String str2) {
        return request(str, str2, "UTF-8", null);
    }

    public static String request(String str, String str2, Map<String, String> map) {
        return request(str, str2, "UTF-8", map);
    }

    public static String request(String str, String str2, String str3, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod(str2);
            if (MapUtil.isNotEmpty(map)) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if ("GET".equalsIgnoreCase(str2)) {
                httpURLConnection.connect();
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, str3);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Map<String, String> buildHeaderMap(String str) {
        List<String> allLines = FileUtil.readAllLines(str);
        HashMap map = new HashMap(allLines.size());
        for (String str2 : allLines) {
            int iIndexOf = str2.indexOf(":");
            map.put(str2.substring(0, iIndexOf).trim(), str2.substring(iIndexOf + 1).trim());
        }
        return map;
    }

    public static void download(String str, String str2) {
        download(str, str2, null);
    }

    public static void download(String str, String str2, Map<String, String> map) {
        FileOutputStream fileOutputStream;
        ArgUtil.notEmpty(str, "remoteUrl");
        ArgUtil.notEmpty(str2, "localUrl");
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            if (MapUtil.isNotEmpty(map)) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    uRLConnectionOpenConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            DataInputStream dataInputStream = new DataInputStream(uRLConnectionOpenConnection.getInputStream());
            try {
                fileOutputStream = new FileOutputStream(new File(str2));
            } finally {
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = dataInputStream.read(bArr);
                    if (i > 0) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        fileOutputStream.write(byteArrayOutputStream.toByteArray());
                        fileOutputStream.close();
                        dataInputStream.close();
                        return;
                    }
                }
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
