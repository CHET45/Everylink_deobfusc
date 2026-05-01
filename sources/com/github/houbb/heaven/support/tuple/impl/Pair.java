package com.github.houbb.heaven.support.tuple.impl;

import com.github.houbb.heaven.support.tuple.IValueOne;
import com.github.houbb.heaven.support.tuple.IValueTwo;

/* JADX INFO: loaded from: classes3.dex */
public class Pair<A, B> extends AbstractTuple implements IValueOne<A>, IValueTwo<B> {

    /* JADX INFO: renamed from: a */
    private final A f530a;

    /* JADX INFO: renamed from: b */
    private final B f531b;

    public Pair(A a, B b) {
        super(a, b);
        this.f530a = a;
        this.f531b = b;
    }

    /* JADX INFO: renamed from: of */
    public static <A, B> Pair<A, B> m528of(A a, B b) {
        return new Pair<>(a, b);
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueOne
    public A getValueOne() {
        return this.f530a;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueTwo
    public B getValueTwo() {
        return this.f531b;
    }

    public String toString() {
        return "Pair{a=" + this.f530a + ", b=" + this.f531b + '}';
    }
}
