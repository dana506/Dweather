package com.example.dweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display user's locations in app's database
 * @author Dana Buzatu
 */

public class ShowLocationsActivity extends AppCompatActivity {

    public static final int SHOW_LOCATIONS_ACTIVITY = 1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);

        recyclerView = findViewById(R.id.locationsRecyclerView);

        WeatherDbHelper helper = new WeatherDbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.query(LocationEntry.TABLE_NAME, null, null, null, null, null, null);

        //array list for the app's database location
        final List<LocationEntry> locationEntries = new ArrayList<>();
        while(cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_NAME_CITY));
            String country = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_NAME_COUNTRY));
            String countryCode = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_NAME_COUNTRY_CODE));

            locationEntries.add(new LocationEntry(city, country, countryCode));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LocationAdapter adapter = new LocationAdapter(locationEntries, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //intent connects information between activities
                Intent data = new Intent();
                data.putExtra("city", locationEntries.get(position).getCity());
                data.putExtra("countryCode", locationEntries.get(position).getCountryCode());
                setResult(RESULT_OK, data);
                finish();
            }
        });

        recyclerView.setAdapter(adapter);

    }
}
