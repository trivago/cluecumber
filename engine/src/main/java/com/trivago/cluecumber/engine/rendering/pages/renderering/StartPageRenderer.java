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
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.Link;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.StartPageCollection;
import freemarker.template.Template;

import javax.inject.Inject;
import java.util.List;

/**
 * The renderer for the index page that redirects to the chosen start page.
 */
public class StartPageRenderer extends PageRenderer {

    /**
     * Default constructor.
     */
    @Inject
    public StartPageRenderer() {
    }

    public String getRenderedContent(final Template template, final StartPageCollection startPageCollection, final List<Link> navigation)
            throws CluecumberException {
        return processedContent(template, startPageCollection, navigation);
    }
}
