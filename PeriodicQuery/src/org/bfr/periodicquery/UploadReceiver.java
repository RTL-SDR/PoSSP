package org.bfr.periodicquery;

import org.bfr.periodicquery.sdr.UploadService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UploadReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		context.startService(new Intent (context , UploadService.class));
		//Log.d("RECEIVER" , "FROM THE RECEIVER");

	}

}
