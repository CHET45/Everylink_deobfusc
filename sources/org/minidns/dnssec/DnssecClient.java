package org.minidns.dnssec;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.minidns.DnsCache;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.dnssec.DnssecUnverifiedReason;
import org.minidns.dnssec.DnssecValidationFailedException;
import org.minidns.iterative.ReliableDnsClient;
import org.minidns.record.C5086DS;
import org.minidns.record.DLV;
import org.minidns.record.DNSKEY;
import org.minidns.record.Data;
import org.minidns.record.DelegatingDnssecRR;
import org.minidns.record.NSEC;
import org.minidns.record.NSEC3;
import org.minidns.record.RRSIG;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnssecClient extends ReliableDnsClient {
    private DnsName dlv;
    private final Map<DnsName, byte[]> knownSeps;
    private boolean stripSignatureRecords;
    private static final BigInteger rootEntryKey = new BigInteger("1628686155461064465348252249725010996177649738666492500572664444461532807739744536029771810659241049343994038053541290419968870563183856865780916376571550372513476957870843322273120879361960335192976656756972171258658400305760429696147778001233984421619267530978084631948434496468785021389956803104620471232008587410372348519229650742022804219634190734272506220018657920136902014393834092648785514548876370028925405557661759399901378816916683122474038734912535425670533237815676134840739565610963796427401855723026687073600445461090736240030247906095053875491225879656640052743394090544036297390104110989318819106653199917493");
    private static final DnsName DEFAULT_DLV = DnsName.from("dlv.isc.org");

    public DnssecClient() {
        this(DEFAULT_CACHE);
    }

    public DnssecClient(DnsCache dnsCache) {
        super(dnsCache);
        this.knownSeps = new ConcurrentHashMap();
        this.stripSignatureRecords = true;
        addSecureEntryPoint(DnsName.ROOT, rootEntryKey.toByteArray());
    }

    @Override // org.minidns.AbstractDnsClient
    public DnsQueryResult query(Question question) throws IOException {
        DnssecQueryResult dnssecQueryResultQueryDnssec = queryDnssec(question);
        if (!dnssecQueryResultQueryDnssec.isAuthenticData()) {
            throw new IOException();
        }
        return dnssecQueryResultQueryDnssec.dnsQueryResult;
    }

    public DnssecQueryResult queryDnssec(CharSequence charSequence, Record.TYPE type) throws IOException {
        return queryDnssec(new Question(charSequence, type, Record.CLASS.IN));
    }

    public DnssecQueryResult queryDnssec(Question question) throws IOException {
        return performVerification(super.query(question));
    }

    private DnssecQueryResult performVerification(DnsQueryResult dnsQueryResult) throws IOException {
        if (dnsQueryResult == null) {
            return null;
        }
        DnsMessage dnsMessage = dnsQueryResult.response;
        DnsMessage.Builder builderAsBuilder = dnsMessage.asBuilder();
        Set<DnssecUnverifiedReason> setVerify = verify(dnsMessage);
        builderAsBuilder.setAuthenticData(setVerify.isEmpty());
        List<Record<? extends Data>> list = dnsMessage.answerSection;
        List<Record<? extends Data>> list2 = dnsMessage.authoritySection;
        List<Record<? extends Data>> list3 = dnsMessage.additionalSection;
        HashSet hashSet = new HashSet();
        Record.filter(hashSet, RRSIG.class, list);
        Record.filter(hashSet, RRSIG.class, list2);
        Record.filter(hashSet, RRSIG.class, list3);
        if (this.stripSignatureRecords) {
            builderAsBuilder.setAnswers(stripSignatureRecords(list));
            builderAsBuilder.setNameserverRecords(stripSignatureRecords(list2));
            builderAsBuilder.setAdditionalResourceRecords(stripSignatureRecords(list3));
        }
        return new DnssecQueryResult(builderAsBuilder.build(), dnsQueryResult, hashSet, setVerify);
    }

    private static List<Record<? extends Data>> stripSignatureRecords(List<Record<? extends Data>> list) {
        if (list.isEmpty()) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Record<? extends Data> record : list) {
            if (record.type != Record.TYPE.RRSIG) {
                arrayList.add(record);
            }
        }
        return arrayList;
    }

    private Set<DnssecUnverifiedReason> verify(DnsMessage dnsMessage) throws IOException {
        if (!dnsMessage.answerSection.isEmpty()) {
            return verifyAnswer(dnsMessage);
        }
        return verifyNsec(dnsMessage);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Set<DnssecUnverifiedReason> verifyAnswer(DnsMessage dnsMessage) throws IOException {
        boolean z = false;
        Question question = dnsMessage.questions.get(0);
        List<Record<? extends Data>> list = dnsMessage.answerSection;
        List<Record<? extends Data>> listCopyAnswers = dnsMessage.copyAnswers();
        VerifySignaturesResult verifySignaturesResultVerifySignatures = verifySignatures(question, list, listCopyAnswers);
        Set<DnssecUnverifiedReason> set = verifySignaturesResultVerifySignatures.reasons;
        if (!set.isEmpty()) {
            return set;
        }
        HashSet hashSet = new HashSet();
        Iterator<Record<? extends Data>> it = listCopyAnswers.iterator();
        while (it.hasNext()) {
            Record<E> recordIfPossibleAs = it.next().ifPossibleAs(DNSKEY.class);
            if (recordIfPossibleAs != 0) {
                Set<DnssecUnverifiedReason> setVerifySecureEntryPoint = verifySecureEntryPoint(recordIfPossibleAs);
                if (setVerifySecureEntryPoint.isEmpty()) {
                    z = true;
                } else {
                    hashSet.addAll(setVerifySecureEntryPoint);
                }
                if (!verifySignaturesResultVerifySignatures.sepSignaturePresent) {
                    LOGGER.finer("SEP key is not self-signed.");
                }
                it.remove();
            }
        }
        if (verifySignaturesResultVerifySignatures.sepSignaturePresent && !z) {
            set.addAll(hashSet);
        }
        if (verifySignaturesResultVerifySignatures.sepSignatureRequired && !verifySignaturesResultVerifySignatures.sepSignaturePresent) {
            set.add(new DnssecUnverifiedReason.NoSecureEntryPointReason(question.name));
        }
        if (!listCopyAnswers.isEmpty()) {
            if (listCopyAnswers.size() != list.size()) {
                throw new DnssecValidationFailedException(question, "Only some records are signed!");
            }
            set.add(new DnssecUnverifiedReason.NoSignaturesReason(question));
        }
        return set;
    }

    private Set<DnssecUnverifiedReason> verifyNsec(DnsMessage dnsMessage) throws IOException {
        DnsName dnsName;
        DnssecUnverifiedReason dnssecUnverifiedReasonVerifyNsec;
        HashSet hashSet = new HashSet();
        boolean z = false;
        Question question = dnsMessage.questions.get(0);
        List<Record<? extends Data>> list = dnsMessage.authoritySection;
        Iterator<Record<? extends Data>> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                dnsName = null;
                break;
            }
            Record<? extends Data> next = it.next();
            if (next.type == Record.TYPE.SOA) {
                dnsName = next.name;
                break;
            }
        }
        if (dnsName == null) {
            throw new DnssecValidationFailedException.AuthorityDoesNotContainSoa(dnsMessage);
        }
        boolean z2 = false;
        for (Record<? extends Data> record : list) {
            int i = C50761.$SwitchMap$org$minidns$record$Record$TYPE[record.type.ordinal()];
            if (i == 1) {
                dnssecUnverifiedReasonVerifyNsec = Verifier.verifyNsec(record.m1955as(NSEC.class), question);
            } else if (i == 2) {
                dnssecUnverifiedReasonVerifyNsec = Verifier.verifyNsec3(dnsName, record.m1955as(NSEC3.class), question);
            }
            if (dnssecUnverifiedReasonVerifyNsec != null) {
                hashSet.add(dnssecUnverifiedReasonVerifyNsec);
            } else {
                z2 = true;
            }
            z = true;
        }
        if (z && !z2) {
            throw new DnssecValidationFailedException(question, "Invalid NSEC!");
        }
        List<Record<? extends Data>> listCopyAuthority = dnsMessage.copyAuthority();
        VerifySignaturesResult verifySignaturesResultVerifySignatures = verifySignatures(question, list, listCopyAuthority);
        if (z2 && verifySignaturesResultVerifySignatures.reasons.isEmpty()) {
            hashSet.clear();
        } else {
            hashSet.addAll(verifySignaturesResultVerifySignatures.reasons);
        }
        if (listCopyAuthority.isEmpty() || listCopyAuthority.size() == list.size()) {
            return hashSet;
        }
        throw new DnssecValidationFailedException(question, "Only some resource records from the authority section are signed!");
    }

    /* JADX INFO: renamed from: org.minidns.dnssec.DnssecClient$1 */
    static /* synthetic */ class C50761 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$Record$TYPE;

        static {
            int[] iArr = new int[Record.TYPE.values().length];
            $SwitchMap$org$minidns$record$Record$TYPE = iArr;
            try {
                iArr[Record.TYPE.NSEC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[Record.TYPE.NSEC3.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private static class VerifySignaturesResult {
        Set<DnssecUnverifiedReason> reasons;
        boolean sepSignaturePresent;
        boolean sepSignatureRequired;

        private VerifySignaturesResult() {
            this.sepSignatureRequired = false;
            this.sepSignaturePresent = false;
            this.reasons = new HashSet();
        }

        /* synthetic */ VerifySignaturesResult(C50761 c50761) {
            this();
        }
    }

    private VerifySignaturesResult verifySignatures(Question question, Collection<Record<? extends Data>> collection, List<Record<? extends Data>> list) throws IOException {
        Date date = new Date();
        LinkedList linkedList = new LinkedList();
        VerifySignaturesResult verifySignaturesResult = new VerifySignaturesResult(null);
        ArrayList<Record> arrayList = new ArrayList(list.size());
        Iterator<Record<? extends Data>> it = list.iterator();
        while (it.hasNext()) {
            Record<E> recordIfPossibleAs = it.next().ifPossibleAs(RRSIG.class);
            if (recordIfPossibleAs != 0) {
                RRSIG rrsig = (RRSIG) recordIfPossibleAs.payloadData;
                if (rrsig.signatureExpiration.compareTo(date) < 0 || rrsig.signatureInception.compareTo(date) > 0) {
                    linkedList.add(rrsig);
                } else {
                    arrayList.add(recordIfPossibleAs);
                }
            }
        }
        if (arrayList.isEmpty()) {
            if (!linkedList.isEmpty()) {
                verifySignaturesResult.reasons.add(new DnssecUnverifiedReason.NoActiveSignaturesReason(question, linkedList));
            } else {
                verifySignaturesResult.reasons.add(new DnssecUnverifiedReason.NoSignaturesReason(question));
            }
            return verifySignaturesResult;
        }
        for (Record record : arrayList) {
            RRSIG rrsig2 = (RRSIG) record.payloadData;
            ArrayList arrayList2 = new ArrayList(collection.size());
            for (Record<? extends Data> record2 : collection) {
                if (record2.type == rrsig2.typeCovered && record2.name.equals(record.name)) {
                    arrayList2.add(record2);
                }
            }
            verifySignaturesResult.reasons.addAll(verifySignedRecords(question, rrsig2, arrayList2));
            if (question.name.equals(rrsig2.signerName) && rrsig2.typeCovered == Record.TYPE.DNSKEY) {
                Iterator<Record<? extends Data>> it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    DNSKEY dnskey = (DNSKEY) it2.next().ifPossibleAs(DNSKEY.class).payloadData;
                    it2.remove();
                    if (dnskey.getKeyTag() == rrsig2.keyTag) {
                        verifySignaturesResult.sepSignaturePresent = true;
                    }
                }
                verifySignaturesResult.sepSignatureRequired = true;
            }
            if (!isParentOrSelf(record.name.ace, rrsig2.signerName.ace)) {
                LOGGER.finer("Records at " + ((Object) record.name) + " are cross-signed with a key from " + ((Object) rrsig2.signerName));
            } else {
                list.removeAll(arrayList2);
            }
            list.remove(record);
        }
        return verifySignaturesResult;
    }

    private static boolean isParentOrSelf(String str, String str2) {
        if (str.equals(str2) || str2.isEmpty()) {
            return true;
        }
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        if (strArrSplit2.length > strArrSplit.length) {
            return false;
        }
        for (int i = 1; i <= strArrSplit2.length; i++) {
            if (!strArrSplit2[strArrSplit2.length - i].equals(strArrSplit[strArrSplit.length - i])) {
                return false;
            }
        }
        return true;
    }

    private Set<DnssecUnverifiedReason> verifySignedRecords(Question question, RRSIG rrsig, List<Record<? extends Data>> list) throws IOException {
        HashSet hashSet = new HashSet();
        DNSKEY dnskey = null;
        if (rrsig.typeCovered == Record.TYPE.DNSKEY) {
            Iterator it = Record.filter(DNSKEY.class, list).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Record record = (Record) it.next();
                if (((DNSKEY) record.payloadData).getKeyTag() == rrsig.keyTag) {
                    dnskey = (DNSKEY) record.payloadData;
                    break;
                }
            }
        } else {
            if (question.type == Record.TYPE.DS && rrsig.signerName.equals(question.name)) {
                hashSet.add(new DnssecUnverifiedReason.NoTrustAnchorReason(question.name));
                return hashSet;
            }
            DnssecQueryResult dnssecQueryResultQueryDnssec = queryDnssec(rrsig.signerName, Record.TYPE.DNSKEY);
            hashSet.addAll(dnssecQueryResultQueryDnssec.getUnverifiedReasons());
            Iterator it2 = dnssecQueryResultQueryDnssec.dnsQueryResult.response.filterAnswerSectionBy(DNSKEY.class).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Record record2 = (Record) it2.next();
                if (((DNSKEY) record2.payloadData).getKeyTag() == rrsig.keyTag) {
                    dnskey = (DNSKEY) record2.payloadData;
                    break;
                }
            }
        }
        if (dnskey == null) {
            throw new DnssecValidationFailedException(question, list.size() + " " + rrsig.typeCovered + " record(s) are signed using an unknown key.");
        }
        DnssecUnverifiedReason dnssecUnverifiedReasonVerify = Verifier.verify(list, rrsig, dnskey);
        if (dnssecUnverifiedReasonVerify != null) {
            hashSet.add(dnssecUnverifiedReasonVerify);
        }
        return hashSet;
    }

    private Set<DnssecUnverifiedReason> verifySecureEntryPoint(Record<DNSKEY> record) throws IOException {
        DelegatingDnssecRR delegatingDnssecRR;
        DnsName dnsName;
        DNSKEY dnskey = (DNSKEY) record.payloadData;
        HashSet hashSet = new HashSet();
        Set<DnssecUnverifiedReason> hashSet2 = new HashSet<>();
        if (this.knownSeps.containsKey(record.name)) {
            if (dnskey.keyEquals(this.knownSeps.get(record.name))) {
                return hashSet;
            }
            hashSet.add(new DnssecUnverifiedReason.ConflictsWithSep(record));
            return hashSet;
        }
        if (record.name.isRootLabel()) {
            hashSet.add(new DnssecUnverifiedReason.NoRootSecureEntryPointReason());
            return hashSet;
        }
        DnssecQueryResult dnssecQueryResultQueryDnssec = queryDnssec(record.name, Record.TYPE.DS);
        hashSet.addAll(dnssecQueryResultQueryDnssec.getUnverifiedReasons());
        Iterator it = dnssecQueryResultQueryDnssec.dnsQueryResult.response.filterAnswerSectionBy(C5086DS.class).iterator();
        while (true) {
            if (!it.hasNext()) {
                delegatingDnssecRR = null;
                break;
            }
            C5086DS c5086ds = (C5086DS) ((Record) it.next()).payloadData;
            if (dnskey.getKeyTag() == c5086ds.keyTag) {
                hashSet2 = dnssecQueryResultQueryDnssec.getUnverifiedReasons();
                delegatingDnssecRR = c5086ds;
                break;
            }
        }
        if (delegatingDnssecRR == null) {
            LOGGER.fine("There is no DS record for " + ((Object) record.name) + ", server gives empty result");
        }
        if (delegatingDnssecRR == null && (dnsName = this.dlv) != null && !dnsName.isChildOf(record.name)) {
            DnssecQueryResult dnssecQueryResultQueryDnssec2 = queryDnssec(DnsName.from(record.name, this.dlv), Record.TYPE.DLV);
            hashSet.addAll(dnssecQueryResultQueryDnssec2.getUnverifiedReasons());
            Iterator it2 = dnssecQueryResultQueryDnssec2.dnsQueryResult.response.filterAnswerSectionBy(DLV.class).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Record record2 = (Record) it2.next();
                if (((DNSKEY) record.payloadData).getKeyTag() == ((DLV) record2.payloadData).keyTag) {
                    LOGGER.fine("Found DLV for " + ((Object) record.name) + ", awesome.");
                    delegatingDnssecRR = (DelegatingDnssecRR) record2.payloadData;
                    hashSet2 = dnssecQueryResultQueryDnssec2.getUnverifiedReasons();
                    break;
                }
            }
        }
        if (delegatingDnssecRR != null) {
            DnssecUnverifiedReason dnssecUnverifiedReasonVerify = Verifier.verify(record, delegatingDnssecRR);
            if (dnssecUnverifiedReasonVerify == null) {
                return hashSet2;
            }
            hashSet.add(dnssecUnverifiedReasonVerify);
            return hashSet;
        }
        if (!hashSet.isEmpty()) {
            return hashSet;
        }
        hashSet.add(new DnssecUnverifiedReason.NoTrustAnchorReason(record.name));
        return hashSet;
    }

    @Override // org.minidns.iterative.ReliableDnsClient, org.minidns.AbstractDnsClient
    protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
        builder.getEdnsBuilder().setUdpPayloadSize(this.dataSource.getUdpPayloadSize()).setDnssecOk();
        builder.setCheckingDisabled(true);
        return super.newQuestion(builder);
    }

    @Override // org.minidns.iterative.ReliableDnsClient
    protected String isResponseAcceptable(DnsMessage dnsMessage) {
        if (!dnsMessage.isDnssecOk()) {
            return "DNSSEC OK (DO) flag not set in response";
        }
        if (!dnsMessage.checkingDisabled) {
            return "CHECKING DISABLED (CD) flag not set in response";
        }
        return super.isResponseAcceptable(dnsMessage);
    }

    public void addSecureEntryPoint(DnsName dnsName, byte[] bArr) {
        this.knownSeps.put(dnsName, bArr);
    }

    public void removeSecureEntryPoint(DnsName dnsName) {
        this.knownSeps.remove(dnsName);
    }

    public void clearSecureEntryPoints() {
        this.knownSeps.clear();
    }

    public boolean isStripSignatureRecords() {
        return this.stripSignatureRecords;
    }

    public void setStripSignatureRecords(boolean z) {
        this.stripSignatureRecords = z;
    }

    public void enableLookasideValidation() {
        configureLookasideValidation(DEFAULT_DLV);
    }

    public void disableLookasideValidation() {
        configureLookasideValidation(null);
    }

    public void configureLookasideValidation(DnsName dnsName) {
        this.dlv = dnsName;
    }
}
