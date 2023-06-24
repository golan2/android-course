package com.selagroup.menus;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MenuDemoActivity extends ListActivity {
	
	private TextView selectionText;
	private String[] items;
	
	private static final int ONE_ID = Menu.FIRST+1;
	private static final int TWO_ID = Menu.FIRST+2;
	private static final int THREE_ID = Menu.FIRST+3; 
	private static final int FOUR_ID = Menu.FIRST+4; 
	private static final int FIVE_ID = Menu.FIRST+5; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();
		items = res.getStringArray(R.array.items_array);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)); 
		selectionText = (TextView)findViewById(R.id.selectedItem);
		
		registerForContextMenu(getListView());
    }
    
    public void onListItemClick(ListView parent, View v, int position, long id) {
		selectionText.setText(items[position]); 
	}
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
    	//Create menu from code
    	populateMenuFromCode(menu);
    	
    	//Create menu from XML definition
    	//new MenuInflater(getApplicationContext()).inflate(R.menu.menus, menu);  
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		//Create menu from code
		populateMenuFromCode(menu);
		
		//Create menu from XML definition
		//new MenuInflater(getApplicationContext()).inflate(R.menu.menus, menu);    	
		
		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	return (applyMenuChoice(item) || super.onOptionsItemSelected(item));
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item){
    	return (applyMenuChoice(item) || super.onContextItemSelected(item));
    }
    
	private boolean applyMenuChoice(MenuItem item) {
		Resources r = getResources();
		
		switch (item.getItemId())
		{
		case ONE_ID:
			getListView().setDivider(r.getDrawable(R.drawable.divider1));
			return true;
		case TWO_ID: 
			getListView().setDivider(r.getDrawable(R.drawable.divider2)); 
			getListView().setDividerHeight(8);
			return true ;
		case THREE_ID: 
			getListView().setDivider(r.getDrawable(R.drawable.divider3));
			return true;
		case FOUR_ID: 
			getListView().setDivider(r.getDrawable(R.drawable.divider4));
			return true;
		case FIVE_ID: 
			getListView().setDivider(r.getDrawable(android.R.drawable.divider_horizontal_dark));
			getListView().setDividerHeight(16);
			return true;
		}
		return false;
	}

	private void populateMenuFromCode(Menu menu) {
		menu.add(Menu.NONE, ONE_ID, Menu.NONE, "Pink Flowers"); 
		menu.add(Menu.NONE, TWO_ID, Menu.NONE, "Violet Flowers"); 
		menu.add(Menu.NONE, THREE_ID, Menu.NONE, "Panda Bear"); 
		menu.add(Menu.NONE, FOUR_ID, Menu.NONE, "Mixed Flowers");
		menu.add(Menu.NONE, FIVE_ID, Menu.NONE, "16 pixels");
	}
}