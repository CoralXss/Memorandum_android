package com.xss.com.memorandum.widget.customdialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.xss.com.memorandum.R;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/23 14:55
 */
public class DelDataDialog extends BaseDialog {
    private Button btn_ok, btn_cancel;

    public DelDataDialog(Context context, boolean isCanClose) {
        super(context, isCanClose, R.style.dialogStyle);
    }

    @Override
    public void setDialogContent() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        dialog.setContentView(v);

        btn_ok = (Button) v.findViewById(R.id.btn_ok);
        btn_cancel = (Button)v.findViewById(R.id.btn_cancel);

        setDialogAttributes(0.85f, -1f, Gravity.CENTER);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public Button getBtn_ok() {
        return btn_ok;
    }

    public void setBtn_ok(Button btn_ok) {
        this.btn_ok = btn_ok;
    }
}
