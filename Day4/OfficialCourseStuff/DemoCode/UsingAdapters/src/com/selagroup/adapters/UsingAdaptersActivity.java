package com.selagroup.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UsingAdaptersActivity extends ListActivity {

	private List<Person> people;
	private ArrayAdapter<Person> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        people = new ArrayList<Person>();
        people.add(new Person("Joe", "Seattle"));
        people.add(new Person("Mike", "Denver"));
        people.add(new Person("Kate", "Paris"));
        people.add(new Person("David", "Tel-Aviv"));
        people.add(new Person("Nathalie", "Singapore"));
        
        simple();
        
        ListView listView = (ListView)findViewById(android.R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				people.remove(position);
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			}
        });
    }

	private void simple() {
		adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, people);
        setListAdapter(adapter);
	}
	private void customLayout() {
		adapter = new ArrayAdapter<Person>(this, R.layout.row_layout, R.id.nameTextView, people);
		setListAdapter(adapter);
	}
	private void customView() {
		adapter = new PersonAdapter(this, people);
		setListAdapter(adapter);
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.mainmenu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.simple:
    		simple();
    		return true;
    	case R.id.customlayout:
    		customLayout();
    		return true;
    	case R.id.customview:
    		customView();
    		return true;
    	}
    	return false;
    }
    
}