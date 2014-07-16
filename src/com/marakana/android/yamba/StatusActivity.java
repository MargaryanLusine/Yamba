package com.marakana.android.yamba;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class StatusActivity extends Activity implements OnClickListener {

	private static final String TAG = "StatusActivity";
	private EditText editStatus;
	private Button buttonTweet;
	private TextView textCount;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        textCount = (TextView) findViewById(R.id.textCount);
        
        buttonTweet.setOnClickListener(this);
        
        editStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				int count = 140 - editStatus.length();
				textCount.setText(Integer.toString(count));
				
				// Change the color
				if (count < 10) {
					textCount.setTextColor(Color.YELLOW);
					
					if (count < 0) {
						textCount.setTextColor(Color.RED);
					} 
				}	
			}
        	
        });
    }

	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		Log.d(TAG, "OnClick with status:" + status);
		
		new PostTask().execute(status);
	}
	
	private final class PostTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			YambaClient yambaCloud = new YambaClient("student", "password");
			
			try {
				yambaCloud.postStatus(params[0]);
				return "Successfully posted";
			} catch (YambaClientException e) {
				e.printStackTrace();
				return "Failed to post to yamba service";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
		}
		
	}
}
