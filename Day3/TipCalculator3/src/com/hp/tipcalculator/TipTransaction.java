package com.hp.tipcalculator;

import java.io.Serializable;

public class TipTransaction implements Serializable {

	private static final long serialVersionUID = 6879263026167121463L;
	
	private double billAmount;
	private double tipAmount;
	
	public TipTransaction(double billAmount, double tipAmount) {
		this.billAmount = billAmount;
		this.tipAmount = tipAmount;
	}
	
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	public double getTipAmount() {
		return tipAmount;
	}
	public void setTipAmount(double tipAmount) {
		this.tipAmount = tipAmount;
	}
	
}
