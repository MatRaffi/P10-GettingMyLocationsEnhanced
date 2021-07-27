package sg.edu.rp.c346.s19024292.p10_gettingmylocationsenhanced;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.FileWriter;

public class MyServiceLocation extends Service {
    LocationRequest mLocationRequest = LocationRequest.create();
    LocationCallback mLocationCallback;
    FusedLocationProviderClient client;

    @Override
    public void onCreate() {
        Log.d("MyService", "Service created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "Service started");
            client = LocationServices.getFusedLocationProviderClient(MyServiceLocation.this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location data = locationResult.getLastLocation();
                    double lat = data.getLatitude();
                    double lng = data.getLongitude();
                    Toast.makeText(getApplicationContext(), lat + ", " + lng, Toast.LENGTH_SHORT).show();

                    // Folder creation
                    String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                    File folder = new File(folderLocation_I);
                    if (folder.exists() == false) {
                        boolean result = folder.mkdir();
                        if (result == true) {
                            Log.d("File Read/Write", "Folder created");
                        }
                    }

                    // File creation and writing
                    try {
                        File targetFile_I = new File(folderLocation_I, "P10LocationData2.txt");
                        FileWriter writer_I = new FileWriter(targetFile_I, true);
                        writer_I.write(lat + ", " + lng + "\n");
                        writer_I.flush();
                        writer_I.close();
                    } catch (Exception e) {
                        Toast.makeText(MyServiceLocation.this, "Failed to write!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        };
        if (checkPermission() == true) {

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(30);
            mLocationRequest.setFastestInterval(100);
            mLocationRequest.setSmallestDisplacement(500);
            client.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy () {
        Log.d("MyService", "Service exited");
        client.removeLocationUpdates(mLocationCallback);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Step 3b
    private boolean checkPermission() {
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(MyServiceLocation.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}