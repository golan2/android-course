package com.hp.tipcalculator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

	private static final int PREFERENCES_ACTIVITY_REQUEST_CODE = 42;
	private static final String TIP_HISTORY_FILENAME = "tiphistory.dat";

	private int preferredTipPercentIndex = 0;
	
    private ArrayList<TipTransaction> tips = new ArrayList<TipTransaction>();
	private ArrayAdapter<TipTransaction> tipsAdapter;
	
	private void saveTipHistory() {
		try {
			FileOutputStream output = openFileOutput(TIP_HISTORY_FILENAME, MODE_PRIVATE);
			ObjectOutputStream objectOutput = new ObjectOutputStream(output);
			objectOutput.writeObject(tips);
			objectOutput.close();
			output.close();
		} catch (IOException e) {
			Toast.makeText(
					this, "Error saving tip history: " + e.toString(), Toast.LENGTH_LONG)
					.show();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadTipHistory() {
		try {
			FileInputStream input = openFileInput(TIP_HISTORY_FILENAME);
			ObjectInputStream objectInput = new ObjectInputStream(input);
			tips = (ArrayList<TipTransaction>) objectInput.readObject();
			objectInput.close();
			input.close();
		} catch (IOException e) {
			Toast.makeText(
					this, "Error loading tip history: " + e.toString(), Toast.LENGTH_LONG)
					.show();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

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
        
        loadTipHistory();
        
        ListView listTips = (ListView) findViewById(R.id.listTips);
//        tipsAdapter = new ArrayAdapter<TipTransaction>(
//        		//this, android.R.layout.simple_list_item_1, tips);
//        		this, R.layout.tip_row, R.id.textTipAmount, tips);
        tipsAdapter = new TipsAdapter(this, tips);
        listTips.setAdapter(tipsAdapter);
    }
	
	@Override
	public void onClick(View v) {
		String tipText = getTipOutput();
		TextView textResult = (TextView) findViewById(R.id.textResult);
		textResult.setText(tipText);
		saveTipHistory();
	}
	
	private String getTipOutput() {
		EditText editBillAmount = (EditText) findViewById(R.id.editBillAmount);
		String billAmountStr = editBillAmount.getText().toString();
		double billAmount = Double.parseDouble(billAmountStr);
		int tipPercent = getResources().getIntArray(R.array.tip_percent_values)[preferredTipPercentIndex];
		double tipAmount = billAmount * (tipPercent / 100.0);
		tipsAdapter.add(new TipTransaction(billAmount, tipAmount));
		return String.format("Tip: %2.2f", tipAmount);
	}

}
