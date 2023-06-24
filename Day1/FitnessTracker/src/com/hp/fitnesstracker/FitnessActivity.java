package com.hp.fitnesstracker;

import java.util.Date;

public class FitnessActivity {

	private String activity;
	private Date when;
	
	public FitnessActivity(String activity, Date when) {
		this.activity = activity;
		this.when = when;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return this.activity + " at " + this.when.toGMTString();
	}
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	
}
