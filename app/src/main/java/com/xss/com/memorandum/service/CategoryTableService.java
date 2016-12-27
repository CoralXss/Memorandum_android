package com.xss.com.memorandum.service;

import android.content.Context;
import android.database.Cursor;

import com.xss.com.memorandum.cache.sqlite.MySqlLiteUtil;
import com.xss.com.memorandum.model.CategoryModel;

import java.util.ArrayList;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/18 16:51
 */
public class CategoryTableService {
    private final static String MEMORANDUM_TABLE_NAME = "tb_memorandum";
    private final static String CATEGORY_TABLE_NAME = "tb_category";

    private final static String ITEM_ID = "_id";
    private final static String ITEM_CATEGORY_ID = "category_id";
    private final static String ITEM_TYPE = "type";

    private MySqlLiteUtil mySqlLiteUtil;

    public CategoryTableService(Context context) {
        mySqlLiteUtil = new MySqlLiteUtil(context);
    }

    /**
     * 查询 所有类型
     * @param model
     * @return
     */
    public ArrayList<CategoryModel> queryCategory(CategoryModel model) {
        ArrayList<CategoryModel> list = new ArrayList<CategoryModel>();
        String sql = "select * from " + CATEGORY_TABLE_NAME;
        Cursor cursor = null;

        try {
            cursor = mySqlLiteUtil.query(sql, null);
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
                int category_id = cursor.getInt(cursor.getColumnIndex(ITEM_CATEGORY_ID));
                String type = cursor.getString(cursor.getColumnIndex(ITEM_TYPE));

                CategoryModel categoryModel = new CategoryModel();
                categoryModel.set_id(_id);
                categoryModel.setCategory_id(category_id);
                categoryModel.setType(type);

                list.add(categoryModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mySqlLiteUtil.close();
        }
        return list;
    }

}
