package com.trivago.rta.rendering.pages.pojos;

import com.trivago.rta.json.pojo.Tag;

public class TagStats {
    private Tag tag;
    private int passed;
    private int failed;
    private int skipped;

    public TagStats(final Tag tag, final int passed, final int failed, final int skipped) {
        this.tag = tag;
        this.passed = passed;
        this.failed = failed;
        this.skipped = skipped;
    }
}
