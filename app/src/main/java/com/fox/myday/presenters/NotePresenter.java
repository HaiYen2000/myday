package com.fox.myday.presenters;

import android.content.Context;

import com.fox.myday.daos.NoteDAO;
import com.fox.myday.interfaces.NoteView;
import com.fox.myday.models.Note;

import java.util.List;

public class NotePresenter {

    private Context mContext;
    private NoteView noteView;
    private NoteDAO noteDAO;

    public NotePresenter(NoteView noteView, Context mContext) {
        this.noteView = noteView;
        this.mContext = mContext;
        this.noteDAO = new NoteDAO(mContext);
    }

    public List<Note> onLoadAllNote(){
        return noteDAO.getAllNote();
    }

}
