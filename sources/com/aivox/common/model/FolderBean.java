package com.aivox.common.model;

import com.aivox.base.C0874R;
import com.aivox.common.base.AppApplication;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class FolderBean implements Serializable {
    private Integer colour;
    private String colourText;
    private Integer count;

    /* JADX INFO: renamed from: id */
    private Integer f244id;
    private String name;
    private boolean selected;
    private Integer sort;
    private Integer type;
    private Object uuid;

    public Integer getColour() {
        return this.colour;
    }

    public void setColour(Integer num) {
        this.colour = num;
    }

    public String getName() {
        Integer num = this.type;
        if (num == null) {
            return AppApplication.getIns().getString(C0874R.string.folder_name_all);
        }
        int iIntValue = num.intValue();
        if (iIntValue == 0) {
            return AppApplication.getIns().getString(C0874R.string.folder_name_star);
        }
        if (iIntValue == 1) {
            return AppApplication.getIns().getString(C0874R.string.folder_name_recycle);
        }
        if (iIntValue == 3) {
            return AppApplication.getIns().getString(C0874R.string.folder_name_share);
        }
        if (iIntValue == 4) {
            return AppApplication.getIns().getString(C0874R.string.folder_name_device);
        }
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer num) {
        this.count = num;
    }

    public Integer getId() {
        return this.f244id;
    }

    public void setId(Integer num) {
        this.f244id = num;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer num) {
        this.sort = num;
    }

    public Integer getType() {
        Integer num = this.type;
        return Integer.valueOf(num == null ? -1 : num.intValue());
    }

    public void setType(Integer num) {
        this.type = num;
    }

    public Object getUuid() {
        return this.uuid;
    }

    public void setUuid(Object obj) {
        this.uuid = obj;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public String getColourText() {
        return this.colourText;
    }

    public void setColourText(String str) {
        this.colourText = str;
    }
}
