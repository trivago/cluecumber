package com.trivago.rta.rendering.pages.pojos;

import java.util.Objects;

public class Feature {
    private String name;
    private int index;

    public Feature(final String name, final int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", index='" + index + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return index == feature.index &&
                Objects.equals(name, feature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }
}
