package com.xss.com.memorandum.widget.dynamicheight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/22 17:07
 */
public class DynamicHeightLinearLayout extends LinearLayout {

    private double mHeightRatio;

    public DynamicHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightLinearLayout(Context context) {
        super(context);
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mLeft = 0;
        int mTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // 取出当前子View的高和宽
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

//            Log.e("l", l + ", " + t+", "+r+","+b);
//            Log.e("wi", width + ", " + height);

            int mRight = mLeft + width;
            int mBottom = mTop + height;

            // 调用layout并床底计算过的参数为子view的布局
            child.layout(mLeft, mTop, r, b);
            mTop += height;
//            child.layout(l, t, r, b);
        }
    }
}
