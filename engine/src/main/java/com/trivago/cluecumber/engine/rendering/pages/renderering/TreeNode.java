package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {
    private final String name;
    private final Map<String, TreeNode> children = new HashMap<>();
    private final Map<Feature, List<Element>> features;

    public TreeNode(final String name, final Map<Feature, List<Element>> features) {
        this.name = name;
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public Map<String, TreeNode> getChildren() {
        return children;
    }

    public Map<Feature, List<Element>> getFeatures() {
        return features;
    }
}