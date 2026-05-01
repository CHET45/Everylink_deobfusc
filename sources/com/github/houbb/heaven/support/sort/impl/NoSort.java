package com.github.houbb.heaven.support.sort.impl;

import com.github.houbb.heaven.support.sort.ISort;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class NoSort<T> implements ISort<T> {
    @Override // com.github.houbb.heaven.support.sort.ISort
    public List<T> sort(List<T> list) {
        return list;
    }
}
