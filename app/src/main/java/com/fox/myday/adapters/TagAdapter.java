package com.fox.myday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;
import com.fox.myday.holder.TagViewHolder;
import com.fox.myday.models.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {

    private List<Tag> mTags;
    private Context mContext;

    public TagAdapter(List<Tag> mTags, Context mContext) {
        this.mTags = mTags;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        Tag tag = mTags.get(position);
        holder.tvTagName.setText(tag.TAG_NAME);
    }

    @Override
    public int getItemCount() {
        return (mTags != null) ? mTags.size() : 0;
    }

    public void removeTagItem(int position){
        mTags.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreTagItem(Tag tag, int position){
        mTags.add(position, tag);
        notifyItemInserted(position);
    }

    public List<Tag> getAllTagData(){
        return mTags;
    }

}
