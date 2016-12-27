package com.xss.com.memorandum.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.xss.com.memorandum.R;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/17 11:42
 */
public class FontColorAdapter extends BaseRecyclerAdapter<Integer> {

    private boolean[] checked;

    public FontColorAdapter(Context context) {
        super(context);
    }

    public boolean[] getChecked() {
        return checked;
    }

    public void setChecked(boolean[] checked) {
        this.checked = checked;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_font_color_item, viewGroup, false);
        BaseViewHolder viewHolder = new ColorViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int i) {
        ColorViewHolder holder = (ColorViewHolder)baseViewHolder;
        final Integer color_id = itemList.get(i);

        holder.cb_font_color.setBackgroundColor(color_id);

        holder.cb_font_color.setChecked(checked[i]);

        holder.cb_font_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(i, color_id);
            }
        });
    }

    public class ColorViewHolder extends BaseViewHolder {
        private CheckBox cb_font_color;

        public ColorViewHolder(View itemView) {
            super(itemView);

            cb_font_color = $(R.id.cb_font_color);
        }
    }
}
