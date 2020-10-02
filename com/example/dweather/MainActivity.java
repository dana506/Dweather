package com.example.dweather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dweather.ShowLocationsActivity.SHOW_LOCATIONS_ACTIVITY;

/**
 * The Class supplies the hourly weather data and creates the views representing each data item achieving a list view concept
 * @author Dana Buzatu
 */

public class MainActivity extends AppCompatActivity {

    TextView dateTextView;
    TextView locationTextView;
    TextView temperatureTextView;
    TextView descriptionTextView;
    TextView windSpeedTextView;
    TextView humidityTextView;
    TextView pressureTextView;
    ImageView descriptionImageView;
    RecyclerView recyclerViewHorizontal;
    RecyclerView recyclerViewVertical;

    Retrofit retrofit;


    private FusedLocationProviderClient fusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private Location currentLocation;
    private Address currentAddress;
    Geocoder geocoder;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate app main components
        readLocation();
        dateTextView = findViewById(R.id.dateTextView);
        locationTextView = findViewById(R.id.locationTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionImageView = findViewById(R.id.descriptionImageView);
        windSpeedTextView = findViewById(R.id.windSpeedTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        pressureTextView = findViewById(R.id.pressureTextView);

        //scrolling horizontally for hourly info
        recyclerViewHorizontal = findViewById(R.id.recyclerViewHorizontal);
        recyclerViewHorizontal.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(layoutManager);

        //scrolling vertically for more exact weather information (s.a. humidity, wind speed, pressure)
        recyclerViewVertical = findViewById(R.id.recyclerViewVertical);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewVertical.setLayoutManager(layoutManager2);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //menu with buttons for adding and location and choosing desired location from app's database
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItemButton:
                Intent addLocation = new Intent(this, AddLocationActivity.class);
                startActivity(addLocation);
                return true;
            case R.id.showLocationsButton:
                Intent showLocations = new Intent(this, ShowLocationsActivity.class);
                startActivityForResult(showLocations, SHOW_LOCATIONS_ACTIVITY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SHOW_LOCATIONS_ACTIVITY: {
                if (resultCode == RESULT_OK) {
                    String city = data.getStringExtra("city");
                    String countryCode = data.getStringExtra("countryCode");

                    loadWeatherData(city, countryCode);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readLocation();
                }
            }
        }
    }

    private void loadWeatherData(String city, String countryCode) {
        ApiWeather apiWeather = retrofit.create(ApiWeather.class);
        locationTextView.setText(city);

        // make a request by calling the corresponding method

        Call<MeasurementResponse> measurementCall = apiWeather.getWeatherDataByCity("c18d98005d77546a460736db380e0740",
                city + "," + countryCode, "metric");


        measurementCall.enqueue(new Callback<MeasurementResponse>() {
            @Override
            public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                loadMeasurements(response.body().measurements);
            }

            @Override
            public void onFailure(Call<MeasurementResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong. Refresh.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //establishing the right format for date and weather measurments
    private void loadMeasurements(List<Measurement> measurements) {
        Date date = new Date();
        String dateValue = DateFormat.format("EEE dd MMM", date).toString();
        dateTextView.setText(dateValue);

        MeasurementAdapter adapter = new MeasurementAdapter(measurements);
        recyclerViewHorizontal.setAdapter(adapter);

        Measurement measurement = measurements.get(0);
        Integer temperature = Math.round(measurement.getMain().getTemperature());
        temperatureTextView.setText(temperature.toString()+ "°C");
        descriptionTextView.setText(measurement.getWeatherMeasurements().get(0).getDescription());
        Picasso.get().load(measurement.getWeatherMeasurements().get(0).getIcon()).into(descriptionImageView);

        windSpeedTextView.setText(measurement.getWind().getWindSpeed().toString());
        humidityTextView.setText(measurement.getMain().getHumidity().toString());
        pressureTextView.setText(measurement.getMain().getPressure().toString());

        HashMap<String, Float> maxTemps = new HashMap<>();
        HashMap<String, Float> minTemps = new HashMap<>();
        Map<String, String> iconMap = new LinkedHashMap<>();

        String today = DateFormat.format("EEEE", new Date()).toString();
        for(Measurement item : measurements) {
            String dayName = DateFormat.format("EEEE", item.getDate()).toString();
            String hour = DateFormat.format("HH", item.getDate()).toString();

            if (dayName.equals(today)) {
                continue;
            }

            Float maxTemp = item.getMain().getTemperature_max();
            Float minTemp = item.getMain().getTemperature_min();

            if (!maxTemps.containsKey(dayName) || maxTemps.get(dayName) < maxTemp) {
                maxTemps.put(dayName, maxTemp);
            }

            if (!minTemps.containsKey(dayName) || minTemps.get(dayName) > minTemp) {
                minTemps.put(dayName, minTemp);
            }

            if (hour.equals("15")) {
                iconMap.put(dayName, item.getWeatherMeasurements().get(0).getIcon());
            }
        }

        List<DailyMeasurement> dailyMeasurements = new ArrayList<>();

        for (Map.Entry<String, String> entry: iconMap.entrySet()) {
            String dayName = entry.getKey();
            Float maxTemp = maxTemps.get(dayName);
            Float minTemp = minTemps.get(dayName);
            String iconUrl = entry.getValue();

            dailyMeasurements.add(new DailyMeasurement(dayName, iconUrl, maxTemp, minTemp));
        }


        DailyMeasurementAdapter dailyMeasurementAdapter = new DailyMeasurementAdapter(dailyMeasurements);
        recyclerViewVertical.setAdapter(dailyMeasurementAdapter);
    }

    private void loadWeatherData(Location location) {

        // create instance of ApiWeather
        ApiWeather apiWeather = retrofit.create(ApiWeather.class);

        // make a request by calling the corresponding method
        Call<MeasurementResponse> measurementCall = apiWeather.getWeatherData("c18d98005d77546a460736db380e0740",
                location.getLatitude(), location.getLongitude(), "metric");

        measurementCall.enqueue(new Callback<MeasurementResponse>() {
            @Override
            public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                loadMeasurements(response.body().measurements);
            }

            @Override
            public void onFailure(Call<MeasurementResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong. Refresh.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void readLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            currentLocation = location;
                            loadWeatherData(currentLocation);
                            if (geocoder == null) {
                                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            }
                            try {
                                List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                currentAddress = addressList.get(0);

                                locationTextView.setText(currentAddress.getLocality());
                            } catch (Exception e) {

                            }

                        }
                    });
        }

    }
}
