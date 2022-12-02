package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.constants.PluginSettings;

@SuppressWarnings({"FieldMayBeFinal", "unused"})
public class StartPageCollection {
    private PluginSettings.StartPage startPage;
    private boolean redirectToFirstScenario;

    public StartPageCollection(PluginSettings.StartPage startPage, boolean redirectToFirstScenario) {
        this.startPage = startPage;
        this.redirectToFirstScenario = redirectToFirstScenario;
    }

    public PluginSettings.StartPage getStartPage() {
        return startPage;
    }

    public boolean isRedirectToFirstScenario() {
        return redirectToFirstScenario;
    }
}
