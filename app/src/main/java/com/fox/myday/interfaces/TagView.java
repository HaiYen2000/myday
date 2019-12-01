package com.fox.myday.interfaces;

public interface TagView {

    void onCreateTagSuccess();

    void onCreateTagFail();

    void onUpdateTagSuccess();

    void onUpdateTagFail();

    void onDeleteTagSuccess();

    void onDeleteTagFail();

    void onSameTagValue();

    void onEmptyTag();

}
