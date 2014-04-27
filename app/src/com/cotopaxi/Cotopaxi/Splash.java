
package com.cotopaxi.Cotopaxi;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.parse.ParseUser;

public class Splash extends Activity
{
	private static class MyHandler extends Handler
	{
		private final WeakReference<Splash> mTarget; // Magic line that prevents memory leaks

		private MyHandler(Splash mTarget)
		{
			this.mTarget = new WeakReference<Splash>(mTarget);
		}

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case MSG_CONTINUE: // Called if there are no other messages called
				Splash target = mTarget.get();

				if(target != null)
				{
					target.startMain();
				}

				break;
			}
		}
	}

	private final static int MSG_CONTINUE = 1234; // A magic number
	private final static long DELAY = 1000; // The delay before starting main in milliseconds

	private final MyHandler mHandler = new MyHandler(this);

	@Override
	protected void onCreate(Bundle args)
	{
		super.onCreate(args);
		setContentView(R.layout.activity_splash);

		// This makes it so that if the user presses back during the delay they leave the app
		mHandler.sendEmptyMessageDelayed(MSG_CONTINUE, DELAY);
	}

	@Override
	protected void onDestroy()
	{
		// Because MSG_CONTINUE is a magic message we don't want to pass that on to be handled later
		mHandler.removeMessages(MSG_CONTINUE);
		super.onDestroy();
	}

	private void startMain()
	{
		if(ParseUser.getCurrentUser() == null)
		{
			startActivity(new Intent(this, WelcomeActivity.class));
		}
		else
		{
			startActivity(new Intent(this, HomeActivity.class));
		}

		finish();
	}
}
