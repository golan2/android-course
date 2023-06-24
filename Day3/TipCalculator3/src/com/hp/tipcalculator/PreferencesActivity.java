package com.hp.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;

public class PreferencesActivity extends Activity implements OnClickListener {

	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		
		Intent creatingIntent = getIntent();
		int spinnerPosition = 
				creatingIntent.getIntExtra("preferredTipParam", -1 /*default value*/);
		spinner = (Spinner) findViewById(R.id.spinnerTipPercents);
		spinner.setSelection(spinnerPosition);
		
		findViewById(R.id.buttonSave).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int pos = spinner.getSelectedItemPosition();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("preferredTipResult", pos);
		setResult(RESULT_OK, resultIntent);
		finish();
	}
}
