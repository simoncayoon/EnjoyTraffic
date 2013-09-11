package com.eteng.interactionServer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 13-9-9.
 */
public class ConnSQL extends SQLiteOpenHelper{

    /**
     * 定义数据库表名、字段、数据库名称
     *
     */
    private Context context;
    public static final String TABLE_NAME = "localContent";
    public static final String ID = "_id";
    public static final String CONTENT = "content";

    //构造方法
    public ConnSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+ ID + "INTEGER PRIMARY KEY,"+CONTENT+" VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
