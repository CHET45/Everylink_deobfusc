package com.tencent.qcloud.network.sonar.dns;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Dns {
    public static List<String> getLocalHost() {
        BufferedReader bufferedReader;
        ArrayList arrayList = new ArrayList();
        try {
            bufferedReader = new BufferedReader(new FileReader("/system/etc/hosts"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            arrayList.add(line);
            return arrayList;
        }
        bufferedReader.close();
        return arrayList;
    }
}
