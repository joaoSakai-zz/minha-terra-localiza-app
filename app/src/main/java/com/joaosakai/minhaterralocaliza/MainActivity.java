package com.joaosakai.minhaterralocaliza;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private LocationManager locationManager;
    private String latitude, longitude, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(
                        view,
                        getMessage(),
                        Snackbar.LENGTH_INDEFINITE
                ).setAction("Action", null).show();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }

        Listener listener = new Listener();

        final long refreshTime = 0;
        final float distance = 0;

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                refreshTime, distance, listener);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                refreshTime, distance, listener);


    }

    private String getMessage() {
        if(latitude == null & longitude == null) {
            return message == null ? "Gerando informações de localização..." : message;
        } else {
            return  "Lat: " + latitude + "/" + "Long: :" + longitude + "\n";
        }
    }

    private class Listener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            String latitudeStr = String.valueOf(location.getLatitude());
            String longitudeStr = String.valueOf(location.getLongitude());
            latitude = latitudeStr;
            longitude = longitudeStr;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    message = "O aplicativo necessita de informações de localização";
                }
                return;
            }
        }
    }
}
