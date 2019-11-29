package com.fox.myday.activities;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fox.myday.R;
import com.fox.myday.adapters.StaggeredRecycleViewNoteAdapter;
import com.fox.myday.base.BaseActivity;
import com.fox.myday.base.GridSpacingItemDecoration;
import com.fox.myday.databinding.ActivityNoteBinding;
import com.fox.myday.interfaces.NoteView;
import com.fox.myday.listeners.RecyclerViewClickListener;
import com.fox.myday.listeners.RecyclerViewTouchListener;
import com.fox.myday.models.Note;
import com.fox.myday.presenters.NotePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fox.myday.Constants.NUM_COLUMNS;

public class NoteActivity extends BaseActivity implements NoteView,View.OnClickListener {

    private RecyclerView recyclerView;
    private List<Note> mNotes;
    private StaggeredRecycleViewNoteAdapter staggeredRecycleViewNoteAdapter;
    private Button createNote;
    private NotePresenter notePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        ActivityNoteBinding activityNoteBinding = DataBindingUtil.inflate(inflater, R.layout.activity_note, null, false);
        View contentView = activityNoteBinding.getRoot();
        drawerLayout.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_note);
        setTitle(R.string.menu_note);
        initViews();
        initRecycleView();

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Intent intent = new Intent(NoteActivity.this, NoteCreationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("action","modify");
                bundle.putInt("position",pos);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int pos) {

            }
        }));

        // Drag and drop
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(recyclerView);

        createNote.setOnClickListener(this);
    }

    private void initViews(){
        mNotes = new ArrayList<>();
        recyclerView = findViewById(R.id.note_recycle_view);
        createNote = findViewById(R.id.create_new_note);
        notePresenter = new NotePresenter(this, NoteActivity.this);
    }

    private void initData(){
        mNotes = notePresenter.onLoadAllNote();
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
            Bundle bundle = new Bundle();
            bundle.putString("action","create");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);

        MenuItem menuItem = menu.findItem( R.id.action_search);
        SearchManager searchManager = (SearchManager) NoteActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (menuItem != null) {
            searchView = (SearchView) menuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(NoteActivity.this.getComponentName()));
        }
        SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Toast.makeText(NoteActivity.this, "You search " + query, Toast.LENGTH_SHORT).show();
                if(!finalSearchView.isIconified()) {
                    finalSearchView.setIconified(true);
                }
                menuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //Toast.makeText(NoteActivity.this, "You search " + s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onLoadNoteFromFirebaseSuccess() {

    }

    @Override
    public void onLoadNoteFromFirebaseFail() {

    }

    @Override
    public void onSaveToFireBaseSuccess() {

    }

    @Override
    public void onSaveToFirebaseFail() {

    }

    @Override
    public void onDeleteFromFirebaseSuccess() {

    }

    @Override
    public void onDeleteFromFirebaseFail() {

    }

}
