package com.aivox.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/* JADX INFO: loaded from: classes.dex */
public class JsonUtils {
    private static JsonUtils jsonUtils;
    PrintStream outputStream = null;

    public static JsonUtils getIns() {
        if (jsonUtils == null) {
            jsonUtils = new JsonUtils();
        }
        return jsonUtils;
    }

    public String obj2jsonStr(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public Object json2Obj(JSON json, Class cls) {
        return JSONObject.toJavaObject(json, cls);
    }

    public Object jsonStr2Obj(String str, Class cls) {
        return JSONObject.parseObject(str, cls);
    }

    public void writeJSONObjectToSdCard(String str, String str2) {
        PrintStream printStream;
        File file = new File(str2);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            try {
                PrintStream printStream2 = new PrintStream(new FileOutputStream(file));
                this.outputStream = printStream2;
                printStream2.print(str);
                LogUtil.m338i("写入成功");
                printStream = this.outputStream;
                if (printStream == null) {
                    return;
                }
            } catch (FileNotFoundException e) {
                BaseAppUtils.printErrorMsg(e);
                LogUtil.m336e("writeJSONObjectToSdCard:" + e.getLocalizedMessage());
                printStream = this.outputStream;
                if (printStream == null) {
                    return;
                }
            }
            printStream.close();
        } catch (Throwable th) {
            PrintStream printStream3 = this.outputStream;
            if (printStream3 != null) {
                printStream3.close();
            }
            throw th;
        }
    }
}
