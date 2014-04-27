
package com.cotopaxi.Cotopaxi;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public abstract class ActivityWithFooter extends BaseActivity
{
	private final Context me = this;

	@Override
	public void goForTheGoodPressed(View view)
	{
		NavigationUtils.visitWebsite(this, Uri.parse(getResources().getString(R.string.cotopaxi_url)));
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		final ParseUser user = ParseUser.getCurrentUser();
		user.fetchInBackground(new GetCallback<ParseObject>()
		{
			@Override
			public void done(ParseObject object, ParseException e)
			{
				if(e == null)
				{
					ParseObject team = object.getParseObject("team");

					if(team == null)
					{
						updatePointsFromUser(user);
					}
					else
					{
						team.fetchInBackground(new GetCallback<ParseObject>()
						{
							@Override
							public void done(ParseObject object, ParseException e)
							{
								if(e == null)
								{
									updatePointsFromTeam(object);
								}
								else
								{
									NavigationUtils.displayMessage(me, e.getMessage(), "Problem updating points");
								}
							}
						});
					}
				}
				else
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Problem updating points");
				}
			}
		});
	}

	private void updatePointsFromTeam(ParseObject team)
	{
		TextView pointsCounter = ((TextView) findViewById(R.id.total_points_counter));
		pointsCounter.setText(team.getInt("points") + "");
	}

	private void updatePointsFromUser(ParseUser user)
	{
		TextView pointsCounter = ((TextView) findViewById(R.id.total_points_counter));
		String points = user.getString("points");

		if(points == null || points.equals("null") || points.isEmpty())
		{
			points = "0";
		}

		pointsCounter.setText(points + "");
	}
}
