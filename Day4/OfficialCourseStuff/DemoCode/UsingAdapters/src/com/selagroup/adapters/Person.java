package com.selagroup.adapters;

public class Person {

	private String name;
	private String location;
	
	public String getName() { 
		return name;
	}
	public String getLocation() {
		return location;
	}
	
	public Person(String nm, String loc) {
		name = nm;
		location = loc;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
