package com.bnt.bntshine;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
class MenuItemAdapter extends ArrayAdapter<GlobalItem> {
	
	public MenuItemAdapter(Context context, List<GlobalItem> items) {
	    super(context, android.R.layout.select_dialog_item, items);
	}
	
    ViewHolder holder;

    class ViewHolder {
        ImageView icon;
        TextView title;
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    
		final LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.custom_menu_icon, null);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.menu_icon);
                    holder.title = (TextView) convertView
                    .findViewById(R.id.menu_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }       

        Drawable drawable = getContext().getResources().getDrawable(getItem(position).getIcon());

        holder.title.setText(getItem(position).getName());
        holder.icon.setImageDrawable(drawable);

        return convertView;
	}

}