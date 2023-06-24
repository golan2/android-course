package com.selagroup.tipcalculator;

import java.text.DecimalFormat;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class RestaurantTipCalculatorActivity extends Activity {
	
	private boolean roundTip = false;
	private boolean roundTotal = false;
	private double billAmount;
	private double billTip;
	private int selectedTipAmount;
	private int selectedPartySize;
	
	private EditText txtBillAmount;
	private Spinner spinnerTip;
	private Spinner spinnerParty;
	private ToggleButton tglBtnRoundTip;
	private ToggleButton tglBtnRoundTotal;
	private TextView txtMyTip;
	private TextView txtTotalTip;
	private TextView txtMyTotal;
	private TextView txtTotal;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        txtBillAmount = (EditText)findViewById(R.id.txtBillAmount);
        txtBillAmount.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
					txtBillAmount.clearFocus();
					return true;
				}
				return false;
			}
		});
        txtBillAmount.setOnFocusChangeListener(new OnFocusChangeListener() {		
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
				{
					billAmount = Double.parseDouble(txtBillAmount.getText().toString());
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	                imm.hideSoftInputFromWindow(txtBillAmount.getWindowToken(), 0);
					splitTheBill();
				}
			}
		});
        
        txtMyTip = (TextView)findViewById(R.id.txtMyTip);
        txtTotalTip = (TextView)findViewById(R.id.txtTotalTip);
        txtMyTotal = (TextView)findViewById(R.id.txtMyTotal);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        
        tglBtnRoundTip = (ToggleButton)findViewById(R.id.tglBtnRoundTip);
        tglBtnRoundTip.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				roundTip = tglBtnRoundTip.isChecked();
				splitTheBill();
			}
		});           
        tglBtnRoundTotal = (ToggleButton)findViewById(R.id.tglBtnRoundTotal);
        tglBtnRoundTotal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				roundTotal = tglBtnRoundTotal.isChecked();	
				splitTheBill();
			}
		});
        
        spinnerTip = (Spinner)findViewById(R.id.spinnerTip);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        		this, R.array.tipAmounts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTip.setAdapter(adapter);
        spinnerTip.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        		selectedTipAmount = (Integer.parseInt(parent.getItemAtPosition(pos).toString()));
        		splitTheBill();
        	}
			public void onNothingSelected(AdapterView<?> parent) {
				selectedTipAmount = 10;
				splitTheBill();
        	}
		});
        
        spinnerParty = (Spinner) findViewById(R.id.spinnerParty);
        adapter = ArrayAdapter.createFromResource(
        		this, R.array.partySize, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParty.setAdapter(adapter);
        spinnerParty.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        		selectedPartySize = (Integer.parseInt(parent.getItemAtPosition(pos).toString()));
        		splitTheBill();
        	}
			public void onNothingSelected(AdapterView<?> parent) {
				selectedPartySize = 1;
				splitTheBill();
        	}
		});
        
    }

	protected void splitTheBill() {
		billTip = billAmount * selectedTipAmount / 100;
		double myTip = billTip / selectedPartySize;
		double myTotal = billAmount / selectedPartySize + myTip;
		
		DecimalFormat formatter = new DecimalFormat("0.00");
		txtMyTip.setText("$" + (roundTip ? (Double.toString(Math.ceil(myTip))) : (formatter.format(myTip))));
		txtMyTotal.setText("$" + (roundTotal ? (Double.toString(Math.ceil(myTotal))) : (formatter.format(myTotal))));
		
		txtTotalTip.setText("$" + (roundTip ? (Double.toString(Math.ceil(billTip))) : (formatter.format(billTip))));
		txtTotal.setText("$" + (roundTotal ? (Double.toString(Math.ceil(billAmount + billTip))) : (formatter.format(billAmount + billTip))));
	}
}