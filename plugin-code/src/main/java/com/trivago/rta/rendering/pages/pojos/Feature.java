package com.trivago.rta.rendering.pages.pojos;

import java.util.Objects;

public class Feature {
    private String name;
    private int id;

    public Feature(final String name, final int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return id == feature.id &&
                Objects.equals(name, feature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
