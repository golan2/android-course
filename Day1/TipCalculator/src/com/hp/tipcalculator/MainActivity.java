package com.hp.tipcalculator;

import java.util.ArrayList;

import android.app.Activity;
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

    private ArrayList<String> tips = new ArrayList<String>();
	private ArrayAdapter<String> tipsAdapter;

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
		Spinner spinnerTipPercents = (Spinner) findViewById(R.id.spinnerTipPercents);
		int pos = spinnerTipPercents.getSelectedItemPosition();
		int tipPercent = getResources().getIntArray(R.array.tip_percent_values)[pos];
		double tipAmount = billAmount * (tipPercent / 100.0);
		return String.format("Tip: %2.2f", tipAmount);
	}

}
