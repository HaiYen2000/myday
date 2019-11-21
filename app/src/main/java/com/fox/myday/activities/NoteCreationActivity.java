package com.fox.myday.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fox.myday.R;

public class NoteCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
