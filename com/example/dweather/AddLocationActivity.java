package com.example.dweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * The Class implements adding a new location that the user typed and wanted to save in the app's database
 * @author Dana Buzatu
 */

public class AddLocationActivity extends AppCompatActivity {

    TextView cityEditText;
    TextView countryEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        //user adds city and country information to set a new location
        cityEditText = findViewById(R.id.addCityEditText);
        countryEditText = findViewById(R.id.addCountryEditText);


        saveButton = findViewById(R.id.saveLocationButton);

        //adds the location typed by the user from Api database to user's database on Button Click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //transform in String the data entered by user
                String cityName = cityEditText.getText().toString();
                String countryName = countryEditText.getText().toString();

                String countryCode = getCountryCode(countryName);

                //extracts the weather data from API database for the user's added location
                WeatherDbHelper helper = new WeatherDbHelper(AddLocationActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                //location format for new location's Api Call
                ContentValues location = new ContentValues();
                location.put(LocationEntry.COLUMN_NAME_CITY, cityName);
                location.put(LocationEntry.COLUMN_NAME_COUNTRY, countryCode);
                location.put(LocationEntry.COLUMN_NAME_COUNTRY_CODE, countryCode);

                //new location inserted in user's database
                db.insert(LocationEntry.TABLE_NAME, null, location);
                finish();
            }
        });
    }

    private String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String code : isoCountryCodes) {
            Locale locale = new Locale("", code);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return code;
            }
        }
        return "";
    }
}
