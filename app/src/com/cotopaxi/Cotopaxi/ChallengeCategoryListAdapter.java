
package com.cotopaxi.Cotopaxi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChallengeCategoryListAdapter extends ArrayAdapter<String>
{
	static class ViewHolder
	{
		public TextView text;
	}

	private final Context context;
	private final List<String> items;

	public ChallengeCategoryListAdapter(Context context, List<String> items)
	{
		super(context, R.layout.challenge_list_row, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;

		if(rowView == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.challenge_list_row, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.challenge);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.text.setCompoundDrawablePadding(20);
		holder.text.setText(items.get(position));
		Drawable icon = null;

		switch(position)
		{
		case 0:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.adventure_sml);
			break;
		}
		case 1:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.camp_sml);
			break;
		}
		case 2:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.community_sml);
			break;
		}
		case 3:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.hikes_sml);
			break;
		}
		case 4:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.quirky_sml);
			break;
		}
		case 5:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.service_sml);
			break;
		}
		case 6:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.socialmedia_sml);
			break;
		}
		case 7:
		{
			icon = ((Activity) context).getResources().getDrawable(R.drawable.survival_sml);
			break;
		}
		}

		icon.setBounds(-5, -5, 64, 64);
		holder.text.setCompoundDrawables(icon, null, null, null);

		return rowView;
	}
}
