package com.selagroup.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntroducingViewsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Wire buttons
        Button btn = (Button)findViewById(R.id.btnFrameLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FrameLayoutActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnLinearLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LinearLayoutActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnRelativeLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RelativeLayoutActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnTableLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TableLayoutActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnMixedLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), MixedLayoutsActivity.class);
				startActivity(intent);
			}
		});
        
        btn = (Button)findViewById(R.id.btnCodeLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CodeBasedLayoutActivity.class);
				startActivity(intent);
			}
		});
    }
}