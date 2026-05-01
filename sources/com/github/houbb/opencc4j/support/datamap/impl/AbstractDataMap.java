package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p010io.StreamUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractDataMap implements IDataMap {
    private volatile Set<String> tSet = new HashSet();
    private volatile Set<String> sSet = new HashSet();

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Set<String> tChars() {
        if (CollectionUtil.isNotEmpty(this.tSet)) {
            return this.tSet;
        }
        if (CollectionUtil.isEmpty(this.tSet)) {
            synchronized (this.tSet) {
                if (CollectionUtil.isEmpty(this.tSet)) {
                    addCharToSet(this.tSet, tsPhrase().keySet());
                    addCharToSet(this.tSet, tsChar().keySet());
                    Iterator<Map.Entry<String, List<String>>> it = stPhrase().entrySet().iterator();
                    while (it.hasNext()) {
                        addCharToSet(this.tSet, it.next().getValue());
                    }
                    Iterator<Map.Entry<String, List<String>>> it2 = stChar().entrySet().iterator();
                    while (it2.hasNext()) {
                        addCharToSet(this.tSet, it2.next().getValue());
                    }
                    Iterator<String> it3 = StreamUtil.readAllLines("/data/dictionary/tc.txt").iterator();
                    while (it3.hasNext()) {
                        this.tSet.addAll(StringUtil.toCharStringList(it3.next()));
                    }
                }
            }
        }
        return this.tSet;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Set<String> sChars() {
        if (CollectionUtil.isNotEmpty(this.sSet)) {
            return this.sSet;
        }
        if (CollectionUtil.isEmpty(this.sSet)) {
            synchronized (this.sSet) {
                if (CollectionUtil.isEmpty(this.sSet)) {
                    Iterator<Map.Entry<String, List<String>>> it = tsPhrase().entrySet().iterator();
                    while (it.hasNext()) {
                        addCharToSet(this.sSet, it.next().getValue());
                    }
                    Iterator<Map.Entry<String, List<String>>> it2 = tsChar().entrySet().iterator();
                    while (it2.hasNext()) {
                        addCharToSet(this.sSet, it2.next().getValue());
                    }
                    addCharToSet(this.sSet, stPhrase().keySet());
                    addCharToSet(this.sSet, stChar().keySet());
                    Iterator<String> it3 = StreamUtil.readAllLines("/data/dictionary/sc.txt").iterator();
                    while (it3.hasNext()) {
                        this.sSet.addAll(StringUtil.toCharStringList(it3.next()));
                    }
                }
            }
        }
        return this.sSet;
    }

    protected void addCharToSet(Set<String> set, Collection<String> collection) {
        if (CollectionUtil.isEmpty(collection)) {
            return;
        }
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            addCharToSet(set, it.next());
        }
    }

    protected void addCharToSet(Set<String> set, String str) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        for (char c : str.toCharArray()) {
            set.add(c + "");
        }
    }
}
