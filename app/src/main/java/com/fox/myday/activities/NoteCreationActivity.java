package com.fox.myday.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fox.myday.R;
import com.fox.myday.databinding.ActivityNoteCreationBinding;
import com.fox.myday.interfaces.NoteCreationView;
import com.fox.myday.presenters.NoteCreationPresenter;

public class NoteCreationActivity extends AppCompatActivity implements View.OnClickListener, NoteCreationView {

    private LinearLayout linearLayoutColor, linearLayoutNoteContent;
    private EditText edtTitle;
    private EditText edtContent;
    private ImageButton btnSave;
    private TextView tvDateModify;
    private ImageButton btnDelete;
    private ImageButton btnMore;
    private NoteCreationPresenter noteCreationPresenter;
    private String action;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityNoteCreationBinding activityNoteCreationBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_creation);
        setContentView(R.layout.activity_note_creation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        Bundle bundle = getIntent().getExtras();
        action = bundle.getString("action");
        if(!action.equalsIgnoreCase("create")){
            btnSave.setEnabled(false);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(this);
            noteCreationPresenter.onInitDataModify(edtTitle, edtContent, tvDateModify, getIntent().getExtras().getInt("position"));
        }else{
            btnDelete.setVisibility(View.GONE);
            btnSave.setOnClickListener(this);
        }
        btnMore.setOnClickListener(this );
    }

    private void initViews(){
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        tvDateModify = findViewById(R.id.tvDateModify);
        btnDelete = findViewById(R.id.btnDelete);
        btnMore = findViewById(R.id.btnMore);
        noteCreationPresenter = new NoteCreationPresenter(this, NoteCreationActivity.this);
        linearLayoutColor = findViewById(R.id.linearLayoutColor);
        linearLayoutNoteContent = findViewById(R.id.linearLayoutNoteContent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!action.equalsIgnoreCase("create")){
            getMenuInflater().inflate(R.menu.menu_creation_note, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_edit){
            String title = edtTitle.getText().toString().trim();
            String content = edtContent.getText().toString().trim();
            int position = getIntent().getExtras().getInt("position");
            noteCreationPresenter.onEditNote(position, title, content);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                noteCreationPresenter.onCreateNote(edtTitle.getText().toString().trim(), edtContent.getText().toString().trim());
                break;
            case R.id.btnDelete:
                onConfirmDeleteNote(getIntent().getExtras().getInt("position"));
                break;
            case R.id.btnMore:
                if(!isOpen){
                    linearLayoutColor.setVisibility(View.VISIBLE);
                    isOpen = true;
                }else{
                    linearLayoutColor.setVisibility(View.GONE);
                    isOpen = false;
                }
                break;
        }
    }

    private void onConfirmDeleteNote(int id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmation");
        dialog.setMessage("Are you sure to delete this note ?");
        dialog.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
        dialog.setPositiveButton("Delete", (dialogInterface, i) -> noteCreationPresenter.onDeleteNote(id));
        dialog.show();
    }

    @Override
    public void onCreateNoteSuccess() {
        Toast.makeText(this, "Create note successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateNoteFail() {
        Toast.makeText(this, "Create note fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateNoteSuccess() {

    }

    @Override
    public void onUpdateNoteFail() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFail() {

    }

    @Override
    public void onNavigate() {
        startActivity(new Intent(NoteCreationActivity.this, NoteActivity.class));
        NoteCreationActivity.this.finish();
    }

    @Override
    public void onUpdateState() {
        linearLayoutNoteContent.clearFocus();
    }
}
