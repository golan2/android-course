package com.selagroup.ListAndSelectorsDemo;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class AutoCompleteDemoActivity extends Activity implements TextWatcher {
	
	private TextView selectionText;
	private String[] items;
	private AutoCompleteTextView editbox;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle); 
		setContentView(R.layout.autocomplete);
		
		Resources res = getResources();
		items = res.getStringArray(R.array.items_array);
		
		selectionText = (TextView)findViewById(R.id.selectedItem);
		
		editbox = (AutoCompleteTextView)findViewById(R.id.edit); 
		editbox.addTextChangedListener(this);
		editbox.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		selectionText.setText(editbox.getText()); 
	}
	
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}
	
	public void afterTextChanged(Editable s) { 
	}
}
