package org.bfr.periodicquery.sdr;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.bfr.periodicquery.sdr.dataModel.LocationUnit;
import org.bfr.periodicquery.sdr.db.*;

public class LocationService extends Service  implements
GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,
LocationListener,
Runnable  {
	
    public static final int LOCATION_REQUEST_INTERVAL = 1000;
    public static final int FASTEST_LOCATION_INTERVAL = 100;
    private GoogleApiClient gac;
    private LocationRequest lr;
    private Location location;
    private LocationDataSource lDSource;
	
    final Context context = this;
    Thread LocThread;

    @Override
    public void onCreate() {
    	Log.d("LocationService" , "location Service has been created");
        super.onCreate();
        LocationTrack();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopIt();
    //    LocThread.interrupt();
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



        public void stopIt(){
            lDSource.close();
            LocationServices.FusedLocationApi.removeLocationUpdates(gac, this);
        }

        public void LocationTrack(){

            gac = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            if (gac != null) {
                gac.connect();
            }
            //g
            createLocationRequestObject();
            //1
            lDSource = new LocationDataSource(this);
            lDSource.open();

        }
        private void createLocationRequestObject() {
            lr = new LocationRequest();
            lr.setInterval(LOCATION_REQUEST_INTERVAL);
            lr.setFastestInterval(FASTEST_LOCATION_INTERVAL);
            lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        @Override
        public void onConnected(Bundle bundle) {
            //f
            location = LocationServices.FusedLocationApi.getLastLocation(gac);

            if (location != null) {
                //2
                LocationUnit lu = new LocationUnit();
                lu.setTime(System.currentTimeMillis());
                lu.setLat(location.getLatitude());
                lu.setLon(location.getLongitude());
                //4
                if (lu != null) {
                    //3
                    lDSource.addRow(lu);
                }
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(gac, lr, this);
        }


        @Override
        public void onLocationChanged(Location location) {
            //i
            this.location = location;

            if (location != null) {
                //2
                LocationUnit lu = new LocationUnit();
                lu.setTime(System.currentTimeMillis());
                lu.setLat(location.getLatitude());
                lu.setLon(location.getLongitude());

                //4
                if (lu != null) {
                    //3
                    lDSource.addRow(lu);
                }
            }
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

		@Override
		public void run() {
			
		}

    }

