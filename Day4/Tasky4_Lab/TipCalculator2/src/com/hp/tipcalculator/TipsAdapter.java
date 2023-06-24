package com.hp.tipcalculator;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TipsAdapter extends ArrayAdapter<TipTransaction> {

	public TipsAdapter(Context context, List<TipTransaction> tips) {
		super(context, -1, tips);
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TipTransaction tx = getItem(position);
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View row = inflater.inflate(R.layout.tip_row_2, parent, false);
		
		TextView textBillAmount = (TextView) row.findViewById(R.id.textBillAmount);
		TextView textTipAmount = (TextView) row.findViewById(R.id.textTipAmount);
		textBillAmount.setText(String.format("Bill amount: %2.2f", tx.getBillAmount()));
		textTipAmount.setText(String.format("Tip amount: %2.2f", tx.getTipAmount()));
		
		return row;
	}
	
}
