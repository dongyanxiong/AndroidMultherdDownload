package com.example.androidmultherddownloadthree.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 建立一个数据库帮助类
 * @author miaowei
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	
	public DBHelper(Context context) {
		super(context, "download.db", null, 1);
	}
	/**
	 * 创建一个download_info表存储下载信息
	 * thread_id 下载器id
	 * start_pos 开始点
	 * end_pos 结束点
	 * compelete_size 完成度
	 * url 下载器网络标识地址
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
				     +"start_pos integer, end_pos integer, compelete_size integer, url char)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}

}
