package com.github.houbb.heaven.reflect.api;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface IClass extends IMember {
    List<IField> fields();

    List<IMethod> methods();
}
