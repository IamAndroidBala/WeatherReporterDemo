package com.weather.weatherreporter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.weather.weatherreporter.R;
import com.weather.weatherreporter.adapters.CityAdapter;
import com.weather.weatherreporter.models.CityModel;
import com.weather.weatherreporter.utils.AppLog;

import org.json.JSONArray;
import org.json.JSONObject;
//
import java.util.ArrayList;
import java.util.List;

/**
 * this  activity used to display all the cities of a country
 * recived data from country adapter
 */
public class CityListActivity extends AppCompatActivity {

    String strcitylist=null;
    List<CityModel> cityList = new ArrayList<CityModel>();
    CityAdapter cityAdapter;
    RecyclerView recyclerCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        /**
         * enable home button and set  heading
         */

        Toolbar toolbar    = (Toolbar)findViewById(R.id.toolbar_weather);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.city_list));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);

        TextView tv_city = (TextView) findViewById(R.id.tv_toolbar_heading);
        tv_city.setText(getResources().getString(R.string.city_list));

        recyclerCity    =   (RecyclerView) findViewById(R.id.recyler_cities);

        /**
         * set grid layout manager to recycler and span count is 1
         */
        recyclerCity.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(CityListActivity.this, 1);
        recyclerCity.setLayoutManager(mLayoutManager);

        /**
         * get the values from intent which sent from countries adapter
         */
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            strcitylist = bundle.getString("Cities");
        }

        displayData(strcitylist);
    }

    /**
     * convert string into json array and ready all json obj
     * get all values from json obj then add into array list
     * create adapter and pass arralist values and set this adapter to listview
     * @param strcitylist
     */
    private void displayData(String strcitylist){

        try {
            if(TextUtils.isEmpty(strcitylist)){
                return;
            }

            JSONArray obj = new JSONArray(strcitylist);
            for (int s = 0; s < obj.length(); s++) {
                JSONObject jsonObject = obj.getJSONObject(s);

                CityModel city = new CityModel();
                city.setCity_id(jsonObject.getInt("city_id"));
                city.setCity_name(jsonObject.getString("city_name"));
                city.setWeather(jsonObject.getString("weather"));
                city.setActive(jsonObject.getBoolean("active_flag"));
                city.setSugguested(jsonObject.getString("suggested_cities"));

                cityList.add(city);

            }

        }catch (Exception e){
            AppLog.d("CITY ==== >>>> " + e);
        }

        cityAdapter   =   new CityAdapter(CityListActivity.this, cityList);
        recyclerCity.setAdapter(cityAdapter);

    }

    /**
     * handle the back in toolbar home button
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

}
