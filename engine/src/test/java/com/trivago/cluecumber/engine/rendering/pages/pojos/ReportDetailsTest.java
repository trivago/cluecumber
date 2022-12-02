package com.trivago.cluecumber.engine.rendering.pages.pojos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportDetailsTest {
    private ReportDetails reportDetails;

    @BeforeEach
    public void setup() {
        reportDetails = new ReportDetails();
    }

    @Test
    public void getGeneratorNameTest() {
        assertTrue(reportDetails.getGeneratorName().startsWith("Cluecumber Report Plugin version"));
    }

    @Test
    public void reportDetailsDateTest() {
        ReportDetails reportDetails = new ReportDetails();
        assertNotNull(reportDetails.getDate());
    }
}
