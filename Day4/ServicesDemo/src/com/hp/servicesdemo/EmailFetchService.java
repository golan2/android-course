package com.hp.servicesdemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class EmailFetchService extends IntentService {

	public EmailFetchService() {
		super("Email fetch service thread");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			Log.i("EmailFetchService", "Fetching email started.");
			Thread.sleep(5000);
			Log.i("EmailFetchService", "Fetching email completed.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Intent resultIntent = new Intent("com.hp.servicesdemo.EMAIL_FETCH_DONE_BROADCAST");
		resultIntent.putExtra("newEmailCount", 42);
		sendBroadcast(resultIntent);
	}

}
