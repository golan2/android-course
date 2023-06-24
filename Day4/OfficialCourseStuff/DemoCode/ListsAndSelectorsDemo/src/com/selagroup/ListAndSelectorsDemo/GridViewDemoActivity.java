package com.selagroup.ListAndSelectorsDemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class GridViewDemoActivity extends Activity implements AdapterView.OnItemSelectedListener {
	
	private TextView selectionText;
	private String[] items;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle); 
		setContentView(R.layout.gridview);
		
		Resources res = getResources();
		items = res.getStringArray(R.array.items_array);
		
		selectionText = (TextView)findViewById(R.id.selectedItem);
		
		GridView g = (GridView) findViewById(R.id.grid); 
		g.setAdapter(new MyAdapter(this, android.R.layout.simple_list_item_1, items)); 
		g.setOnItemSelectedListener(this);
	}
	
	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		selectionText.setText(items[position]); 
	}
	
	public void onNothingSelected(AdapterView<?> parent) { 
		selectionText.setText("");
	}
	
	private class MyAdapter extends ArrayAdapter<String> { 
		private Context ctxt;
		
		public MyAdapter(Context ctxt, int resource, String[] items) {
				super(ctxt, resource, items); 
				this.ctxt = ctxt;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
	      TextView label = (TextView)convertView;
	      
	      if (convertView == null) { 
	    	  convertView = new TextView(ctxt); 
	    	  label = (TextView)convertView;
	      }
	      
	      label.setText(items[position]);
	      return(convertView); 
		}
	}
}
