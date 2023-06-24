package com.hp.helloworld;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    private Button buttonClickMe;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonClickMe = (Button) findViewById(R.id.buttonClickMe);
        buttonClickMe.setOnClickListener(this);
        
        String message = getResources().getString(R.string.message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

	@Override
	public void onClick(View v) {
		buttonClickMe.setText("Title from code");
        buttonClickMe.setBackgroundColor(Color.CYAN);
	}

}
