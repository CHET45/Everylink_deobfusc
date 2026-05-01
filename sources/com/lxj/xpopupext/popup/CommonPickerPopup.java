package com.lxj.xpopupext.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupext.C2296R;
import com.lxj.xpopupext.adapter.ArrayWheelAdapter;
import com.lxj.xpopupext.listener.CommonPickerListener;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class CommonPickerPopup extends BottomPopupView {
    TextView btnCancel;
    TextView btnConfirm;
    private CommonPickerListener commonPickerListener;
    int currentItem;
    public int dividerColor;
    private int itemTextSize;
    private int itemsVisibleCount;
    public float lineSpace;
    List<String> list;
    public int textColorCenter;
    public int textColorOut;
    private WheelView wheelView;

    public CommonPickerPopup(Context context) {
        super(context);
        this.itemsVisibleCount = 7;
        this.itemTextSize = 18;
        this.dividerColor = -2763307;
        this.lineSpace = 2.4f;
        this.textColorOut = -5723992;
        this.textColorCenter = -14013910;
        this.list = new ArrayList();
        this.currentItem = 0;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return C2296R.layout._xpopup_ext_common_picker;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.btnCancel = (TextView) findViewById(C2296R.id.btnCancel);
        this.btnConfirm = (TextView) findViewById(C2296R.id.btnConfirm);
        this.wheelView = (WheelView) findViewById(C2296R.id.commonWheel);
        this.btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.CommonPickerPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CommonPickerPopup.this.dismiss();
            }
        });
        this.btnConfirm.setTextColor(XPopup.getPrimaryColor());
        this.btnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.CommonPickerPopup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int currentItem = CommonPickerPopup.this.wheelView.getCurrentItem();
                if (CommonPickerPopup.this.commonPickerListener != null) {
                    CommonPickerPopup.this.commonPickerListener.onItemSelected(currentItem, CommonPickerPopup.this.list.get(currentItem));
                }
                CommonPickerPopup.this.dismiss();
            }
        });
        initWheelData();
    }

    private void initWheelData() {
        this.wheelView.setItemsVisibleCount(this.itemsVisibleCount);
        this.wheelView.setAlphaGradient(true);
        this.wheelView.setTextSize(this.itemTextSize);
        this.wheelView.setCyclic(false);
        this.wheelView.setDividerColor(this.popupInfo.isDarkTheme ? Color.parseColor("#444444") : this.dividerColor);
        this.wheelView.setDividerType(WheelView.DividerType.FILL);
        this.wheelView.setLineSpacingMultiplier(this.lineSpace);
        this.wheelView.setTextColorOut(this.textColorOut);
        this.wheelView.setTextColorCenter(this.popupInfo.isDarkTheme ? Color.parseColor("#CCCCCC") : this.textColorCenter);
        this.wheelView.isCenterLabel(false);
        this.wheelView.setCurrentItem(this.currentItem);
        this.wheelView.setAdapter(new ArrayWheelAdapter(this.list));
        this.wheelView.setOnItemSelectedListener(new OnItemSelectedListener() { // from class: com.lxj.xpopupext.popup.CommonPickerPopup.3
            @Override // com.contrarywind.listener.OnItemSelectedListener
            public void onItemSelected(int i) {
            }
        });
        if (this.popupInfo.isDarkTheme) {
            applyDarkTheme();
        } else {
            applyLightTheme();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        this.btnCancel.setTextColor(Color.parseColor("#999999"));
        this.btnConfirm.setTextColor(Color.parseColor("#ffffff"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2296R.color._xpopup_dark_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        this.btnCancel.setTextColor(Color.parseColor("#666666"));
        this.btnConfirm.setTextColor(Color.parseColor("#222222"));
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2296R.color._xpopup_light_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }

    public CommonPickerPopup setCommonPickerListener(CommonPickerListener commonPickerListener) {
        this.commonPickerListener = commonPickerListener;
        return this;
    }

    public CommonPickerPopup setItemTextSize(int i) {
        this.itemTextSize = i;
        return this;
    }

    public CommonPickerPopup setItemsVisibleCount(int i) {
        this.itemsVisibleCount = i;
        return this;
    }

    public CommonPickerPopup setLineSpace(float f) {
        this.lineSpace = f;
        return this;
    }

    public CommonPickerPopup setPickerData(List<String> list) {
        this.list = list;
        return this;
    }

    public CommonPickerPopup setCurrentItem(int i) {
        this.currentItem = i;
        return this;
    }
}
