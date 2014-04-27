
package com.cotopaxi.Cotopaxi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PledgeintroActivity extends BaseActivity
{
	private final Context me = this;

	public void myPledgesPressed(View view)
	{
		startActivity(new Intent(this, MyPledgesActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pledge_intro);
		getActionBar().setTitle(R.string.the_pledge);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		final TextView pledgesCounter = (TextView) findViewById(R.id.pledges_made_counter);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Pledge");
		query.countInBackground(new CountCallback()
		{
			@Override
			public void done(int count, ParseException e)
			{
				if(e == null)
				{
					pledgesCounter.setText(count + "");
				}
				else
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Problem updating pledge count");
				}
			}
		});
	}

	public void pledgePressed(View view)
	{
		Intent intent = new Intent(this, ComposePledgeActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(getResources().getString(R.string.required_hashtags),
				getIntent().getExtras().getString(getResources().getString(R.string.required_hashtags)));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
