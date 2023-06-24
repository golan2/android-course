package com.selagroup.ListAndSelectorsDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn = (Button)findViewById(R.id.btnListView);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ListViewDemoActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnSpinner);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SpinnerDemoActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnGridView);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), GridViewDemoActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnAutoComplete);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AutoCompleteDemoActivity.class);
				startActivity(intent);
			}
		});
		
    }
}