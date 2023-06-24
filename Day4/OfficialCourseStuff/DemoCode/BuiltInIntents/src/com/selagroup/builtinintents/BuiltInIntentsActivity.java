package com.selagroup.builtinintents;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BuiltInIntentsActivity extends Activity {
	
	private static final int SELECT_PICTURE = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.dialButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:555-1212"));
				startActivity(dialIntent);
			}
        });
        findViewById(R.id.geoButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:51.0,0.0"));
				startActivity(geoIntent);
			}
        });
        findViewById(R.id.webSearchButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH, null);
				searchIntent.putExtra(SearchManager.QUERY, "SELA Group");
				startActivity(searchIntent);
			}
        });
        findViewById(R.id.picturePickButton).setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		        intent.setType("image/*");
		        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        	}
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
    		Uri uri = data.getData();
    		ImageView imageView = (ImageView)findViewById(R.id.mainImageView);
    		imageView.setImageURI(uri);
    	}
    }
    
}