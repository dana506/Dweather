package com.example.dweather;

    import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The Interface provides the format for a API Call
 * @author Dana Buzatu
 */

public interface ApiWeather {

    // Api call url format for weather data
    @GET("forecast")
    Call<MeasurementResponse> getWeatherData(@Query("APPID") String apiKey,
                                           @Query("lat") Double latitude,
                                           @Query("lon") Double longitude,
                                           @Query("units") String uom);

    // Api call url format for weather location
    @GET("forecast")
    Call<MeasurementResponse> getWeatherDataByCity(@Query("APPID") String apiKey,
                                             @Query("q") String location,
                                             @Query("units") String uom);
}
