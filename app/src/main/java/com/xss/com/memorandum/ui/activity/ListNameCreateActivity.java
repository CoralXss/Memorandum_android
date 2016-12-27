package com.xss.com.memorandum.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.model.CategoryModel;
import com.xss.com.memorandum.ui.activity.base.BaseActivity;
import com.xss.com.memorandum.ui.adapter.BaseListAdapter;
import com.xss.com.memorandum.utils.Constants;
import com.xss.com.memorandum.utils.Utility;

import java.util.ArrayList;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/18 14:24
 */
public class ListNameCreateActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText edt_list_name;
    private GridView card_category;

    private ArrayList<CategoryModel> list = new ArrayList<CategoryModel>();

    public static void startListCreateActivity(Activity startActivity) {
        Intent intent = new Intent(startActivity, ListNameCreateActivity.class);
        startActivity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_name_create);

        initView();

        initData();
    }

    @Override
    protected void initView() {

        setToolBar();
        // 给左上角图标的左边加上一个返回的图标, 对应id为android.R.id.home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.fab_background);

        card_category = $(R.id.card_category);
        edt_list_name = $(R.id.edt_list_name);
    }

    @Override
    protected void initData() {
        list = categoryTableService.queryCategory(null);
        CategoryAdapter adapter = new CategoryAdapter(this);
        adapter.setItems(list);

        card_category.setAdapter(adapter);

        card_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = edt_list_name.getText().toString();
                Integer type_id = list.get(i).getCategory_id();
                MemorandumEditActivity.startEditActivity(ListNameCreateActivity.this, Constants.FROM_LIST_CREATE, null, title, type_id);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public class CategoryAdapter extends BaseListAdapter<CategoryModel> {

        public CategoryAdapter(Context context) {
            super(context);
        }

        @Override
        public View buildView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            CategoryModel model = itemList.get(position);

            if (convertView == null) {
                convertView = setContentView(R.layout.adapter_type_item);
                holder = new ViewHolder();

                holder.tv_type = $(R.id.tv_type);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Log.e("type", model.getType());
            holder.tv_type.setText(model.getType());
            holder.tv_type.setBackgroundColor(Utility.getRGB());

            return convertView;
        }

        class ViewHolder {
            TextView tv_type;
        }
    }
}
