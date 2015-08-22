/**
	Copyright 2014 [BFR]

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 **/
package org.bfr.periodicquery;

import org.bfr.periodicquery.sdr.LocationService;
import org.bfr.periodicquery.sdr.SdrSpectrumSensing;
import org.bfr.periodicquery.sdr.UploadService;
import org.bfr.querytools.logging.Logger;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PeriodicQueryService extends Service
{
	private MediaPlayer tonePlayer;


	@Override
	public void onCreate()
	{

		Intent iLoc = new Intent(getBaseContext(), LocationService.class);
		startService(iLoc);

		tonePlayer = MediaPlayer.create(this,R.raw.tone); 
		tonePlayer.start();

		java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
		java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "debug");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "debug");

		Logger.setLogger(new Logger() {

			@Override
			public void logMessage(String message)
			{
				Log.d("SpectrumQuery", message);
			}

		});

		super.onCreate();
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
	
		new Handler().postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	SdrSpectrumSensing.sense();
		    	Toast.makeText(getBaseContext() , "I'm sensing  .... "  , Toast.LENGTH_SHORT).show();
		    }
		},1000);
		
		new Handler().postDelayed(new Runnable() {
		    @Override
		    public void run() {
		    	tonePlayer.stop();
		    	
		    	Intent uploadIntent = new Intent(getBaseContext(), UploadService.class);
				startService(uploadIntent);

				Intent iLoc = new Intent(getBaseContext(), LocationService.class);
				stopService(iLoc);
				
				stopSelf();
		    }
		},5000);
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy()
	{
	//	Toast.makeText(getBaseContext() , "PeriodicQueryService is Destroyed "  , Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}




}

