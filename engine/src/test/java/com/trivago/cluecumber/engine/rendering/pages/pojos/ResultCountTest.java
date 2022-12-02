package com.trivago.cluecumber.engine.rendering.pages.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultCountTest {
    private ResultCount tagStat;

    @BeforeEach
    public void setup() {
        tagStat = new ResultCount();
    }

    @Test
    public void addPassedTest() {
        tagStat.addPassed(3);
        assertEquals(tagStat.getPassed(), 3);
        assertEquals(tagStat.getFailed(), 0);
        assertEquals(tagStat.getSkipped(), 0);
    }

    @Test
    public void addFailedTest() {
        tagStat.addFailed(4);
        assertEquals(tagStat.getPassed(), 0);
        assertEquals(tagStat.getFailed(), 4);
        assertEquals(tagStat.getSkipped(), 0);
    }

    @Test
    public void addSkippedTest() {
        tagStat.addSkipped(5);
        assertEquals(tagStat.getPassed(), 0);
        assertEquals(tagStat.getFailed(), 0);
        assertEquals(tagStat.getSkipped(), 5);
    }

    @Test
    public void getTotalTest() {
        tagStat.addSkipped(100);
        tagStat.addFailed(20);
        tagStat.addPassed(7);
        assertEquals(tagStat.getTotal(), 127);
    }
}
