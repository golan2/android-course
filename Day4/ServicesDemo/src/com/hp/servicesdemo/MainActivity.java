package com.hp.servicesdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.downloadFileButton).setOnClickListener(this);
        findViewById(R.id.startPlaybackButton).setOnClickListener(this);
        findViewById(R.id.stopPlaybackButton).setOnClickListener(this);
        findViewById(R.id.fetchEmailButton).setOnClickListener(this);
        findViewById(R.id.previousTrackButton).setOnClickListener(this);
        findViewById(R.id.nextTrackButton).setOnClickListener(this);
    }
    
	private AudioPlaybackService service;
    
    private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			Log.i("MainActivity", "Established binding to service");
			service = ((AudioPlaybackService.MyBinder)binder).getService();
		}
	}; 
    
    @Override
    protected void onResume() {
    	bindService(new Intent(this, AudioPlaybackService.class), serviceConnection, 0);
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	unbindService(serviceConnection);
    	super.onPause();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.downloadFileButton:
			Intent startIntent = new Intent(this, FileDownloaderService.class);
			startIntent.putExtra("fileURL", "http://www.google.com/");
			startService(startIntent);
			break;
		case R.id.previousTrackButton:
			service.previousTrack();
			break;
		case R.id.nextTrackButton:
			service.nextTrack();
			break;
		case R.id.startPlaybackButton:
			Intent intent = new Intent(this, AudioPlaybackService.class);
			startService(intent);
			break;
		case R.id.stopPlaybackButton:
			Intent stopIntent = new Intent(this, AudioPlaybackService.class);
			stopService(stopIntent);
			break;
		case R.id.fetchEmailButton:
			emailFetchResultReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					int newEmailCount = intent.getIntExtra("newEmailCount", -1);
					((TextView)findViewById(R.id.newEmailCountTextView)).setText(
							String.format("You have %d new emails", newEmailCount));
				}
			};
			registerReceiver(emailFetchResultReceiver,
					new IntentFilter("com.hp.servicesdemo.EMAIL_FETCH_DONE_BROADCAST"));
			Intent emailIntent = new Intent(this, EmailFetchService.class);
			startService(emailIntent);
			break;
		}
	}
	
	private BroadcastReceiver emailFetchResultReceiver;

}
