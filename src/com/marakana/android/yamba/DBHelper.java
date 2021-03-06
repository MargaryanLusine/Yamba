package com.marakana.android.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = DBHelper.class.getSimpleName();
	
	public DBHelper(Context context) {
		super(context, StatusContract.DN_NAME, null, StatusContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s ( %s int primary key, %s text, %s text, %s int)", StatusContract.TABLE, 
				   StatusContract.Column.ID, StatusContract.Column.USER, StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT );
		Log.d(TAG, "onCreate with SQL: " + sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + StatusContract.TABLE);
		onCreate(db);
	}
	
	
}
