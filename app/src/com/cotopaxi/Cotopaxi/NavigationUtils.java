
package com.cotopaxi.Cotopaxi;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;

public abstract class NavigationUtils
{
	public static void displayMessage(Context context, String message, String title)
	{
		Builder builder = new Builder(context);
		builder.setMessage(message).setTitle(title);

		builder.setPositiveButton(R.string.ok, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int id)
			{
				return;
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public static void visitWebsite(Context context, Uri site)
	{
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, site);
		context.startActivity(Intent.createChooser(browserIntent, "View website..."));
	}
}
