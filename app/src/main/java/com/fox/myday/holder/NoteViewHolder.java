package com.fox.myday.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvContent;
    public CardView rowCardView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvContent = itemView.findViewById(R.id.tvContent);
        rowCardView = itemView.findViewById(R.id.rowCardView);
    }
}
