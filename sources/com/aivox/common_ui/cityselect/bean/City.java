package com.aivox.common_ui.cityselect.bean;

import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class City implements Serializable {

    /* JADX INFO: renamed from: id */
    private int f273id;
    private boolean isHot;
    private String name;
    private String pinyin;
    private String province;
    private String word;

    public City() {
    }

    public City(String str, String str2) {
        this.name = str;
        this.pinyin = str2;
        this.f273id = this.f273id;
    }

    public City(int i, String str, String str2) {
        this.name = str;
        this.pinyin = str2;
        this.f273id = i;
    }

    public City(int i, String str, String str2, boolean z) {
        this.name = str;
        this.pinyin = str2;
        this.f273id = i;
        this.isHot = z;
    }

    public City(int i, String str, String str2, boolean z, String str3) {
        this.f273id = i;
        this.name = str;
        this.pinyin = str2;
        this.isHot = z;
        this.province = str3;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String str) {
        this.word = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String str) {
        this.pinyin = str;
    }

    public int getId() {
        return this.f273id;
    }

    public void setId(int i) {
        this.f273id = i;
    }

    public boolean isHot() {
        return this.isHot;
    }

    public void setHot(boolean z) {
        this.isHot = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.name, ((City) obj).name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }
}
