package com.example.midtronics_first_technical;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CountryLoaderRunnable implements Runnable {

    private final MainActivity mainActivity;
    private final String country;
    private static final String DATA_URL = "https://restcountries.com/v3.1/name/";

    CountryLoaderRunnable(MainActivity mainActivity, String s) {
        this.mainActivity = mainActivity;
        this.country = s;
    }


    @Override
    public void run() {

        Uri dataUri = Uri.parse(DATA_URL + country);
        String urlToUse = dataUri.toString();

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                handleResults(null);
                return;
            }
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }


        } catch (Exception e) {
            handleResults(null);
            return;
        }

        handleResults(sb.toString());
    }

    private void handleResults(String s) {

        if (s == null) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mainActivity.downloadFailed();
                }
            });
            return;
        }

        final Country countryObject = parseJSON(s, country);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (countryObject != null)
                mainActivity.startCountryActivity(countryObject);
            }
        });
    }

    private Country parseJSON(String s, String country) {
        Country countryObject = null;

        try {

            JSONArray content = new JSONArray(s);
            JSONObject contentMain = content.getJSONObject(0);

            JSONArray capitalArray = contentMain.getJSONArray("capital");
            String capital = capitalArray.getString(0);
            String region = contentMain.getString("region");
            String subregion = contentMain.getString("subregion");
            String area = contentMain.getString("area");
            String population = contentMain.getString("population");

            countryObject = new Country(country, capital, population, area, region, subregion);

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return countryObject;
    }


}
