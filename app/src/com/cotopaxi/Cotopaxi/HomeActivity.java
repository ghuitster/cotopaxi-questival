
package com.cotopaxi.Cotopaxi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class HomeActivity extends BaseActivity
{
	public void microAdventurePressed(View view)
	{
		startActivity(new Intent(this, ChallengeCategoryListActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		ParseUser user = ParseUser.getCurrentUser();
		ParseObject team = user.getParseObject("team");

		if(team != null)
		{
			try
			{
				team.fetch();
			}
			catch(ParseException e)
			{
				NavigationUtils.displayMessage(this, e.getMessage(), "Log in error");
			}

			((TextView) findViewById(R.id.team_name)).setText("#" + team.getString("name"));
		}
		else
		{
			((TextView) findViewById(R.id.team_name)).setTextSize(15);
			((TextView) findViewById(R.id.team_name)).setText(R.string.no_team);
		}
	}

	public void pledgeForGoodPressed(View view)
	{
		Intent intent = new Intent(this, PledgeintroActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(getResources().getString(R.string.required_hashtags), ((TextView) findViewById(R.id.team_name)).getText().toString());
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
