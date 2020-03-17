package com.trivago.cluecumber.rendering.pages.pojos;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Requirement {
    private final Map<String, Requirement> children;
    private final Set<Feature> features;
    private final String name;
    private final String id;
    private final ResultCount count;

    private int level = 0;

    public Requirement(final String name, int level, String id) {
        this.name = name;
        this.level = level;
        this.id = id;
        count = new ResultCount();
        children = new HashMap<>();
        features = new HashSet<>();
    }

    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public String getId() {
         return id;
    }
    public Collection<Requirement> getChildren() {
        return children.values();
    }
    public ResultCount getCount() {
        return count;
    }

    public void addFeature(final Feature feature) {
        if (!features.contains(feature)) {
            features.add(feature);
        }
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    private Requirement getOrAddChild(final String name) {
        if (!children.containsKey(name)) {
            children.put(name, new Requirement(name, this.level + 1, id + "-" + children.size()));
        }
        return children.get(name);
    }

    // retrieve requirement from name
    // create it (and parent) if not exist
    private Requirement getSubrequirement(final String[] names) {
        Requirement req = this;
        for (final String name : names) {
            req = req.getOrAddChild(name);        
        }
        return req;
    }

    public Requirement getSubRequirement(final String uri) {
        String[] names = uri.split("/");
        if (names.length < 2) {
            // bad path to requirements, get root
            return this;
        }
        return getSubrequirement(Arrays.copyOfRange(names, 0, names.length - 1));
    }

	public void computeResultCount() {
        for (Requirement req: getChildren()) {
            req.computeResultCount();
            count.addFailed(req.count.getFailed());
            count.addPassed(req.count.getPassed());
            count.addSkipped(req.count.getSkipped());
        }
        for (Feature feature : features) {
            switch (feature.getStatus()) {
                case FAILED:
                    count.addFailed(1);
                    break;
                case PASSED:
                    count.addPassed(1);
                    break;
                case SKIPPED:
                    count.addSkipped(1);
                    break;
                default:
                    break;
            }
        }
	}

}

