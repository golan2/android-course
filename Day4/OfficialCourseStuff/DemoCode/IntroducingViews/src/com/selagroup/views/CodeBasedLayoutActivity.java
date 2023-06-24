package com.selagroup.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CodeBasedLayoutActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
		LinearLayout ll = new LinearLayout(this); 
		ll.setOrientation(LinearLayout.VERTICAL);
		
		TextView myTextView = new TextView(this); 
		EditText myEditText = new EditText(this);
		
		myTextView.setText("Some input field:"); 
		myEditText.setText("Hello from dynamic layout!");
		
		int lHeight = LinearLayout.LayoutParams.FILL_PARENT; 
		int lWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
		
		ll.addView(myTextView, new LinearLayout.LayoutParams(lHeight, lWidth)); 
		ll.addView(myEditText, new LinearLayout.LayoutParams(lHeight, lWidth)); 
		
		setContentView(ll);
	}
}
