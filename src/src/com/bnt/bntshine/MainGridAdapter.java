package com.bnt.bntshine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bnt.bntshine.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGridAdapter;

public class MainGridAdapter implements PagedDragDropGridAdapter {

	private Context context;
	private PagedDragDropGrid gridview;
	
	ProfileManager profileManager;
	List<Page> pages = new ArrayList<Page>();
	
	public MainGridAdapter(Context context, PagedDragDropGrid gridview, int pageCount) {
		super();
		this.context = context;
		this.gridview = gridview;
		
		for (int i = 0; i < pageCount; i++) {
			pages.add(new Page());
		}
	}
	
	public void notifyParentOnChange() {
		gridview.notifyDataSetChanged();
	}
	
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

	@Override
	public int pageCount() {
		return pages.size();
	}

	private List<GlobalItem> itemsInPage(int page) {
		if (pages.size() > page) {
			return pages.get(page).getItems();
		}	
		return Collections.emptyList();
	}

    @Override
	public View view(int page, int index) {
		
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		ImageView icon = new ImageView(context);
		GlobalItem item = getItem(page, index);
		icon.setImageResource(item.getIcon());
		icon.setPadding(3, 3, 3, 3);
		
		layout.addView(icon);
		
		TextView label = new TextView(context);
		label.setTag("text");
		label.setWidth(150);
		label.setMinHeight(65);
		label.setTextSize(12);
		label.setText(item.getUserName().equals("") ? item.getName() : item.getUserName());	
		label.setTextColor(Color.WHITE);
		label.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
	
		label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		setViewBackground(layout);
		layout.setClickable(true);
		layout.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return gridview.onLongClick(v);
			}
		});


		layout.addView(label);
		return layout;
	}

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViewBackground(LinearLayout layout) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            layout.setBackground(context.getResources().getDrawable(R.drawable.list_selector_holo_light));
        }
    }

	public GlobalItem getItem(int page, int index) {
		try {
			List<GlobalItem> items = itemsInPage(page);
			return items.get(index);
		} catch (Exception exc) {
			return null;
		}
	}
	
	public boolean hasItem(GlobalItem item) {
		if (item.getAddress() == -1) {
			return hasGroupItem(item);
		} else {
			return hasDeviceItem(item);
		}
	}

	private boolean hasDeviceItem(GlobalItem item) {
		for (int i = 0; i < pageCount(); i++) {
			if (pages.get(i).getItems().contains(item))
				return true;
		}
		
		return false;
	}

	private boolean hasGroupItem(GlobalItem item) {
		for (int i = 0; i < pageCount(); i++) {
			for (int j = 0; j < pages.get(i).itemsCount(); j++) {
				GlobalItem it = pages.get(i).getItem(j);
				if (it.getGroup() == item.getGroup() && it.getType() == item.getType())
					return true;	
			}
		}
		
		return false;
	}

	@Override
	public int rowCount() {
		return AUTOMATIC;
	}

	@Override
	public int columnCount() {
		return AUTOMATIC;
	}

	@Override
	public int itemCountInPage(int page) {
		return itemsInPage(page).size();
	}

	public void printLayout() {
		int i=0;
		for (Page page : pages) {
			Log.d("Page", Integer.toString(i++));
			
			for (GlobalItem item : page.getItems()) {
				Log.d("Item", item.getName());
			}
		}
	}

	public Page getPage(int pageIndex) {
		return pages.get(pageIndex);
	}
	
	public List<Page> getPages() {
		return pages;
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		getPage(pageIndex).swapItems(itemIndexA, itemIndexB);
		profileManager.saveToConfigFile();
	} 

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		int leftPageIndex = pageIndex-1;
		if (leftPageIndex >= 0) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(leftPageIndex);
			
			GlobalItem item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);	
		}	
		profileManager.saveToConfigFile();
	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		int rightPageIndex = pageIndex+1;
		if (rightPageIndex < pageCount()) {
			Page startpage = getPage(pageIndex);
			Page landingPage = getPage(rightPageIndex);
			
			GlobalItem item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);			
		}	
		profileManager.saveToConfigFile();
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		getPage(pageIndex).deleteItem(itemIndex);
		profileManager.saveToConfigFile();
	}
	
	public void deleteItem(GlobalItem item) {
		for (int i = 0; i < pageCount(); i++) {
			if (item.getAddress() == -1) {
				getPage(i).deleteItem(item);
			}else if (getPage(i).getItems().contains(item)) {
				getPage(i).deleteItem(item);
				return;
			}
		}
		profileManager.saveToConfigFile();
	}

    @Override
    public int deleteDropZoneLocation() {        
        return BOTTOM;
    }
    
    public void addToPage(int page, GlobalItem item) {
    	getPage(page).addItem(item);
    	profileManager.saveToConfigFile();
    }
}
