package com.selagroup.fonts;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class FontsDemoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView text = (TextView)findViewById(R.id.theText);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Zapfino.ttf");
        text.setTypeface(font);
    }
}