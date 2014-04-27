
package com.cotopaxi.Cotopaxi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpecificChallengeListAdapter extends ArrayAdapter<Challenge>
{
	static class ViewHolder
	{
		public TextView text;
		public TextView points;
	}

	private final Context context;
	private final List<Challenge> items;

	public SpecificChallengeListAdapter(Context context, List<Challenge> items)
	{
		super(context, R.layout.specific_challenge_list_row, items);
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
			rowView = inflater.inflate(R.layout.specific_challenge_list_row, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.specific_challenge);
			viewHolder.points = (TextView) rowView.findViewById(R.id.points);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.text.setText(items.get(position).getDescription());
		holder.points.setText(items.get(position).getPoints() + "");

		if(items.get(position).isCompleted())
		{
			holder.text.setText("This challenge is completed:\n" + items.get(position).getDescription());
		}

		holder.text.setTag(items.get(position));

		return rowView;
	}
}
