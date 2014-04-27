
package com.cotopaxi.Cotopaxi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;

public abstract class BaseActivity extends Activity
{
	public void goForTheGoodPressed(View view)
	{
		NavigationUtils.visitWebsite(this, Uri.parse(getResources().getString(R.string.cotopaxi_url)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_guide:
		{
			startActivity(new Intent(this, GuideActivity.class));
			return true;
		}
		case R.id.action_website:
		{
			NavigationUtils.visitWebsite(this, Uri.parse(getResources().getString(R.string.cotopaxi_url)));

			return true;
		}
		case R.id.action_sign_out:
		{
			ParseUser.logOut();

			Intent intent = new Intent(this, WelcomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();

			return true;
		}
		case R.id.action_email:
		{
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {getResources().getString(R.string.action_email)});
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cotopaxi Micro-Adventure Question");

			startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			return true;
		}
		case R.id.action_phone:
		{
			Uri number = Uri.parse("tel:" + getResources().getString(R.string.action_phone));
			Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

			startActivity(Intent.createChooser(callIntent, "Make call..."));
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
