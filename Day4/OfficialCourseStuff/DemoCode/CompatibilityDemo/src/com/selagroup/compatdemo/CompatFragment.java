package com.selagroup.compatdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CompatFragment extends Fragment {
	private String theVal = "";
	
	public CompatFragment() {
	}
	
	public CompatFragment(String selection){
		theVal = selection;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment, container,false);
		TextView txt = (TextView)view.findViewById(R.id.txt);
		txt.setText(theVal);
		
		return view;
	}
}
