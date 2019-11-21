package com.fox.myday.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;

public class EventHolder extends RecyclerView.ViewHolder {
    public TextView tveventtitle;
    public TextView tveventcontent;


    public EventHolder(@NonNull View itemView) {
        super(itemView);
        tveventtitle = (TextView) itemView.findViewById(R.id.tveventtitle);
        tveventcontent = (TextView) itemView.findViewById(R.id.tveventcontent);
    }
}
