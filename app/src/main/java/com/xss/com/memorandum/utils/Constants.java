package com.xss.com.memorandum.utils;

import android.util.SparseArray;

import com.xss.com.memorandum.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 15:29
 */
public class Constants {
    /**
     * 以列表显示
     */
    public static final int SHOW_AS_LIST = 0;
    /**
     * 以卡片显示
     */
    public static final int SHOW_AS_CARD = 1;

    public static final int ID_ALL = 0;
    public static final int ID_WORK = 1;
    public static final int ID_LIFE = 2;
    public static final int ID_LEARN = 3;
    public static final int ID_READ = 4;
    public static final int ID_FUN = 5;
    public static final int ID_TRAVEL = 6;

    public static final String TYPE_NAME = "type_name";
    public static final String TYPE_COUNT = "type_count";

    public static final String TYPE_ALL = "全部";
    public static final String TYPE_WORK = "工作";
    public static final String TYPE_LIFE = "生活";
    public static final String TYPE_LEARN = "学习";
    public static final String TYPE_READ = "阅读";
    public static final String TYPE_FUN = "娱乐";
    public static final String TYPE_TRAVEL = "旅行";

    public static final String CREATE_LIST = "创建清单";

    public static final SparseArray<String> CATEGORY_TYPES = new SparseArray<String>();

    // (1,'工作'), (2,'生活'), (3,'学习'), (4,'阅读'), (5, '娱乐'), (6,'旅行')
    static {
        CATEGORY_TYPES.put(ID_ALL, TYPE_ALL);
        CATEGORY_TYPES.put(ID_WORK, TYPE_WORK);
        CATEGORY_TYPES.put(ID_LIFE, TYPE_LIFE);
        CATEGORY_TYPES.put(ID_LEARN, TYPE_LEARN);
        CATEGORY_TYPES.put(ID_READ, TYPE_READ);
        CATEGORY_TYPES.put(ID_FUN, TYPE_FUN);
        CATEGORY_TYPES.put(ID_TRAVEL, TYPE_TRAVEL);
    }

    public static final ArrayList<Map<String,String>> TYPE_LIST = new ArrayList<Map<String,String>>();
    private static Map<String,String> map = new HashMap<String,String>();
    static {
        map.put(Constants.TYPE_NAME, TYPE_ALL);
        map.put(Constants.TYPE_COUNT, (R.color.color_red)+"");
        TYPE_LIST.add(map);

    }

    public static final String MEMORANDUM_ITEM = "memorandum_item";

    public static final int FROM_ADD = 1;
    public static final int FROM_LIST_ITEM = 2;
    public static final int FROM_LIST_CREATE = 3;


}
