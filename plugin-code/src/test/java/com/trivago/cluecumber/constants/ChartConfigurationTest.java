package com.trivago.cluecumber.constants;

import com.trivago.cluecumber.properties.PropertyManager;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChartConfigurationTest {

    private ChartConfiguration chartConfiguration;

    @Before
    public void setup(){
        PropertyManager propertyManager = mock(PropertyManager.class);
        when(propertyManager.getCustomStatusColorPassed()).thenReturn("#ff0000");
        when(propertyManager.getCustomStatusColorFailed()).thenReturn("#00ff00");
        when(propertyManager.getCustomStatusColorSkipped()).thenReturn("#00ffff");
        chartConfiguration = new ChartConfiguration(propertyManager);
    }

    @Test
    public void getPassedColorRgbaStringTest() {
        String color = chartConfiguration.getPassedColorRgbaString();
        assertThat(color, is("rgba(255, 0, 0, 1.000)"));
    }

    @Test
    public void getFailedColorRgbaStringTest() {
        String color = chartConfiguration.getFailedColorRgbaString();
        assertThat(color, is("rgba(0, 255, 0, 1.000)"));
    }

    @Test
    public void getSkippedColorRgbaStringTest() {
        String color = chartConfiguration.getSkippedColorRgbaString();
        assertThat(color, is("rgba(0, 255, 255, 1.000)"));
    }
}
