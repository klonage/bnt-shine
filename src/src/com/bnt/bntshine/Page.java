package com.bnt.bntshine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Page {

	private List<GlobalItem> items = new ArrayList<GlobalItem>();

	public List<GlobalItem> getItems() {
		return items;
	}

	public void setItems(List<GlobalItem> items) {
		this.items = items;
	}
	
	public void addItem(GlobalItem item) {
		items.add(item);
	}
	
	public void swapItems(int itemA, int itemB) {
		Collections.swap(items, itemA, itemB);
	}

	public GlobalItem removeItem(int itemIndex) {
		GlobalItem item = items.get(itemIndex);
		items.remove(itemIndex);
		return item;
	}

	public void deleteItem(int itemIndex) {
		items.remove(itemIndex);
	}
	
	public void deleteItem(GlobalItem item) {
		items.remove(item);
	}
}
