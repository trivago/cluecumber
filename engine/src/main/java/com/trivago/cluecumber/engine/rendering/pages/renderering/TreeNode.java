package com.trivago.cluecumber.engine.rendering.pages.renderering;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    private final String name;
    private final boolean isFeatureFile;
    private final Map<String, TreeNode> children = new HashMap<>();

    public TreeNode(final String name, final boolean isFeatureFile) {
        this.name = name;
        this.isFeatureFile = isFeatureFile;
    }

    public String getName() {
        return name;
    }

    public boolean isFeatureFile() {
        return isFeatureFile;
    }

    public Map<String, TreeNode> getChildren() {
        return children;
    }
}