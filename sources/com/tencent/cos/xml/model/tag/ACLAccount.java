package com.tencent.cos.xml.model.tag;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ACLAccount {
    List<String> idList = new ArrayList();

    public void addAccount(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.idList.add(String.format("id=\"qcs::cam::uin/%s:uin/%s\"", str, str2));
    }

    public void addAccount(String str) {
        addAccount(str, str);
    }

    public String getAccount() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = this.idList.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(PunctuationConst.COMMA);
        }
        String string = sb.toString();
        int iLastIndexOf = string.lastIndexOf(PunctuationConst.COMMA);
        if (iLastIndexOf > 0) {
            return string.substring(0, iLastIndexOf);
        }
        return null;
    }
}
