package com.xss.com.memorandum.widget.customdialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.cache.preference.SharedPrefUtil;
import com.xss.com.memorandum.ui.adapter.BaseRecyclerAdapter;
import com.xss.com.memorandum.ui.adapter.FontColorAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/17 14:24
 */
public class FontSetPopWindow extends BasePopWindow {
    private SeekBar seekBar;
    private ImageView img_font_s, img_font_l;
    private RecyclerView rv_font_colors;
    private ArrayList<Integer> colors = null;
    private FontColorAdapter fontColorAdapter = null;

    private ArrayList<Map<Integer, Boolean>> color_list = null;

    public FontSetPopWindow(Context context) {
        super(context);
    }

    @Override
    public void setWindowContentView() {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_font_set, null);
        popupWindow.setContentView(v);

        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        img_font_s = (ImageView)v.findViewById(R.id.img_font_s);
        img_font_l = (ImageView)v.findViewById(R.id.img_font_l);
        rv_font_colors = (RecyclerView)v.findViewById(R.id.rv_font_colors);

        setWindowAttribute(R.style.popWindowAnimFromBottomStyle, 1f, 0.2f);
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
        fontColorAdapter.setChecked(checked);
//        fontColorAdapter.setItems(color_list);
        rv_font_colors.setAdapter(fontColorAdapter);

        fontColorAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                Log.e("ff", "onclick");
                SharedPrefUtil.getInstance(context).setContentTxtColor(colors.get(position));
            }
        });
    }

    private boolean[] checked;
    private void setChecked() {
        checked = new boolean[colors.size()];
        for (int i = 0; i < checked.length; i++) {
            checked[i] = false;
        }
    }

    private void setColors() {
        colors = new ArrayList<Integer>();
        colors.add(context.getResources().getColor(R.color.color_dark));
        colors.add(context.getResources().getColor(R.color.color_white));
        colors.add(context.getResources().getColor(R.color.color_red));
        colors.add(context.getResources().getColor(R.color.color_orange));
        colors.add(context.getResources().getColor(R.color.color_yellow));
        colors.add(context.getResources().getColor(R.color.color_green));
        colors.add(context.getResources().getColor(R.color.color_cyan));
        colors.add(context.getResources().getColor(R.color.color_blue));
        colors.add(context.getResources().getColor(R.color.color_purple));
        colors.add(context.getResources().getColor(R.color.color_gray));

        setChecked();
    }

    public ArrayList<Integer> getColors() {
        return colors;
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
