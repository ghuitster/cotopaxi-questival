
package com.cotopaxi.Cotopaxi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class CreateAccountActivity extends Activity
{
	private class MyTextWatcher implements TextWatcher
	{
		@Override
		public void afterTextChanged(Editable s)
		{
			if(!((TextView) findViewById(R.id.password_entry)).getText().toString().isEmpty()
					&& ((TextView) findViewById(R.id.password_entry)).getText().toString()
							.equals(((TextView) findViewById(R.id.confirm_password_entry)).getText().toString()))
			{
				((ImageView) findViewById(R.id.password_checkbox_image)).setVisibility(View.VISIBLE);
			}
			else
			{
				((ImageView) findViewById(R.id.password_checkbox_image)).setVisibility(View.INVISIBLE);
			}

			((Button) findViewById(R.id.continue_button))
					.setEnabled(((ImageView) findViewById(R.id.password_checkbox_image)).getVisibility() == View.VISIBLE);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{}
	}

	private final Context me = this;

	public void continuePressed(View view)
	{
		final ParseUser user = new ParseUser();
		final String email = ((EditText) findViewById(R.id.email_address_entry)).getText().toString();
		final String password = ((EditText) findViewById(R.id.password_entry)).getText().toString();

		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);

		ParseQuery<ParseUser> query = ParseUser.getQuery();

		try
		{
			query.whereEqualTo("username", email);
			List<ParseUser> users;
			users = query.find();

			if(users.size() > 0)
			{
				loginAndUpdatePassword(email, password);
			}
			else
			{
				signUpUser(user);
			}
		}
		catch(ParseException e)
		{
			NavigationUtils.displayMessage(me, "Sign up error", e.getMessage());
		}
	}

	private void loginAndUpdatePassword(String email, final String password)
	{
		try
		{
			ParseUser.logIn(email, getResources().getString(R.string.temp_password));
			ParseUser currentUser = ParseUser.getCurrentUser();
			currentUser.setPassword(password);
			currentUser.saveInBackground();

			Intent intent = new Intent(me, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
		catch(ParseException e)
		{
			NavigationUtils.displayMessage(me, getResources().getString(R.string.registration_error_content),
					getResources().getString(R.string.registration_error_title));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		getActionBar().setTitle(R.string.create_account);

		MyTextWatcher watcher = new MyTextWatcher();
		TextView password = (TextView) findViewById(R.id.password_entry);
		password.addTextChangedListener(watcher);
		TextView passwordConfirm = (TextView) findViewById(R.id.confirm_password_entry);
		passwordConfirm.addTextChangedListener(watcher);
	}

	private void signUpUser(final ParseUser user)
	{
		try
		{
			user.signUp();
		}
		catch(ParseException e)
		{
			NavigationUtils.displayMessage(me, "Sign up error", e.getMessage());
		}

		Intent intent = new Intent(me, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}
}
