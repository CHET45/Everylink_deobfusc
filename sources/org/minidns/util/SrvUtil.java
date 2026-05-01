package org.minidns.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import org.minidns.dnsname.DnsName;
import org.minidns.record.SRV;

/* JADX INFO: loaded from: classes4.dex */
public class SrvUtil {
    public static List<SRV> sortSrvRecords(Collection<SRV> collection) {
        int i;
        int i2;
        int iBisect;
        if (collection.size() == 1 && collection.iterator().next().target.equals(DnsName.ROOT)) {
            return Collections.emptyList();
        }
        TreeMap treeMap = new TreeMap();
        for (SRV srv : collection) {
            Integer numValueOf = Integer.valueOf(srv.priority);
            List linkedList = (List) treeMap.get(numValueOf);
            if (linkedList == null) {
                linkedList = new LinkedList();
                treeMap.put(numValueOf, linkedList);
            }
            linkedList.add(srv);
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (List list : treeMap.values()) {
            while (true) {
                int size = list.size();
                if (size > 0) {
                    int[] iArr = new int[size];
                    Iterator it = list.iterator();
                    while (true) {
                        i = 0;
                        if (!it.hasNext()) {
                            i2 = 1;
                            break;
                        }
                        if (((SRV) it.next()).weight > 0) {
                            i2 = 0;
                            break;
                        }
                    }
                    Iterator it2 = list.iterator();
                    int i3 = 0;
                    while (it2.hasNext()) {
                        i += ((SRV) it2.next()).weight + i2;
                        iArr[i3] = i;
                        i3++;
                    }
                    if (i == 0) {
                        iBisect = (int) (Math.random() * ((double) size));
                    } else {
                        iBisect = bisect(iArr, Math.random() * ((double) i));
                    }
                    arrayList.add((SRV) list.remove(iBisect));
                }
            }
        }
        return arrayList;
    }

    private static int bisect(int[] iArr, double d) {
        int length = iArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length && d >= iArr[i2]; i2++) {
            i++;
        }
        return i;
    }
}
