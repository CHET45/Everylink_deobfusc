package org.minidns.dnsmessage;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class Question {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private byte[] byteArray;
    public final Record.CLASS clazz;
    public final DnsName name;
    public final Record.TYPE type;
    private final boolean unicastQuery;

    public Question(CharSequence charSequence, Record.TYPE type, Record.CLASS r3, boolean z) {
        this(DnsName.from(charSequence), type, r3, z);
    }

    public Question(DnsName dnsName, Record.TYPE type, Record.CLASS r3, boolean z) {
        this.name = dnsName;
        this.type = type;
        this.clazz = r3;
        this.unicastQuery = z;
    }

    public Question(DnsName dnsName, Record.TYPE type, Record.CLASS r4) {
        this(dnsName, type, r4, false);
    }

    public Question(DnsName dnsName, Record.TYPE type) {
        this(dnsName, type, Record.CLASS.IN);
    }

    public Question(CharSequence charSequence, Record.TYPE type, Record.CLASS r3) {
        this(DnsName.from(charSequence), type, r3);
    }

    public Question(CharSequence charSequence, Record.TYPE type) {
        this(DnsName.from(charSequence), type);
    }

    public Question(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        this.name = DnsName.parse(dataInputStream, bArr);
        this.type = Record.TYPE.getType(dataInputStream.readUnsignedShort());
        this.clazz = Record.CLASS.getClass(dataInputStream.readUnsignedShort());
        this.unicastQuery = false;
    }

    public byte[] toByteArray() {
        if (this.byteArray == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                this.name.writeToStream(dataOutputStream);
                dataOutputStream.writeShort(this.type.getValue());
                dataOutputStream.writeShort(this.clazz.getValue() | (this.unicastQuery ? 32768 : 0));
                dataOutputStream.flush();
                this.byteArray = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this.byteArray;
    }

    public int hashCode() {
        return Arrays.hashCode(toByteArray());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Question) {
            return Arrays.equals(toByteArray(), ((Question) obj).toByteArray());
        }
        return false;
    }

    public String toString() {
        return this.name.getRawAce() + ".\t" + this.clazz + '\t' + this.type;
    }

    public DnsMessage.Builder asMessageBuilder() {
        DnsMessage.Builder builder = DnsMessage.builder();
        builder.setQuestion(this);
        return builder;
    }

    public DnsMessage asQueryMessage() {
        return asMessageBuilder().build();
    }
}
