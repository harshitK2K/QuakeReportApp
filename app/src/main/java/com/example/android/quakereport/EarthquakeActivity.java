/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


import static java.security.AccessController.getContext;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks< ArrayList<EarthquakeClass> > {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    EarthquakeAdapter adapter;
    ListView listView;
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        adapter = new EarthquakeAdapter(this,new ArrayList<EarthquakeClass>());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                EarthquakeClass object = adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(object.getUrl());
                Intent web = new Intent(Intent.ACTION_VIEW,earthquakeUri);
                startActivity(web);

            }
        });
        if(isNetworkConnected()) {
            getLoaderManager().initLoader(1, null, this);
            listView.setEmptyView(findViewById(R.id.Text_view));
        }
        else{
            View ProgressBar = findViewById(R.id.progress_bar);
            ProgressBar.setVisibility(View.GONE);
            TextView tx = (TextView) findViewById(R.id.Text_view);
            tx.setText("No Internet Connection");
        }


        /**/
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public Loader<ArrayList<EarthquakeClass>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeClass>> loader, ArrayList<EarthquakeClass> earthquakes) {
        View ProgressBar = findViewById(R.id.progress_bar);
        ProgressBar.setVisibility(View.GONE);
        if(earthquakes != null && !earthquakes.isEmpty())
            adapter.addAll(earthquakes);
        TextView tx = (TextView) findViewById(R.id.Text_view);
        tx.setText(R.string.No_data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeClass>> loader) {
        adapter.clear();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
