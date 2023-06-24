package com.selagroup.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class LinearLayoutActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayout);
        
        Button btn = (Button)findViewById(R.id.btnChangeLayout);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout layout = (LinearLayout)v.getParent();
				
				if (layout.getOrientation() == LinearLayout.HORIZONTAL)
					layout.setOrientation(LinearLayout.VERTICAL);
				else
					layout.setOrientation(LinearLayout.HORIZONTAL);
			}
		});
	}
}
