package com.fox.myday.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fox.myday.R;
import com.fox.myday.adapters.StaggeredRecycleViewEventAdapter;
import com.fox.myday.models.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AddEventFragment extends Fragment {

    public AddEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        return view;
    }

}
