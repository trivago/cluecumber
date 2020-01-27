package com.trivago.cluecumber.rendering.pages.pojos;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResultCountTest {
    private ResultCount tagStat;

    @Before
    public void setup() {
        tagStat = new ResultCount();
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

    @Test
    public void getTotalTest() {
        tagStat.addSkipped(100);
        tagStat.addFailed(20);
        tagStat.addPassed(7);
        assertThat(tagStat.getTotal(), is(127));
    }
}
