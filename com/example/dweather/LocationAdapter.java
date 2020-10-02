package com.example.dweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The Class supplies the location data and creates the views representing each data item achieving a list view concept
 * @author Dana Buzatu
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<LocationEntry> locationEntries;
    CustomItemClickListener listener;

    public LocationAdapter(List<LocationEntry> locationEntries, CustomItemClickListener listener) {
        this.locationEntries = locationEntries;
        this.listener = listener;
    }

    // describes the location data and temperature for that location and their view place in RecyclerView
    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView;
        TextView temperatureTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            locationTextView = itemView.findViewById(R.id.itemLocationTextView);
            temperatureTextView = itemView.findViewById(R.id.itemTemperatureTextView);
        }
    }

    // instantiate layout XML file into its corresponding view
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        final LocationViewHolder viewHolder = new LocationViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationEntry locationEntry = locationEntries.get(position);

        holder.locationTextView.setText(locationEntry.getCity());
    }

    @Override
    public int getItemCount() {
        return locationEntries.size();
    }
}
