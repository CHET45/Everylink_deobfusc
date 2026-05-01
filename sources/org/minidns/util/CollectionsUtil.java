package org.minidns.util;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class CollectionsUtil {
    public static <T> T getRandomFrom(Set<T> set, Random random) {
        int iNextInt = random.nextInt(set.size());
        Iterator<T> it = set.iterator();
        for (int i = 0; i < iNextInt && it.hasNext(); i++) {
            it.next();
        }
        return it.next();
    }
}
