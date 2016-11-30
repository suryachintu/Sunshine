package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "ONSTOP" + " XXXXXXXXXXXXXXXXXXXXXXX");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "ONSTART" + " XXXXXXXXXXXXXXXXXXXXXXX");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "ONDESTROY" + " XXXXXXXXXXXXXXXXXXXXXXX");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "OnPause" + " XXXXXXXXXXXXXXXXXXXXXXX");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "ONResume" + " XXXXXXXXXXXXXXXXXXXXXXX");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e(LOG_TAG, "XXXXXXXXXXXXXXXX " + "ONcreate" + " XXXXXXXXXXXXXXXXXXXXXXX");
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_map) {
            openPreferredLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocation() {

        String location = Utility.getPreferredLocation(MainActivity.this);
        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("LOG_TAG", "Couldn't call " + location + ", no receiving apps installed!");
        }
    }
}

    /**
     * A placeholder fragment containing a simple view.
     */

