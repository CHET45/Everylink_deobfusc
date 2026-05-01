package com.github.houbb.heaven.reflect.simple;

import com.github.houbb.heaven.reflect.api.IClass;
import com.github.houbb.heaven.reflect.api.IField;
import com.github.houbb.heaven.reflect.api.IMethod;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SimpleClass implements IClass {
    @Override // com.github.houbb.heaven.reflect.api.IMember
    public int access() {
        return 0;
    }

    @Override // com.github.houbb.heaven.reflect.api.IClass
    public List<IField> fields() {
        return null;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String fullName() {
        return null;
    }

    @Override // com.github.houbb.heaven.reflect.api.IClass
    public List<IMethod> methods() {
        return null;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public String name() {
        return null;
    }

    @Override // com.github.houbb.heaven.reflect.api.IMember
    public Class type() {
        return null;
    }
}
