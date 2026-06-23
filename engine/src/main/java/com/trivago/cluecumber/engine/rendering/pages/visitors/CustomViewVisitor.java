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
package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.constants.Settings;
import com.trivago.cluecumber.engine.exceptions.CluecumberException;
import com.trivago.cluecumber.engine.exceptions.filesystem.PathCreationException;
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.filesystem.FileSystemManager;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.CustomView;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.CustomViewPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.CUSTOM_VIEW;

/**
 * The visitor for custom embedded view pages.
 */
@Singleton
public class CustomViewVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final FileSystemManager fileSystemManager;
    private final CustomViewPageRenderer customViewPageRenderer;

    /**
     * The constructor for dependency injection.
     *
     * @param fileIO                  The {@link FileIO} instance.
     * @param templateEngine          The Pebble template engine.
     * @param propertyManager         The {@link PropertyManager} instance.
     * @param fileSystemManager       The {@link FileSystemManager} instance.
     * @param customViewPageRenderer  The renderer for custom embedded view pages.
     */
    @Inject
    public CustomViewVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final FileSystemManager fileSystemManager,
            final CustomViewPageRenderer customViewPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.fileSystemManager = fileSystemManager;
        this.customViewPageRenderer = customViewPageRenderer;
    }

    /**
     * The main method that is called on this visitor.
     *
     * @param allScenariosPageCollection The scenarios page collection.
     * @throws CluecumberException Thrown on all errors.
     */
    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        List<CustomView> customViews = propertyManager.getCustomViews();
        if (customViews.isEmpty()) {
            return;
        }

        String pagesDirectory = propertyManager.getGeneratedHtmlReportDirectory()
                + "/" + Settings.PAGES_DIRECTORY + "/";
        try {
            fileSystemManager.createDirectory(pagesDirectory + Settings.CUSTOM_VIEW_PAGE_PATH);
        } catch (PathCreationException e) {
            throw new CluecumberException("Could not create custom view directory: " + e.getMessage());
        }

        for (CustomView customView : customViews) {
            fileIO.writeContentToFile(
                    customViewPageRenderer.getRenderedContent(customView, templateEngine.getTemplate(CUSTOM_VIEW)),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" + Settings.PAGES_DIRECTORY
                            + Settings.CUSTOM_VIEW_PAGE_FRAGMENT + customView.getSlug()
                            + Settings.HTML_FILE_EXTENSION
            );
        }
    }
}
