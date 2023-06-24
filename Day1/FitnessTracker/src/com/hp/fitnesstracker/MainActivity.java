package com.hp.fitnesstracker;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class MainActivity extends Activity implements OnClickListener {

    private Spinner spinnerActivities;
	private ArrayAdapter<FitnessActivity> activityHistoryAdapter;

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
    }

	@Override
	public void onClick(View v) {
		String activity = (String) spinnerActivities.getSelectedItem();
		activityHistoryAdapter.add(new FitnessActivity(activity, new Date()));
	}

}
