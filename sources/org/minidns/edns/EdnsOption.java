package org.minidns.edns;

import java.io.DataOutputStream;
import java.io.IOException;
import org.minidns.edns.Edns;

/* JADX INFO: loaded from: classes4.dex */
public abstract class EdnsOption {
    public final int optionCode;
    protected final byte[] optionData;
    public final int optionLength;
    private String terminalOutputCache;
    private String toStringCache;

    protected abstract CharSequence asTerminalOutputInternal();

    public abstract Edns.OptionCode getOptionCode();

    protected abstract CharSequence toStringInternal();

    protected EdnsOption(int i, byte[] bArr) {
        this.optionCode = i;
        this.optionLength = bArr.length;
        this.optionData = bArr;
    }

    protected EdnsOption(byte[] bArr) {
        this.optionCode = getOptionCode().asInt;
        this.optionLength = bArr.length;
        this.optionData = bArr;
    }

    public final void writeToDos(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeShort(this.optionCode);
        dataOutputStream.writeShort(this.optionLength);
        dataOutputStream.write(this.optionData);
    }

    public final String toString() {
        if (this.toStringCache == null) {
            this.toStringCache = toStringInternal().toString();
        }
        return this.toStringCache;
    }

    public final String asTerminalOutput() {
        if (this.terminalOutputCache == null) {
            this.terminalOutputCache = asTerminalOutputInternal().toString();
        }
        return this.terminalOutputCache;
    }

    public static EdnsOption parse(int i, byte[] bArr) {
        if (C50791.$SwitchMap$org$minidns$edns$Edns$OptionCode[Edns.OptionCode.from(i).ordinal()] == 1) {
            return new Nsid(bArr);
        }
        return new UnknownEdnsOption(i, bArr);
    }

    /* JADX INFO: renamed from: org.minidns.edns.EdnsOption$1 */
    static /* synthetic */ class C50791 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$edns$Edns$OptionCode;

        static {
            int[] iArr = new int[Edns.OptionCode.values().length];
            $SwitchMap$org$minidns$edns$Edns$OptionCode = iArr;
            try {
                iArr[Edns.OptionCode.NSID.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }
}
