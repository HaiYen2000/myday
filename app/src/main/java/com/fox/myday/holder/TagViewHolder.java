package com.fox.myday.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;

public class TagViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTagName;

    public TagViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTagName = itemView.findViewById(R.id.tvTagName);
    }

}
