package com.fox.myday.interfaces;

public interface NoteView {

    void onLoadNoteFromFirebaseSuccess();

    void onLoadNoteFromFirebaseFail();

    void onSaveToFireBaseSuccess();

    void onSaveToFirebaseFail();

    void onDeleteFromFirebaseSuccess();

    void onDeleteFromFirebaseFail();

}
