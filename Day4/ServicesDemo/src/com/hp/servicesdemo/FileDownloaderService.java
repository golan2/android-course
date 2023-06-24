package com.hp.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FileDownloaderService extends Service {

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				doActualDownload(intent);				
			}
		});
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	private void doActualDownload(Intent intent) {
		Log.i("FileDownloaderService",
				"Requested to download file: " + intent.getStringExtra("fileURL"));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i("FileDownloaderService", "Download completed.");
		stopSelf();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
