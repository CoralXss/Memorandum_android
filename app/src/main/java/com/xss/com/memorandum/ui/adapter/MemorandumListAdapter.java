package com.xss.com.memorandum.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.model.MemorandumModel;
import com.xss.com.memorandum.utils.Constants;
import com.xss.com.memorandum.utils.DateUtils;
import com.xss.com.memorandum.widget.dynamicheight.DynamicHeightLinearLayout;

import java.util.Random;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 15:04
 */
public class MemorandumListAdapter extends BaseListAdapter<MemorandumModel> {
    private int mode;
    private Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public MemorandumListAdapter(Context context) {
        super(context);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public View buildView(int position, View convertView, ViewGroup parent) {
        MemorandumModel model = itemList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = setContentView(R.layout.adapter_memorandum_list);
            viewHolder = new ViewHolder();

            viewHolder.ll_dynamic = $(R.id.ll_dynamic);
            viewHolder.ll_content = $(R.id.ll_content);
            viewHolder.tv_title = $(R.id.tv_title);
            viewHolder.tv_content = $(R.id.tv_content);
            viewHolder.tv_category = $(R.id.tv_category);
            viewHolder.tv_create_time = $(R.id.tv_create_time);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_content.setText(model.getContent());

        if (model.getCategory_id() == Constants.ID_ALL) {
            viewHolder.tv_category.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tv_category.setVisibility(View.VISIBLE);
            viewHolder.tv_category.setText(model.getCategoryType(model.getCategory_id()));
        }

        String create_date = model.getCreate_date();
        if (!TextUtils.isEmpty(create_date)) {
            // 今天创建的清单
            if (DateUtils.getYearMonthDay(create_date).equals(DateUtils.getYearMonthDay(System.currentTimeMillis()+""))) {
                viewHolder.tv_create_time.setText(DateUtils.getHourMinute(Long.parseLong(model.getCreate_date())));
            } else {
                viewHolder.tv_create_time.setText(DateUtils.getYearMonthDay(create_date));
            }
        }

//        viewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listener.onItemClick(i, model);
//            }
//        });

        // 卡片布局显示
        if (mode == Constants.SHOW_AS_CARD) {
            double positionHeight = getPositionRatio(position);
            viewHolder.ll_dynamic.setHeightRatio(positionHeight);
        }

        return convertView;
    }

    public class ViewHolder {
        private DynamicHeightLinearLayout ll_dynamic;
        private LinearLayout ll_content;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_category;
        private TextView tv_create_time;
    }

    /*

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_memorandum_list, viewGroup, false);
        BaseViewHolder viewHolder = new MemorandumViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, final int i) {
        MemorandumViewHolder viewHolder = (MemorandumViewHolder)baseViewHolder;
        final MemorandumModel model = itemList.get(i);
        if (!TextUtils.isEmpty(model.getTitle())) {
            viewHolder.tv_title.setVisibility(View.VISIBLE);
            viewHolder.tv_title.setText(model.getTitle());
        } else {
            viewHolder.tv_title.setVisibility(View.GONE);
        }

        viewHolder.tv_content.setText(model.getContent());

        if (model.getCategory_id() == Constants.ID_ALL) {
            viewHolder.tv_category.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.tv_category.setVisibility(View.VISIBLE);
            viewHolder.tv_category.setText(model.getCategoryType(model.getCategory_id()));
        }

        String create_date = model.getCreate_date();
        if (!TextUtils.isEmpty(create_date)) {
            // 今天创建的清单
            if (DateUtils.getYearMonthDay(create_date).equals(DateUtils.getYearMonthDay(System.currentTimeMillis()+""))) {
                viewHolder.tv_create_time.setText(DateUtils.getHourMinute(Long.parseLong(model.getCreate_date())));
            } else {
                viewHolder.tv_create_time.setText(DateUtils.getYearMonthDay(create_date));
            }
        }

        viewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(i, model);
            }
        });

        // 卡片布局显示
        if (mode == Constants.SHOW_AS_CARD) {
            double positionHeight = getPositionRatio(i);
            viewHolder.ll_dynamic.setHeightRatio(positionHeight);
        }
    }
 */

    private double getPositionRatio(final int position) {
        double height = sPositionHeightRatios.get(position, 0.0);
        if (height == 0) {
            height = getRandomHeight();
            sPositionHeightRatios.append(position, height);
        }
        return height;
    }

    private double getRandomHeight() {
        mRandom = new Random();
        return (mRandom.nextDouble() / 2.0) + 1.0;
    }
/*
    public class MemorandumViewHolder extends BaseViewHolder {
        private DynamicHeightLinearLayout ll_dynamic;
        private LinearLayout ll_content;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_category;
        private TextView tv_create_time;

        public MemorandumViewHolder(View itemView) {
            super(itemView);
            ll_dynamic = $(R.id.ll_dynamic);
            ll_content = $(R.id.ll_content);
            tv_title = $(R.id.tv_title);
            tv_content = $(R.id.tv_content);
            tv_category = $(R.id.tv_category);
            tv_create_time = $(R.id.tv_create_time);
        }
    }
    */
}
