package org.bfr.periodicquery.sdr;


import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bfr.periodicquery.sdr.dataModel.LocationUnit;
import org.bfr.periodicquery.sdr.db.LocationDataSource;

public class UploadService extends IntentService {

    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("UploadService" , "From onHandleIntent");
        String path = "/mnt/sdcard/airband.txt";

        //TODO extract the last known location
        LocationDataSource lDSource =  new LocationDataSource(this);
        lDSource.open();
        
        
        LocationUnit lu = lDSource.findFirstRow();

        // If external storage is not available do nothing return
        if(!checkExternalStorage()){
            Log.d("UploadService" , "External storage is not available!!!!");
            return;
        }
        // Append to a file
        appendToFile(path , lu);
        //Uploading the file to the server
        Intent SIntent =  new Intent(this, UploadToServer.class);
        startService(SIntent);
    }

    private void appendToFile(String path,  LocationUnit lu)  {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
            String location = lu.getLat()+","+lu.getLon();
            out.println(location);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }


    }

    public boolean checkExternalStorage(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;

    }

    @Override
    public void onDestroy() {
        Toast.makeText(getBaseContext() , "Service is Destroyed "  , Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}