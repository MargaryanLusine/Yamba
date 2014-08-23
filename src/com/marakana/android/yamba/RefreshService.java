package com.marakana.android.yamba;

import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
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
		
		YambaClient yambaCloud = new YambaClient(username, password);
		
		try {
			List<Status> timeline = yambaCloud.getTimeline(20);
			for (Status status:timeline) {
				Log.d(TAG, String.format("%s %s", status.getUser(), status.getMessage()));
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
