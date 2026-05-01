package com.tencent.beacon.module;

/* JADX INFO: loaded from: classes4.dex */
public enum ModuleName {
    STRATEGY("com.tencent.beacon.module.StrategyModule"),
    EVENT("com.tencent.beacon.module.EventModule"),
    STAT("com.tencent.beacon.module.StatModule");

    private String className;

    ModuleName(String str) {
        this.className = str;
    }

    public String getClassName() {
        return this.className;
    }
}
