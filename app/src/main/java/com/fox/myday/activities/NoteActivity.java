package com.fox.myday.activities;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.fox.myday.R;
import com.fox.myday.adapters.StaggeredRecycleViewNoteAdapter;
import com.fox.myday.base.BaseActivity;
import com.fox.myday.base.GridSpacingItemDecoration;
import com.fox.myday.models.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static com.fox.myday.Constants.NUM_COLUMNS;

public class NoteActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Note> mNotes;
    private StaggeredRecycleViewNoteAdapter staggeredRecycleViewNoteAdapter;
    private Button createNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_note, null, false);
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_note);
        setTitle(R.string.menu_note);
        initViews();
        initRecycleView();
        // Drag and drop
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);

        createNote.setOnClickListener(this);
    }

    private void initViews(){
        mNotes = new ArrayList<>();
        recyclerView = findViewById(R.id.note_recycle_view);
        createNote = findViewById(R.id.create_new_note);
    }

    private void initData(){
        Note note = new Note(1,"Test","abc", Calendar.getInstance().getTime().toString());
        Note note1 = new Note(2,"Test","abdâdadadadadac", Calendar.getInstance().getTime().toString());
        Note note2 = new Note(3,"Test","abadadadadadadc", Calendar.getInstance().getTime().toString());
        Note note3 = new Note(4,"Test","abdâdadadadadc", Calendar.getInstance().getTime().toString());
        Note note4 = new Note(5,"Test","abadadadadadadadc", Calendar.getInstance().getTime().toString());
        Note note5 = new Note(6,"Test","abadadadadadadadadadadadadadadadadacabadadadadadadadadadadadadadadadadacabadadadadadadadadadadadadadadadadacabadadadadadadadadadadadadadadadadac", Calendar.getInstance().getTime().toString());
        Note note6 = new Note(5,"Test","abadadadadadadadc", Calendar.getInstance().getTime().toString());
        Note note7 = new Note(5,"Test","abadadadadadadadc", Calendar.getInstance().getTime().toString());
        Note note8 = new Note(5,"Test","abadadadadadadadc", Calendar.getInstance().getTime().toString());
        Note note9 = new Note(5,"Test","abadadadadadadadc", Calendar.getInstance().getTime().toString());
        mNotes.add(note);
        mNotes.add(note1);
        mNotes.add(note2);
        mNotes.add(note3);
        mNotes.add(note4);
        mNotes.add(note5);
        mNotes.add(note6);
        mNotes.add(note7);
        mNotes.add(note8);
        mNotes.add(note9);
    }

    private void initRecycleView(){
        initData();
        recyclerView.setHasFixedSize(true);
        staggeredRecycleViewNoteAdapter = new StaggeredRecycleViewNoteAdapter(mNotes, NoteActivity.this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, 1);
        //Prevent the loss of items
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);

        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spacing));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecycleViewNoteAdapter);
    }

    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            Collections.swap(mNotes, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            staggeredRecycleViewNoteAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.create_new_note){
            Intent intent = new Intent(NoteActivity.this, NoteCreationActivity.class);
            startActivity(intent);
        }
    }
}
