package com.selagroup.ListAndSelectorsDemo;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDemoActivity extends ListActivity {
	
	private TextView selectionText;
	private String[] items;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle); 
		setContentView(R.layout.listview);
		
		Resources res = getResources();
		items = res.getStringArray(R.array.items_array);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)); 
		selectionText = (TextView)findViewById(R.id.selectedItem);
	}
	
	public void onListItemClick(ListView parent, View v, int position, long id) {
		selectionText.setText(items[position]); 
	}
}
