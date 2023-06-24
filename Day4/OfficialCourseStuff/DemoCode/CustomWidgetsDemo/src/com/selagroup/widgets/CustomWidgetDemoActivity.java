package com.selagroup.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CustomWidgetDemoActivity extends Activity {
	
	private RoundThermometer thermometer;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LoginView loginView = (LoginView)findViewById(R.id.login);
        loginView.setOnLoginCompletedListener(new OnLoginCompletedListener() {
			@Override
			public void loginCompleted(LoginView loginView, boolean successfullyLoggedIn) {
				Toast.makeText(CustomWidgetDemoActivity.this,
						"Logged in successfully? " + successfullyLoggedIn,
						Toast.LENGTH_LONG).show();
			}
		});
        
        SeekBar sb = (SeekBar)findViewById(R.id.seekbar);
        sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				thermometer.SetTemperature(progress - 40);
				
			}
		});
        
        thermometer = (RoundThermometer)findViewById(R.id.thermometer);
        thermometer.SetTemperature(sb.getProgress() - 40);
    }
}