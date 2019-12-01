package com.fox.myday.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fox.myday.R;
import com.fox.myday.adapters.TagAdapter;
import com.fox.myday.base.GridSpacingItemDecoration;
import com.fox.myday.base.SwipeToDeleteCallback;
import com.fox.myday.interfaces.TagView;
import com.fox.myday.listeners.RecyclerViewClickListener;
import com.fox.myday.listeners.RecyclerViewTouchListener;
import com.fox.myday.models.Tag;
import com.fox.myday.presenters.TagPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TagFragment extends Fragment implements TagView{

    private RecyclerView tag_recycle_view;
    private FloatingActionButton fab;
    private TagAdapter tagAdapter;
    private List<Tag> mTags;
    private TagPresenter tagPresenter;
    private CoordinatorLayout container;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_tag, container, false);
        setHasOptionsMenu(true);
        initViews(inflate);
        initRecycleView();
        //Handle onclick on item
        tag_recycle_view.addOnItemTouchListener(new RecyclerViewTouchListener(inflate.getContext(), tag_recycle_view, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int pos) {
                onOpenDialogUpdateTag(pos);
            }

            @Override
            public void onLongClick(View view, int pos) {

            }
        }));
        enableSwipeToDeleteAndUndo();
        // Drag and drop
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(tag_recycle_view);
        fab.setOnClickListener(view -> {
            onOpenDialogCreateTag();
        });
        return inflate;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }

    private void onOpenDialogUpdateTag(int pos) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_update_tag, null);
        dialog.setView(dialogView);
        Dialog dialog_update_tag = dialog.show();

        EditText edtTagName = dialogView.findViewById(R.id.edtTagName);
        EditText edtTagDescription = dialogView.findViewById(R.id.edtTagDescription);
        //Init saved data
        String current_tag_name = mTags.get(pos).TAG_NAME;
        String current_tag_description = mTags.get(pos).TAG_DESCRIPTION;
        edtTagName.setText(current_tag_name);
        edtTagDescription.setText(current_tag_description);
        Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(view -> {
            String new_tag_name = edtTagName.getText().toString().trim();
            String new_tag_description = edtTagDescription.getText().toString().trim();
            if(current_tag_name.equals(new_tag_name) && current_tag_description.equals(new_tag_description)){
                onSameTagValue();
            }else if(TextUtils.isEmpty(new_tag_name)){
                onEmptyTag();
                return;
            }else{
                tagPresenter.onEditTag(pos, new_tag_name, new_tag_description);
                dialog_update_tag.dismiss();
                initRecycleView();
            }
        });

        btnCancel.setOnClickListener(view -> dialog_update_tag.dismiss());
    }

    public void onOpenDialogCreateTag(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_create_tag, null);
        dialog.setView(dialogView);
        Dialog dialog_create_tag = dialog.show();

        EditText edtTagName = dialogView.findViewById(R.id.edtTagName);
        EditText edtTagDescription = dialogView.findViewById(R.id.edtTagDescription);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(view -> {
            if(TextUtils.isEmpty(edtTagName.getText().toString().trim())){
                onEmptyTag();
                return;
            }else{
                tagPresenter.onCreateTag(edtTagName.getText().toString().trim(), edtTagDescription.getText().toString().trim());
                dialog_create_tag.dismiss();
                initRecycleView();
            }
        });

        btnCancel.setOnClickListener(view -> dialog_create_tag.dismiss());
    }

    private void initViews(View view){
        container = view.findViewById(R.id.container);
        tag_recycle_view = view.findViewById(R.id.tag_recycle_view);
        fab = view.findViewById(R.id.fab);
        mTags = new ArrayList<>();
        tagPresenter = new TagPresenter(this , view.getContext());
    }

    private void initRecycleView(){
        initData();
        tag_recycle_view.setHasFixedSize(true);
        tagAdapter = new TagAdapter(mTags, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //Prevent the loss of items
        tag_recycle_view.getRecycledViewPool().setMaxRecycledViews(0, 0);
        final float scale = getResources().getDisplayMetrics().density;
        int spacing = (int) (1 * scale + 0.5f);
        tag_recycle_view.addItemDecoration(new GridSpacingItemDecoration(spacing));
        tag_recycle_view.setLayoutManager(linearLayoutManager);
        tag_recycle_view.setAdapter(tagAdapter);
    }

    private void initData() {
        mTags.clear();
        mTags = tagPresenter.onLoadAllTag();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Tag tag = tagAdapter.getAllTagData().get(position);
                int current_tag_id = tagPresenter.onCurrentTag(position);

                tagAdapter.removeTagItem(position);
                tagPresenter.onDeleteTag(position);

                Snackbar snackbar = Snackbar.make(container, "Tag was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> {
                    tagAdapter.restoreTagItem(tag, position);
                    tagPresenter.onCreateTagWithId(current_tag_id, tag.TAG_NAME, tag.TAG_DESCRIPTION);
                    tag_recycle_view.scrollToPosition(position);
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(tag_recycle_view);
    }

    ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            Collections.swap(mTags, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            tagAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //TODO
        }

        // Defines the enabled move directions in each state (idle, swiping, dragging).
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
        }
    };

    @Override
    public void onCreateTagSuccess() {
        Toast.makeText(getActivity(), "Create tag successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateTagFail() {
        Toast.makeText(getActivity(), "Create tag fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateTagSuccess() {
        Toast.makeText(getActivity(), "Update tag successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateTagFail() {
        Toast.makeText(getActivity(), "Update tag fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteTagSuccess() {
        Toast.makeText(getActivity(), "Delete tag successful !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteTagFail() {
        Toast.makeText(getActivity(), "Delete tag fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSameTagValue() {
        Toast.makeText(getActivity(), "Your tag has not changed yet !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyTag() {
        Toast.makeText(getActivity(), "Tag name can not be empty !", Toast.LENGTH_SHORT).show();
    }

}
