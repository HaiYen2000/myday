package com.fox.myday.presenters;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;
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

    public void onCreateNote(String title, String content){
        Note note = new Note(title, content, noteDAO.getCurrentTime());
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

    public void onEditNote(int id, String title, String content, String created_date){
        Note note = new Note(id, title, content, created_date, noteDAO.getCurrentTime());
        long result = noteDAO.updateNote(note);
        if(result != -1){
            noteCreationView.onUpdateNoteSuccess();
        }else{
            noteCreationView.onUpdateNoteFail();
        }
        noteCreationView.onUpdateState();
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

    public void onInitDataModify(EditText edtTitle, EditText edtContent, TextView tvDateModify, int pos){
        Note current_modify_note = noteDAO.getAllNote().get(pos);
        edtTitle.setText(current_modify_note.NOTE_TITLE);
        edtContent.setText(current_modify_note.NOTE_CONTENT);
        if(current_modify_note.NOTE_MODIFIED_DATE != null){
            tvDateModify.setText("Last modification on " + noteDAO.getModifiedDayOfWeek() +", " + current_modify_note.NOTE_MODIFIED_DATE);
        }
        tvDateModify.setText("Create on " + noteDAO.getCreatedDayOfWeek() + ", " + current_modify_note.NOTE_CREATED_DATE);
    }

}
