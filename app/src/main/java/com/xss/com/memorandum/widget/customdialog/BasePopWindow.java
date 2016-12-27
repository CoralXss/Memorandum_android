package com.xss.com.memorandum.widget.customdialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/17 14:37
 */
public abstract class BasePopWindow {
    protected PopupWindow popupWindow;
    protected Context context;

    public BasePopWindow(Context context) {
        this.context = context;
        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        //设置背景色
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(drawable);

        //设置popWindow的内容
        setWindowContentView();
    }

    public abstract void setWindowContentView();

    protected void setWindowAttribute(int style, float width_rate, float height_rate) {
        popupWindow.setAnimationStyle(style);

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (width_rate != -1) {
            popupWindow.setWidth((int) (display.getWidth() * width_rate));
        }
        if (height_rate != -1) {
            popupWindow.setHeight((int) (display.getHeight() * height_rate));
        }
    }

    /**
     * 在View的上方显示
     * @param view
     */
    public void showAtViewTop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
    }

    /**
     * 在View的下方显示
     * @param view
     */
    public void showAtViewBottom(View view) {
        popupWindow.showAsDropDown(view);
    }

    /**
     * 在View的左边显示
     * @param view
     */
    public void showAtViewLeft(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth(), location[1]);
    }

    /**
     * 在View的右边显示
     * @param view
     */
    public void showAtViewRight(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] + popupWindow.getWidth(), location[1]);
    }
}
