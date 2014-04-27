
package com.cotopaxi.Cotopaxi;

import java.util.Random;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.widget.TextView;

public class ChallengeCompletedActivity extends ActivityWithFooter
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_completed);
		getActionBar().setTitle(R.string.challenge);
		Bundle bundle = getIntent().getExtras();
		String points = bundle.getString(getResources().getString(R.string.points_awarded));
		((TextView) findViewById(R.id.number_points_awarded)).setText(points);

		String[] messages = getResources().getStringArray(R.array.challenge_complete_messages);
		String message = messages[new Random().nextInt(messages.length)];
		((TextView) findViewById(R.id.challenge_complete_message)).setText(message);

		MediaPlayer mp = MediaPlayer.create(this, R.raw.llama_sound);
		mp.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				mp.reset();
				mp.release();
				mp = null;
			}
		});
		mp.start();
	}
}
