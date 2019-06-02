package com.trivago.cluecumber.rendering.pages.visitors;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;

public interface Visitable {
    void accept(final PageVisitor visitor) throws CluecumberPluginException;
}
