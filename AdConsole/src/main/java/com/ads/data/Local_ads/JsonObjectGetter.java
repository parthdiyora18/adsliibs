package com.ads.data.Local_ads;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonObjectGetter extends AsyncTask<Void, Void, Appdetail[]> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private String jsonUrl;
    private JsonObjectGetListener listener;

    private Exception exception;

    public JsonObjectGetter(Context context, String jsonUrl, JsonObjectGetListener listener) {
        this.context = context;
        this.jsonUrl = jsonUrl;
        this.listener = listener;
    }

    /**
     * @noinspection deprecation
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (context == null || listener == null || jsonUrl == null) {
            Log.d(TAG, "onPreExecute: context == null || listener == null || jsonUrl == null");
            cancel(true);
        } else if (!isNetworkAvailable(context)) {
            listener.onError("Please check your network connection");
            cancel(true);
        } else if (jsonUrl.isEmpty()) {
            listener.onError("Please provide a valid JSON URL");
            cancel(true);
        }
    }

    /**
     * @noinspection deprecation
     */
    @Override
    protected Appdetail[] doInBackground(Void... voids) {
        try {
            URL url = new URL(jsonUrl);
            InputStream is = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = bufferedReader.read()) != -1) {
                sb.append((char) cp);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            Appdetail[] appdetail = new Appdetail[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                appdetail[i] = new Appdetail(
                        object.getString("app_name"),
                        object.getString("app_banner"),
                        object.getString("app_icon"),
                        object.getString("app_body"),
                        object.getString("pakage")
                );
            }
            return appdetail;
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    /**
     * @noinspection deprecation
     */
    @Override
    protected void onPostExecute(Appdetail[] appdetails) {
        super.onPostExecute(appdetails);
        if (listener != null && appdetails != null) {
            listener.onSuccess(appdetails);
        } else {
            listener.onError(exception.toString());
        }
    }

    private boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}