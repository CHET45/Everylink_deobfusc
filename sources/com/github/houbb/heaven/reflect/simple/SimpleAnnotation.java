package com.github.houbb.heaven.reflect.simple;

import com.github.houbb.heaven.reflect.api.IAnnotation;

/* JADX INFO: loaded from: classes3.dex */
public class SimpleAnnotation implements IAnnotation {
    private int access;
    private String fullName;
    private String name;
    private Class type;

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String name() {
        return this.name;
    }

    public SimpleAnnotation name(String str) {
        this.name = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String fullName() {
        return this.fullName;
    }

    public SimpleAnnotation fullName(String str) {
        this.fullName = str;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public Class type() {
        return this.type;
    }

    public SimpleAnnotation type(Class cls) {
        this.type = cls;
        return this;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public int access() {
        return this.access;
    }

    public SimpleAnnotation access(int i) {
        this.access = i;
        return this;
    }
}
