package com.xss.com.memorandum.widget.customdialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.ui.adapter.FontColorAdapter;
import com.xss.com.memorandum.ui.adapter.RecyclerDividerDecoration;

import java.util.ArrayList;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/17 11:01
 */
public class FontSetDialog extends BaseDialog {
    private SeekBar seekBar;
    private ImageView img_font_s, img_font_l;
    private RecyclerView rv_font_colors;

    private ArrayList<Integer> colors = null;
    private FontColorAdapter fontColorAdapter = null;

    private RecyclerDividerDecoration decoration;

    public FontSetDialog(Context context, boolean isCanClose) {
        super(context, isCanClose, R.style.dialogStyle);
    }

    @Override
    public void setDialogContent() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_font_set, null);
        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        img_font_s = (ImageView)v.findViewById(R.id.img_font_s);
        img_font_l = (ImageView)v.findViewById(R.id.img_font_l);
        rv_font_colors = (RecyclerView)v.findViewById(R.id.rv_font_colors);
        dialog.setContentView(v);

        setDialogAttributes(1f, 0.2f, Gravity.BOTTOM);

        setAdapter();
    }

    private void setAdapter() {
        //设置水平滚动的 item
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_font_colors.setLayoutManager(mLayoutManager);
        rv_font_colors.setHasFixedSize(true);

        setColors();
        if (fontColorAdapter == null) {
            fontColorAdapter = new FontColorAdapter(context);
        }

//        //设置item之间的间距
//        int item_space = context.getResources().getDimensionPixelOffset(R.dimen.marginPadding10);
//        if (decoration == null) {
//            decoration = new RecyclerDividerDecoration(item_space, colors.size());
//            rv_font_colors.addItemDecoration(decoration);
//        }

        fontColorAdapter.setItems(colors);
        rv_font_colors.setAdapter(fontColorAdapter);
    }

    private void setColors() {
        colors = new ArrayList<Integer>();
        colors.add(context.getResources().getColor(R.color.color_white));
        colors.add(context.getResources().getColor(R.color.color_red));
        colors.add(context.getResources().getColor(R.color.color_orange));
        colors.add(context.getResources().getColor(R.color.color_yellow));
        colors.add(context.getResources().getColor(R.color.color_green));
        colors.add(context.getResources().getColor(R.color.color_cyan));
        colors.add(context.getResources().getColor(R.color.color_blue));
        colors.add(context.getResources().getColor(R.color.color_purple));
        colors.add(context.getResources().getColor(R.color.color_gray));
        colors.add(context.getResources().getColor(R.color.color_dark));

        colors.add(context.getResources().getColor(R.color.color_cyan));
        colors.add(context.getResources().getColor(R.color.color_blue));
        colors.add(context.getResources().getColor(R.color.color_purple));
        colors.add(context.getResources().getColor(R.color.color_gray));
        colors.add(context.getResources().getColor(R.color.color_dark));
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public ImageView getImg_font_s() {
        return img_font_s;
    }

    public void setImg_font_s(ImageView img_font_s) {
        this.img_font_s = img_font_s;
    }

    public ImageView getImg_font_l() {
        return img_font_l;
    }

    public void setImg_font_l(ImageView img_font_l) {
        this.img_font_l = img_font_l;
    }

    public RecyclerView getRv_font_colors() {
        return rv_font_colors;
    }

    public void setRv_font_colors(RecyclerView rv_font_colors) {
        this.rv_font_colors = rv_font_colors;
    }
}
