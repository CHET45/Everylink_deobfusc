package org.minidns.record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.minidns.edns.EdnsOption;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class OPT extends Data {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final List<EdnsOption> variablePart;

    public OPT() {
        this(Collections.emptyList());
    }

    public OPT(List<EdnsOption> list) {
        this.variablePart = Collections.unmodifiableList(list);
    }

    public static OPT parse(DataInputStream dataInputStream, int i) throws IOException {
        List listEmptyList;
        if (i == 0) {
            listEmptyList = Collections.emptyList();
        } else {
            ArrayList arrayList = new ArrayList(4);
            while (i > 0) {
                int unsignedShort = dataInputStream.readUnsignedShort();
                int unsignedShort2 = dataInputStream.readUnsignedShort();
                byte[] bArr = new byte[unsignedShort2];
                dataInputStream.read(bArr);
                arrayList.add(EdnsOption.parse(unsignedShort, bArr));
                i -= unsignedShort2 + 4;
            }
            listEmptyList = arrayList;
        }
        return new OPT(listEmptyList);
    }

    @Override // org.minidns.record.Data
    public Record.TYPE getType() {
        return Record.TYPE.OPT;
    }

    @Override // org.minidns.record.Data
    protected void serialize(DataOutputStream dataOutputStream) throws IOException {
        Iterator<EdnsOption> it = this.variablePart.iterator();
        while (it.hasNext()) {
            it.next().writeToDos(dataOutputStream);
        }
    }
}
