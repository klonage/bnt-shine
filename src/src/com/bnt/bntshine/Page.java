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
		if (!items.contains(item))
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
		if (item.getAddress() == -1) {
			deleteGroupItem(item);
		} else {
			deleteDeviceItem(item);
		}
	}
	
	private void deleteGroupItem(GlobalItem item) {
		for (int i = 0; i < itemsCount(); i++) {
			if (getItem(i).getGroup() == item.getGroup() && 
					getItem(i).getAddress() == item.getAddress() && 
					getItem(i).getType() == item.getType()) {
				items.remove(i);
				return;
			}
		}
	}

	private void deleteDeviceItem(GlobalItem item) {
		items.remove(item);
	}

	public int itemsCount() {
		return items.size();
	}
	

	public GlobalItem getItem(int index) {
		return items.get(index);
	}
}
