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
package com.trivago.cluecumber.engine.rendering.pages.templates;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds Pebble template contexts from page model objects.
 */
public final class TemplateContextFactory {

    /**
     * Default constructor.
     */
    private TemplateContextFactory() {
        // Utility class
    }

    /**
     * Create a Pebble context map from a model object.
     * Bean properties are exposed at the root level; the model itself is available as {@code _model}
     * for method invocations that are not simple property getters.
     *
     * @param model The model object.
     * @return The context map.
     * @throws CluecumberException Thrown when the context cannot be built.
     */
    public static Map<String, Object> create(final Object model) throws CluecumberException {
        final Map<String, Object> context = new HashMap<>();
        context.put("_model", model);

        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(model.getClass(), Object.class);
            for (final PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                final Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null && !"class".equals(propertyDescriptor.getName())) {
                    context.put(propertyDescriptor.getName(), readMethod.invoke(model));
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new CluecumberException("Failed to build template context: " + e.getMessage());
        }

        return context;
    }
}
