package org.minidns.dnssec;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.UByte;
import org.minidns.dnslabel.DnsLabel;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnssec.DnssecUnverifiedReason;
import org.minidns.dnssec.algorithms.AlgorithmMap;
import org.minidns.record.DNSKEY;
import org.minidns.record.Data;
import org.minidns.record.DelegatingDnssecRR;
import org.minidns.record.NSEC;
import org.minidns.record.NSEC3;
import org.minidns.record.RRSIG;
import org.minidns.record.Record;
import org.minidns.util.Base32;

/* JADX INFO: loaded from: classes4.dex */
class Verifier {
    private static final AlgorithmMap algorithmMap = AlgorithmMap.INSTANCE;

    Verifier() {
    }

    public static DnssecUnverifiedReason verify(Record<DNSKEY> record, DelegatingDnssecRR delegatingDnssecRR) throws DnssecValidationFailedException {
        DNSKEY dnskey = (DNSKEY) record.payloadData;
        DigestCalculator dsDigestCalculator = algorithmMap.getDsDigestCalculator(delegatingDnssecRR.digestType);
        if (dsDigestCalculator == null) {
            return new DnssecUnverifiedReason.AlgorithmNotSupportedReason(delegatingDnssecRR.digestTypeByte, delegatingDnssecRR.getType(), record);
        }
        byte[] byteArray = dnskey.toByteArray();
        byte[] bytes = record.name.getBytes();
        byte[] bArr = new byte[bytes.length + byteArray.length];
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        System.arraycopy(byteArray, 0, bArr, bytes.length, byteArray.length);
        try {
            if (delegatingDnssecRR.digestEquals(dsDigestCalculator.digest(bArr))) {
                return null;
            }
            throw new DnssecValidationFailedException(record, "SEP is not properly signed by parent DS!");
        } catch (Exception e) {
            return new DnssecUnverifiedReason.AlgorithmExceptionThrownReason(delegatingDnssecRR.digestType, "DS", record, e);
        }
    }

    public static DnssecUnverifiedReason verify(List<Record<? extends Data>> list, RRSIG rrsig, DNSKEY dnskey) throws IOException {
        SignatureVerifier signatureVerifier = algorithmMap.getSignatureVerifier(rrsig.algorithm);
        if (signatureVerifier == null) {
            return new DnssecUnverifiedReason.AlgorithmNotSupportedReason(rrsig.algorithmByte, rrsig.getType(), list.get(0));
        }
        if (signatureVerifier.verify(combine(rrsig, list), rrsig, dnskey)) {
            return null;
        }
        throw new DnssecValidationFailedException(list, "Signature is invalid.");
    }

    public static DnssecUnverifiedReason verifyNsec(Record<NSEC> record, Question question) {
        NSEC nsec = (NSEC) record.payloadData;
        if ((!record.name.equals(question.name) || nsec.types.contains(question.type)) && !nsecMatches(question.name, record.name, nsec.next)) {
            return new DnssecUnverifiedReason.NSECDoesNotMatchReason(question, record);
        }
        return null;
    }

    public static DnssecUnverifiedReason verifyNsec3(DnsName dnsName, Record<NSEC3> record, Question question) {
        NSEC3 nsec3 = (NSEC3) record.payloadData;
        DigestCalculator nsecDigestCalculator = algorithmMap.getNsecDigestCalculator(nsec3.hashAlgorithm);
        if (nsecDigestCalculator == null) {
            return new DnssecUnverifiedReason.AlgorithmNotSupportedReason(nsec3.hashAlgorithmByte, nsec3.getType(), record);
        }
        String strEncodeToString = Base32.encodeToString(nsec3hash(nsecDigestCalculator, nsec3, question.name, nsec3.iterations));
        if (record.name.equals(DnsName.from(strEncodeToString + "." + ((Object) dnsName)))) {
            if (nsec3.types.contains(question.type)) {
                return new DnssecUnverifiedReason.NSECDoesNotMatchReason(question, record);
            }
            return null;
        }
        if (nsecMatches(strEncodeToString, record.name.getHostpart(), Base32.encodeToString(nsec3.getNextHashed()))) {
            return null;
        }
        return new DnssecUnverifiedReason.NSECDoesNotMatchReason(question, record);
    }

    static byte[] combine(RRSIG rrsig, List<Record<? extends Data>> list) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            rrsig.writePartialSignature(dataOutputStream);
            DnsName dnsNameFrom = list.get(0).name;
            if (!dnsNameFrom.isRootLabel()) {
                if (dnsNameFrom.getLabelCount() < rrsig.labels) {
                    throw new DnssecValidationFailedException("Invalid RRsig record");
                }
                if (dnsNameFrom.getLabelCount() > rrsig.labels) {
                    dnsNameFrom = DnsName.from(DnsLabel.WILDCARD_LABEL, dnsNameFrom.stripToLabels(rrsig.labels));
                }
            }
            DnsName dnsName = dnsNameFrom;
            ArrayList arrayList = new ArrayList(list.size());
            for (Record<? extends Data> record : list) {
                arrayList.add(new Record(dnsName, record.type, record.clazzValue, rrsig.originalTtl, record.payloadData).toByteArray());
            }
            final int size = dnsName.size() + 10;
            Collections.sort(arrayList, new Comparator<byte[]>() { // from class: org.minidns.dnssec.Verifier.1
                @Override // java.util.Comparator
                public int compare(byte[] bArr, byte[] bArr2) {
                    int length;
                    int length2;
                    for (int i = size; i < bArr.length && i < bArr2.length; i++) {
                        byte b = bArr[i];
                        byte b2 = bArr2[i];
                        if (b != b2) {
                            length = b & UByte.MAX_VALUE;
                            length2 = b2 & UByte.MAX_VALUE;
                            break;
                        }
                    }
                    length = bArr.length;
                    length2 = bArr2.length;
                    return length - length2;
                }
            });
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                dataOutputStream.write((byte[]) it.next());
            }
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static boolean nsecMatches(String str, String str2, String str3) {
        return nsecMatches(DnsName.from(str), DnsName.from(str2), DnsName.from(str3));
    }

    static boolean nsecMatches(DnsName dnsName, DnsName dnsName2, DnsName dnsName3) {
        int labelCount = dnsName2.getLabelCount();
        int labelCount2 = dnsName3.getLabelCount();
        int labelCount3 = dnsName.getLabelCount();
        if (labelCount3 > labelCount && !dnsName.isChildOf(dnsName2) && dnsName.stripToLabels(labelCount).compareTo(dnsName2) < 0) {
            return false;
        }
        if (labelCount3 <= labelCount && dnsName.compareTo(dnsName2.stripToLabels(labelCount3)) < 0) {
            return false;
        }
        if (labelCount3 <= labelCount2 || dnsName.isChildOf(dnsName3) || dnsName.stripToLabels(labelCount2).compareTo(dnsName3) <= 0) {
            return labelCount3 > labelCount2 || dnsName.compareTo(dnsName3.stripToLabels(labelCount3)) < 0;
        }
        return false;
    }

    static byte[] nsec3hash(DigestCalculator digestCalculator, NSEC3 nsec3, DnsName dnsName, int i) {
        return nsec3hash(digestCalculator, nsec3.getSalt(), dnsName.getBytes(), i);
    }

    static byte[] nsec3hash(DigestCalculator digestCalculator, byte[] bArr, byte[] bArr2, int i) {
        while (true) {
            int i2 = i - 1;
            if (i < 0) {
                return bArr2;
            }
            byte[] bArr3 = new byte[bArr2.length + bArr.length];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
            bArr2 = digestCalculator.digest(bArr3);
            i = i2;
        }
    }
}
