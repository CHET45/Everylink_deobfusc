package com.aivox.base.ifly;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microsoft.azure.storage.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class Demo {
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String SERVICE_URL = "https://api.iflyrec.com";
    private static String accessKeyId = "n9HpM358xb";
    private static String accessKeySecret = "7l8Y3dK050PKZ4G382GLsi3489Fq3y2r";

    public void step01_upload() {
        byte[] byteArray;
        File file = new File("tmp/1分钟音频.mp3");
        InputStream resourceAsStream = Demo.class.getClassLoader().getResourceAsStream(file.getPath());
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            for (int i = resourceAsStream.read(bArr); i != -1; i = resourceAsStream.read(bArr)) {
                byteArrayOutputStream.write(bArr, 0, i);
            }
            byteArrayOutputStream.flush();
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            byteArray = null;
        }
        TreeMap treeMap = new TreeMap();
        treeMap.put("dateTime", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date()));
        treeMap.put("accessKeyId", accessKeyId);
        treeMap.put("signatureRandom", UUID.randomUUID().toString());
        treeMap.put("fileName", file.getName());
        treeMap.put("fileSize", Integer.valueOf(byteArray.length));
        treeMap.put(TypedValues.TransitionType.S_DURATION, 56431L);
        treeMap.put("language", "cn");
        try {
            NRTSignature.formUrlEncodedValueParameters(treeMap);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String parseResult(String str) {
        JSONArray jSONArray = JSONObject.parseObject(JSONObject.parseObject(str).getJSONObject("content").getString("orderResult")).getJSONArray("lattice");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONArray jSONArray2 = JSONObject.parseObject(jSONArray.getJSONObject(i).getString("json_1best")).getJSONObject(Constants.QueryConstants.SIGNED_START).getJSONArray("rt");
            for (int i2 = 0; i2 < jSONArray2.size(); i2++) {
                JSONArray jSONArray3 = jSONArray2.getJSONObject(i2).getJSONArray("ws");
                StringBuilder sb2 = new StringBuilder();
                for (int i3 = 0; i3 < jSONArray3.size(); i3++) {
                    JSONArray jSONArray4 = jSONArray3.getJSONObject(i3).getJSONArray("cw");
                    for (int i4 = 0; i4 < jSONArray4.size(); i4++) {
                        sb2.append(jSONArray4.getJSONObject(i4).getString("w"));
                    }
                }
                sb.append((CharSequence) sb2);
            }
        }
        return sb.toString();
    }
}
