package com.whispercpp.whisper;

import android.util.Log;
import com.github.houbb.heaven.constant.CharConst;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p036io.CloseableKt;
import kotlin.p036io.TextStreamsKt;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: WhisperCpuConfig.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m1900d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0004\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J*\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\u0006\u0010\b\u001a\u00020\u00042\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\nH\u0002J\b\u0010\u000b\u001a\u00020\u0007H\u0002J\b\u0010\f\u001a\u00020\u0007H\u0002J\b\u0010\r\u001a\u00020\u0007H\u0002J\u001e\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u000f*\b\u0012\u0004\u0012\u00020\u00070\u0003H\u0002J\u0012\u0010\u0010\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0003H\u0002J\u0012\u0010\u0011\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0003H\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, m1901d2 = {"Lcom/whispercpp/whisper/CpuInfo;", "", "lines", "", "", "(Ljava/util/List;)V", "getCpuValues", "", "property", "mapper", "Lkotlin/Function1;", "getHighPerfCpuCount", "getHighPerfCpuCountByFrequencies", "getHighPerfCpuCountByVariant", "binnedValues", "", "countDroppingMin", "countKeepingMin", "Companion", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
final class CpuInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String LOG_TAG = "WhisperCpuConfig";
    private final List<String> lines;

    public CpuInfo(List<String> lines) {
        Intrinsics.checkNotNullParameter(lines, "lines");
        this.lines = lines;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getHighPerfCpuCount() {
        try {
            return getHighPerfCpuCountByFrequencies();
        } catch (Exception e) {
            Log.d(LOG_TAG, "Couldn't read CPU frequencies", e);
            return getHighPerfCpuCountByVariant();
        }
    }

    private final int getHighPerfCpuCountByFrequencies() {
        List<Integer> cpuValues = getCpuValues("processor", new Function1<String, Integer>() { // from class: com.whispercpp.whisper.CpuInfo.getHighPerfCpuCountByFrequencies.1
            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(CpuInfo.INSTANCE.getMaxCpuFrequency(Integer.parseInt(it)));
            }
        });
        Log.d(LOG_TAG, "Binned cpu frequencies (frequency, count): " + binnedValues(cpuValues));
        return countDroppingMin(cpuValues);
    }

    private final int getHighPerfCpuCountByVariant() {
        List<Integer> cpuValues = getCpuValues("CPU variant", new Function1<String, Integer>() { // from class: com.whispercpp.whisper.CpuInfo.getHighPerfCpuCountByVariant.1
            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Integer.valueOf(Integer.parseInt(StringsKt.substringAfter$default(it, "0x", (String) null, 2, (Object) null), CharsKt.checkRadix(16)));
            }
        });
        Log.d(LOG_TAG, "Binned cpu variants (variant, count): " + binnedValues(cpuValues));
        return countKeepingMin(cpuValues);
    }

    private final Map<Integer, Integer> binnedValues(List<Integer> list) {
        final List<Integer> list2 = list;
        return GroupingKt.eachCount(new Grouping<Integer, Integer>() { // from class: com.whispercpp.whisper.CpuInfo$binnedValues$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Iterator<Integer> sourceIterator() {
                return list2.iterator();
            }

            @Override // kotlin.collections.Grouping
            public Integer keyOf(Integer element) {
                return Integer.valueOf(element.intValue());
            }
        });
    }

    private final List<Integer> getCpuValues(final String property, final Function1<? super String, Integer> mapper) {
        return SequencesKt.toList(SequencesKt.sorted(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(this.lines), new Function1<String, Boolean>() { // from class: com.whispercpp.whisper.CpuInfo.getCpuValues.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(StringsKt.startsWith$default(it, property, false, 2, (Object) null));
            }
        }), new Function1<String, Integer>() { // from class: com.whispercpp.whisper.CpuInfo.getCpuValues.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Integer invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return mapper.invoke(StringsKt.trim((CharSequence) StringsKt.substringAfter$default(it, CharConst.COLON, (String) null, 2, (Object) null)).toString());
            }
        })));
    }

    private final int countDroppingMin(List<Integer> list) {
        List<Integer> list2 = list;
        int iIntValue = ((Number) CollectionsKt.minOrThrow((Iterable<Double>) list2)).intValue();
        int i = 0;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (((Number) it.next()).intValue() > iIntValue && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    private final int countKeepingMin(List<Integer> list) {
        List<Integer> list2 = list;
        int iIntValue = ((Number) CollectionsKt.minOrThrow((Iterable<Double>) list2)).intValue();
        int i = 0;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (((Number) it.next()).intValue() == iIntValue && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    /* JADX INFO: compiled from: WhisperCpuConfig.kt */
    @Metadata(m1900d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0002J\b\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, m1901d2 = {"Lcom/whispercpp/whisper/CpuInfo$Companion;", "", "()V", "LOG_TAG", "", "getHighPerfCpuCount", "", "getMaxCpuFrequency", "cpuIndex", "readCpuInfo", "Lcom/whispercpp/whisper/CpuInfo;", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final int getHighPerfCpuCount() {
            try {
                return readCpuInfo().getHighPerfCpuCount();
            } catch (Exception e) {
                Log.d(CpuInfo.LOG_TAG, "Couldn't read CPU info", e);
                return RangesKt.coerceAtLeast(Runtime.getRuntime().availableProcessors() - 4, 0);
            }
        }

        private final CpuInfo readCpuInfo() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            try {
                List list = SequencesKt.toList(TextStreamsKt.lineSequence(bufferedReader));
                CloseableKt.closeFinally(bufferedReader, null);
                return new CpuInfo(list);
            } finally {
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getMaxCpuFrequency(int cpuIndex) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/sys/devices/system/cpu/cpu" + cpuIndex + "/cpufreq/cpuinfo_max_freq"));
            try {
                String line = bufferedReader.readLine();
                CloseableKt.closeFinally(bufferedReader, null);
                Intrinsics.checkNotNull(line);
                return Integer.parseInt(line);
            } finally {
            }
        }
    }
}
