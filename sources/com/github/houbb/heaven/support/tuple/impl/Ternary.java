package com.github.houbb.heaven.support.tuple.impl;

import com.github.houbb.heaven.support.tuple.IValueOne;
import com.github.houbb.heaven.support.tuple.IValueThree;
import com.github.houbb.heaven.support.tuple.IValueTwo;

/* JADX INFO: loaded from: classes3.dex */
public class Ternary<A, B, C> extends AbstractTuple implements IValueOne<A>, IValueTwo<B>, IValueThree<C> {

    /* JADX INFO: renamed from: a */
    private final A f536a;

    /* JADX INFO: renamed from: b */
    private final B f537b;

    /* JADX INFO: renamed from: c */
    private final C f538c;

    public Ternary(A a, B b, C c) {
        super(a, b, c);
        this.f536a = a;
        this.f537b = b;
        this.f538c = c;
    }

    /* JADX INFO: renamed from: of */
    public static <A, B, C> Ternary<A, B, C> m530of(A a, B b, C c) {
        return new Ternary<>(a, b, c);
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueOne
    public A getValueOne() {
        return this.f536a;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueTwo
    public B getValueTwo() {
        return this.f537b;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueThree
    public C getValueThree() {
        return this.f538c;
    }

    public String toString() {
        return "Ternary{a=" + this.f536a + ", b=" + this.f537b + ", c=" + this.f538c + '}';
    }
}
