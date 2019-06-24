package com.trivago.cluecumber.constants;

import com.trivago.cluecumber.properties.PropertyManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChartConfiguration {

    public enum Type {bar, pie}

    private PropertyManager propertyManager;

    private String passedColorRgbaString;
    private String failedColorRgbaString;
    private String skippedColorRgbaString;

    @Inject
    public ChartConfiguration(final PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public String getPassedColorRgbaString() {
        if (passedColorRgbaString == null) {
            passedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorPassed());
        }
        return passedColorRgbaString;
    }

    public String getFailedColorRgbaString() {
        if (failedColorRgbaString == null) {
            failedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorFailed());
        }
        return failedColorRgbaString;
    }

    public String getSkippedColorRgbaString() {
        if (skippedColorRgbaString == null) {
            skippedColorRgbaString = getRgbaColorStringFromHex(propertyManager.getCustomStatusColorSkipped());
        }
        return skippedColorRgbaString;
    }

    private String getRgbaColorStringFromHex(final String hexColorString) {
        if (hexColorString == null || hexColorString.isEmpty()) {
            return "";
        }
        String hex = hexColorString;
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }
        final int[] rgbEntries = new int[3];
        for (int i = 0; i < 3; i++) {
            rgbEntries[i] = Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return String.format("rgba(%d, %d, %d, 1.000)", rgbEntries[0], rgbEntries[1], rgbEntries[2]);
    }
}
