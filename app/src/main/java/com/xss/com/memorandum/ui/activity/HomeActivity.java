package com.xss.com.memorandum.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.cache.preference.SharedPrefUtil;
import com.xss.com.memorandum.model.CategoryModel;
import com.xss.com.memorandum.model.MemorandumModel;
import com.xss.com.memorandum.ui.activity.base.BaseActivity;
import com.xss.com.memorandum.ui.adapter.BaseRecyclerAdapter;
import com.xss.com.memorandum.ui.adapter.MemorandumAdapter;
import com.xss.com.memorandum.ui.adapter.NavigationAdapter;
import com.xss.com.memorandum.ui.adapter.RecyclerDividerDecoration;
import com.xss.com.memorandum.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 14:50
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private RecyclerView rv_memorandum_list;
    private TextView tv_empty_view;
    private ImageView img_edit;
    private ListView lv_navdrawer;

    private MemorandumAdapter memorandumAdapter = null;

//    private MemorandumListAdapter memorandumListAdapter = null;
    private ArrayList<MemorandumModel> list = new ArrayList<MemorandumModel>();
    private ArrayList<Map<String, String>> type_list = new ArrayList<Map<String, String>>();
    private RecyclerDividerDecoration decoration = null;

//    private StaggeredGridView staggeredGridView;

    private ArrayList<CategoryModel> category_list = new ArrayList<CategoryModel>();

    private SharedPrefUtil sharedPrefUtil = null;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefUtil = SharedPrefUtil.getInstance(this);

        initView();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initContent();

        // 从数据库中获取不同时间管理类型数据总条数
        setNavigation();
    }

    @Override
    protected void initView() {
        mDrawerLayout = $(R.id.drawer_layout);
        toolbar = $(R.id.toolbar);

        rv_memorandum_list = $(R.id.rv_memorandum_list);
        img_edit = $(R.id.img_edit);
        lv_navdrawer = $(R.id.lv_navdrawer);

        tv_empty_view = $(R.id.tv_empty_view);

//        staggeredGridView = $(R.id.staggeredGridView);

        img_edit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
//        }
        setToolBar();
        toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);

    }

    private void setNavigation() {
        type_list = memorandumTableService.queryMemorandumTypes(null);
        Map<String, String> map = new HashMap<String, String>();
        map.put(Constants.TYPE_NAME, "创建清单");
        map.put(Constants.TYPE_COUNT, "-1");
        type_list.add(map);
        NavigationAdapter navigationAdapter = new NavigationAdapter(HomeActivity.this);
        navigationAdapter.addItems(type_list);
        lv_navdrawer.setAdapter(navigationAdapter);

        lv_navdrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDrawerLayout.closeDrawer(Gravity.START);
                if ("-1".equals(type_list.get(i).get(Constants.TYPE_COUNT))) {
                    ListNameCreateActivity.startListCreateActivity(HomeActivity.this);
                }
            }
        });
    }

    private void showListView() {
        rv_memorandum_list.setVisibility(View.VISIBLE);
        tv_empty_view.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        tv_empty_view.setVisibility(View.VISIBLE);
        rv_memorandum_list.setVisibility(View.GONE);
    }



    /**
     * 填充RecycleView
     */
    private void initContent() {
        int mode = SharedPrefUtil.getDataShowMode();
        switch (mode) {
            case Constants.SHOW_AS_LIST:
                showAsList();

                break;
            case Constants.SHOW_AS_CARD:
                showAsCard();

                break;
        }
        //设置添加删除item时的动画
        rv_memorandum_list.setItemAnimator(new DefaultItemAnimator());

//        setData();
    }

    /**
     * 查询 便签信息
     */
    private void setAdapter() {
        list = memorandumTableService.queryMemorandum(null);
        if (list.isEmpty()) {
            showEmptyView();
        } else {
            showListView();
            if (memorandumAdapter == null) {
                memorandumAdapter = new MemorandumAdapter(this);
            }
            memorandumAdapter.setItems(list);
        }
    }

    /**
     * 填充RecyclerView
     */
    private void setData() {
        //设置item之间的间距
        int item_space = getResources().getDimensionPixelOffset(R.dimen.marginPadding10);
        if (decoration == null) {
            decoration = new RecyclerDividerDecoration(item_space, list.size());
            rv_memorandum_list.addItemDecoration(decoration);
        }
        if (mode == Constants.SHOW_AS_CARD) {
            decoration.setShow_mode(Constants.SHOW_AS_CARD);
        } else if (mode == Constants.SHOW_AS_LIST) {
            decoration.setShow_mode(Constants.SHOW_AS_LIST);
        }

        rv_memorandum_list.setAdapter(memorandumAdapter);

        memorandumAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object) {
                MemorandumEditActivity.startEditActivity(HomeActivity.this, Constants.FROM_LIST_ITEM, list.get(position), null, null);
            }
        });
    }

    private void showAsList() {
        setAdapter();

        mode = Constants.SHOW_AS_LIST;
        sharedPrefUtil.setDataShowMode(mode);
        memorandumAdapter.setMode(Constants.SHOW_AS_LIST);
        rv_memorandum_list.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        setData();
    }

    private void showAsCard() {
        setAdapter();

        mode = Constants.SHOW_AS_CARD;
        sharedPrefUtil.setDataShowMode(mode);
        memorandumAdapter.setMode(Constants.SHOW_AS_CARD);
        rv_memorandum_list.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            case R.id.menu_list_mode:
                if (mode == Constants.SHOW_AS_LIST) {
                    item.setIcon(R.drawable.ic_card);
                    showAsCard();
                } else if (mode == Constants.SHOW_AS_CARD) {
                    item.setIcon(R.drawable.ic_list);
                    showAsList();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_edit) {
            MemorandumEditActivity.startEditActivity(HomeActivity.this, Constants.FROM_ADD, null, null, null);
        }
    }
}
