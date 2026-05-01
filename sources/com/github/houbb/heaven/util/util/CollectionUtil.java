package com.github.houbb.heaven.util.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.support.condition.ICondition;
import com.github.houbb.heaven.support.filler.IFiller;
import com.github.houbb.heaven.support.filter.IFilter;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/* JADX INFO: loaded from: classes3.dex */
public final class CollectionUtil {
    public static final List EMPTY_LIST = Collections.emptyList();

    private CollectionUtil() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static List<String> arrayToList(String[] strArr) {
        if (ArrayUtil.isEmpty(strArr)) {
            return Guavas.newArrayList();
        }
        return Guavas.newArrayList(strArr);
    }

    public static String[] listToArray(List<String> list) {
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static List<String> trimCollection(Collection<String> collection) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        List<String> listNewArrayList = Guavas.newArrayList(collection.size());
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            listNewArrayList.add(it.next().trim());
        }
        return listNewArrayList;
    }

    public static <T, R> Collection<R> buildCollection(Collection<T> collection, IHandler<T, R> iHandler) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            R rHandle = iHandler.handle(it.next());
            if (ObjectUtil.isNotNull(rHandle)) {
                arrayList.add(rHandle);
            }
        }
        return arrayList;
    }

    public static <T, R> List<R> buildCollection(T[] tArr, IHandler<T, R> iHandler) {
        if (ArrayUtil.isEmpty(tArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(tArr.length);
        for (T t : tArr) {
            R rHandle = iHandler.handle(t);
            if (ObjectUtil.isNotNull(rHandle)) {
                arrayList.add(rHandle);
            }
        }
        return arrayList;
    }

    public static <T> void addArray(Collection<T> collection, T[] tArr) {
        if (ArrayUtil.isEmpty(tArr)) {
            return;
        }
        collection.addAll(Guavas.newArrayList(tArr));
    }

    public static <K, V> List<K> toList(Iterable<V> iterable, IHandler<? super V, K> iHandler) {
        if (ObjectUtil.isNull(iterable)) {
            return Collections.emptyList();
        }
        return toList(iterable.iterator(), iHandler);
    }

    public static <K, V> List<K> toList(Iterator<V> it, IHandler<? super V, K> iHandler) {
        if (ObjectUtil.isNull(it)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            arrayList.add(iHandler.handle(it.next()));
        }
        return arrayList;
    }

    public static <E> List<E> fillList(List<E> list, IFiller<E> iFiller) {
        if (ObjectUtil.isNull(list)) {
            return list;
        }
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            iFiller.fill(it.next());
        }
        return list;
    }

    public static List<String> splitByAnyBlank(String str) {
        if (StringUtil.isEmpty(str)) {
            return Collections.emptyList();
        }
        return Guavas.newArrayList(str.split("\\s+"));
    }

    public static <T> List<T> filterList(Collection<T> collection, IFilter<T> iFilter) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            if (!iFilter.filter(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T> List<T> conditionList(Collection<T> collection, ICondition<T> iCondition) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (T t : collection) {
            if (iCondition.condition(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static List<String> toStringList(List<?> list) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Object obj : list) {
            if (ObjectUtil.isNotNull(obj)) {
                arrayList.add(obj.toString());
            }
        }
        return arrayList;
    }

    public static <T> Optional<T> firstNotNullElem(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        for (T t : collection) {
            if (ObjectUtil.isNotNull(t)) {
                return Optional.m536of(t);
            }
        }
        return Optional.empty();
    }

    public static String join(Collection<?> collection, String str) {
        return StringUtil.join(collection, str);
    }

    public static String join(Collection<?> collection) {
        return StringUtil.join(collection, PunctuationConst.COMMA);
    }

    public static <E> void foreach(Collection<E> collection, IHandler<E, Void> iHandler) {
        if (isEmpty(collection)) {
            return;
        }
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            iHandler.handle(it.next());
        }
    }

    public static <E> void foreachPrint(Collection<E> collection) {
        foreach(collection, new IHandler<E, Void>() { // from class: com.github.houbb.heaven.util.util.CollectionUtil.1
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Void handle(E e) {
                System.out.println(e);
                return null;
            }
        });
    }

    public static <E> List<E> fill(int i, E e) {
        List<E> listNewArrayList = Guavas.newArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            listNewArrayList.add(e);
        }
        return listNewArrayList;
    }

    public static List<Integer> fill(int i, int i2) {
        List<Integer> listNewArrayList = Guavas.newArrayList(i);
        for (int i3 = 0; i3 < i; i3++) {
            listNewArrayList.add(Integer.valueOf(i3 + i2));
        }
        return listNewArrayList;
    }

    public static List<Integer> fill(int i) {
        return fill(i, 0);
    }

    public static <E> E getFirst(Collection<E> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        Iterator<E> it = collection.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public static <T> List<T> distinct(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        if (collection instanceof Set) {
            return new ArrayList(collection);
        }
        return new ArrayList(new LinkedHashSet(collection));
    }

    public static <T extends Comparable> List<T> distinctAndSort(Collection<T> collection) {
        return sort(distinct(collection));
    }

    public static <T extends Comparable> List<T> getRepeatList(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        List<T> listNewArrayList = Guavas.newArrayList();
        Set setNewHashSet = Guavas.newHashSet();
        for (T t : collection) {
            if (setNewHashSet.contains(t)) {
                listNewArrayList.add(t);
            }
            setNewHashSet.add(t);
        }
        return listNewArrayList;
    }

    public static <T extends Comparable> List<T> sort(List<T> list) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        Collections.sort(list);
        return new ArrayList(list);
    }

    public static int getStartIndex(int i, Collection<?> collection) {
        if (!isEmpty(collection) && i >= 0 && i <= collection.size() - 1) {
            return i;
        }
        return 0;
    }

    public static int getEndIndex(int i, Collection<?> collection) {
        if (isEmpty(collection)) {
            return 0;
        }
        int size = collection.size() - 1;
        return (i < 0 || i > size) ? size : i;
    }

    public static <E> List<E> union(Collection<E> collection, Collection<E> collection2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(collection);
        linkedHashSet.addAll(collection2);
        return new ArrayList(linkedHashSet);
    }

    public static <E> List<E> difference(Collection<E> collection, Collection<E> collection2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(collection);
        linkedHashSet.removeAll(collection2);
        return new ArrayList(linkedHashSet);
    }

    public static <E> List<E> interSection(Collection<E> collection, Collection<E> collection2) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (E e : collection) {
            if (collection2.contains(e)) {
                linkedHashSet.add(e);
            }
        }
        return new ArrayList(linkedHashSet);
    }

    public static boolean containAny(Collection<String> collection, Collection<String> collection2) {
        if (!isEmpty(collection) && !isEmpty(collection2)) {
            Iterator<String> it = collection2.iterator();
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getLast(List<String> list) {
        if (isEmpty(list)) {
            return "";
        }
        return list.get(list.size() - 1);
    }

    public static void setLast(List<String> list, String str) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (isEmpty(list)) {
            list.add(str);
        }
        list.set(list.size() - 1, str);
    }

    public static <T> List<T> getTopK(Collection<T> collection, int i) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        int iMin = Math.min(collection.size(), i);
        List<T> listNewArrayList = Guavas.newArrayList();
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            listNewArrayList.add(it.next());
            if (listNewArrayList.size() >= iMin) {
                break;
            }
        }
        return listNewArrayList;
    }

    public static List<String> replaceAll(Collection<String> collection, String str, String str2) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        List<String> listNewArrayList = Guavas.newArrayList(collection.size());
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            listNewArrayList.add(it.next().replaceAll(str, str2));
        }
        return listNewArrayList;
    }

    public static <E> List<E> subList(List<E> list, int i, int i2) {
        ArgUtil.notNegative(i, TypedValues.CycleType.S_WAVE_OFFSET);
        ArgUtil.notNegative(i2, "limit");
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int size = list.size();
        int iMin = Math.min(i, size);
        int iMin2 = Math.min(i2, size - iMin);
        List<E> listNewArrayList = Guavas.newArrayList(iMin2);
        for (int i3 = iMin; i3 < iMin + iMin2; i3++) {
            listNewArrayList.add(list.get(i3));
        }
        return listNewArrayList;
    }

    public static <E> E random(List<E> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(T... tArr) {
        return new ArrayList(Arrays.asList(tArr));
    }

    public static <T> List<T> copy(List<T> list) {
        return new ArrayList(list);
    }

    public static <T> T head(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static <T> T tail(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static <T> List<T> append(List<T> list, T t) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(t);
        return list;
    }

    public static <T> List<T> reverse(List<T> list, T t) {
        if (isEmpty(list)) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int size = list.size() - 1; size >= 0; size--) {
            arrayList.add(list.get(size));
        }
        list.add(t);
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> void addAll(Collection<T> collection, Collection<T> collection2) {
        if (isNotEmpty(collection2)) {
            collection.addAll(collection2);
        }
    }

    public static <T> T random(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        int iNextInt = new SecureRandom().nextInt(collection.size());
        int i = 0;
        for (T t : collection) {
            if (i >= iNextInt) {
                return t;
            }
            i++;
        }
        return null;
    }

    public static <T> List<T> pageList(List<T> list, int i, int i2) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        int i3 = (i - 1) * i2;
        int i4 = i * i2;
        if (i3 < 0) {
            i3 = 0;
        }
        int size = list.size();
        if (i4 >= size) {
            i4 = size - 1;
        }
        return Collections.unmodifiableList(list.subList(i3, i4));
    }

    public static <T> List<T> pageList(Collection<T> collection, int i, int i2) {
        if (isEmpty(collection)) {
            return Collections.emptyList();
        }
        int i3 = (i - 1) * i2;
        int i4 = i * i2;
        int i5 = 0;
        if (i3 < 0) {
            i3 = 0;
        }
        int size = collection.size();
        if (i4 >= size) {
            i4 = size - 1;
        }
        ArrayList arrayList = new ArrayList(i4 - i3);
        for (T t : collection) {
            if (i5 >= i3) {
                arrayList.add(t);
            }
            i5++;
            if (i5 >= i4) {
                break;
            }
        }
        return arrayList;
    }

    public static boolean contains(Collection<?> collection, Object obj) {
        if (isEmpty(collection)) {
            return false;
        }
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (it.next().equals(obj)) {
                return true;
            }
        }
        return false;
    }
}
