package com.fox.myday.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fox.myday.R;
import com.fox.myday.daos.NoteDAO;
import com.fox.myday.fragments.TagFragment;
import com.fox.myday.interfaces.NoteCreationView;
import com.fox.myday.interfaces.TagView;
import com.fox.myday.presenters.NoteCreationPresenter;
import com.fox.myday.presenters.TagPresenter;


public class NoteCreationActivity extends AppCompatActivity implements View.OnClickListener, NoteCreationView, TagView {

    private LinearLayout linearLayoutColor, linearLayoutNoteContent;
    private TableLayout bottomToolbar;
    private ActionBar actionBar;
    private EditText edtTitle;
    private EditText edtContent;
    private ImageButton btnSave;
    private TextView tvDateModify;
    private ImageButton btnMore;
    private RadioGroup colorPickerRadioGroup;
    private NoteCreationPresenter noteCreationPresenter;
    private TagPresenter tagPresenter;
    private String action;
    private String noteColor;
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
            noteCreationPresenter.onInitDataModify(edtTitle, edtContent, tvDateModify, linearLayoutNoteContent, linearLayoutColor, bottomToolbar, getIntent().getExtras().getInt("position"));
            btnSave.setEnabled(false);
        }else{
            btnSave.setOnClickListener(this);
        }
        btnMore.setOnClickListener(this );
    }


    @SuppressLint("ResourceType")
    private void initViews(){
        actionBar = getSupportActionBar();
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnSave = findViewById(R.id.btnSave);
        tvDateModify = findViewById(R.id.tvDateModify);
        btnMore = findViewById(R.id.btnMore);
        noteCreationPresenter = new NoteCreationPresenter(this, NoteCreationActivity.this);
        tagPresenter = new TagPresenter(this, NoteCreationActivity.this);
        linearLayoutColor = findViewById(R.id.linearLayoutColor);
        linearLayoutNoteContent = findViewById(R.id.linearLayoutNoteContent);
        colorPickerRadioGroup = findViewById(R.id.color_picker_radio_group);
        bottomToolbar = findViewById(R.id.bottom_toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(action.equalsIgnoreCase("modify")){
            getMenuInflater().inflate(R.menu.menu_modification_note, menu);
            return true;
        }else if(action.equalsIgnoreCase("create")){
            getMenuInflater().inflate(R.menu.menu_creation_note, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_add_tag){

        }
        if(item.getItemId() == R.id.action_edit){
            String title = edtTitle.getText().toString().trim();
            String content = edtContent.getText().toString().trim();
            int position = getIntent().getExtras().getInt("position");
            String current_color = new NoteDAO(this).getAllNote().get(position).NOTE_BACKGROUND_COLOR;
            if(noteColor == null){
                noteColor = current_color;
            }
            noteCreationPresenter.onEditNote(position, title, content, noteColor);
            return true;
        }
        if(item.getItemId() == R.id.action_remove){
            onConfirmDeleteNote(getIntent().getExtras().getInt("position"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                if(noteColor == null){
                    noteColor = getResources().getString(R.color.colorNoteDefault);
                }
                noteCreationPresenter.onCreateNote(edtTitle.getText().toString().trim(), edtContent.getText().toString().trim(), noteColor);
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
                        getBackGroundColor();
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

    @SuppressLint("ResourceType")
    public void getBackGroundColor() {
        colorPickerRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.red_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteRed);
                    break;
                case R.id.orange_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteOrange);
                    break;
                case R.id.yellow_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteYellow);
                    break;
                case R.id.green_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteGreen);
                    break;
                case R.id.cyan_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteCyan);
                    break;
                case R.id.light_blue_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteLightBlue);
                    break;
                case R.id.dark_blue_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteDarkBlue);
                    break;
                case R.id.purple_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNotePurple);
                    break;
                case R.id.pink_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNotePink);
                    break;
                case R.id.brown_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteBrow);
                    break;
                case R.id.grey_color_checkbox:
                    noteColor = getResources().getString(R.color.colorNoteGrey);
                    break;
                case R.id.default_color_checkbox :
                default:
                    noteColor = getResources().getString(R.color.colorNoteDefault);
                    break;
            }
            noteCreationPresenter.setBackgroundColor(linearLayoutNoteContent, linearLayoutColor, bottomToolbar, noteColor);
        });
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

    @SuppressLint("NewApi")
    @Override
    public void onUpdateBackgroundColor() {
        int position = getIntent().getExtras().getInt("position");
        NoteDAO noteDAO = new NoteDAO(getApplicationContext());
        String color = noteDAO.getAllNote().get(position).NOTE_BACKGROUND_COLOR;
        noteCreationPresenter.setBackgroundColor(linearLayoutNoteContent, linearLayoutColor, bottomToolbar, color);
    }

    @Override
    public void onEmptyContent() {
        Toast.makeText(this, "Content can not be empty !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSameNoteData() {
        Toast.makeText(this, "Your note has not changed yet !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateTagSuccess() {

    }

    @Override
    public void onCreateTagFail() {

    }

    @Override
    public void onUpdateTagSuccess() {

    }

    @Override
    public void onUpdateTagFail() {

    }

    @Override
    public void onDeleteTagSuccess() {

    }

    @Override
    public void onDeleteTagFail() {

    }

    @Override
    public void onSameTagValue() {

    }

    @Override
    public void onEmptyTag() {

    }
}
