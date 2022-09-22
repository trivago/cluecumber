package com.trivago.cluecumber.constants;

import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.Link;
import com.trivago.cluecumber.rendering.pages.pojos.pagecollections.LinkType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Navigation {
    public static List<Link> internalLinks = Arrays.asList(
            new Link("scenario_summary", "pages/scenario-summary.html", LinkType.INTERNAL),
            new Link("scenario_sequence", "pages/scenario-sequence.html", LinkType.INTERNAL),
            new Link("tag_summary", "pages/tag-summary.html", LinkType.INTERNAL),
            new Link("step_summary", "pages/step-summary.html", LinkType.INTERNAL),
            new Link("feature_summary", "pages/feature-summary.html", LinkType.INTERNAL)
    );
}
