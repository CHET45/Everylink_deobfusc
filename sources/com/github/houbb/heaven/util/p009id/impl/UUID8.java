package com.github.houbb.heaven.util.p009id.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.p009id.InterfaceC1503Id;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
@Deprecated
public class UUID8 implements InterfaceC1503Id {
    private static final char[] CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override // com.github.houbb.heaven.util.p009id.InterfaceC1503Id
    public String genId() {
        List<String> listUuidUnits = uuidUnits();
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = listUuidUnits.iterator();
        while (it.hasNext()) {
            sb.append(CHARS[Integer.parseInt(it.next(), 16) % 62]);
        }
        return sb.toString();
    }

    private List<String> uuidUnits() {
        String strGenId = new UUID32().genId();
        List<String> listNewArrayList = Guavas.newArrayList(8);
        for (int i = 0; i < 8; i++) {
            int i2 = i * 4;
            listNewArrayList.add(strGenId.substring(i2, i2 + 4));
        }
        return listNewArrayList;
    }
}
