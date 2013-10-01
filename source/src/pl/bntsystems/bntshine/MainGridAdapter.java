package pl.bntsystems.bntshine;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGridAdapter;

public class MainGridAdapter implements PagedDragDropGridAdapter {
	private Context context;
	
	public MainGridAdapter(Context context) {
		super();
		
		this.context = context;
	}

	@Override
	public int pageCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int itemCountInPage(int page) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public View view(int page, int index) {
		LinearLayout layout = new LinearLayout(context);
		
		return layout;
	}

	@Override
	public int rowCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int columnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void printLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int deleteDropZoneLocation() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
