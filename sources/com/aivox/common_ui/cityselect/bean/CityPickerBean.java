package com.aivox.common_ui.cityselect.bean;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CityPickerBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public List<AreasBean> areas;
        public List<List<City>> s_region;
    }
}
