package com.trivago.cluecumberCore.rendering.pages.visitors;

import com.trivago.cluecumberCore.exceptions.CluecumberPluginException;

public interface Visitable {
    void accept(final PageVisitor visitor) throws CluecumberPluginException;
}
