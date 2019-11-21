package com.fox.myday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;
import com.fox.myday.holder.NoteViewHolder;
import com.fox.myday.models.Note;

import java.util.List;

public class StaggeredRecycleViewNoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private static final String TAG = "StaggeredRecycleViewAd";

    private List<Note> mNotes;
    private Context mContext;

    public StaggeredRecycleViewNoteAdapter(List<Note> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.tvTitle.setText(note.NOTE_TITLE);
        holder.tvContent.setText(note.NOTE_CONTENT);
    }

    @Override
    public int getItemCount() {
        return (mNotes != null) ? mNotes.size() : 0;
    }

}
