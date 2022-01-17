/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.renderering;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;
import com.trivago.cluecumber.properties.PropertyManager;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.StartPageCollection;
import freemarker.template.Template;

public class StartPageRenderer extends PageRenderer {

    public StartPageRenderer() {
    }

    public String getRenderedContent(final Template template, final StartPageCollection startPageCollection)
            throws CluecumberPluginException {
        return processedContent(template, startPageCollection);
    }
}
