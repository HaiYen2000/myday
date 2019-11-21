package com.fox.myday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;
import com.fox.myday.holder.EventHolder;
import com.fox.myday.models.Event;
import com.fox.myday.models.Note;

import java.util.List;

public class StaggeredRecycleViewEventAdapter extends RecyclerView.Adapter<EventHolder> {
    private static final String TAG = "StaggeredRecycleViewAd";

    private List<Event> mEvents;
    private Context mContext;

    public StaggeredRecycleViewEventAdapter(List<Event> mEvents, Context mContext) {
        this.mEvents = mEvents;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Event event = mEvents.get(position);
        holder.tveventtitle.setText(event.EVENT_TITLE);
        holder.tveventcontent.setText(event.EVENT_CONTENT);
    }

    @Override
    public int getItemCount() {
        return (mEvents != null) ? mEvents.size() : 0;
    }
}
