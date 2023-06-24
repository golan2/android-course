package com.selagroup.compatdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CompatibilityDemoActivity extends FragmentActivity implements OnItemClickListener {
	private static final String[] items = new String[] {"Alex","Nicole","John","Sebastian","Alice"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        
        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    	//Nothing to do!!!
    	super.onConfigurationChanged(newConfig);
    }
    
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		CompatFragment fragment = new CompatFragment(items[pos]);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.details, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
}