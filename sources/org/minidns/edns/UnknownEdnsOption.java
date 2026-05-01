package org.minidns.edns;

import org.minidns.edns.Edns;
import org.minidns.util.Hex;

/* JADX INFO: loaded from: classes4.dex */
public class UnknownEdnsOption extends EdnsOption {
    protected UnknownEdnsOption(int i, byte[] bArr) {
        super(i, bArr);
    }

    @Override // org.minidns.edns.EdnsOption
    public Edns.OptionCode getOptionCode() {
        return Edns.OptionCode.UNKNOWN;
    }

    @Override // org.minidns.edns.EdnsOption
    protected CharSequence asTerminalOutputInternal() {
        return Hex.from(this.optionData);
    }

    @Override // org.minidns.edns.EdnsOption
    protected CharSequence toStringInternal() {
        return asTerminalOutputInternal();
    }
}
