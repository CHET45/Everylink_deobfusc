package com.lxj.xpopupext.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupext.C2296R;
import com.lxj.xpopupext.bean.JsonBean;
import com.lxj.xpopupext.listener.CityPickerListener;
import com.lxj.xpopupext.listener.OnOptionsSelectListener;
import com.lxj.xpopupext.view.WheelOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes4.dex */
public class CityPickerPopup extends BottomPopupView {
    TextView btnCancel;
    TextView btnConfirm;
    private CityPickerListener cityPickerListener;
    public int dividerColor;
    public float lineSpace;
    private List<String> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    public int textColorCenter;
    public int textColorOut;
    private WheelOptions wheelOptions;

    public CityPickerPopup(Context context) {
        super(context);
        this.options1Items = new ArrayList();
        this.options2Items = new ArrayList<>();
        this.options3Items = new ArrayList<>();
        this.dividerColor = -2763307;
        this.lineSpace = 2.4f;
        this.textColorOut = -5723992;
        this.textColorCenter = -14013910;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return C2296R.layout._xpopup_ext_city_picker;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.btnCancel = (TextView) findViewById(C2296R.id.btnCancel);
        this.btnConfirm = (TextView) findViewById(C2296R.id.btnConfirm);
        this.btnCancel.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.CityPickerPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CityPickerPopup.this.dismiss();
            }
        });
        this.btnConfirm.setTextColor(XPopup.getPrimaryColor());
        this.btnConfirm.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopupext.popup.CityPickerPopup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (CityPickerPopup.this.cityPickerListener != null) {
                    int[] currentItems = CityPickerPopup.this.wheelOptions.getCurrentItems();
                    int i = currentItems[0];
                    int i2 = currentItems[1];
                    CityPickerPopup.this.cityPickerListener.onCityConfirm((String) CityPickerPopup.this.options1Items.get(i), (String) ((ArrayList) CityPickerPopup.this.options2Items.get(i)).get(i2), (String) ((ArrayList) ((ArrayList) CityPickerPopup.this.options3Items.get(i)).get(i2)).get(currentItems[2]), view2);
                }
                CityPickerPopup.this.dismiss();
            }
        });
        WheelOptions wheelOptions = new WheelOptions(findViewById(C2296R.id.citypicker), false);
        this.wheelOptions = wheelOptions;
        if (this.cityPickerListener != null) {
            wheelOptions.setOptionsSelectChangeListener(new OnOptionsSelectListener() { // from class: com.lxj.xpopupext.popup.CityPickerPopup.3
                @Override // com.lxj.xpopupext.listener.OnOptionsSelectListener
                public void onOptionsSelect(int i, int i2, int i3) {
                    if (i < CityPickerPopup.this.options1Items.size() && i < CityPickerPopup.this.options2Items.size() && i2 < ((ArrayList) CityPickerPopup.this.options2Items.get(i)).size() && i < CityPickerPopup.this.options3Items.size() && i2 < ((ArrayList) CityPickerPopup.this.options3Items.get(i)).size() && i3 < ((ArrayList) ((ArrayList) CityPickerPopup.this.options3Items.get(i)).get(i2)).size()) {
                        CityPickerPopup.this.cityPickerListener.onCityChange((String) CityPickerPopup.this.options1Items.get(i), (String) ((ArrayList) CityPickerPopup.this.options2Items.get(i)).get(i2), (String) ((ArrayList) ((ArrayList) CityPickerPopup.this.options3Items.get(i)).get(i2)).get(i3));
                    }
                }
            });
        }
        this.wheelOptions.setTextContentSize(18);
        this.wheelOptions.setItemsVisible(7);
        this.wheelOptions.setAlphaGradient(true);
        this.wheelOptions.setCyclic(false);
        this.wheelOptions.setDividerColor(this.popupInfo.isDarkTheme ? Color.parseColor("#444444") : this.dividerColor);
        this.wheelOptions.setDividerType(WheelView.DividerType.FILL);
        this.wheelOptions.setLineSpacingMultiplier(this.lineSpace);
        this.wheelOptions.setTextColorOut(this.textColorOut);
        this.wheelOptions.setTextColorCenter(this.popupInfo.isDarkTheme ? Color.parseColor("#CCCCCC") : this.textColorCenter);
        this.wheelOptions.isCenterLabel(false);
        if (!this.options1Items.isEmpty() && !this.options2Items.isEmpty() && !this.options3Items.isEmpty()) {
            WheelOptions wheelOptions2 = this.wheelOptions;
            if (wheelOptions2 != null) {
                wheelOptions2.setPicker(this.options1Items, this.options2Items, this.options3Items);
                this.wheelOptions.setCurrentItems(0, 0, 0);
            }
        } else {
            initJsonData();
        }
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

    public CityPickerPopup setCityPickerListener(CityPickerListener cityPickerListener) {
        this.cityPickerListener = cityPickerListener;
        return this;
    }

    private void initJsonData() {
        ArrayList<JsonBean> data = parseData(readJson(getContext(), "province.json"));
        for (int i = 0; i < data.size(); i++) {
            this.options1Items.add(data.get(i).getName());
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<ArrayList<String>> arrayList2 = new ArrayList<>();
            for (int i2 = 0; i2 < data.get(i).getCityList().size(); i2++) {
                arrayList.add(data.get(i).getCityList().get(i2).getName());
                ArrayList<String> arrayList3 = new ArrayList<>();
                arrayList3.addAll(data.get(i).getCityList().get(i2).getArea());
                arrayList2.add(arrayList3);
            }
            this.options2Items.add(arrayList);
            this.options3Items.add(arrayList2);
        }
        this.wheelOptions.setPicker(this.options1Items, this.options2Items, this.options3Items);
        this.wheelOptions.setCurrentItems(0, 0, 0);
    }

    public ArrayList<JsonBean> parseData(String str) {
        ArrayList<JsonBean> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            Gson gson = new Gson();
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add((JsonBean) gson.fromJson(jSONArray.optJSONObject(i).toString(), JsonBean.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public String readJson(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(str)));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
