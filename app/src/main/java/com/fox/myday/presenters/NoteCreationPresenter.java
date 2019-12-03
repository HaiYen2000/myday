package com.fox.myday.presenters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import com.fox.myday.daos.NoteDAO;
import com.fox.myday.interfaces.NoteCreationView;
import com.fox.myday.models.Note;

import static com.fox.myday.Constants.NAVIGATE_TIME_OUT;

public class NoteCreationPresenter {

    NoteCreationView noteCreationView;
    NoteDAO noteDAO;
    Context mContext;

    public NoteCreationPresenter(NoteCreationView noteCreationView, Context mContext) {
        this.noteCreationView = noteCreationView;
        this.mContext = mContext;
        noteDAO = new NoteDAO(mContext);
    }

    public void onCreateNote(String title, String content, String color){
        if(TextUtils.isEmpty(content)){
            noteCreationView.onEmptyContent();
            return;
        }else{
            Note note = new Note(title, content, color, noteDAO.getCurrentTime());
            long result = noteDAO.insertNote(note);
            if ((result != -1)) {
                noteCreationView.onCreateNoteSuccess();
            } else {
                noteCreationView.onCreateNoteFail();
            }
            new Handler().postDelayed(() -> {
                noteCreationView.onNavigate();
            },NAVIGATE_TIME_OUT);
        }
    }

    public void onEditNote(int pos, String title, String content, String color){
        if(TextUtils.isEmpty(content)){
            noteCreationView.onEmptyContent();
            return;
        }else if(content.equals(noteDAO.getAllNote().get(pos).NOTE_CONTENT)){
            noteCreationView.onSameNoteData();
            return;
        }else{
            int id = noteDAO.getAllNote().get(pos).NOTE_ID;
            String created_date = noteDAO.getAllNote().get(pos).NOTE_CREATED_DATE;
            Note note = new Note(id, title, content, color, created_date, noteDAO.getCurrentTime());
            long result = noteDAO.updateNote(note);
            if(result != -1){
                noteCreationView.onUpdateNoteSuccess();
            }else{
                noteCreationView.onUpdateNoteFail();
            }
            noteCreationView.onUpdateState();
            noteCreationView.onUpdateBackgroundColor();
        }
    }

    public void onDeleteNote(int id){
        long result = noteDAO.deleteNote(noteDAO.getAllNote().get(id).NOTE_ID);
        if(result != -1){
            noteCreationView.onDeleteSuccess();
        }else{
            noteCreationView.onDeleteFail();
        }
        new Handler().postDelayed(() -> {
            noteCreationView.onNavigate();
        }, NAVIGATE_TIME_OUT);
    }

    public void onInitDataModify(EditText edtTitle, EditText edtContent, TextView tvDateModify, LinearLayout l1, LinearLayout l2, TableLayout tb, int pos){
        Note current_modify_note = noteDAO.getAllNote().get(pos);
        edtTitle.setText(current_modify_note.NOTE_TITLE);
        edtContent.setText(current_modify_note.NOTE_CONTENT);
        setBackgroundColor(l1, l2, tb, current_modify_note.NOTE_BACKGROUND_COLOR);
        if(current_modify_note.NOTE_MODIFIED_DATE != null){
            onUpdateLastModification(tvDateModify, "Last modification on ", noteDAO.getModifiedDayOfWeek() + ", ", current_modify_note.NOTE_MODIFIED_DATE);
        }else{
            onUpdateLastModification(tvDateModify, "Create on ", noteDAO.getCreatedDayOfWeek() + ", ", current_modify_note.NOTE_CREATED_DATE);
        }
    }

    public String getColorString(int pos){
        return noteDAO.getAllNote().get(pos).NOTE_BACKGROUND_COLOR;
    }

    public void onUpdateLastModification(TextView tvDateModify, String pre_string, String day, String time){
        tvDateModify.setText(pre_string + day + time );
    }

    @SuppressLint("NewApi")
    public void setBackgroundColor(LinearLayout l1, LinearLayout l2, TableLayout tb, String color){
        l1.setBackgroundColor(Color.parseColor(color));
        l2.setBackgroundColor(Color.parseColor(color));
        tb.setBackgroundColor(Color.parseColor(color));
        //ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
        //activity.getWindow().setStatusBarColor(darkenNoteColor(Color.parseColor(color), 0.7f));
        //ib.setBackgroundColor(darkenNoteColor(Color.parseColor(color), 0.9f));
    }

//    private static int darkenNoteColor(int color, float factor) {
//        int a = Color.alpha(color);
//        int r = Math.round(Color.red(color) * factor);
//        int g = Math.round(Color.green(color) * factor);
//        int b = Math.round(Color.blue(color) * factor);
//        return Color.argb(a, Math.min(r,255), Math.min(g,255), Math.min(b,255));
//    }

}
