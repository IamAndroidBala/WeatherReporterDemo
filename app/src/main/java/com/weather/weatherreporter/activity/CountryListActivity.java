package com.weather.weatherreporter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.weather.weatherreporter.R;
import com.weather.weatherreporter.adapters.CountriesAdapter;
import com.weather.weatherreporter.models.CountryModel;
import com.weather.weatherreporter.utils.AppLog;
import com.weather.weatherreporter.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity of the app
 * Reading the data from country_data.json from assets folder
 * Read the country info , add it into array list
 * Populate  data into listview using adapter
 */
public class CountryListActivity extends AppCompatActivity {
    /**
     * define the view and variable
     */
    TextView tv_empty;
    RecyclerView recyler_countries;
    List<CountryModel> countryList = new ArrayList<CountryModel>();
    CountriesAdapter countriesAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar    = (Toolbar)findViewById(R.id.toolbar_weather);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.city_list));

        TextView tv_cntry = (TextView) findViewById(R.id.tv_toolbar_heading);
        tv_cntry.setText(getResources().getString(R.string.country_list));

        tv_empty            =   (TextView)     findViewById(R.id.tv_country_empty);
        recyler_countries   =   (RecyclerView) findViewById(R.id.recyler_countries);

        /**
         * Set gridlayout manager to recylerviw
         * 1 is the span count for display one data in one row
         */
        recyler_countries.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(CountryListActivity.this, 1);
        recyler_countries.setLayoutManager(mLayoutManager);

        new ReadJSON().execute();

    }

    /**
     * Read country_data.json from assets  through loadJSONFromAsset()  and load countries info into adapter
     * set adapter to listview
     * countries json array contains info of all country
     */

    private class ReadJSON extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = CommonMethods.getCommonMethods().showProgress(CountryListActivity.this,getResources().getString(R.string.pls_wait));
        }

        @Override
        protected String doInBackground(String... strings) {
            return loadJSONFromAsset(getApplicationContext());
        }

        @Override
        protected void onPostExecute(String thisJson) {
            super.onPostExecute(thisJson);

            try {
                CommonMethods.getCommonMethods().closeProgrss(progressDialog);
                JSONObject obj = new JSONObject(thisJson);
                for (int s=0;s<obj.length();s++){
                    JSONArray m_jArry = obj.getJSONArray("countries");

                    for(int n = 0; n < m_jArry.length(); n++) {

                        JSONObject object = m_jArry.getJSONObject(n);

                        CountryModel countryModel = new CountryModel();

                        if (!object.isNull("country_id")) {
                            countryModel.setCountry_id(object.getInt("country_id"));
                        }

                        if (!object.isNull("country_name")) {
                            countryModel.setCountry_name(object.getString("country_name"));
                        }

                        if (!object.isNull("flag_image_url")) {
                            countryModel.setCountry_flag(object.getString("flag_image_url"));
                        }

                        if (!object.isNull("cities")) {
                            countryModel.setCountry_cities(object.getString("cities"));
                        }

                        countryList.add(countryModel);

                    }
                }

                displayData(countryList);

            }catch (Exception e){
                AppLog.d(" Error====>>>> " + e);
            }

        }

    }

    /**
     * create adapter and set to listview
     * adapter uses thisList values
     * @param thisList
     */

    private void displayData(List<CountryModel> thisList){

        countriesAdapter   =   new CountriesAdapter(CountryListActivity.this, thisList);
        recyler_countries.setAdapter(countriesAdapter);

        if(thisList!=null && thisList.isEmpty()){
            tv_empty.setVisibility(View.VISIBLE);
            recyler_countries.setVisibility(View.GONE);
        }else {
            tv_empty.setVisibility(View.GONE);
            recyler_countries.setVisibility(View.VISIBLE);
        }

    }

    /**
     * used to read the country_data.json fro assets folder of this app
     * this will return the string value
     * @param context
     * @return
     */

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("country_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
           AppLog.d("Error====>>>> " + ex);
        }

        return json;
    }

}
