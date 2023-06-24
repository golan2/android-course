package com.selagroup.intentext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IntentExtensibilityActivity extends Activity {

	public static final String PLANETS_URI = "content://com.selagroup.intentext.planets";
	
	private String[] planets;
	private ArrayAdapter<String> adapter;
	private ListView list;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        planets = getResources().getStringArray(R.array.planets);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets);
        list = (ListView)findViewById(R.id.mainListView);
        list.setAdapter(adapter);
        
        registerForContextMenu(list);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.setHeaderTitle("Planet Actions");
    	Intent searchIntent = new Intent();
    	searchIntent.setData(Uri.parse(PLANETS_URI));
    	searchIntent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);
    	menu.addIntentOptions(0, 0, Menu.NONE, getComponentName(), null, searchIntent, Menu.FLAG_APPEND_TO_GROUP, null);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	return super.onContextItemSelected(item);
    }
    
}