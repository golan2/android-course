package com.hp.fitnesstracker;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.AdapterContextMenuInfo;


public class MainActivity extends Activity implements OnClickListener {

    private Spinner spinnerActivities;
	private ArrayAdapter<FitnessActivity> activityHistoryAdapter;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		switch (v.getId()) {
		case R.id.listActivityHistory:
			menu.setHeaderTitle(R.string.fitness_activity_options);
			getMenuInflater().inflate(R.menu.contextmenu, menu);
			break;
		default:
			// ...
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemDeleteAll:
			activityHistoryAdapter.clear();
			break;
		case R.id.menuItemShare:
			break;
		case R.id.menuItemSomethingElse:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ContextMenuInfo info = item.getMenuInfo();
		AdapterContextMenuInfo realInfo = (AdapterContextMenuInfo) info;
		switch (item.getItemId()) {
		case R.id.contextMenuItemDelete:
			activityHistoryAdapter.remove(
					activityHistoryAdapter.getItem(realInfo.position));
			break;
		case R.id.contextMenuItemEdit:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.buttonTrackActivity).setOnClickListener(this);
        
        spinnerActivities = (Spinner) findViewById(R.id.spinnerActivities);
        String[] activities = { "Running", "Hiking", "Elliptical", "Swimming" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        		this, android.R.layout.simple_spinner_item, activities
        		);
        spinnerActivities.setAdapter(adapter);
        
        ListView listActivityHistory = (ListView) findViewById(R.id.listActivityHistory);
        ArrayList<FitnessActivity> activityHistory = new ArrayList<FitnessActivity>();
        activityHistoryAdapter = new ArrayAdapter<FitnessActivity>(
        		this, android.R.layout.simple_list_item_1, activityHistory
        		);
        listActivityHistory.setAdapter(activityHistoryAdapter);
        
        registerForContextMenu(listActivityHistory);
    }

	@Override
	public void onClick(View v) {
		String activity = (String) spinnerActivities.getSelectedItem();
		activityHistoryAdapter.add(new FitnessActivity(activity, new Date()));
	}

}
