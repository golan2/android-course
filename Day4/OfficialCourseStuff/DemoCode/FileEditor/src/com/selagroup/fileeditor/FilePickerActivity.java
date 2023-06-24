package com.selagroup.fileeditor;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FilePickerActivity extends ListActivity {

	private String[] files;
	private ArrayAdapter<String> adapter;
	
	public static final String SELECTED_FILE = "SelectedFile";
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		files = fileList();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);
		setListAdapter(adapter);
	}
	
	 @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent resultIntent = new Intent((String)null);
		resultIntent.putExtra(SELECTED_FILE, files[position]);
		setResult(RESULT_OK, resultIntent);
		finish();
	}
}
