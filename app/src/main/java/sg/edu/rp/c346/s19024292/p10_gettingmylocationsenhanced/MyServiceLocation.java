package sg.edu.rp.c346.s19024292.p10_gettingmylocationsenhanced;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MyServiceLocation extends Service {
    boolean started;
    TextView tvLat, tvLong, tvLoc;
    FusedLocationProviderClient client;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    String folderLocation;
    GoogleMap map;
    LatLng latLng;
    Marker central;

    private DownloadBinder mBind = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService","startDownload executed");
        }
        public int getProgress() {
            Log.d("MyService","getProgress executed");
            return 0;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (started == false) {
            started = true;
            Log.d("MyService", "onStartCommand: Service Started");
        } else {
            Log.d("MyService","Service is still running");
        }

        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy() {
        Log.d("MyService","Service exited");
        super.onDestroy();
    }
}