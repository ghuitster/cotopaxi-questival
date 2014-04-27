
package com.cotopaxi.Cotopaxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends Activity
{
	private final Context me = this;

	public void forgotPasswordPressed(View view)
	{
		final String email = ((EditText) findViewById(R.id.email_address_entry)).getText().toString();

		if(email.isEmpty())
		{
			NavigationUtils.displayMessage(me, getResources().getString(R.string.empty_email_message),
					getResources().getString(R.string.empty_email_title));

			return;
		}

		Builder builder = new Builder(this);
		builder.setMessage(getResources().getString(R.string.request_password_content) + " " + email).setTitle(R.string.empty_email_title);

		builder.setPositiveButton(R.string.ok, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				try
				{
					ParseUser.requestPasswordReset(email);

					Builder builder = new Builder(me);
					builder.setTitle(R.string.password_reset_confirm);

					builder.setPositiveButton(R.string.ok, new OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int id)
						{}
					});

					AlertDialog innerDialog = builder.create();
					innerDialog.show();
				}
				catch(ParseException e)
				{
					NavigationUtils.displayMessage(me, e.getMessage(), "Password reset error");
				}

				return;
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void loginPressed(View view)
	{
		final String email = ((EditText) findViewById(R.id.email_address_entry)).getText().toString();
		final String password = ((EditText) findViewById(R.id.password_entry)).getText().toString();

		loginUser(email, password);
	}

	private void loginUser(String email, String password)
	{
		try
		{
			ParseUser.logIn(email, password);

			Intent intent = new Intent(me, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
		catch(ParseException e)
		{
			NavigationUtils.displayMessage(me, e.getMessage(), "Log in error");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setTitle(R.string.login);
	}
}
