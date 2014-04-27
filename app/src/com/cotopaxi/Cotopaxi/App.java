
package com.cotopaxi.Cotopaxi;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class App extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Parse.initialize(this, getResources().getString(R.string.parse_application_id), getResources().getString(R.string.parse_client_key));

		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
		PushService.setDefaultPushCallback(this, HomeActivity.class);
		ParseInstallation.getCurrentInstallation().getInstallationId();
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
}
