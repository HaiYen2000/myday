package com.fox.myday.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fox.myday.R;
import com.fox.myday.activities.NoteActivity;
import com.fox.myday.adapters.StaggeredRecycleViewEventAdapter;
import com.fox.myday.adapters.StaggeredRecycleViewNoteAdapter;
import com.fox.myday.base.BaseActivity;
import com.fox.myday.base.GridSpacingItemDecoration;
import com.fox.myday.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fox.myday.Constants.NUM_COLUMNS;


public class SimpleEventsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Event> mEvent;
    FloatingActionButton fab;
    private StaggeredRecycleViewEventAdapter staggeredRecycleViewEventAdapter;


    public SimpleEventsListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.fragment_simple_events_list, container, false);
        mEvent = new ArrayList<>();
        recyclerView = view.findViewById(R.id.note_recycle_view);
        fab = view.findViewById(R.id.fabaddevent);
        recyclerView = view.findViewById(R.id.rcvevents);
        initRecycleView();
        initData();
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);

        return view;

    }

    private void initData() {

    }

    private void initRecycleView() {
        initData();
        recyclerView.setHasFixedSize(true);
        staggeredRecycleViewEventAdapter = new StaggeredRecycleViewEventAdapter(mEvent, getContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, 1);
        //Prevent the loss of items
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);

        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecycleViewEventAdapter);

    }

    //Class nay de drag and drop event ko can su dung
    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            Collections.swap(mEvent, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            staggeredRecycleViewEventAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //TODO

        }

        // Defines the enabled move directions in each state (idle, swiping, dragging).
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
        }
    };


}
