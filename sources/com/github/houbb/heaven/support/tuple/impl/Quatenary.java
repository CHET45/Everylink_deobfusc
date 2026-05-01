package com.github.houbb.heaven.support.tuple.impl;

import com.github.houbb.heaven.support.tuple.IValueFour;
import com.github.houbb.heaven.support.tuple.IValueOne;
import com.github.houbb.heaven.support.tuple.IValueThree;
import com.github.houbb.heaven.support.tuple.IValueTwo;

/* JADX INFO: loaded from: classes3.dex */
public class Quatenary<A, B, C, D> extends AbstractTuple implements IValueOne<A>, IValueTwo<B>, IValueThree<C>, IValueFour<D> {

    /* JADX INFO: renamed from: a */
    private final A f532a;

    /* JADX INFO: renamed from: b */
    private final B f533b;

    /* JADX INFO: renamed from: c */
    private final C f534c;

    /* JADX INFO: renamed from: d */
    private final D f535d;

    public Quatenary(A a, B b, C c, D d) {
        super(a, b, c, d);
        this.f532a = a;
        this.f533b = b;
        this.f534c = c;
        this.f535d = d;
    }

    /* JADX INFO: renamed from: of */
    public static <A, B, C, D> Quatenary<A, B, C, D> m529of(A a, B b, C c, D d) {
        return new Quatenary<>(a, b, c, d);
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueOne
    public A getValueOne() {
        return this.f532a;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueTwo
    public B getValueTwo() {
        return this.f533b;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueThree
    public C getValueThree() {
        return this.f534c;
    }

    @Override // com.github.houbb.heaven.support.tuple.IValueFour
    public D getValueFour() {
        return this.f535d;
    }

    public String toString() {
        return "Quatenary{a=" + this.f532a + ", b=" + this.f533b + ", c=" + this.f534c + ", d=" + this.f535d + '}';
    }
}
