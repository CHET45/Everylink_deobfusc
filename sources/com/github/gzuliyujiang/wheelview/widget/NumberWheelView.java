package com.github.gzuliyujiang.wheelview.widget;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class NumberWheelView extends WheelView {
    public NumberWheelView(Context context) {
        super(context);
    }

    public NumberWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // com.github.gzuliyujiang.wheelview.widget.WheelView
    protected List<?> generatePreviewData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    @Override // com.github.gzuliyujiang.wheelview.widget.WheelView
    @Deprecated
    public void setData(List<?> data) {
        if (isInEditMode()) {
            super.setData(generatePreviewData());
            return;
        }
        throw new UnsupportedOperationException("Use setRange instead");
    }

    public void setRange(int min, int max, int step) {
        int iMin = Math.min(min, max);
        int iMax = Math.max(min, max);
        ArrayList arrayList = new ArrayList((iMax - iMin) / step);
        while (iMin <= iMax) {
            arrayList.add(Integer.valueOf(iMin));
            iMin += step;
        }
        super.setData(arrayList);
    }

    public void setRange(float min, float max, float step) {
        float fMin = Math.min(min, max);
        float fMax = Math.max(min, max);
        ArrayList arrayList = new ArrayList((int) ((fMax - fMin) / step));
        while (fMin <= fMax) {
            arrayList.add(Float.valueOf(fMin));
            fMin += step;
        }
        super.setData(arrayList);
    }
}
