package com.github.houbb.opencc4j.support.segment.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import com.github.houbb.opencc4j.support.datamap.impl.DataMaps;
import com.github.houbb.opencc4j.support.segment.Segment;

/* JADX INFO: loaded from: classes3.dex */
public final class Segments {
    private Segments() {
    }

    public static Segment chars() {
        return (Segment) Instances.singleton(CharSegment.class);
    }

    public static Segment huaBan() {
        return (Segment) Instances.singleton(HuaBanSegment.class);
    }

    public static Segment fastForward() {
        return (Segment) Instances.singleton(FastForwardSegment.class);
    }

    public static Segment twFastForward() {
        return (Segment) Instances.singleton(TwFastForwardSegment.class);
    }

    public static Segment defaults() {
        return fastForward();
    }

    public static Segment dataMapFastForward(IDataMap iDataMap) {
        return new DataMapFastForwardSegment(iDataMap);
    }

    public static Segment hkFastForward() {
        return new DataMapFastForwardSegment(DataMaps.hongKong());
    }

    public static Segment hkSelfFastForward() {
        return new DataMapFastForwardSegment(DataMaps.hongKongSelf());
    }

    public static Segment jpSelfFastForward() {
        return new DataMapFastForwardSegment(DataMaps.japanSelf());
    }

    public static Segment jpFastForward() {
        return new DataMapFastForwardSegment(DataMaps.japan());
    }
}
