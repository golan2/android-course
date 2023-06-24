package com.hp.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AudioPlaybackService extends Service {

	public void changeVolume(int newVolume) {}
	public void nextTrack() { Log.i("AudioPlaybackService", "Next track."); }
	public void previousTrack() { Log.i("AudioPlaybackService", "Previous track."); }
	public void shuffle() {}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO play music
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO stop playback
		super.onDestroy();
	}
	
	public class MyBinder extends Binder {
		public AudioPlaybackService getService() {
			return AudioPlaybackService.this;
		}
	}
	
	private MyBinder binder = new MyBinder();
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

}
