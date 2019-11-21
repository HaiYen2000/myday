package com.fox.myday.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    public TextView itemDate;
    public TextView itemTemperature;
    public TextView itemDescription;
    public TextView itemyWind;
    public TextView itemPressure;
    public TextView itemHumidity;
    public TextView itemIcon;
    public View lineView;

    public WeatherViewHolder(View view) {
        super(view);
        this.itemDate = view.findViewById(R.id.itemDate);
        this.itemTemperature = view.findViewById(R.id.itemTemperature);
        this.itemDescription = view.findViewById(R.id.itemDescription);
        this.itemyWind = view.findViewById(R.id.itemWind);
        this.itemPressure = view.findViewById(R.id.itemPressure);
        this.itemHumidity = view.findViewById(R.id.itemHumidity);
        this.itemIcon = view.findViewById(R.id.itemIcon);
        this.lineView = view.findViewById(R.id.lineView);
    }

}
