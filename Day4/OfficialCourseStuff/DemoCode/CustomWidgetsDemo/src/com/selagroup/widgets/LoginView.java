package com.selagroup.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginView extends LinearLayout {

	private OnLoginCompletedListener myListener;

	public LoginView(Context context) {
		super(context);
	}

	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setOnLoginCompletedListener(OnLoginCompletedListener listener) {
		myListener = listener;
	}

	{
		Context context = getContext();
		setFocusable(true);
		
		LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.loginview, null);
		
		final EditText edtFirstName = (EditText) v.findViewById(R.id.edtFirstName);
		final EditText edtLastName = (EditText) v.findViewById(R.id.edtLastName);
		
		Button btnSubmit = (Button)v.findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edtFirstName.getText().toString().equals("Sasha") &&
					edtLastName.getText().toString().equals("Goldshtein")) {
					myListener.loginCompleted(LoginView.this, true);
				} else {
					myListener.loginCompleted(LoginView.this, false);
				}
			}
		});
		
		this.addView(v);
	}
}
