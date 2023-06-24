package com.selagroup.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopupsDemoActivity extends Activity {
    
	private TextView alertResult;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        alertResult = (TextView)findViewById(R.id.alertResult);
        
        Button btn = (Button)findViewById(R.id.btnToast);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), R.string.toast_text, Toast.LENGTH_LONG).show();
			}
		});
        
        btn = (Button)findViewById(R.id.btnAlert);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle(R.string.alert_title)
					   .setMessage(getString(R.string.alert_message))
					   .setCancelable(false)
					   .setIcon(R.drawable.icon)
					   .setPositiveButton("Oh Yes!", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alertResult.setText("You love alerts, are you?");
								
							}
					   })
					   .setNegativeButton("Oh No!", new DialogInterface.OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							alertResult.setText("You hate alerts, are you?");
							dialog.cancel();
						}
					   });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
    }
}