package com.selagroup.globalbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class AirplaneModeChangedBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getBooleanExtra("state", false)) {
		
			Intent launchIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:5551212"));
			launchIntent.setFlags(launchIntent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(launchIntent);
		
		}
		
		Log.v("Receiver", "Airplane mode changed");
		
	}

}
