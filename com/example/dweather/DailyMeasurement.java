package com.example.dweather;

/**
 * The Class instantiates daily details for weather in the named day
 * @author Dana Buzatu
 */

public class DailyMeasurement {
    String dayName;
    String iconUrl;
    Float maxTemp;
    Float minTemp;

    public DailyMeasurement(String dayName, String iconUrl, Float maxTemp, Float minTemp) {
        this.dayName = dayName;
        this.iconUrl = iconUrl;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }
}
