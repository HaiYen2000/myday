package com.fox.myday.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.fox.myday.R;
import com.fox.myday.daos.NoteDAO;
import com.fox.myday.databinding.ActivityNoteCreationBinding;
import com.fox.myday.fragments.TagFragment;
import com.fox.myday.interfaces.NoteCreationView;
import com.fox.myday.presenters.NoteCreationPresenter;

public class NoteCreationActivity extends AppCompatActivity implements View.OnClickListener, NoteCreationView {

    private LinearLayout linearLayoutColor, linearLayoutNoteContent;
    private EditText edtTitle;
    private EditText edtContent;
    private ImageButton btnSave;
    private TextView tvDateModify;
    private ImageButton btnMore;
    private NoteCreationPresenter noteCreationPresenter;
    private String action;
    private boolean isOpen = false;

    private static final int TAG_FRAGMENT_ID = 1010;
    private static final String TAG_FRAGMENT_TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityNoteCreationBinding activityNoteCreationBinding = DataBindingUtil.setContentView(this, R.layout.activity_note_creation);
        setContentView(R.layout.activity_note_creation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        Bundle bundle = getIntent().getExtras();
        action = bundle.getString("action");
        if (!action.equalsIgnoreCase("create")){
            noteCreationPresenter.onInitDataModify(edtTitle, edtContent, tvDateModify, getIntent().getExtras().getInt("position"));
            btnSave.setEnabled(false);
        }else{
            btnSave.setOnClickListener(this);
        }
        btnMore.setOnClickListener(this );
    }

    private void initViews(){
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        tvDateModify = findViewById(R.id.tvDateModify);
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
        if(item.getItemId() == R.id.action_remove){
            onConfirmDeleteNote(getIntent().getExtras().getInt("position"));
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
            case R.id.btnMore:
                setUpPopupMenu();
                break;
        }
    }

    private void setUpPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), btnMore);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_more, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.manager_tag:
                    FrameLayout frameLayout = new FrameLayout(getApplicationContext());
                    frameLayout.setId(TAG_FRAGMENT_ID);
                    setContentView(frameLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    TagFragment tagFragment = new TagFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(TAG_FRAGMENT_ID, tagFragment, TAG_FRAGMENT_TAG).addToBackStack(null).commit();
                    return true;
                case R.id.background_color:
                    if(!isOpen){
                        linearLayoutColor.setVisibility(View.VISIBLE);
                        isOpen = true;
                    }else{
                        linearLayoutColor.setVisibility(View.GONE);
                        isOpen = false;
                    }
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 0){
            super.onBackPressed();
        }else{
            getSupportFragmentManager().popBackStack();
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
        Toast.makeText(this, "Update note successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateNoteFail() {
        Toast.makeText(this, "Update note fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(this, "Delete note successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFail() {
        Toast.makeText(this, "Delete note fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNavigate() {
        startActivity(new Intent(NoteCreationActivity.this, NoteActivity.class));
        NoteCreationActivity.this.finish();
    }

    @Override
    public void onUpdateState() {
        linearLayoutNoteContent.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) NoteCreationActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(inputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        int position = getIntent().getExtras().getInt("position");
        NoteDAO noteDAO = new NoteDAO(getApplicationContext());
        noteCreationPresenter.onUpdateLastModification(tvDateModify, "Last modification on ", noteDAO.getModifiedDayOfWeek() + ", ", noteDAO.getAllNote().get(position).NOTE_MODIFIED_DATE);
    }
}
