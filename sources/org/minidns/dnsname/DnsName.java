package org.minidns.dnsname;

import com.alibaba.fastjson.asm.Opcodes;
import com.github.houbb.heaven.constant.CharConst;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import org.minidns.dnslabel.DnsLabel;
import org.minidns.dnsname.InvalidDnsNameException;
import org.minidns.idna.MiniDnsIdna;

/* JADX INFO: loaded from: classes4.dex */
public final class DnsName implements CharSequence, Serializable, Comparable<DnsName> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String LABEL_SEP_REGEX = "[.。．｡]";
    static final int MAX_DNSNAME_LENGTH_IN_OCTETS = 255;
    public static final int MAX_LABELS = 128;
    private static final long serialVersionUID = 1;
    public final String ace;
    private transient byte[] bytes;
    private transient String domainpart;
    private transient int hashCode;
    private transient String hostpart;
    private transient String idn;
    private transient DnsLabel[] labels;
    private final String rawAce;
    private transient byte[] rawBytes;
    private transient DnsLabel[] rawLabels;
    private int size;
    public static final DnsName ROOT = new DnsName(".");
    public static final DnsName IN_ADDR_ARPA = new DnsName("in-addr.arpa");
    public static final DnsName IP6_ARPA = new DnsName("ip6.arpa");
    public static boolean VALIDATE = true;

    private DnsName(String str) {
        this(str, true);
    }

    private DnsName(String str, boolean z) {
        this.size = -1;
        if (str.isEmpty()) {
            this.rawAce = ROOT.rawAce;
        } else {
            int length = str.length();
            int i = length - 1;
            if (length >= 2 && str.charAt(i) == '.') {
                str = str.subSequence(0, i).toString();
            }
            if (z) {
                this.rawAce = str;
            } else {
                this.rawAce = MiniDnsIdna.toASCII(str);
            }
        }
        this.ace = this.rawAce.toLowerCase(Locale.US);
        if (VALIDATE) {
            validateMaxDnsnameLengthInOctets();
        }
    }

    private DnsName(DnsLabel[] dnsLabelArr, boolean z) {
        this.size = -1;
        this.rawLabels = dnsLabelArr;
        this.labels = new DnsLabel[dnsLabelArr.length];
        int length = 0;
        for (int i = 0; i < dnsLabelArr.length; i++) {
            length += dnsLabelArr[i].length() + 1;
            this.labels[i] = dnsLabelArr[i].asLowercaseVariant();
        }
        this.rawAce = labelsToString(dnsLabelArr, length);
        this.ace = labelsToString(this.labels, length);
        if (z && VALIDATE) {
            validateMaxDnsnameLengthInOctets();
        }
    }

    private static String labelsToString(DnsLabel[] dnsLabelArr, int i) {
        StringBuilder sb = new StringBuilder(i);
        for (int length = dnsLabelArr.length - 1; length >= 0; length--) {
            sb.append((CharSequence) dnsLabelArr[length]).append(CharConst.DOT);
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private void validateMaxDnsnameLengthInOctets() {
        setBytesIfRequired();
        if (this.bytes.length > 255) {
            throw new InvalidDnsNameException.DNSNameTooLongException(this.ace, this.bytes);
        }
    }

    public void writeToStream(OutputStream outputStream) throws IOException {
        setBytesIfRequired();
        outputStream.write(this.bytes);
    }

    public byte[] getBytes() {
        setBytesIfRequired();
        return (byte[]) this.bytes.clone();
    }

    public byte[] getRawBytes() {
        if (this.rawBytes == null) {
            setLabelsIfRequired();
            this.rawBytes = toBytes(this.rawLabels);
        }
        return (byte[]) this.rawBytes.clone();
    }

    private void setBytesIfRequired() {
        if (this.bytes != null) {
            return;
        }
        setLabelsIfRequired();
        this.bytes = toBytes(this.labels);
    }

    private static byte[] toBytes(DnsLabel[] dnsLabelArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64);
        for (int length = dnsLabelArr.length - 1; length >= 0; length--) {
            dnsLabelArr[length].writeToBoas(byteArrayOutputStream);
        }
        byteArrayOutputStream.write(0);
        return byteArrayOutputStream.toByteArray();
    }

    private void setLabelsIfRequired() {
        if (this.labels == null || this.rawLabels == null) {
            if (isRootLabel()) {
                DnsLabel[] dnsLabelArr = new DnsLabel[0];
                this.labels = dnsLabelArr;
                this.rawLabels = dnsLabelArr;
            } else {
                this.labels = getLabels(this.ace);
                this.rawLabels = getLabels(this.rawAce);
            }
        }
    }

    private static DnsLabel[] getLabels(String str) {
        String[] strArrSplit = str.split(LABEL_SEP_REGEX, 128);
        for (int i = 0; i < strArrSplit.length / 2; i++) {
            String str2 = strArrSplit[i];
            int length = (strArrSplit.length - i) - 1;
            strArrSplit[i] = strArrSplit[length];
            strArrSplit[length] = str2;
        }
        try {
            return DnsLabel.from(strArrSplit);
        } catch (DnsLabel.LabelToLongException e) {
            throw new InvalidDnsNameException.LabelTooLongException(str, e.label);
        }
    }

    public String getRawAce() {
        return this.rawAce;
    }

    public String asIdn() {
        String str = this.idn;
        if (str != null) {
            return str;
        }
        String unicode = MiniDnsIdna.toUnicode(this.ace);
        this.idn = unicode;
        return unicode;
    }

    public String getDomainpart() {
        setHostnameAndDomainpartIfRequired();
        return this.domainpart;
    }

    public String getHostpart() {
        setHostnameAndDomainpartIfRequired();
        return this.hostpart;
    }

    public DnsLabel getHostpartLabel() {
        setLabelsIfRequired();
        return this.labels[r0.length - 1];
    }

    private void setHostnameAndDomainpartIfRequired() {
        if (this.hostpart != null) {
            return;
        }
        String[] strArrSplit = this.ace.split(LABEL_SEP_REGEX, 2);
        this.hostpart = strArrSplit[0];
        if (strArrSplit.length > 1) {
            this.domainpart = strArrSplit[1];
        } else {
            this.domainpart = "";
        }
    }

    public int size() {
        if (this.size < 0) {
            if (isRootLabel()) {
                this.size = 1;
            } else {
                this.size = this.ace.length() + 2;
            }
        }
        return this.size;
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.ace.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.ace.charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return this.ace.subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return this.ace;
    }

    public static DnsName from(CharSequence charSequence) {
        return from(charSequence.toString());
    }

    public static DnsName from(String str) {
        return new DnsName(str, false);
    }

    public static DnsName from(DnsName dnsName, DnsName dnsName2) {
        dnsName.setLabelsIfRequired();
        dnsName2.setLabelsIfRequired();
        int length = dnsName.rawLabels.length;
        DnsLabel[] dnsLabelArr = dnsName2.rawLabels;
        DnsLabel[] dnsLabelArr2 = new DnsLabel[length + dnsLabelArr.length];
        System.arraycopy(dnsLabelArr, 0, dnsLabelArr2, 0, dnsLabelArr.length);
        DnsLabel[] dnsLabelArr3 = dnsName.rawLabels;
        System.arraycopy(dnsLabelArr3, 0, dnsLabelArr2, dnsName2.rawLabels.length, dnsLabelArr3.length);
        return new DnsName(dnsLabelArr2, true);
    }

    public static DnsName from(CharSequence charSequence, DnsName dnsName) {
        return from(DnsLabel.from(charSequence.toString()), dnsName);
    }

    public static DnsName from(DnsLabel dnsLabel, DnsName dnsName) {
        dnsName.setLabelsIfRequired();
        DnsLabel[] dnsLabelArr = dnsName.rawLabels;
        DnsLabel[] dnsLabelArr2 = new DnsLabel[dnsLabelArr.length + 1];
        System.arraycopy(dnsLabelArr, 0, dnsLabelArr2, 0, dnsLabelArr.length);
        dnsLabelArr2[dnsName.rawLabels.length] = dnsLabel;
        return new DnsName(dnsLabelArr2, true);
    }

    public static DnsName from(DnsLabel dnsLabel, DnsLabel dnsLabel2, DnsName dnsName) {
        dnsName.setBytesIfRequired();
        DnsLabel[] dnsLabelArr = dnsName.rawLabels;
        DnsLabel[] dnsLabelArr2 = new DnsLabel[dnsLabelArr.length + 2];
        System.arraycopy(dnsLabelArr, 0, dnsLabelArr2, 0, dnsLabelArr.length);
        DnsLabel[] dnsLabelArr3 = dnsName.rawLabels;
        dnsLabelArr2[dnsLabelArr3.length] = dnsLabel2;
        dnsLabelArr2[dnsLabelArr3.length + 1] = dnsLabel;
        return new DnsName(dnsLabelArr2, true);
    }

    public static DnsName from(DnsName... dnsNameArr) {
        int length = 0;
        for (DnsName dnsName : dnsNameArr) {
            dnsName.setLabelsIfRequired();
            length += dnsName.rawLabels.length;
        }
        DnsLabel[] dnsLabelArr = new DnsLabel[length];
        int length2 = 0;
        for (int length3 = dnsNameArr.length - 1; length3 >= 0; length3--) {
            DnsName dnsName2 = dnsNameArr[length3];
            DnsLabel[] dnsLabelArr2 = dnsName2.rawLabels;
            System.arraycopy(dnsLabelArr2, 0, dnsLabelArr, length2, dnsLabelArr2.length);
            length2 += dnsName2.rawLabels.length;
        }
        return new DnsName(dnsLabelArr, true);
    }

    public static DnsName from(String[] strArr) {
        return new DnsName(DnsLabel.from(strArr), true);
    }

    public static DnsName parse(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        int unsignedByte = dataInputStream.readUnsignedByte();
        if ((unsignedByte & Opcodes.CHECKCAST) == 192) {
            int unsignedByte2 = ((unsignedByte & 63) << 8) + dataInputStream.readUnsignedByte();
            HashSet hashSet = new HashSet();
            hashSet.add(Integer.valueOf(unsignedByte2));
            return parse(bArr, unsignedByte2, hashSet);
        }
        if (unsignedByte == 0) {
            return ROOT;
        }
        byte[] bArr2 = new byte[unsignedByte];
        dataInputStream.readFully(bArr2);
        return from(new DnsName(new String(bArr2, StandardCharsets.US_ASCII)), parse(dataInputStream, bArr));
    }

    private static DnsName parse(byte[] bArr, int i, HashSet<Integer> hashSet) throws IllegalStateException {
        int i2 = bArr[i];
        int i3 = i2 & 255;
        if ((i2 & Opcodes.CHECKCAST) != 192) {
            if (i3 == 0) {
                return ROOT;
            }
            int i4 = i + 1;
            return from(new DnsName(new String(bArr, i4, i3, StandardCharsets.US_ASCII)), parse(bArr, i4 + i3, hashSet));
        }
        int i5 = ((i2 & 63) << 8) + (bArr[i + 1] & 255);
        if (hashSet.contains(Integer.valueOf(i5))) {
            throw new IllegalStateException("Cyclic offsets detected.");
        }
        hashSet.add(Integer.valueOf(i5));
        return parse(bArr, i5, hashSet);
    }

    @Override // java.lang.Comparable
    public int compareTo(DnsName dnsName) {
        return this.ace.compareTo(dnsName.ace);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DnsName)) {
            return false;
        }
        DnsName dnsName = (DnsName) obj;
        setBytesIfRequired();
        dnsName.setBytesIfRequired();
        return Arrays.equals(this.bytes, dnsName.bytes);
    }

    public int hashCode() {
        if (this.hashCode == 0 && !isRootLabel()) {
            setBytesIfRequired();
            this.hashCode = Arrays.hashCode(this.bytes);
        }
        return this.hashCode;
    }

    public boolean isDirectChildOf(DnsName dnsName) {
        setLabelsIfRequired();
        dnsName.setLabelsIfRequired();
        if (this.labels.length - 1 != dnsName.labels.length) {
            return false;
        }
        int i = 0;
        while (true) {
            DnsLabel[] dnsLabelArr = dnsName.labels;
            if (i >= dnsLabelArr.length) {
                return true;
            }
            if (!this.labels[i].equals(dnsLabelArr[i])) {
                return false;
            }
            i++;
        }
    }

    public boolean isChildOf(DnsName dnsName) {
        setLabelsIfRequired();
        dnsName.setLabelsIfRequired();
        if (this.labels.length < dnsName.labels.length) {
            return false;
        }
        int i = 0;
        while (true) {
            DnsLabel[] dnsLabelArr = dnsName.labels;
            if (i >= dnsLabelArr.length) {
                return true;
            }
            if (!this.labels[i].equals(dnsLabelArr[i])) {
                return false;
            }
            i++;
        }
    }

    public int getLabelCount() {
        setLabelsIfRequired();
        return this.labels.length;
    }

    public DnsLabel[] getLabels() {
        setLabelsIfRequired();
        return (DnsLabel[]) this.labels.clone();
    }

    public DnsLabel getLabel(int i) {
        setLabelsIfRequired();
        return this.labels[i];
    }

    public DnsLabel[] getRawLabels() {
        setLabelsIfRequired();
        return (DnsLabel[]) this.rawLabels.clone();
    }

    public DnsName stripToLabels(int i) {
        setLabelsIfRequired();
        DnsLabel[] dnsLabelArr = this.labels;
        if (i > dnsLabelArr.length) {
            throw new IllegalArgumentException();
        }
        if (i == dnsLabelArr.length) {
            return this;
        }
        if (i == 0) {
            return ROOT;
        }
        return new DnsName((DnsLabel[]) Arrays.copyOfRange(this.rawLabels, 0, i), false);
    }

    public DnsName getParent() {
        return isRootLabel() ? ROOT : stripToLabels(getLabelCount() - 1);
    }

    public boolean isRootLabel() {
        return this.ace.isEmpty() || this.ace.equals(".");
    }
}
