package com.selagroup.activitycomm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityCommunicationActivity extends Activity {

	private static final int NUMBER_PICKER = 1;
	private static final int DEFAULT_VALUE = 42;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.pickNumberButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchIntent = new Intent(ActivityCommunicationActivity.this, NumberPickerActivity.class);
				//Intent launchIntent = new Intent(NumberPickerActivity.ACTION_NUMBER_PICK);
				startActivityForResult(launchIntent, NUMBER_PICKER);
			}
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == NUMBER_PICKER) {
    		TextView textView = (TextView)findViewById(R.id.selectedNumberText);
    		if (resultCode == RESULT_CANCELED) {
    			textView.setText("No number selected");
    		} else if (resultCode == RESULT_OK) {
    			int number = data.getIntExtra(NumberPickerActivity.SELECTED_NUMBER, DEFAULT_VALUE);
    			textView.setText(Integer.toString(number));
    		}
    	}
    }
}