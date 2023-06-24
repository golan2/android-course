package com.selagroup.activitycomm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class NumberPickerActivity extends Activity {

	public static final String SELECTED_NUMBER = "SelectedNumber";
	public static final String ACTION_NUMBER_PICK = "com.selagroup.NUMBER_PICK";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.number_picker);
		
		final EditText edit = (EditText)findViewById(R.id.numberText);
		
		findViewById(R.id.okButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra(SELECTED_NUMBER, Integer.parseInt(edit.getText().toString()));
				setResult(RESULT_OK, data);
				finish();
			}
		});
		findViewById(R.id.cancelButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED, null);
				finish();
			}
		});
	}
	
}
