package com.selagroup.todo;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

public class CallToDoItem extends ToDoItem {

	private static final long serialVersionUID = 8848179160899486026L;
	private static final int MENU_ITEM_CALL = 1;
	
	private String toCall;
	
	public String getNumberToCall() {
		return toCall;
	}
	
	public CallToDoItem(String name, String numberToCall) {
		this(name, numberToCall, new Date(System.currentTimeMillis()));
	}
	
	public CallToDoItem(String name, String numberToCall, Date created) {
		super("Call " + name, created);
		toCall = numberToCall;
	}
	
	@Override
	public void buildContextMenu(ContextMenu menu) {
		super.buildContextMenu(menu);
		MenuItem callItem = menu.add(0, MENU_ITEM_CALL, Menu.NONE, "Call");
		callItem.setIcon(android.R.drawable.ic_menu_call);
	}
	
	@Override
	public boolean contextMenuClicked(Context context, int itemId) {
		if (itemId == MENU_ITEM_CALL) {
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + toCall));
			context.startActivity(intent);
			return true;
		}
		return false;
	}
}
