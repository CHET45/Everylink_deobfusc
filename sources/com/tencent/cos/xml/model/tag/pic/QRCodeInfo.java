package com.tencent.cos.xml.model.tag.pic;

import android.graphics.Point;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class QRCodeInfo {
    public List<QRCodePoint> codeLocation;
    public String codeUrl;

    public static class QRCodePoint {
        public String point;

        public Point point() {
            String[] strArrSplit = this.point.split(PunctuationConst.COMMA);
            if (strArrSplit.length == 2) {
                return new Point(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]));
            }
            return null;
        }
    }
}
