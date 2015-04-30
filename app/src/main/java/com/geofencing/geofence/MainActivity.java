package com.geofencing.geofence;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    //DEBUGGING
    private static final String TAG = "GEOFENCING";
    //////////

    // Declaring a Location Manager
    protected LocationManager mLocationManager;
    // The minimum time between updates
    private static final long LOCATION_REFRESH_TIME = 1; // 1 minute
    // The minimum distance to refresh
    private static final long LOCATION_REFRESH_DISTANCE = 1; // 10 meters

    // Current Location
    private Location mLocation;
    // Current Center of GeoFence
    private Location centerOfFence;
    // Current radius of fence
    private float radiusOfFence;

    // Location Listener
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLocation = location;
            TextView currentLocationTextView = (TextView) findViewById(R.id.currentLocationTextView);
            currentLocationTextView.setText(location.getLatitude() + " " + location.getLongitude());

            // Checks to see if out of range of fence
            withinFence();
        }

        @Override
        public void onProviderDisabled(String s){

        }
        @Override
        public void onProviderEnabled(String s){

        }
        @Override
        public void onStatusChanged(String s, int n,Bundle bundle){

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Populates Radius Spinner
        Spinner spinner = (Spinner) findViewById(R.id.radiusSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.radius_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Initilaizes Android Location Manager
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
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

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        radiusOfFence = (float) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    // onClick listener for setLocation Button
    // Sets the current center of GeoFence to the current location
    public void setLocationButtonClick(View view){
        centerOfFence = new Location(mLocation);
        Log.v(TAG, "SET CENTER OF FENCE!");
    }

    // Checks if user is still within the fence depending on fence range selected
    private void withinFence(){
        if(mLocation != null && centerOfFence != null) {
            if (mLocation.distanceTo(centerOfFence) > radiusOfFence) {
                TextView currentLocationTextView = (TextView) findViewById(R.id.currentLocationTextView);
                currentLocationTextView.setText("!!EXITED FENCE!!");
            }
        }
    }
}
