
package com.cotopaxi.Cotopaxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends Activity
{
	public void createAccountPressed(View view)
	{
		startActivity(new Intent(this, CreateAccountActivity.class));
	}

	public void loginPressed(View view)
	{
		startActivity(new Intent(this, LoginActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
	}
}
