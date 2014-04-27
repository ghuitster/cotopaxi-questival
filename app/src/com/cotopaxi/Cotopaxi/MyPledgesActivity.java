
package com.cotopaxi.Cotopaxi;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MyPledgesActivity extends ListActivity
{
	private final Context me = this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pledges);
		getActionBar().setTitle(R.string.my_pledges);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Pledge");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> pledges, ParseException e)
			{
				if(e == null)
				{
					ArrayList<String> entries = new ArrayList<String>();

					for(ParseObject p: pledges)
					{
						entries.add(p.getString("content"));
					}

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(me, R.layout.my_pledge_list_row, entries);

					setListAdapter(adapter);
				}
				else
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Problem viewing your pledges");
				}
			}
		});
	}
}
