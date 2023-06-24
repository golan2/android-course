package com.selagroup.adapters;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PersonAdapter extends ArrayAdapter<Person> {

	private Activity context;
	private List<Person> people;
	
	public PersonAdapter(Activity ctx, List<Person> ppl) {
		super(ctx, R.layout.new_row_layout, ppl);
		context = ctx;
		people = ppl;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			convertView = inflater.inflate(R.layout.new_row_layout, null);
		}
		TextView nameText = (TextView)convertView.findViewById(R.id.nameTextView);
		Person person = people.get(position);
		nameText.setText(person.getName());
		TextView locationText = (TextView)convertView.findViewById(R.id.locationTextView);
		locationText.setText(person.getLocation());
		return convertView;
	}

}
