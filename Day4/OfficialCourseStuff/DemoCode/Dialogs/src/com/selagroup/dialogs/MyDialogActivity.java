package com.selagroup.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.QuickContactBadge;

public class MyDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity);
		QuickContactBadge badge = (QuickContactBadge)findViewById(R.id.contactBadge);
		badge.assignContactFromPhone("555-1212", false);
	}
	
}
