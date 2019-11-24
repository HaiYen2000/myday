package com.fox.myday.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FlexibleFrameLayout extends FrameLayout {

    private static final int[][] DRAW_ORDERS = new int[][]{
            {0, 1, 2},
            {2, 1, 0}
    };

    private int currentOrder;

    public FlexibleFrameLayout(@NonNull Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
    }

    public FlexibleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    public FlexibleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    @SuppressLint("NewApi")
    public FlexibleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setChildrenDrawingOrderEnabled(true);
    }

    public void setDrawOrder(int order) {
        currentOrder = order;
        invalidate();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return DRAW_ORDERS[currentOrder][i];
    }

}
