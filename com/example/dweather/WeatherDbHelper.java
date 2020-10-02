package com.example.dweather;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * The class creates databases for location and weather details
 * @author Dana Buzatu
 */

class LocationEntry implements BaseColumns {
    public static final String TABLE_NAME = "locations";
    public static final String COLUMN_NAME_CITY = "city";
    public static final String COLUMN_NAME_COUNTRY = "coutry";
    public static final String COLUMN_NAME_COUNTRY_CODE = "coutrycode";

    private String city;
    private String country;
    private String countryCode;

    public LocationEntry(String city, String country, String countryCode) {
        this.city = city;
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeatherDatabase.db";

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +
                LocationEntry._ID + " INTEGER PRIMARY KEY," +
                LocationEntry.COLUMN_NAME_CITY + " TEXT," +
                LocationEntry.COLUMN_NAME_COUNTRY + " TEXT, " +
                LocationEntry.COLUMN_NAME_COUNTRY_CODE + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
