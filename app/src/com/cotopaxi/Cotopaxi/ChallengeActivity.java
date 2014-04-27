
package com.cotopaxi.Cotopaxi;

import java.util.List;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ChallengeActivity extends ActivityWithFooter
{
	private final Context me = this;

	private void copyHashtagsToClipboard()
	{
		Toast.makeText(this, "The required hashtag list has been copied to your clipboard", Toast.LENGTH_LONG).show();

		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText("hashtags", ((TextView) findViewById(R.id.required_hashtag_list)).getText().toString());
		clipboard.setPrimaryClip(clip);
	}

	public void donePressed(View view)
	{
		pushCompletion();

		Intent intent = new Intent(this, ChallengeCompletedActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(getResources().getString(R.string.points_awarded),
				getIntent().getExtras().getInt(getResources().getString(R.string.points_default)) + "");

		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}

	public void facebookPressed(View view)
	{
		copyHashtagsToClipboard();

		try
		{
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
			startActivity(LaunchIntent);
		}
		catch(Exception e)
		{
			NavigationUtils.displayMessage(this, "Please install Facebook", "Facebook not installed");
		}
	}

	public void instagramPressed(View view)
	{
		copyHashtagsToClipboard();

		try
		{
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
			startActivity(LaunchIntent);
		}
		catch(Exception e)
		{
			NavigationUtils.displayMessage(this, "Please install Instagram", "Instagram not installed");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge);

		Bundle bundle = getIntent().getExtras();
		String challenge = bundle.getString(getResources().getString(R.string.challenge));
		((TextView) findViewById(R.id.challenge_content)).setText(challenge);

		int points = bundle.getInt(getResources().getString(R.string.points_default));
		((TextView) findViewById(R.id.challenge_points)).setText(points + "pts.");

		String challengeHashTag = " " + bundle.getString(getResources().getString(R.string.required_hashtags));

		if(challengeHashTag == null || challengeHashTag.isEmpty() || challengeHashTag.equals(" null"))
		{
			challengeHashTag = "";
		}

		ParseUser user = ParseUser.getCurrentUser();
		ParseObject team = user.getParseObject("team");
		String teamName = "";

		if(team != null)
		{
			try
			{
				team.fetch();
			}
			catch(ParseException e)
			{
				NavigationUtils.displayMessage(this, e.getMessage(), "Challenge completion error");
			}

			teamName = "#" + team.getString("name") + " ";
		}

		((TextView) findViewById(R.id.required_hashtag_list)).setText(teamName + "#cotopaxi #microadventure" + challengeHashTag);
		getActionBar().setTitle(challengeHashTag);

	}

	private void pushCompletion()
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
		query.whereEqualTo("description", ((TextView) findViewById(R.id.challenge_content)).getText().toString());

		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> challengeList, ParseException e)
			{
				if(e == null)
				{
					final ParseObject completedChallenge = new ParseObject("CompletedChallenge");
					completedChallenge.put("challenge", challengeList.get(0));

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
									completedChallenge.put("user", ParseUser.getCurrentUser());
									updatePointsToUser(user);
								}
								else
								{
									completedChallenge.put("team", team);
									updatePointsToTeam(team);
								}

								completedChallenge.saveInBackground();
							}
							else
							{
								NavigationUtils.displayMessage(me, e.getMessage(), "Challenge complete submission error");
							}
						}
					});
				}
				else
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Challenge complete submission error");
				}
			}
		});
	}

	public void twitterPressed(View view)
	{
		copyHashtagsToClipboard();

		try
		{
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
			startActivity(LaunchIntent);
		}
		catch(Exception e)
		{
			NavigationUtils.displayMessage(this, "Please install Twitter", "Twitter not installed");
		}
	}

	private void updatePointsToTeam(ParseObject team)
	{
		int points = getIntent().getExtras().getInt(getResources().getString(R.string.points_default));

		points += team.getInt("points");
		team.put("points", points);
		team.saveInBackground();
	}

	private void updatePointsToUser(ParseUser user)
	{
		int points = getIntent().getExtras().getInt(getResources().getString(R.string.points_default));

		String userPoints = user.getString("points");

		if(userPoints == null || userPoints.equals("null") || userPoints.isEmpty())
		{
			userPoints = "0";
		}

		points += Integer.parseInt(userPoints);
		user.put("points", points + "");
		user.saveInBackground();
	}

	public void vinePressed(View view)
	{
		copyHashtagsToClipboard();

		try
		{
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("co.vine.android");
			startActivity(LaunchIntent);
		}
		catch(Exception e)
		{
			NavigationUtils.displayMessage(this, "Please install Vine", "Vine not installed");
		}
	}
}
