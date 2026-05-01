package com.lzy.okgo.utils;

import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class HttpUtils {
    public static String createUrlFromParams(String str, Map<String, List<String>> map) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            if (str.indexOf(38) > 0 || str.indexOf(63) > 0) {
                sb.append(PunctuationConst.AND);
            } else {
                sb.append(PunctuationConst.QUESTION_MARK);
            }
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                Iterator<String> it = entry.getValue().iterator();
                while (it.hasNext()) {
                    sb.append(entry.getKey()).append(PunctuationConst.EQUAL).append(URLEncoder.encode(it.next(), "UTF-8")).append(PunctuationConst.AND);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            OkLogger.m864e(e);
            return str;
        }
    }

    public static Request.Builder appendHeaders(HttpHeaders httpHeaders) {
        Request.Builder builder = new Request.Builder();
        if (httpHeaders.headersMap.isEmpty()) {
            return builder;
        }
        Headers.Builder builder2 = new Headers.Builder();
        try {
            for (Map.Entry<String, String> entry : httpHeaders.headersMap.entrySet()) {
                builder2.add(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            OkLogger.m864e(e);
        }
        builder.headers(builder2.build());
        return builder;
    }

    public static RequestBody generateMultipartRequestBody(HttpParams httpParams, boolean z) {
        if (httpParams.fileParamsMap.isEmpty() && !z) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String str : httpParams.urlParamsMap.keySet()) {
                Iterator<String> it = httpParams.urlParamsMap.get(str).iterator();
                while (it.hasNext()) {
                    builder.add(str, it.next());
                }
            }
            return builder.build();
        }
        MultipartBody.Builder type = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!httpParams.urlParamsMap.isEmpty()) {
            for (Map.Entry<String, List<String>> entry : httpParams.urlParamsMap.entrySet()) {
                Iterator<String> it2 = entry.getValue().iterator();
                while (it2.hasNext()) {
                    type.addFormDataPart(entry.getKey(), it2.next());
                }
            }
        }
        for (Map.Entry<String, List<HttpParams.FileWrapper>> entry2 : httpParams.fileParamsMap.entrySet()) {
            for (HttpParams.FileWrapper fileWrapper : entry2.getValue()) {
                type.addFormDataPart(entry2.getKey(), fileWrapper.fileName, RequestBody.create(fileWrapper.contentType, fileWrapper.file));
            }
        }
        return type.build();
    }

    public static String getNetFileName(Response response, String str) {
        String headerFileName = getHeaderFileName(response);
        if (TextUtils.isEmpty(headerFileName)) {
            headerFileName = getUrlFileName(str);
        }
        return TextUtils.isEmpty(headerFileName) ? "nofilename" : headerFileName;
    }

    private static String getHeaderFileName(Response response) {
        int iIndexOf;
        String strHeader = response.header("Content-Disposition");
        if (strHeader == null || (iIndexOf = strHeader.indexOf("filename=")) == -1) {
            return null;
        }
        return strHeader.substring(iIndexOf + "filename=".length(), strHeader.length()).replaceAll(PunctuationConst.DOUBLE_QUOTES, "");
    }

    private static String getUrlFileName(String str) {
        int iLastIndexOf = str.lastIndexOf(63);
        if (iLastIndexOf > 1) {
            return str.substring(str.lastIndexOf(47) + 1, iLastIndexOf);
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        File file = new File(str);
        if (!file.exists()) {
            return true;
        }
        if (!file.isFile()) {
            return false;
        }
        boolean zDelete = file.delete();
        OkLogger.m862e("deleteFile:" + zDelete + " path:" + str);
        return zDelete;
    }
}
