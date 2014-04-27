
package com.cotopaxi.Cotopaxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GuideActivity extends Activity
{
	public void gotItPressed(View view)
	{
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		getActionBar().setTitle(R.string.guide);
	}
}
