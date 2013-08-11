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
	
	List<Page> pages = new ArrayList<Page>();
	
	public MainGridAdapter(Context context, PagedDragDropGrid gridview) {
		super();
		this.context = context;
		this.gridview = gridview;
		
		Page page1 = new Page();
		pages.add(page1);
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
		icon.setPadding(15, 15, 15, 15);
		
		layout.addView(icon);
		
		TextView label = new TextView(context);
		label.setTag("text");
		label.setText(item.getName());	
		label.setTextColor(Color.BLACK);
		label.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
	
		label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		// only set selector on every other page for demo purposes
		// if you do not wish to use the selector functionality, simply disregard this code
		if(page % 2 == 0) {
    		setViewBackground(layout);
    		layout.setClickable(true);
    		layout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return gridview.onLongClick(v);
                }
    		});
		}

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
		List<GlobalItem> items = itemsInPage(page);
		return items.get(index);
	}
	
	public boolean hasItem(GlobalItem item) {
		for (int i = 0; i < pageCount(); i++) {
			if (pages.get(i).getItems().contains(item))
				return true;
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

	private Page getPage(int pageIndex) {
		return pages.get(pageIndex);
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		getPage(pageIndex).swapItems(itemIndexA, itemIndexB);
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
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		getPage(pageIndex).deleteItem(itemIndex);
	}
	
	public void deleteItem(int pageIndex, GlobalItem item) {
		getPage(pageIndex).deleteItem(item);
	}

    @Override
    public int deleteDropZoneLocation() {        
        return BOTTOM;
    }
    
    public void addToCurrentPage(GlobalItem item) {
    	pages.get(0).addItem(item);
    }
}
