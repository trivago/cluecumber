package com.trivago.rta.json.pojo;

public class Match {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Match{" +
                "location='" + location + '\'' +
                '}';
    }
}
