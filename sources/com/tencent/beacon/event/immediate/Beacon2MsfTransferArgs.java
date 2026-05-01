package com.tencent.beacon.event.immediate;

/* JADX INFO: loaded from: classes4.dex */
public class Beacon2MsfTransferArgs extends BeaconTransferArgs {

    /* JADX INFO: renamed from: d */
    private String f1604d;

    public Beacon2MsfTransferArgs(byte[] bArr) {
        super(bArr);
        this.f1604d = "trpc.Beacon.BeaconLogServerLC.blslongconnection.SsoProcess";
    }

    @Override // com.tencent.beacon.event.immediate.BeaconTransferArgs
    public String getCommand() {
        return this.f1604d;
    }

    @Override // com.tencent.beacon.event.immediate.BeaconTransferArgs
    public void setCommand(String str) {
        this.f1604d = str;
    }
}
