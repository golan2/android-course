package com.hp.supersimplecalculator;

import java.util.Collection;
import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

    private EditText input1;
	private EditText input2;
	private TextView result;

	private Stack<String> undoStack = new Stack<String>();
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("lastResult", result.getText().toString());
		outState.putSerializable("undoStack", undoStack);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState.containsKey("lastResult")) {
			String lastResult = savedInstanceState.getString("lastResult");
			result.setText(lastResult);
		}
		if (savedInstanceState.containsKey("undoStack")) {
			Collection<String> collection = (Collection<String>) savedInstanceState.getSerializable("undoStack");
			undoStack.addAll(collection);
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	input1 = (EditText) findViewById(R.id.input1);
    	input2 = (EditText) findViewById(R.id.input2);
    	result = (TextView) findViewById(R.id.result);
        
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.undo).setOnClickListener(this);
    }
    
    private void add() {
    	String oldResult = result.getText().toString();
    	if (oldResult.length() > 0) {
    		undoStack.push(oldResult);
    	}
    	
    	double a = Double.parseDouble(input1.getText().toString());
    	double b = Double.parseDouble(input2.getText().toString());
    	String sum = String.format("%2.5f", a + b);
		result.setText(sum);
    }
    
    private void undo() {
    	if (undoStack.size() == 0)
    		result.setText("");
    	else
    		result.setText(undoStack.pop());
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			add();
			break;
		case R.id.undo:
			undo();
			break;
		}
	}

}
