package com.marakana.android.yamba;

import android.app.Activity;
import android.os.Bundle;

public class DetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check if this activity was created before
		if(savedInstanceState == null) {
			
			//create fragment
			DetailsFragment fragment = new DetailsFragment();	
			getFragmentManager().beginTransaction().add(android.R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
		}
	}
}
