package com.selagroup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivitiesSampleActivity extends Activity {
	private static final String TAG = "ActivitiesSample";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Navigate to sub-activity on button click
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SecondaryActivity.class);
				startActivity(i);
			}
		});
        
		//Override current view on button click
		Button btnOverideMe = (Button)findViewById(R.id.btnOverrideMe);
		btnOverideMe.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				TextView text = new TextView(getApplicationContext());
				text.setText("New text!");
				setContentView(text);	
			}
		});
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
    	super.onRestoreInstanceState(savedInstanceState);
    	
    	Log.d(TAG, "onRestoreInstanceState");
    }
    
    @Override
    public void onRestart(){
    	super.onRestart();
    	
    	Log.d(TAG, "onRestart");
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	
    	Log.d(TAG, "onStart");
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    	Log.d(TAG, "onResume");
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	
    	Log.d(TAG, "onSaveInstanceState");
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	
    	Log.d(TAG, "onPause");
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	
    	Log.d(TAG, "onStop");
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	
    	Log.d(TAG, "onDestroy");
    }
}