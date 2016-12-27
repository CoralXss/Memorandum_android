package com.xss.com.memorandum.widget.customdialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xss.com.memorandum.R;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/17 10:23
 */
public class InsertPictureDialog extends BaseDialog {
    private TextView tv_from_pictures, tv_from_files, tv_from_camera;

    public InsertPictureDialog(Context context, boolean isCanClose) {
        super(context, isCanClose, R.style.dialogStyle);
    }

    /**
     * 设置dialog显示大小和位置
     */
    public void setDialogContent() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_insert_picture, null);
        tv_from_pictures = (TextView)v.findViewById(R.id.tv_from_pictures);
        tv_from_files = (TextView)v.findViewById(R.id.tv_from_files);
        tv_from_camera = (TextView)v.findViewById(R.id.tv_from_camera);
        dialog.setContentView(v);

        setDialogAttributes(0.85f, -1f, Gravity.CENTER);
    }

    public TextView getTv_from_pictures() {
        return tv_from_pictures;
    }

    public TextView getTv_from_files() {
        return tv_from_files;
    }

    public TextView getTv_from_camera() {
        return tv_from_camera;
    }
}
