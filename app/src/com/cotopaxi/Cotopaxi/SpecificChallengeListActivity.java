
package com.cotopaxi.Cotopaxi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class SpecificChallengeListActivity extends ActivityWithFooter
{
	private final Activity me = this;
	ArrayList<Challenge> mEntries = null;
	SpecificChallengeListAdapter mAdapter = null;

	private boolean findIfChallengeCompleted(ParseObject challenge, List<ParseObject> completedChallengeList)
	{
		for(ParseObject completedChallenge: completedChallengeList)
		{
			if(completedChallenge.getParseObject("challenge").getObjectId().equals(challenge.getObjectId()))
			{
				return true;
			}
		}

		return false;
	}

	protected void markChallengeIncomplete(Challenge challenge)
	{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Challenge");
		query.whereEqualTo("description", challenge.getDescription());
		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> challengeList, ParseException e)
			{
				if(e == null)
				{
					final int points = challengeList.get(0).getInt("points");
					final ParseQuery<ParseObject> query = ParseQuery.getQuery("CompletedChallenge");
					query.whereEqualTo("challenge", challengeList.get(0));

					final ParseUser user = ParseUser.getCurrentUser();
					user.fetchInBackground(new GetCallback<ParseObject>()
					{
						@Override
						public void done(ParseObject user, ParseException e)
						{
							if(e == null)
							{
								ParseObject team = user.getParseObject("team");

								if(team == null)
								{
									Log.d("Debug", "team is null");
									query.whereEqualTo("user", ParseUser.getCurrentUser());
								}
								else
								{
									Log.d("Debug", "team is not null");
									query.whereEqualTo("team", team);
								}

								query.findInBackground(new FindCallback<ParseObject>()
								{
									@Override
									public void done(List<ParseObject> challengeList, ParseException e)
									{
										if(e == null)
										{
											challengeList.get(0).deleteInBackground(new DeleteCallback()
											{
												@Override
												public void done(ParseException e)
												{
													if(e == null)
													{
														final ParseUser user = ParseUser.getCurrentUser();
														user.fetchInBackground(new GetCallback<ParseObject>()
														{
															@Override
															public void done(ParseObject object, ParseException e)
															{
																if(e == null)
																{
																	ParseObject team = object.getParseObject("team");
																	NavigationUtils.displayMessage(me, "Challenge marked as incomplete",
																			"Challenge incomplete");

																	if(team == null)
																	{
																		updatePointsToUser(user, points);
																	}
																	else
																	{
																		updatePointsToTeam(team, points);
																	}
																}
																else
																{
																	NavigationUtils.displayMessage(me, e.getMessage(),
																			"Problem marking challenge incomplete");
																}
															}
														});
													}
													else
													{
														NavigationUtils.displayMessage(me, e.getMessage(), "Problem marking challenge incomplete");
													}

												}
											});
										}
										else
										{
											NavigationUtils.displayMessage(me, e.getMessage(), "Problem marking challenge incomplete");
										}
									}
								});
							}
							else
							{
								NavigationUtils.displayMessage(me, e.getMessage(), "Problem marking challenge incomplete");
							}
						}
					});
				}
				else
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Problem marking challenge incomplete");
				}
			}
		});

		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specific_challenge_list);

		String challengeCategory = getIntent().getExtras().getString(getResources().getString(R.string.challenge));

		getActionBar().setTitle(challengeCategory);
		setMainImage(challengeCategory);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		mEntries = new ArrayList<Challenge>();
		mAdapter = new SpecificChallengeListAdapter(me, mEntries);
		setChallengeList(getIntent().getExtras().getString(getResources().getString(R.string.challenge)));
	}

	private void promptUserIncomplete(final Challenge challenge)
	{

		Builder builder = new Builder(me);
		builder.setMessage(R.string.mark_challenge_incomplete_message).setTitle(R.string.mark_challenge_incomplete_title);

		builder.setNegativeButton(R.string.cancel, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				return;
			}
		});

		builder.setPositiveButton(R.string.yes, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				markChallengeIncomplete(challenge);
				return;
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
		return;
	}

	private void setChallengeList(String challengeCategory)
	{
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Category");
		query.whereEqualTo("name", challengeCategory);

		query.findInBackground(new FindCallback<ParseObject>()
		{
			@Override
			public void done(List<ParseObject> categoryList, ParseException e)
			{
				if(e != null)
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Category retrieval error");
				}
				else
				{
					if(categoryList.isEmpty())
					{
						return;
					}

					ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Challenge");
					query.whereEqualTo("category", categoryList.get(0));
					query.orderByAscending("createdAt");

					query.findInBackground(new FindCallback<ParseObject>()
					{
						@Override
						public void done(final List<ParseObject> challengeList, ParseException e)
						{
							if(e != null)
							{
								NavigationUtils.displayMessage(me, e.getMessage(), "Challenge query error");
							}
							else
							{
								final ParseUser user = ParseUser.getCurrentUser();
								user.fetchInBackground(new GetCallback<ParseObject>()
								{
									@Override
									public void done(ParseObject object, ParseException e)
									{
										if(e == null)
										{
											ParseObject team = object.getParseObject("team");

											ParseQuery<ParseObject> userCompleted = ParseQuery.getQuery("CompletedChallenge");
											userCompleted.whereEqualTo("user", ParseUser.getCurrentUser());
											List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();

											if(team != null)
											{
												ParseQuery<ParseObject> teamCompleted = ParseQuery.getQuery("CompletedChallenge");
												teamCompleted.whereEqualTo("team", ParseUser.getCurrentUser().getParseObject("team"));
												queries.add(teamCompleted);
											}

											queries.add(userCompleted);

											ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
											mainQuery.setLimit(200);

											mainQuery.findInBackground(new FindCallback<ParseObject>()
											{
												@Override
												public void done(List<ParseObject> completedChallengeList, ParseException e)
												{
													if(e == null)
													{
														for(int i = 0; i < challengeList.size(); i++)
														{
															final Challenge challenge = new Challenge();
															challenge.setDescription(challengeList.get(i).getString("description"));
															challenge.setHashTag(challengeList.get(i).getString("hashTag"));
															challenge.setPoints(challengeList.get(i).getInt("points"));

															challenge.setCompleted(findIfChallengeCompleted(challengeList.get(i),
																	completedChallengeList));

															mEntries.add(challenge);
															mAdapter.notifyDataSetChanged();
														}
													}
													else
													{
														NavigationUtils.displayMessage(me, e.getMessage(), "Challenge query error");
													}
												}

											});
										}
										else
										{
											NavigationUtils.displayMessage(me, e.getMessage(), "Challenge query error");
										}
									}
								});
							}
						}
					});
				}
			}
		});

		ListView challengeListView = (ListView) findViewById(R.id.specific_challenge_list);
		challengeListView.setAdapter(mAdapter);
		challengeListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				final Challenge challenge = ((Challenge) ((TextView) view.findViewById(R.id.specific_challenge)).getTag());

				if(challenge.isCompleted())
				{
					promptUserIncomplete(challenge);
					return;
				}

				Intent intent = new Intent(me, ChallengeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(getResources().getString(R.string.challenge), challenge.getDescription());
				bundle.putInt(getResources().getString(R.string.points_default), challenge.getPoints());
				bundle.putString(getResources().getString(R.string.required_hashtags), challenge.getHashTag());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void setMainImage(String challengeCategory)
	{
		ImageView image = (ImageView) findViewById(R.id.challenge_category_image);
		int id = -1;

		if(challengeCategory.equals("Adventure"))
		{
			id = R.drawable.adventure;
		}
		if(challengeCategory.equals("Camping"))
		{
			id = R.drawable.camping;
		}
		if(challengeCategory.equals("Community"))
		{
			id = R.drawable.community;
		}
		if(challengeCategory.equals("Hikes"))
		{
			id = R.drawable.hikes;
		}
		if(challengeCategory.equals("Quirky"))
		{
			id = R.drawable.quirky;
		}
		if(challengeCategory.equals("Service"))
		{
			id = R.drawable.service;
		}
		if(challengeCategory.equals("Social Media"))
		{
			id = R.drawable.socialmedia;
		}
		if(challengeCategory.equals("Survival"))
		{
			id = R.drawable.survival;
		}

		image.setImageDrawable(getResources().getDrawable(id));
	}

	private void updatePointsToTeam(ParseObject team, int points)
	{
		points = team.getInt("points") - points;
		team.put("points", points);
		team.saveInBackground();
	}

	private void updatePointsToUser(ParseUser user, int points)
	{
		points = Integer.parseInt(user.getString("points")) - points;
		user.put("points", points + "");
		user.saveInBackground();
	}
}
