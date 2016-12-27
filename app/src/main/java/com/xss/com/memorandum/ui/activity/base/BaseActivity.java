package com.xss.com.memorandum.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.xss.com.memorandum.R;
import com.xss.com.memorandum.service.CategoryTableService;
import com.xss.com.memorandum.service.MemorandumTableService;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 09:54
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MemorandumTableService memorandumTableService = null;
    protected CategoryTableService categoryTableService = null;

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memorandumTableService = new MemorandumTableService(this);
        categoryTableService = new CategoryTableService(this);

//        setToolBar();
    }

    protected void setToolBar() {
        toolbar = $(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected abstract void initView();
    protected abstract void initData();

    protected <T extends View> T $(int viewID) {
        return (T) findViewById(viewID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
