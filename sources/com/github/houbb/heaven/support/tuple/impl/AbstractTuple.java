package com.github.houbb.heaven.support.tuple.impl;

import com.github.houbb.heaven.support.tuple.ITuple;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.util.ArrayUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractTuple implements ITuple {
    private final List<Object> valueList;

    protected AbstractTuple(Object... objArr) {
        this.valueList = Guavas.newArrayList(objArr);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.valueList.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.valueList.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.valueList.contains(obj);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<Object> iterator() {
        return this.valueList.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return ArrayUtil.toArray(this.valueList);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.valueList.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Object> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public Object get(int i) {
        return this.valueList.get(i);
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.valueList.indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.valueList.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator() {
        return this.valueList.listIterator();
    }

    @Override // java.util.List
    public ListIterator<Object> listIterator(int i) {
        return this.valueList.listIterator(i);
    }

    @Override // java.util.List
    public List<Object> subList(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.github.houbb.heaven.support.tuple.ITuple
    public List<Object> toList() {
        return Collections.unmodifiableList(new ArrayList(this.valueList));
    }
}
