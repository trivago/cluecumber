package com.trivago.rta.rendering.pages.pojos;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TagStatTest {
    private TagStat tagStat;

    @Before
    public void setup() {
        tagStat = new TagStat();
    }

    @Test
    public void addPassedTest() {
        tagStat.addPassed(3);
        assertThat(tagStat.getPassed(), is(3));
        assertThat(tagStat.getFailed(), is(0));
        assertThat(tagStat.getSkipped(), is(0));
    }
    @Test
    public void addFailedTest() {
        tagStat.addFailed(4);
        assertThat(tagStat.getPassed(), is(0));
        assertThat(tagStat.getFailed(), is(4));
        assertThat(tagStat.getSkipped(), is(0));
    }

    @Test
    public void addSkippedTest() {
        tagStat.addSkipped(5);
        assertThat(tagStat.getPassed(), is(0));
        assertThat(tagStat.getFailed(), is(0));
        assertThat(tagStat.getSkipped(), is(5));
    }
}
