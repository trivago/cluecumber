/*
 * Copyright 2023 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trivago.cluecumber.engine.properties;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Properties extension that keeps the order of properties.
 */
public class LinkedProperties extends Properties {

    private static final long serialVersionUID = 1L;

    /**
     * This is the sorted hash map of properties.
     */
    private final Map<Object, Object> linkMap = new LinkedHashMap<>();

    /**
     * Default constructor.
     */
    public LinkedProperties() {
        // Default constructor
    }

    /**
     * Add a property.
     *
     * @param key   key with which the specified value is to be associated.
     * @param value value to be associated with the specified key.
     * @return the linked map including the new key value pair.
     */
    @Override
    public synchronized Object put(Object key, Object value) {
        return linkMap.put(key, value);
    }

    /**
     * Check if the property map contains a value.
     *
     * @param value a value to search for.
     * @return true if the value is in the property map.
     */
    @Override
    public synchronized boolean contains(Object value) {
        return linkMap.containsValue(value);
    }

    /**
     * Alias for the contains method.
     *
     * @param value value whose presence in this hashtable is to be tested.
     * @return true if the value is in the property map.
     */
    @Override
    public boolean containsValue(Object value) {
        return linkMap.containsValue(value);
    }

    /**
     * Not implemented.
     *
     * @return UnsupportedOperationException error.
     */
    @Override
    public synchronized Enumeration<Object> elements() {
        throw new UnsupportedOperationException(
                "Please use keySet() or entrySet() instead of Enumeration.");
    }

    /**
     * Get the properties as an {@link java.util.Map.Entry} set.
     *
     * @return The set of entries.
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkMap.entrySet();
    }

    /**
     * Clear the properties map.
     */
    @Override
    public synchronized void clear() {
        linkMap.clear();
    }

    /**
     * Check if the properties contain a specific key.
     *
     * @param key The possible key.
     * @return true if this key exists.
     */
    @Override
    public synchronized boolean containsKey(Object key) {
        return linkMap.containsKey(key);
    }

}
