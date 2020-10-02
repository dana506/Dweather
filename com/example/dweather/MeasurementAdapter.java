package com.example.dweather;

import android.text.format.DateFormat;
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
 * The Class supplies the hourly weather data and creates the views representing each data item achieving a list view concept
 * @author Dana Buzatu
 */

public class  MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<Measurement> measurements;

    //describes the hourly weather data and views place in RecyclerView
    public static class MeasurementViewHolder extends RecyclerView.ViewHolder {
        TextView hourView;
        ImageView iconView;
        TextView temperatureView;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hourView = itemView.findViewById(R.id.hourTextView);
            this.iconView = itemView.findViewById(R.id.descriptionIconImageView);
            this.temperatureView = itemView.findViewById(R.id.tempTextView);
        }
    }

    public MeasurementAdapter(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    //instantiate layout XML file into its corresponding view
    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_hourly_item, parent, false);
        return new MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        Measurement measurement = measurements.get(position);
        String hour = DateFormat.format("HH", measurement.getDate()).toString();

        holder.hourView.setText(hour);

        Integer temperature = Math.round(measurement.getMain().getTemperature());

        holder.temperatureView.setText(temperature.toString() + "°C");
        Picasso.get().load(measurement.getWeatherMeasurements().get(0).getIcon()).into(holder.iconView);
    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }
}
