package com.github.houbb.heaven.reflect.simple;

import com.github.houbb.heaven.reflect.api.IParam;

/* JADX INFO: loaded from: classes3.dex */
public class SimpleParam implements IParam {
    private int access;
    private String fullName;
    private String name;
    private Class type;

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String name() {
        return this.name;
    }

    public SimpleParam name(String str) {
        this.name = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String fullName() {
        return this.fullName;
    }

    public SimpleParam fullName(String str) {
        this.fullName = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public Class type() {
        return this.type;
    }

    public SimpleParam type(Class cls) {
        this.type = cls;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public int access() {
        return this.access;
    }

    public SimpleParam access(int i) {
        this.access = i;
        return this;
    }
}
