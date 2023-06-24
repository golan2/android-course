package com.hp.fitnesstracker;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
	private SQLiteDatabase db;
	
	private void processAppLaunchCount() {
		SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
		int launchCount = prefs.getInt("launchCount", 0);
		if (0 == launchCount) {
			new AlertDialog.Builder(this).setTitle("Welcome")
				.setMessage("Thanks for downloading our app").create().show();
		} else if (launchCount % 3 == 0) {
			new AlertDialog.Builder(this).setTitle("Please donate")
				.setMessage("Give us money").create().show();
		}
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("launchCount", launchCount + 1);
		editor.commit();
	}

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
			db.delete("activities", null, null);
			break;
		case R.id.menuItemShare:
			break;
		case R.id.menuItemPreferences:
			startActivity(new Intent(this, MyPreferencesActivity.class));
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
			FitnessActivity fitnessActivity = activityHistoryAdapter.getItem(realInfo.position);
			activityHistoryAdapter.remove(fitnessActivity);
			db.delete("activities", "_id = ?",
					new String[] { Long.toString(fitnessActivity.getDatabaseId()) });
			break;
		case R.id.contextMenuItemEdit:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	protected void onPause() {
		Log.v("FitnessTracker", "onPause()");
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		Log.v("FitnessTracker", "onResume()");
		super.onResume();
	}
	
	@Override
	protected void onStart() {
		Log.v("FitnessTracker", "onStart()");
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		Log.v("FitnessTracker", "onStop()");
		super.onStop();
	}
	
	@Override
	protected void onRestart() {
		Log.v("FitnessTracker", "onRestart()");
		super.onRestart();
	}
	
	@Override
	protected void onDestroy() {
		Log.v("FitnessTracker", "onDestroy()");
		super.onDestroy();
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.v("FitnessTracker", "onCreate()");
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        processAppLaunchCount();
        readSomePreferences();
        
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
        
        createDB();
        readFromDB();
    }

	private void readFromDB() {
		Cursor cursor = db.query("activities",
				new String[] { "_id", "activitydate", "type" },
				null, null, null, null, "activitydate");
//		cursor = db.rawQuery("select * from activities order by activitydate", null);
		if (cursor.moveToFirst()) {
			do {
				long databaseId = cursor.getLong(0);
				long activityDate = cursor.getLong(1);
				String type = cursor.getString(cursor.getColumnIndex("type"));
				FitnessActivity fitnessActivity = new FitnessActivity(type,
						new Date(activityDate));
				fitnessActivity.setDatabaseId(databaseId);
				activityHistoryAdapter.add(fitnessActivity);
			} while (cursor.moveToNext());
		}
	}

	private void createDB() {
		db = new FitnessDBOpenHelper(this).getWritableDatabase();
	}

	private void readSomePreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean workOffline = prefs.getBoolean("work_offline", false);
		String serverName = prefs.getString("server_name", "localhost");
		// TODO
	}

	@Override
	public void onClick(View v) {
		String activity = (String) spinnerActivities.getSelectedItem();
		FitnessActivity fitnessActivity = new FitnessActivity(activity, new Date());
		activityHistoryAdapter.add(fitnessActivity);
		
		ContentValues values = new ContentValues();
		values.put("type", fitnessActivity.getActivity());
		values.put("activitydate", fitnessActivity.getWhen().getTime());
		long databaseId = db.insert("activities", null, values);
		if (databaseId == -1) {
			Log.e("MainActivity", "Error inserting new activity to DB");
		} else {
			fitnessActivity.setDatabaseId(databaseId);
		}
	}

}
