
package com.cotopaxi.Cotopaxi;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ChallengeCategoryListActivity extends ActivityWithFooter
{
	private final Activity me = this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_list);

		ArrayList<String> entries = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.challenge_categories)));

		ListView challengeList = (ListView) findViewById(R.id.challenge_category_list);
		challengeList.setAdapter(new ChallengeCategoryListAdapter(this, entries));
		challengeList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(me, SpecificChallengeListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(getResources().getString(R.string.challenge), ((TextView) view.findViewById(R.id.challenge)).getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		getActionBar().setTitle(R.string.challenges);
	}
}
