package com.lcodecore.tkrefreshlayout.processor;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Decorator implements IDecorator {

    /* JADX INFO: renamed from: cp */
    protected TwinklingRefreshLayout.CoContext f816cp;
    protected IDecorator decorator;

    public Decorator(TwinklingRefreshLayout.CoContext coContext, IDecorator iDecorator) {
        this.f816cp = coContext;
        this.decorator = iDecorator;
    }
}
