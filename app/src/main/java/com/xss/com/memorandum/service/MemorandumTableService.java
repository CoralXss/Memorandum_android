package com.xss.com.memorandum.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.xss.com.memorandum.cache.sqlite.MySqlLiteUtil;
import com.xss.com.memorandum.model.MemorandumModel;
import com.xss.com.memorandum.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 10:55
 */
public class MemorandumTableService {
    private final static String MEMORANDUM_TABLE_NAME = "tb_memorandum";
    private final static String CATEGORY_TABLE_NAME = "tb_category";

    private final static String ITEM_ID = "_id";
    private final static String ITEM_TITLE = "title";
    private final static String ITEM_CONTENT = "content";
    private final static String ITEM_CREATE_DATE = "create_date";
    private final static String ITEM_CATEGORY_ID = "category_id";

    private MySqlLiteUtil mySqlLiteUtil;

    public MemorandumTableService(Context context) {
        mySqlLiteUtil = new MySqlLiteUtil(context);
    }

    /**
     * 检查是否存在 (存在 返回true)
     * @param model
     * @return
     */
    public boolean checkMemorandum(MemorandumModel model) {
        boolean flag = true;
        String sql = "select * from " + MEMORANDUM_TABLE_NAME + " where title = ? and content = ? and category_id = ?";
        Cursor cursor = null;
//        try {
            cursor = mySqlLiteUtil.query(sql, new String[] {model.getTitle(), model.getContent(), model.getCategory_id()+""});
            if (cursor.getCount() == 0) {
                flag = false;
            }
//        } catch (Exception e) {
//            flag = false;
//        } finally {
            if (cursor != null) {
                cursor.close();
            }
            mySqlLiteUtil.close();
//        }
        return flag;
    }

    /**
     * 添加便签
     * @param model
     * @return
     */
    public boolean addMemorandum(MemorandumModel model) {
        boolean flag = true;

        ContentValues values = new ContentValues();
        values.put(ITEM_TITLE, model.getTitle());
        values.put(ITEM_CONTENT, model.getContent());
        values.put(ITEM_CREATE_DATE, model.getCreate_date());
        values.put(ITEM_CATEGORY_ID, model.getCategory_id());

        Log.e("values", values.toString());

//        try {
            mySqlLiteUtil.insert(MEMORANDUM_TABLE_NAME, values);
//        } catch (Exception e) {
//            Log.e("exception", e.getMessage());
//            e.printStackTrace();
        //        flag = false;
//        } finally {
//            mySqlLiteUtil.close();
//        }

        return flag;
    }

    public boolean updateMemorandum(MemorandumModel model) {
        boolean flag = true;

        ContentValues values = new ContentValues();
        values.put(ITEM_TITLE, model.getTitle());
        values.put(ITEM_CONTENT, model.getContent());
        values.put(ITEM_CREATE_DATE, model.getCreate_date());
        values.put(ITEM_CATEGORY_ID, model.getCategory_id());

        mySqlLiteUtil.update(MEMORANDUM_TABLE_NAME, values, "_id = ?", new String[]{ model.get_id() + "" });

        return flag;
    }

    public boolean delMemorandum(MemorandumModel model) {
        boolean flag = true;

//        try {
        mySqlLiteUtil.delete(MEMORANDUM_TABLE_NAME, "_id = ?", new String[]{model.get_id() + ""});
//        } catch (Exception e) {
//            Log.e("exception", e.getMessage());
//            e.printStackTrace();
        //        flag = false;
//        } finally {
//            mySqlLiteUtil.close();
//        }

        return flag;
    }

    /**
     * 查询 所有便签信息
     * @param model
     * @return
     */
    public ArrayList<MemorandumModel> queryMemorandum(MemorandumModel model) {
        ArrayList<MemorandumModel> list = new ArrayList<MemorandumModel>();
        String sql = "select * from " + MEMORANDUM_TABLE_NAME + " order by create_date desc";
        Cursor cursor = null;

        try {
            cursor = mySqlLiteUtil.query(sql, null);
            while (cursor.moveToNext()) {
                int _id = cursor.getInt(cursor.getColumnIndex(ITEM_ID));
                String title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(ITEM_CONTENT));
                String create_time = cursor.getString(cursor.getColumnIndex(ITEM_CREATE_DATE));
                int category_id = cursor.getInt(cursor.getColumnIndex(ITEM_CATEGORY_ID));

                MemorandumModel memorandumModel = new MemorandumModel();
                memorandumModel.set_id(_id);
                memorandumModel.setTitle(title);
                memorandumModel.setContent(content);
                memorandumModel.setCreate_date(create_time);

//                if (!TextUtils.isEmpty(model.getCreate_date())) {
//                    String date = DateUtils.getYearMdh(Long.parseLong(model.getCreate_date()));
//                    if (date.equals(DateUtils.getYearMdh(DateUtils.getCurrentTime()))) {
//                        memorandumModel.setCreate_date(create_time);
//                    } else {
//                        memorandumModel.setCreate_date(DateUtils.getYearMonth2(Long.parseLong(model.getCreate_date())));
//                    }
//                }
                memorandumModel.setCategory_id(category_id);

                list.add(memorandumModel);
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

    /**
     * 查询 不同类型的总数
     * @param model
     * @return
     */
    public ArrayList<Map<String, String>> queryMemorandumTypes(MemorandumModel model) {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String sql = "select type, count(tb_memorandum.category_id) as count from " + MEMORANDUM_TABLE_NAME + "," + CATEGORY_TABLE_NAME
                + " where tb_memorandum.category_id = tb_category.category_id group by tb_memorandum.category_id";
        Cursor cursor = null;

        try {
            cursor = mySqlLiteUtil.query(sql, null);
            while (cursor.moveToNext()) {
                String type = cursor.getString(0);
                Integer count = cursor.getColumnIndex(cursor.getColumnNames()[1]);

                Log.e("types", type + ", " + count + ", " + cursor.getColumnNames().length + ", " + cursor.getCount());

                Map<String, String> map = new HashMap<String, String>();
                map.put(Constants.TYPE_NAME, type);
                map.put(Constants.TYPE_COUNT, count+"");
                list.add(map);
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
