package com.lxj.xpopupext.bean;

import com.contrarywind.interfaces.IPickerViewData;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class JsonBean implements IPickerViewData {
    private List<CityBean> city;
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public List<CityBean> getCityList() {
        return this.city;
    }

    public void setCityList(List<CityBean> list) {
        this.city = list;
    }

    @Override // com.contrarywind.interfaces.IPickerViewData
    public String getPickerViewText() {
        return this.name;
    }

    public static class CityBean {
        private List<String> area;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public List<String> getArea() {
            return this.area;
        }

        public void setArea(List<String> list) {
            this.area = list;
        }
    }
}
