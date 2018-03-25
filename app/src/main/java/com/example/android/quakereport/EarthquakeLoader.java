package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Created by harshit on 4/2/18.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeClass>> {

    private String urls;
    private String LOG_TAG = EarthquakeLoader.class.getName();
    public EarthquakeLoader(Context context,String url) {
        super(context);
        urls = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<EarthquakeClass> loadInBackground() {
        ArrayList<EarthquakeClass> list = QueryUtil.extractData(urls);
        return list;
    }
}
