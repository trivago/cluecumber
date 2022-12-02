package com.trivago.cluecumber.engine.constants;

import com.trivago.cluecumber.engine.properties.PropertyManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChartConfigurationTest {

    private ChartConfiguration chartConfiguration;

    @BeforeEach
    public void setup() {
        PropertyManager propertyManager = mock(PropertyManager.class);
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("#ff0000");
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("#00ff00");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("#00ffff");
        chartConfiguration = new ChartConfiguration(propertyManager);
    }

    @Test
    public void getPassedColorRgbaStringTest() {
        String color = chartConfiguration.getPassedColorRgbaString();
        assertEquals(color, "rgba(255, 0, 0, 1.000)");
    }

    @Test
    public void getFailedColorRgbaStringTest() {
        String color = chartConfiguration.getFailedColorRgbaString();
        assertEquals(color, "rgba(0, 255, 0, 1.000)");
    }

    @Test
    public void getSkippedColorRgbaStringTest() {
        String color = chartConfiguration.getSkippedColorRgbaString();
        assertEquals(color, "rgba(0, 255, 255, 1.000)");
    }
}
