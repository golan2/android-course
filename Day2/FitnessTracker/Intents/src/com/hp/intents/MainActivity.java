package com.hp.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;


public class MainActivity extends Activity implements OnClickListener {

    private ImageView imagePicture;
	private EditText editPhone;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        imagePicture = (ImageView) findViewById(R.id.imagePicture);
        editPhone = (EditText) findViewById(R.id.editPhone);
        
        findViewById(R.id.buttonDial).setOnClickListener(this);
        findViewById(R.id.buttonPickPicture).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonDial:
			doSomething();
			break;
		case R.id.buttonPickPicture:
			pickPicture();
			break;
		}
	}

	private void doSomething() {
		String what = editPhone.getText().toString();
		Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(what));
		startActivity(viewIntent);
		
//		String phone = editPhone.getText().toString();
//		Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//		startActivity(dialIntent);
	}

	private void pickPicture() {
		Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
		pickIntent.setType("image/*"); // MIME type: image/png, image/jpeg, image/gif, ...
		startActivityForResult(pickIntent, 42);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent result) {
		if (resultCode == RESULT_OK) { // or RESULT_CANCELED
			imagePicture.setImageURI(result.getData() /*Uri*/);
		}
		super.onActivityResult(requestCode, resultCode, result);
	}

}
