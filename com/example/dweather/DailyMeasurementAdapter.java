package com.example.dweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * The Class supplies the daily weather data and creates the views representing each data item achieving a list view concept
 * @author Dana Buzatu
 */

public class DailyMeasurementAdapter extends RecyclerView.Adapter<DailyMeasurementAdapter.DailyMeasurementViewHolder> {

    private List<DailyMeasurement> dailyMeasurements;

    public DailyMeasurementAdapter(List<DailyMeasurement> dailyMeasurements) {
        this.dailyMeasurements = dailyMeasurements;
    }

    // describes the daily weather data and views place in RecyclerView
    public static class DailyMeasurementViewHolder extends RecyclerView.ViewHolder {

        TextView dayTextView;
        ImageView dayDescriptionImageView;
        TextView dayTempMaxTextView;
        TextView dayTempMinTextView;


        public DailyMeasurementViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dayTextView = itemView.findViewById(R.id.weatherDayTextView);
            this.dayDescriptionImageView = itemView.findViewById(R.id.weatherDayIconImageView);
            this.dayTempMaxTextView = itemView.findViewById(R.id.weatherDayTempMaxTextView);
            this.dayTempMinTextView = itemView.findViewById(R.id.weatherDayTempMinTextView);
        }
    }

    // instantiate layout XML file into its corresponding view
    @NonNull
    @Override
    public DailyMeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_item, parent, false);

        return new DailyMeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMeasurementViewHolder holder, int position) {
        DailyMeasurement measurement = dailyMeasurements.get(position);
        Integer minTemp = Math.round(measurement.minTemp); //rounds the min Temperature
        Integer maxTemp = Math.round(measurement.maxTemp); //rounds the max Temperature
        holder.dayTempMinTextView.setText(minTemp.toString() + "°C");
        holder.dayTempMaxTextView.setText(maxTemp.toString() + "°C");
        holder.dayTextView.setText(measurement.dayName);
        Picasso.get().load(measurement.iconUrl).into(holder.dayDescriptionImageView);
    }

    @Override
    public int getItemCount() {
        return dailyMeasurements.size();
    }
}
