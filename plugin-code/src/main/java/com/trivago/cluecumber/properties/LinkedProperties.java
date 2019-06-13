package com.trivago.cluecumber.properties;

import java.util.*;

public class LinkedProperties extends Properties {

    private static final long serialVersionUID = 1L;

    private Map<Object, Object> linkMap = new LinkedHashMap<>();

    @Override
    public synchronized Object put(Object key, Object value) {
        return linkMap.put(key, value);
    }

    @Override
    public synchronized boolean contains(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return linkMap.containsValue(value);
    }

    @Override
    public synchronized Enumeration<Object> elements() {
        throw new UnsupportedOperationException(
                "Please use keySet() or entrySet() instead of Enumeration.");
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkMap.entrySet();
    }

    @Override
    public synchronized void clear() {
        linkMap.clear();
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return linkMap.containsKey(key);
    }

}
