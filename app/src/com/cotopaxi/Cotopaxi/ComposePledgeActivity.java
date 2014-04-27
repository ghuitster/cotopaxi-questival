
package com.cotopaxi.Cotopaxi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ComposePledgeActivity extends Activity
{
	private final Context me = this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_pledge);
		getActionBar().setTitle(R.string.the_pledge);

		String team = getIntent().getExtras().getString(getResources().getString(R.string.required_hashtags));

		if(team.equals(getResources().getString(R.string.no_team)))
		{
			((TextView) findViewById(R.id.required_hashtag_list)).setText("#microadventure #gearforgood");
		}
		else
		{
			((TextView) findViewById(R.id.required_hashtag_list)).setText("#microadventure #gearforgood " + team);
		}
	}

	public void pledgePressed(View view)
	{
		EditText pledgeEntry = (EditText) findViewById(R.id.pledge_entry);

		ParseObject pledge = new ParseObject("Pledge");
		pledge.put("content", pledgeEntry.getText().toString());
		pledge.put("user", ParseUser.getCurrentUser());
		pledge.put("hashTag", ((TextView) findViewById(R.id.required_hashtag_list)).getText().toString());
		pledge.saveInBackground(new SaveCallback()
		{
			@Override
			public void done(ParseException e)
			{
				if(e != null)
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Problem saving pledge");
				}
			}
		});

		Toast.makeText(this, "Go for the good!", Toast.LENGTH_LONG).show();
		finish();
	}
}
