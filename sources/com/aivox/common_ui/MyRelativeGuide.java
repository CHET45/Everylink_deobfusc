package com.aivox.common_ui;

import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import com.app.hubert.guide.model.RelativeGuide;
import com.app.hubert.guide.util.LogUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class MyRelativeGuide extends RelativeGuide {

    @Retention(RetentionPolicy.SOURCE)
    @interface LimitGravity {
    }

    public MyRelativeGuide(int i, int i2) {
        super(i, i2);
        this.layout = i;
        this.gravity = i2;
    }

    public MyRelativeGuide(int i, int i2, int i3) {
        super(i, i2, i3);
        this.layout = i;
        this.gravity = i2;
        this.padding = i3;
    }

    private RelativeGuide.MarginInfo getMarginInfo(int i, ViewGroup viewGroup, View view2) {
        RelativeGuide.MarginInfo marginInfo = new RelativeGuide.MarginInfo();
        RectF rectF = this.highLight.getRectF(viewGroup);
        LogUtil.m373i("RectF:" + rectF.top + PunctuationConst.UNDERLINE + rectF.bottom + PunctuationConst.UNDERLINE + rectF.left + PunctuationConst.UNDERLINE + rectF.right);
        LogUtil.m373i("ViewGroup:" + viewGroup.getWidth() + PunctuationConst.UNDERLINE + viewGroup.getHeight());
        LogUtil.m373i("maring1:" + marginInfo.toString());
        if (i == 3) {
            marginInfo.gravity = 5;
            marginInfo.rightMargin = (int) ((viewGroup.getWidth() - rectF.left) + this.padding);
            marginInfo.topMargin = (int) rectF.top;
        } else if (i == 5) {
            marginInfo.leftMargin = (int) (rectF.right + this.padding);
            marginInfo.topMargin = (int) rectF.top;
        } else if (i == 48) {
            marginInfo.gravity = 80;
            marginInfo.bottomMargin = (int) ((viewGroup.getHeight() - rectF.top) + this.padding);
            marginInfo.leftMargin = (int) rectF.left;
        } else if (i == 80) {
            marginInfo.topMargin = (int) (rectF.bottom + this.padding);
            marginInfo.leftMargin = 0;
        }
        LogUtil.m373i("maring2:" + marginInfo.toString());
        return marginInfo;
    }

    @Override // com.app.hubert.guide.model.RelativeGuide
    protected void offsetMargin(RelativeGuide.MarginInfo marginInfo, ViewGroup viewGroup, View view2) {
        super.offsetMargin(marginInfo, viewGroup, view2);
        LogUtil.m373i("offsetMargin");
        getMarginInfo(this.gravity, viewGroup, view2);
    }
}
