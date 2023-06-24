package com.selagroup.todo;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ToDoPreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
