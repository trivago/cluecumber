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
import com.trivago.cluecumber.engine.filesystem.FileIO;
import com.trivago.cluecumber.engine.properties.PropertyManager;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllExceptionsPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.AllScenariosPageCollection;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllExceptionsPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.renderering.AllScenariosPageRenderer;
import com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.ALL_EXCEPTIONS;
import static com.trivago.cluecumber.engine.rendering.pages.templates.TemplateEngine.Template.ALL_SCENARIOS;

/**
 * The visitor for exception related pages.
 */
@Singleton
public class ExceptionVisitor implements PageVisitor {

    private final FileIO fileIO;
    private final TemplateEngine templateEngine;
    private final PropertyManager propertyManager;
    private final AllExceptionsPageRenderer allExceptionsPageRenderer;
    private final AllScenariosPageRenderer allScenariosPageRenderer;

    /**
     * The constructor for dependency injection.
     *
     * @param fileIO                    The {@link FileIO} instance.
     * @param templateEngine            The Freemarker template engine.
     * @param propertyManager           The {@link PropertyManager} instance.
     * @param allExceptionsPageRenderer The renderer for exception pages.
     * @param allScenariosPageRenderer  The renderer for the scenario pages.
     */
    @Inject
    public ExceptionVisitor(
            final FileIO fileIO,
            final TemplateEngine templateEngine,
            final PropertyManager propertyManager,
            final AllExceptionsPageRenderer allExceptionsPageRenderer,
            final AllScenariosPageRenderer allScenariosPageRenderer
    ) {
        this.fileIO = fileIO;
        this.templateEngine = templateEngine;
        this.propertyManager = propertyManager;
        this.allExceptionsPageRenderer = allExceptionsPageRenderer;
        this.allScenariosPageRenderer = allScenariosPageRenderer;
    }

    /**
     * The main method that is called on this visitor.
     *
     * @param allScenariosPageCollection The scenarios page collection.
     * @throws CluecumberException Thrown on all errors.
     */
    @Override
    public void visit(final AllScenariosPageCollection allScenariosPageCollection) throws CluecumberException {
        AllExceptionsPageCollection allExceptionsPageCollection = new AllExceptionsPageCollection(
                allScenariosPageCollection.getReports(), propertyManager.getCustomPageTitle()
        );

        // All exceptions page
        fileIO.writeContentToFile(
                allExceptionsPageRenderer.getRenderedContent(
                        allExceptionsPageCollection,
                        templateEngine.getTemplate(ALL_EXCEPTIONS)
                ),
                propertyManager.getGeneratedHtmlReportDirectory() + "/" + Settings.PAGES_DIRECTORY + "/" +
                        Settings.EXCEPTION_SUMMARY_PAGE + Settings.HTML_FILE_EXTENSION);

        // Scenarios by exception pages
        for (String exception : allExceptionsPageCollection.getExceptions()) {
            fileIO.writeContentToFile(
                    allScenariosPageRenderer.getRenderedContentByExceptionFilter(
                            allScenariosPageCollection, templateEngine.getTemplate(ALL_SCENARIOS), exception),
                    propertyManager.getGeneratedHtmlReportDirectory() + "/" +
                            Settings.PAGES_DIRECTORY + Settings.EXCEPTION_SCENARIO_PAGE_FRAGMENT +
                            exception + Settings.HTML_FILE_EXTENSION);
        }
    }
}