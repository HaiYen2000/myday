package com.fox.myday.presenters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fox.myday.R;
import com.fox.myday.daos.TagDAO;
import com.fox.myday.interfaces.TagView;
import com.fox.myday.models.Tag;

import java.util.List;


public class TagPresenter {

    TagView tagView;
    TagDAO tagDAO;
    Context mContext;

    public TagPresenter(TagView tagView, Context mContext) {
        this.tagView = tagView;
        this.mContext = mContext;
        tagDAO = new TagDAO(mContext);
    }

    public List<Tag> onLoadAllTag(){
        return tagDAO.getAllTag();
    }

    public void onCreateTag(String name, String description){
        Tag tag = new Tag(name, description);
        long result = tagDAO.insertTag(tag);
        if ((result != -1)) {
            tagView.onCreateTagSuccess();
        } else {
            tagView.onCreateTagFail();
        }
    }

    public void onCreateTagWithId(int id, String name, String description){
        Tag tag = new Tag(id, name, description);
        long result = tagDAO.insertTagWithId(tag);
        if ((result != -1)) {
            tagView.onCreateTagSuccess();
        } else {
            tagView.onCreateTagFail();
        }
    }

    public void onEditTag(int pos, String name, String description){
        int id = tagDAO.getAllTag().get(pos).TAG_ID;
        Tag tag = new Tag(id, name, description);
        long result = tagDAO.updateTag(tag);
        if(result != -1){
            tagView.onUpdateTagSuccess();
        }else{
            tagView.onUpdateTagFail();
        }
    }

    public void onDeleteTag(int pos){
        long result = tagDAO.deleteTag(tagDAO.getAllTag().get(pos).TAG_ID);
        if(result != -1){
            tagView.onDeleteTagSuccess();
        }else{
            tagView.onDeleteTagFail();
        }
    }

    public int onCurrentTag(int pos){
        return tagDAO.getAllTag().get(pos).TAG_ID;
    }

}
