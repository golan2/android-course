package com.hp.tipcalculator;

public class TipTransaction {

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
