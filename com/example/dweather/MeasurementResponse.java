package com.example.dweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * The Classes follow the Api's tree layout of objects to identify categories for each weather detail
 * @author Dana Buzatu
 */

public class MeasurementResponse {
    @SerializedName("list")
    public List<Measurement> measurements;
}



class Measurement {
    
    @SerializedName("dt")
    private Long time;

    @SerializedName("main")
    private MainMeasurement main;

    @SerializedName("weather")
    public List<WeatherMeasurement> weatherMeasurements;

    @SerializedName("wind")
    private WindMeasurement wind;

    public Long getTime() {
        return time;
    }

    public Date getDate(){
        return new Date(time*1000);
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public MainMeasurement getMain() {
        return main;
    }

    public void setMain(MainMeasurement main) {
        this.main = main;
    }

    public List<WeatherMeasurement> getWeatherMeasurements() {
        return weatherMeasurements;
    }

    public void setWeatherMeasurements(List<WeatherMeasurement> weatherMeasurements) {
        this.weatherMeasurements = weatherMeasurements;
    }

    public void setWind(WindMeasurement wind) {
        this.wind = wind;
    }

    public WindMeasurement getWind() {
        return wind;
    }
}
//declaration for weather details
class MainMeasurement {

    @SerializedName("temp")
    private Float temperature;

    @SerializedName("pressure")
    private Float pressure;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("temp_min")
    private Float temperature_min;

    @SerializedName("temp_max")
    private Float temperature_max;

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(Float temperature_min) {
        this.temperature_min = temperature_min;
    }

    public Float getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(Float temperature_max) {
        this.temperature_max = temperature_max;
    }
}


class WeatherMeasurement {

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String iconName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon(){
        return "https://openweathermap.org/img/wn/" + iconName + "@2x.png";
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}

class WindMeasurement{
    @SerializedName("speed")
    private Float windSpeed;

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }
}