package com.hp.tipcalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	private static final int PREFERENCES_ACTIVITY_REQUEST_CODE = 42;

	private int preferredTipPercentIndex = 0;
	
    private ArrayList<String> tips = new ArrayList<String>();
	private ArrayAdapter<String> tipsAdapter;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemPreferences:
			Intent prefsIntent = new Intent(this, PreferencesActivity.class);
			prefsIntent.putExtra("preferredTipParam", preferredTipPercentIndex);
			startActivityForResult(prefsIntent, PREFERENCES_ACTIVITY_REQUEST_CODE);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			preferredTipPercentIndex = 
					result.getIntExtra("preferredTipResult", -1 /*defaultValue*/);
		}
		super.onActivityResult(requestCode, resultCode, result);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(this);
        
        ListView listTips = (ListView) findViewById(R.id.listTips);
        tipsAdapter = new ArrayAdapter<String>(
        		this, android.R.layout.simple_list_item_1, tips);
        listTips.setAdapter(tipsAdapter);
    }
	
	@Override
	public void onClick(View v) {
		String tipText = getTipOutput();
		TextView textResult = (TextView) findViewById(R.id.textResult);
		textResult.setText(tipText);
		tipsAdapter.add(tipText);
	}
	
	private String getTipOutput() {
		EditText editBillAmount = (EditText) findViewById(R.id.editBillAmount);
		String billAmountStr = editBillAmount.getText().toString();
		double billAmount = Double.parseDouble(billAmountStr);
		int tipPercent = getResources().getIntArray(R.array.tip_percent_values)[preferredTipPercentIndex];
		double tipAmount = billAmount * (tipPercent / 100.0);
		return String.format("Tip: %2.2f", tipAmount);
	}

}
