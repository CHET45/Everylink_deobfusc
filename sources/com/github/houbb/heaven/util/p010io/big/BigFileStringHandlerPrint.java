package com.github.houbb.heaven.util.p010io.big;

/* JADX INFO: loaded from: classes3.dex */
public class BigFileStringHandlerPrint implements IBigFileStringHandler {
    @Override // com.github.houbb.heaven.util.p010io.big.IBigFileStringHandler
    public void handle(BigFileStringHandlerContext bigFileStringHandlerContext) {
        System.out.println(bigFileStringHandlerContext.getIndex() + ": " + bigFileStringHandlerContext.getLine());
    }
}
