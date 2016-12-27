package com.xss.com.memorandum.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.utils.Constants;

import java.util.Map;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/18 09:22
 */
public class NavigationAdapter extends BaseListAdapter<Map<String, String>> {

    public NavigationAdapter(Context context) {
        super(context);
    }

    @Override
    public View buildView(int position, View convertView, ViewGroup parent) {
        Map<String, String> item = itemList.get(position);

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = setContentView(R.layout.adapter_navigation_item);

            holder.tv_type_name = $(R.id.tv_type_name);
            holder.tv_count = $(R.id.tv_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_type_name.setText(item.get(Constants.TYPE_NAME));
        if (!TextUtils.isEmpty(item.get(Constants.TYPE_COUNT))) {
            if ("-1".equals(item.get(Constants.TYPE_COUNT))) {
                holder.tv_count.setVisibility(View.INVISIBLE);
            } else {
                holder.tv_count.setText(item.get(Constants.TYPE_COUNT));
                holder.tv_count.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView tv_type_name;
        private TextView tv_count;
    }
}
