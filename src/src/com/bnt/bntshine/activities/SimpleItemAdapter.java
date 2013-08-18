package com.bnt.bntshine.activities;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnt.bntshine.GlobalItem;
import com.bnt.bntshine.R;


public class SimpleItemAdapter extends ArrayAdapter<GlobalItem> {
	private boolean isCustomNames = false;
	//TODO: IT IS THE WORST HACK EVER!!!
	public SimpleItemAdapter(Context context, List<GlobalItem> items, boolean isCustomNames) {
	    super(context, android.R.layout.select_dialog_item, items);
	    this.isCustomNames = isCustomNames;
	}
	
    ViewHolder holder;

    class ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
    }
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    
		final LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.custom_menu_simple, null);

            holder = new ViewHolder();
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.menu_icon);
            holder.title = (TextView) convertView
                    .findViewById(R.id.menu_title);
            holder.description = (TextView) convertView
            		.findViewById(R.id.menu_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }       

        final GlobalItem currItem = getItem(position);
        
        Drawable drawable = getContext().getResources().getDrawable(getItem(position).getIcon());

        holder.title.setText((isCustomNames && !currItem.getUserName().equals("") ? "Nazwa użytkownika: " + currItem.getUserName() + " " : "") + "Nazwa główna: " + currItem.getName());
        holder.icon.setImageDrawable(drawable);
        holder.description.setText((currItem.getAddress()>=0 ? "Adres: " + currItem.getAddress() : "") +
        		" Grupa: " + currItem.getGroup() + " Typ: " + GlobalItem.getTypeName(currItem.getType()));
        return convertView;
	}
}
