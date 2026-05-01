package com.aivox.common_ui.antishake;

import java.util.ArrayList;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class LimitQueue<E> {
    private int limitedSize;
    private LinkedList<E> linkedList = new LinkedList<>();

    public LimitQueue(int i) {
        this.limitedSize = i;
    }

    public void offer(E e) {
        if (this.linkedList.size() >= this.limitedSize) {
            this.linkedList.poll();
        }
        this.linkedList.offer(e);
    }

    public E get(int i) {
        return this.linkedList.get(i);
    }

    public E getLast() {
        return this.linkedList.getLast();
    }

    public E getFirst() {
        return this.linkedList.getFirst();
    }

    public int getLimit() {
        return this.limitedSize;
    }

    public void setLimitedSize(int i) {
        this.limitedSize = i;
    }

    public int size() {
        return this.linkedList.size();
    }

    public ArrayList<E> getArrayList() {
        ArrayList<E> arrayList = new ArrayList<>();
        for (int i = 0; i < this.linkedList.size(); i++) {
            arrayList.add(this.linkedList.get(i));
        }
        return arrayList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.linkedList.size(); i++) {
            sb.append(this.linkedList.get(i));
            sb.append(" ");
        }
        return sb.toString();
    }
}
