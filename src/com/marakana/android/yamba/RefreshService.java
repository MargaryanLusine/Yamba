package com.marakana.android.yamba;

import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {

	static final String TAG = "RefreshService";
	
	public RefreshService() {
		super(TAG);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, "Please update your status and password", Toast.LENGTH_LONG).show();
		}
	
		Log.d(TAG, "onStarted");
		
		DBHelper dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		YambaClient yambaCloud = new YambaClient(username, password);
		
		try {
			List<Status> timeline = yambaCloud.getTimeline(20);
			for (Status status:timeline) {
				values.clear();
				values.put(StatusContract.Column.ID, status.getId());
				values.put(StatusContract.Column.USER, status.getUser());
				values.put(StatusContract.Column.MESSAGE, status.getMessage());
				values.put(StatusContract.Column.CREATED_AT, status.getCreatedAt().getTime());
				
				db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
			}
			
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to fetch timeline", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
		Log.d(TAG, "onCreated");
	}

	@Override
	public void onDestroy() {
		
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

}
