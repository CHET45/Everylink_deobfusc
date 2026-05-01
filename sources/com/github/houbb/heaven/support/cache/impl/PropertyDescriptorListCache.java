package com.github.houbb.heaven.support.cache.impl;

import com.github.houbb.heaven.support.cache.ICache;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.reflect.PropertyDescriptorUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyDescriptorListCache implements ICache<Class, List<PropertyDescriptor>> {
    private static final PropertyDescriptorListCache INSTANCE = new PropertyDescriptorListCache();
    private static final Map<Class, List<PropertyDescriptor>> MAP = new ConcurrentHashMap();
    private static final Map<Class, Map<String, Method>> READ_METHOD_MAP = new ConcurrentHashMap();

    public static PropertyDescriptorListCache getInstance() {
        return INSTANCE;
    }

    @Override // com.github.houbb.heaven.support.cache.ICache
    public List<PropertyDescriptor> get(Class cls) {
        List<PropertyDescriptor> list = MAP.get(cls);
        if (CollectionUtil.isNotEmpty(list)) {
            return list;
        }
        List<PropertyDescriptor> allPropertyDescriptorList = PropertyDescriptorUtil.getAllPropertyDescriptorList(cls);
        set(cls, allPropertyDescriptorList);
        return allPropertyDescriptorList;
    }

    @Override // com.github.houbb.heaven.support.cache.ICache
    public void set(Class cls, List<PropertyDescriptor> list) {
        MAP.put(cls, list);
    }

    public Map<String, Method> getReadMethodMap(Class cls) {
        Map<String, Method> map = READ_METHOD_MAP.get(cls);
        if (MapUtil.isNotEmpty(map)) {
            return map;
        }
        List<PropertyDescriptor> list = getInstance().get(cls);
        Map<String, Method> mapNewHashMap = Guavas.newHashMap(list.size());
        for (PropertyDescriptor propertyDescriptor : list) {
            mapNewHashMap.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
        }
        READ_METHOD_MAP.put(cls, mapNewHashMap);
        return mapNewHashMap;
    }
}
