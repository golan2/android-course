package com.selagroup.usingresources;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UsingResourcesSampleActivity extends Activity {
	AnimationDrawable walkingAnimation;
	
    @SuppressWarnings("unused")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		//Use styled resources as-is
		Toast.makeText(this, R.string.styled_hello, Toast.LENGTH_SHORT).show();
		
		//Use styled resources and replace "%1$s"-style placeholders from resources 
		String strHello = getString(R.string.styled_for_code);
		String sHello = String.format(strHello, "Student");
		CharSequence styledSequence = Html.fromHtml(sHello);
		
		Toast.makeText(this, styledSequence, Toast.LENGTH_LONG).show();
		
		//Resources loading sample
		Resources res = getResources();
		//Simple arrays
		String[] streets = res.getStringArray(R.array.streets);
		int[] ages = res.getIntArray(R.array.ages);
		//Typed arrays
		TypedArray names = res.obtainTypedArray(R.array.names);
		String firstName = names.getString(0);
		TypedArray scores = res.obtainTypedArray(R.array.scores);
		int secondScore = scores.getInt(1, -1);
		TypedArray colors = res.obtainTypedArray(R.array.colors);
		int thirdColor = colors.getColor(2, 0);
		
		//Tweened animation
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.simpleanimation);
		TextView text = (TextView) findViewById(R.id.text);
		text.startAnimation(anim);
		
		//Frame-by-frame animation
		ImageView image = (ImageView)findViewById(R.id.walking_animation);
		image.setBackgroundResource(R.drawable.walking);
		walkingAnimation = (AnimationDrawable)image.getBackground();
		//NOTE: Frame-by-frame animation cannot be started during onCreate handler,
		//since the View is not fully initialized at this stage
		
		//Using system resources from code
		CharSequence noPhoneNumberProvided = getString(android.R.string.emptyPhoneNumber); 
		Toast.makeText(this, noPhoneNumberProvided, Toast.LENGTH_LONG).show();
    }
    
    public boolean onTouchEvent(MotionEvent event) {
    	  if (event.getAction() == MotionEvent.ACTION_DOWN) {
			  if (!walkingAnimation.isRunning()) {
				  walkingAnimation.start();
			  } else {
				  walkingAnimation.stop();
			  }
    	    return true;
    	  }
    	  return super.onTouchEvent(event);
	}
}