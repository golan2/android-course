package com.selagroup.savingtransientstate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SavingTransientStateActivity extends Activity {
    
	private static final String UNSAVED_TEXT = "UnsavedText";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	EditText edit = (EditText)findViewById(R.id.myEditText);
    	outState.putString(UNSAVED_TEXT, edit.getText().toString());
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	if (savedInstanceState != null && savedInstanceState.containsKey(UNSAVED_TEXT)) {
    		String text = savedInstanceState.getString(UNSAVED_TEXT);
    		EditText edit = (EditText)findViewById(R.id.myEditText);
    		edit.setText(text);
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.menuPrefs:
    		showPreferences();
    		return true;
    	case R.id.showCurrentPref:
    		showCurrentPreference();
    		return true;
    	}
    	return false;
    }
    
    private void showPreferences() {
    	Intent showPrefs = new Intent(this, MyPreferencesActivity.class);
    	startActivity(showPrefs);
    }
    
    private void showCurrentPreference() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	String hostname = prefs.getString("host_name", "localhost");
    	Toast.makeText(this, "The host name is: " + hostname, Toast.LENGTH_LONG).show();
    }
    
}