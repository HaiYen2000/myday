package com.fox.myday.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;

public class NoteHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvContent;

    public NoteHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvContent = itemView.findViewById(R.id.tvContent);
    }
}
