package com.fox.myday.interfaces;

public interface NoteCreationView {

    void onCreateNoteSuccess();

    void onCreateNoteFail();

    void onUpdateNoteSuccess();

    void onUpdateNoteFail();

    void onDeleteSuccess();

    void onDeleteFail();

    void onNavigate();

    void onUpdateState();

}
