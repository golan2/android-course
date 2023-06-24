package com.selagroup.ListAndSelectorsDemo;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerDemoActivity extends Activity implements AdapterView.OnItemSelectedListener {
	
	private TextView selectionText;
	private String[] items;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle); 
		setContentView(R.layout.spinner);
		
		Resources res = getResources();
		items = res.getStringArray(R.array.items_array);
		
		selectionText = (TextView)findViewById(R.id.selectedItem);
		
		Spinner spin=(Spinner)findViewById(R.id.spinner); 
		spin.setOnItemSelectedListener(this);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		aa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa);
	}
	
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		selectionText.setText(items[position]); 
	}
	
	public void onNothingSelected(AdapterView<?> parent) { 
		selectionText.setText("");
	}
}
