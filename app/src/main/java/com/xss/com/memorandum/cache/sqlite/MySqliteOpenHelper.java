package com.xss.com.memorandum.cache.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Desc：
 * Author: xss
 * Time：2016/2/16 10:40
 */
public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private final static int VERSION = 3;
    private final static String DB_NAME = "memorandum.db";
    private final static String SQL_CREATE_MEMORANDUM_TABLE = "create table if not exists tb_memorandum(_id integer primary key autoincrement," +
            "title text, content text, create_date text, category_id int, " +
            "foreign key(category_id) references tb_category(category_id) on delete cascade on update cascade)";
    private final static String SQL_CREATE_CATEGORY_TABLE = "create table if not exists tb_category(_id integer primary key autoincrement, " +
            "category_id integer, type text)";

    private final static String SQL_INSERT_CATEGORY = "insert into tb_category(category_id, type)" +
            " values(1,'工作'), (2,'生活'), (3,'学习'), (4,'阅读'), (5, '娱乐'), (6,'旅行')";


    //SQLiteOpenHelper子类必须要的一个构造函数
    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteOpenHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    // 数据库的构造函数，传递一个参数的， 数据库名字和版本号都写死
    public MySqliteOpenHelper(Context context) {
        this(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MEMORANDUM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(SQL_INSERT_CATEGORY);
    }

    // 升级软件时更新数据库表结构，当你构造DBHelper的传递的Version与之前的Version调用此函数
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //当版本更新时，在此方法中做更新表结构
        Log.e("onUpgrade", oldVersion + ", " + newVersion);
    }
}
