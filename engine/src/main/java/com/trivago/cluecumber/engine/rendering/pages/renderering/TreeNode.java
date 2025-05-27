package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.json.pojo.Element;
import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a node in a tree structure used for rendering features and their elements.
 * Each node can have children and associated features with their respective elements.
 */
public class TreeNode {
    private final String name;
    private final TreeMap<String, TreeNode> children = new TreeMap<>();
    private final Map<Feature, List<Element>> features;

    /**
     * Constructs a TreeNode with the specified name and features.
     *
     * @param name     The name of the node.
     * @param features A map of features associated with this node, where each feature maps to a list of elements.
     */
    public TreeNode(final String name, final Map<Feature, List<Element>> features) {
        this.name = name;
        this.features = features;
    }

    /**
     * Gets the name of this TreeNode.
     *
     * @return The name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the children of this TreeNode.
     *
     * @return A map of child nodes, where the key is the child's name and the value is the TreeNode itself.
     */
    public Map<String, TreeNode> getChildren() {
        return children;
    }

    /**
     * Gets the features associated with this TreeNode.
     *
     * @return A map where each key is a Feature and the value is a list of Elements associated with that feature.
     */
    public Map<Feature, List<Element>> getFeatures() {
        return features;
    }
}