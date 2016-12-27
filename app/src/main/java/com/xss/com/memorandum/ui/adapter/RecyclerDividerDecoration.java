package com.xss.com.memorandum.ui.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xss.com.memorandum.utils.Constants;

/**
 * Desc：实现item之间的间距
 * Author: xss
 * Time：2016/2/16 17:19
 */
public class RecyclerDividerDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int item_count;
    private int show_mode;

    public RecyclerDividerDecoration(int space, int item_count) {
        this.space = space;
        this.item_count = item_count;
    }

    public void setShow_mode(int show_mode) {
        this.show_mode = show_mode;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        Log.e("space", parent.getChildCount() + ", " + parent.getChildPosition(view));

        if (parent.getChildPosition(view) != 0) {
            outRect.top = space;
        }

        outRect.top = space;

        outRect.right = space;
        if (show_mode == Constants.SHOW_AS_CARD) {
            if (parent.getChildPosition(view) % 2 == 0) {
                outRect.left = space;
            }
        } else {
            outRect.left = space;
        }

        if (parent.getChildPosition(view) == (item_count - 1)) {
            outRect.bottom = space;
        }
    }
}
